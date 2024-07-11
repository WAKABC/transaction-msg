package com.wak.transactionmsg.web;

import com.wak.transactionmsg.common.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author wuankang
 * @date 2024/6/30 16:50
 * @Description TODO
 * @Version 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理业务异常
     *
     * @param e       e
     * @param request 请求
     * @return {@code Result }
     */
    @ExceptionHandler(BusinessException.class)
    public Result handleBusinessException(BusinessException e, HttpServletRequest request){
        logger.info("请求：{}，发生异常：{}", request.getRequestURL(), e.getMessage(), e);
        return ResultUtil.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理springboot参数校验异常
     *
     * @param e       e
     * @param request 请求
     * @return {@code Result }
     */
    @ExceptionHandler(BindException.class)
    public Result handleBindException(BindException e, HttpServletRequest request){
        logger.info("请求：{}，发生异常：{}", request.getRequestURL(), e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return ResultUtil.error(message);
    }

    /**
     * 处理异常
     *
     * @param e       e
     * @param request 请求
     * @return {@code Result }
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e, HttpServletRequest request) {
        logger.info("请求：{}，发生异常：{}", request.getRequestURL(), e.getMessage(), e);
        return ResultUtil.error(ErrorCode.ERROR_500.getCode(), ErrorCode.ERROR_500.getMessage());
    }
}
