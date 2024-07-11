package com.wak.transactionmsg.message.retry;

import com.wak.transactionmsg.entities.MsgPO;

/**
 * @author wuankang
 * @date 2024/7/1 14:18
 * @Description TODO MQ投递失败重试
 * @Version 1.0
 */
public interface MqSendRetry {
    //获取重试信息
    MqSendRetryResult getMqSendRetryResult(MsgPO msgPO);
}
