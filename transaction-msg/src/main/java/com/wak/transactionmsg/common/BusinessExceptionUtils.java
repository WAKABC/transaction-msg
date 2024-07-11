package com.wak.transactionmsg.common;

/**
 * @author wuankang
 * @date 2024/6/30 17:30
 * @Description TODO
 * @Version 1.0
 */
public class BusinessExceptionUtils {
    /**
     * 业务异常
     *
     * @param msg 消息
     * @return {@code BusinessException }
     */
    public static BusinessException businessException(String msg){
        return new BusinessException(null, msg);
    }

    /**
     * 业务异常
     *
     * @param code 代码
     * @param msg  消息
     * @return {@code BusinessException }
     */
    public static BusinessException businessException(String code, String msg){
        return new BusinessException(code, msg);
    }

    /**
     * 业务异常
     *
     * @param code  代码
     * @param msg   消息
     * @param cause 导致
     * @return {@code BusinessException }
     */
    public static BusinessException businessException(String code, String msg, Throwable cause){
        return new BusinessException(code, msg, cause);
    }
}
