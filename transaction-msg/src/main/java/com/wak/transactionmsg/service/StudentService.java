package com.wak.transactionmsg.service;

import com.wak.transactionmsg.dto.StudentDTO;
import com.wak.transactionmsg.entities.Student;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author wuankang
* @description 针对表【student(用户表)】的数据库操作Service
* @createDate 2024-06-30 16:18:34
*/
public interface StudentService extends IService<Student> {
    public String register(StudentDTO dto);
    public String registerError(StudentDTO dto);
}
