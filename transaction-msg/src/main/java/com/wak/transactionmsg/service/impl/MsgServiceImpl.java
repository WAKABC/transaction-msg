package com.wak.transactionmsg.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wak.transactionmsg.common.MsgStatusEnum;
import com.wak.transactionmsg.entities.MsgPO;
import com.wak.transactionmsg.service.IMsgService;
import com.wak.transactionmsg.mapper.MsgPOMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wuankang
 * @description 针对表【t_msg(本地消息表)】的数据库操作Service实现
 * @createDate 2024-06-30 18:00:09
 */
@Service
public class MsgServiceImpl extends ServiceImpl<MsgPOMapper, MsgPO>
        implements IMsgService {

    /**
     * 批量插入
     *
     * @param msgBodyJsonList 消息身体json列表
     * @return {@code List<MsgPO> }
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public List<MsgPO> batchInsert(List<String> msgBodyJsonList) {
        ArrayList<MsgPO> msgPOS = new ArrayList<>(msgBodyJsonList.size());
        for (String msg : msgBodyJsonList) {
            MsgPO msgPO = new MsgPO();
            msgPO.setId(IdUtil.simpleUUID());
            msgPO.setStatus(0);
            msgPO.setBodyJson(msg);
            msgPO.setFailMsg(null);
            msgPO.setFailCount(0);
            msgPO.setSendRetry(0);
            msgPO.setCreateTime(LocalDateTime.now());
            msgPOS.add(msgPO);
        }
        this.saveBatch(msgPOS);
        return msgPOS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NOT_SUPPORTED)
    public void updateStatusSuccess(MsgPO msgPO) {
        LambdaUpdateWrapper<MsgPO> updateWrapper = Wrappers.lambdaUpdate(MsgPO.class);
        updateWrapper.eq(MsgPO::getId, msgPO.getId())
                .set(MsgPO::getStatus, MsgStatusEnum.SUCCESS.getCode())
                .set(MsgPO::getUpdateTime, LocalDateTime.now());
        this.update(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NOT_SUPPORTED)
    public void updateStatusFail(MsgPO msgPO, String reason, boolean retry, LocalDateTime nextRetryTime) {
        LambdaUpdateWrapper<MsgPO> updateWrapper = Wrappers.lambdaUpdate(MsgPO.class);
        updateWrapper.eq(MsgPO::getId, msgPO.getId())
                .ne(MsgPO::getStatus, MsgStatusEnum.SUCCESS.getCode())
                .set(MsgPO::getStatus, MsgStatusEnum.FAIL.getCode())
                .set(MsgPO::getFailMsg, reason)
                .set(MsgPO::getSendRetry, retry ? 1 : 0)
                .set(MsgPO::getNextRetryTime, nextRetryTime)
                .set(MsgPO::getUpdateTime, LocalDateTime.now())
                .setSql("fail_count = fail_count + 1");
        this.update(updateWrapper);
    }
}




