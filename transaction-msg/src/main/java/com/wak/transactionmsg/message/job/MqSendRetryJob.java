package com.wak.transactionmsg.message.job;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wak.transactionmsg.common.MsgStatusEnum;
import com.wak.transactionmsg.entities.MsgPO;
import com.wak.transactionmsg.message.send.IMsgSend;
import com.wak.transactionmsg.service.IMsgService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


/**
 * @author wuankang
 * @date 2024/7/1 15:53
 * @Description TODO
 * @Version 1.0
 */
@Slf4j
@Component
public class MqSendRetryJob implements DisposableBean {
    @Resource
    private IMsgService msgServiceImpl;
    @Resource
    private IMsgSend defaultMsgSend;

    private volatile boolean stop;

    @Scheduled(fixedDelay = 10 * 1000)
    public void sendRetry() {
        while (true) {
            //获取需要重新投递的消息
            LambdaQueryWrapper<MsgPO> queryWrapper = Wrappers.lambdaQuery(MsgPO.class);
            LambdaQueryWrapper<MsgPO> wrapper = queryWrapper.eq(MsgPO::getStatus, MsgStatusEnum.WAIT.getCode())
                    .or(lq -> {
                        lq.eq(MsgPO::getStatus, MsgStatusEnum.FAIL.getCode())
                                .eq(MsgPO::getSendRetry, 1)
                                .le(MsgPO::getNextRetryTime, LocalDateTime.now());
                    });
            //分页查询
            Page<MsgPO> page = new Page<>();
            page.setCurrent(1);
            page.setSize(100);
            this.msgServiceImpl.page(page, wrapper);
            //当前服务器停止
            if (page.getRecords().isEmpty() || stop) {
                break;
            }
            //有数据就重新投递
            for (MsgPO msgPO : page.getRecords()) {
                this.defaultMsgSend.retrySend(msgPO);
            }
        }
    }

    @Override
    public void destroy() throws Exception {
        this.stop = true;
    }
}
