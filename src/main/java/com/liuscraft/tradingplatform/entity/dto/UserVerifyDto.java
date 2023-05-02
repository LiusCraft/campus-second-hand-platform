package com.liuscraft.tradingplatform.entity.dto;

import com.liuscraft.tradingplatform.entity.dto.groups.EmailGroup;
import com.liuscraft.tradingplatform.entity.dto.groups.LoginGroup;
import com.liuscraft.tradingplatform.entity.dto.groups.RegisterGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * @author LiusCraft
 * @date 2023/3/9 13:36
 */
@Data
public class UserVerifyDto {
    @Email(groups = {RegisterGroup.class, LoginGroup.class, EmailGroup.class},regexp = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*", message = "邮箱格式错误")
    private String email = "";
    @Length(groups = {RegisterGroup.class}, min = 2, max = 10, message = "昵称建议在2-10之间的长度")
    private String nickname = "";
    @Length(groups = {RegisterGroup.class, LoginGroup.class}, min =6, message = "密码最少要6位数")
    private String password = "";
    @Length(groups = {RegisterGroup.class}, min = 5, max = 5, message = "请输入正确的验证码")
    private String code = "";
}
