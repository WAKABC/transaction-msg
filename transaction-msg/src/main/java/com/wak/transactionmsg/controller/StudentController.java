package com.wak.transactionmsg.controller;

import com.wak.transactionmsg.common.Result;
import com.wak.transactionmsg.common.ResultUtil;
import com.wak.transactionmsg.dto.StudentDTO;
import com.wak.transactionmsg.service.StudentService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuankang
 * @date 2024/7/2 10:50
 * @Description TODO
 * @Version 1.0
 */
@RestController
public class StudentController {
    @Resource
    private StudentService studentService;

    @PostMapping("/stu/register")
    public Result<String> register(@Validated @RequestBody StudentDTO dto){
        String userId = studentService.register(dto);
        return ResultUtil.success(userId);
    }

    /**
     * 演示：事务中有异常，消息投递会被自动取消
     * @param studentDTO
     * @return
     */
    @PostMapping("/student/registerError")
    public Result<String> registerError(@Validated @RequestBody StudentDTO studentDTO) {
        return ResultUtil.success(studentService.registerError(studentDTO));
    }
}
