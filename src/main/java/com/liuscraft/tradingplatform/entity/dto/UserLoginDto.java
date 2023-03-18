package com.liuscraft.tradingplatform.entity.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

/**
 * @author LiusCraft
 * @date 2023/3/9 15:03
 */
@Data
public class UserLoginDto {
    @Email
    private String email;
    @Length(min = 6, message = "请输入正确密码!")
    private String password;
}
