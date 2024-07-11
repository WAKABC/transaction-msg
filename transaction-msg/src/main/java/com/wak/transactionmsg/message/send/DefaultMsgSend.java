package com.wak.transactionmsg.message.send;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.json.JSONUtil;
import com.wak.transactionmsg.common.MsgStatusEnum;
import com.wak.transactionmsg.entities.MsgPO;
import com.wak.transactionmsg.message.retry.MqSendRetry;
import com.wak.transactionmsg.message.retry.MqSendRetryResult;
import com.wak.transactionmsg.service.IMsgService;
import com.wak.transactionmsg.utils.CollectionUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wuankang
 * @date 2024/7/1 10:31
 * @Description TODO 消息发送类
 * @Version 1.0
 */
@Slf4j
@Component
public class DefaultMsgSend implements IMsgSend {
    @Resource
    private IMsgService msgServiceImpl;
    @Resource
    private MqSendRetry defaultMqSendRetry;
    @Resource
    private ThreadPoolTaskExecutor mqExecutor;

    @Override
    public void batchSend(List<Object> msgList) {
        //转为json格式
        List<String> jsonMsgList = CollectionUtil.convertList(msgList, JSONUtil::toJsonStr);
        //是否存在事务
        if (TransactionSynchronizationManager.isSynchronizationActive() && TransactionSynchronizationManager.isActualTransactionActive()) {
            //存在事务就入库，后投递MQ
            List<MsgPO> msgPOS = this.msgServiceImpl.batchInsert(jsonMsgList);
            //注册事务同步器TransactionSynchronization对象，事务提交后调用
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCompletion(int status) {
                    //异步投递MQ
                    mqExecutor.execute(() -> transactionAfter(msgPOS));
                }
            });
        }
    }

    /**
     * 重试发送
     *
     * @param msg 消息
     */
    @Override
    public void retrySend(MsgPO msg) {
        this.sendMsg(msg);
    }

    /**
     * 事务执行成功后立即执行投递方法
     *
     * @param msgPOS 消息pos
     */
    private void transactionAfter(List<MsgPO> msgPOS) {
        //判断事务是否成功，如果成功投递mq，如果失败打印日志
        MsgPO msgPO = msgServiceImpl.getById(msgPOS.get(0).getId());
        if (ObjUtil.isNotEmpty(msgPO)) {
            //循环遍历投递
            for (MsgPO msg : msgPOS) {
                sendMsg(msg);
            }
            log.info("消息投递完成！");
        } else {
            log.info("事务执行失败，无消息需要投递！");
        }
    }

    /**
     * 发送消息
     *
     * @param msgPO 消息
     */
    private void sendMsg(MsgPO msgPO) {
        try {
            //消息投递
            send2MQ(msgPO.getBodyJson());
            //投递成功修改消息表中的状态
            msgServiceImpl.updateStatusSuccess(msgPO);
        } catch (Exception e) {
            //异常处理，判断是否需要重试
            MqSendRetryResult result = defaultMqSendRetry.getMqSendRetryResult(msgPO);
            String failMsg = ExceptionUtil.stacktraceToString(e);
            //是否重试
            boolean retry = result.isRetry();
            //下次重试时间
            LocalDateTime nextRetryTime = result.getNextRetryTime();
            //更改消息表中的状态
            msgPO.setStatus(MsgStatusEnum.FAIL.getCode());
            //更新数据
            this.msgServiceImpl.updateStatusFail(msgPO, failMsg, retry, nextRetryTime);
            //如果无需重试或者超过最大次数，进行人工干预，或者发送邮件...
            if (!retry) {
                //TODO 发送短信、邮箱、钉钉...
            }
        }
    }


    /**
     * send to mq
     *
     * @param msg 消息
     */
    private void send2MQ(String msg) {
        log.info("开始投递消息到MQ。。。。");
        log.info("消息体：{}", msg);
//        rocketMQTemplate.convertAndSend(msg);
        /*故意使坏抛出异常*/
        if (msg.contains("error")){
            throw new RuntimeException("模拟投递失败的情况，故意失败：" + msg);
        }
        log.info("消息投递成功！");
    }


}
