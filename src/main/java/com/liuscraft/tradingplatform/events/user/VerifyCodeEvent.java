package com.liuscraft.tradingplatform.events.user;

import org.springframework.context.ApplicationEvent;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;

/**
 * @author LiusCraft
 * @DateTime 2023/4/30 22:09
 */
public class VerifyCodeEvent extends ApplicationEvent implements Serializable {
    private static final long serialVersionUID = 1L;
    private String email;
    private String code;
    private String msg;

    public VerifyCodeEvent(Object source, String email, String code) {
        this(source, email, code, "");
    }

    public VerifyCodeEvent(Object source, String email, String code, String msg) {
        super(source);
        this.code = code;
        this.email = email;
        if (ObjectUtils.isEmpty(msg)){
            msg = "无内容";
        }
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getEmail() {
        return email;
    }

    public String getMsg() {
        return msg;
    }
}
