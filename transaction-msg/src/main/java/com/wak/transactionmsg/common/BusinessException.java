package com.wak.transactionmsg.common;

/**
 * @author wuankang
 * @date 2024/6/30 17:11
 * @Description TODO
 * @Version 1.0
 */
public class BusinessException extends RuntimeException {
    /**
     * 代码
     */
    private String code;

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
