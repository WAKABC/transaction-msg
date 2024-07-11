package com.wak.transactionmsg.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author wuankang
 * @date 2024/6/30 17:42
 * @Description TODO
 * @Version 1.0
 */
@Data
public class StudentDTO {
    @NotBlank(message = "用户名不能为空")
    private String userName;
    private Integer age;
}
