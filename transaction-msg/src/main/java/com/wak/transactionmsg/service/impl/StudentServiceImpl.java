package com.wak.transactionmsg.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wak.transactionmsg.dto.StudentDTO;
import com.wak.transactionmsg.entities.Student;
import com.wak.transactionmsg.mapper.StudentMapper;
import com.wak.transactionmsg.message.send.IMsgSend;
import com.wak.transactionmsg.service.StudentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * @author wuankang
 * @description 针对表【student(用户表)】的数据库操作Service实现
 * @createDate 2024-06-30 16:18:34
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
        implements StudentService {

    @Resource
    private IMsgSend defaultMsgSend;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String register(StudentDTO dto) {
        //判断入参
        if (ObjectUtils.isEmpty(dto)) {
            return null;
        }
        //获取用户数据
        Student student = new Student();
        student.setId(IdUtil.simpleUUID());
        student.setName(dto.getUserName());
        student.setAge(dto.getAge());
        //保存用户数据
        this.save(student);
        //发送MQ
        defaultMsgSend.sendMsg(student);
        //返回用户id
        return student.getId();
    }

    /**
     * 错误注册方法
     *
     * @param dto dto
     * @return {@code String }
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String registerError(StudentDTO dto) {
        Student student = new Student();
        student.setId(IdUtil.simpleUUID());
        student.setName(dto.getUserName());
        student.setAge(dto.getAge());
        //保存用户数据
        this.save(student);
        //发送MQ
        defaultMsgSend.sendMsg(student);
        //最后一步故意使坏，爆出异常
        int age = 10/0;
        //返回用户id
        return student.getId();
    }
}




