package com.liuscraft.tradingplatform.entity.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author LiusCraft
 * @date 2023/3/18 16:57
 */
@Data
public class UserDto {

    @Length(max = 20, min = 1, message = "昵称长度在20以内!")
    String nickname;
    @Email(message = "必须是有效邮箱")
    String email;
    @Length(min =6, message = "密码最少要6位数")
    String password;
    @Range(min = 1, max = 2, message = "请设置正确的角色~")
    Integer roleId;
}
