package com.wak.transactionmsg.common;

import lombok.Data;

/**
 * @author wuankang
 * @date 2024/6/30 16:51
 * @Description TODO
 * @Version 1.0
 */
@Data
public class Result<T> {
    /**
     * 是否成功
     */
    private boolean success;
    /**
     * 错误编码
     */
    private String code;
    /**
     * 消息
     */
    private String message;
    /**
     * 数据
     */
    private T data;

    public Result(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public Result(boolean success, String code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
