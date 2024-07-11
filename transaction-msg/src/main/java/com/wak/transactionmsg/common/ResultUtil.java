package com.wak.transactionmsg.common;

/**
 * @author wuankang
 * @date 2024/6/30 16:57
 * @Description TODO
 * @Version 1.0
 */
public class ResultUtil {
    public static <T> Result<T> success() {
        return new Result<>(true, null, null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(true, "success", data);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<>(false, msg, null);
    }

    public static <T> Result<T> error(String code, String msg) {
        return new Result<>(false, code, msg, null);
    }
}
