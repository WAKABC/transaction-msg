package com.wak.transactionmsg.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wuankang
 * @date 2024/6/30 17:05
 * @Description TODO
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {
    /**
     * 错误500
     */
    ERROR_500("500", "系统异常，请稍后重试");
    /**
     * 代码
     */
    private String code;
    /**
     * 消息
     */
    private String message;

}
