package com.liuscraft.tradingplatform.events.user.publish;

import com.liuscraft.tradingplatform.entity.dto.UserDto;
import com.liuscraft.tradingplatform.events.user.RegisterEvent;
import com.liuscraft.tradingplatform.events.user.VerifyCodeEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author LiusCraft
 * @DateTime 2023/4/30 22:07
 */
@Component
public class VerifyCodePublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public VerifyCodePublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
    public void sendMessage(String email, String code) {
        sendMessage(this, email, code, "");
    }

    public void sendMessage(String email, String code, String msg) {
        sendMessage(this, email, code, msg);
    }

    public void sendMessage(Object source, String email, String code, String msg) {
        applicationEventPublisher.publishEvent(new VerifyCodeEvent(source, email, code, msg));
    }
}
