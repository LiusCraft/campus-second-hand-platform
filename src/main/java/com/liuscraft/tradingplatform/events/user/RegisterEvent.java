package com.liuscraft.tradingplatform.events.user;

import com.liuscraft.tradingplatform.entity.User;
import com.liuscraft.tradingplatform.entity.dto.UserDto;
import org.springframework.context.ApplicationEvent;

import java.io.Serializable;

/**
 * @author LiusCraft
 * @DateTime 2023/4/30 13:53
 */
public class RegisterEvent extends ApplicationEvent implements Serializable {
    private static final long serialVersionUID = 1L;
    private User msg;
    public RegisterEvent(Object source, User msg) {
        super(source);
        this.msg = msg;
    }
    public User getMsg() {
        return msg;
    }
    public void setMsg(User msg) {
        this.msg = msg;
    }
}
