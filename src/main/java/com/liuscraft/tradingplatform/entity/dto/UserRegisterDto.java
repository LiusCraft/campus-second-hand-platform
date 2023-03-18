package com.liuscraft.tradingplatform.entity.dto;

import com.sun.org.glassfish.gmbal.NameValue;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author LiusCraft
 * @date 2023/3/9 13:36
 */
@Data
public class UserRegisterDto {
    @Email(message = "邮箱格式错误")
    private String email;
    @NotBlank(message = "请输入昵称")
    @Length(max = 20, min = 1, message = "昵称太长了")
    private String nickname;
    @Length(min =6, message = "密码最少要6位数")
    private String password;
}
