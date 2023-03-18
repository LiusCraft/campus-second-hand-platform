package com.liuscraft.tradingplatform.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.liuscraft.tradingplatform.entity.User;
import jdk.nashorn.internal.objects.annotations.Function;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author LiusCraft
 * @date 2023/3/9 21:45
 */
@Data
public class UserVo {
    private Integer id;
    private String nickname;
    private String email;
    private Integer roleId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;
    @Function
    public static UserVo toVo(User user) {
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return userVo;
    }
}
