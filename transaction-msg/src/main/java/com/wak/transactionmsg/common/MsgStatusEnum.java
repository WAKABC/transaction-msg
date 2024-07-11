package com.wak.transactionmsg.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wuankang
 * @date 2024/6/30 17:51
 * @Description TODO
 * @Version 1.0
 */
@AllArgsConstructor
@Getter
public enum MsgStatusEnum {
    /**
     * 等待
     */
    WAIT(0, "待投递"),
    /**
     * 成功
     */
    SUCCESS(1, "投递成功"),
    /**
     * 失败
     */
    FAIL(2, "投递失败");
    /**
     * 代码
     */
    private final Integer code;
    /**
     * 消息
     */
    private final String message;
}
