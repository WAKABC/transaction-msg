package com.wak.transactionmsg.message.retry;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wuankang
 * @date 2024/7/1 14:19
 * @Description TODO MQ重试结果
 * @Version 1.0
 */
@Data
public class MqSendRetryResult {
    /**
     * 重试
     */
    private boolean retry = false;
    /**
     * 下次重试时间
     */
    private LocalDateTime nextRetryTime;
}
