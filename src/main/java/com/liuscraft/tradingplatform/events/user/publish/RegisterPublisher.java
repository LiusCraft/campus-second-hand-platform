package com.liuscraft.tradingplatform.events.user.publish;

import com.liuscraft.tradingplatform.entity.User;
import com.liuscraft.tradingplatform.entity.dto.UserDto;
import com.liuscraft.tradingplatform.events.user.RegisterEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author LiusCraft
 * @DateTime 2023/4/30 14:03
 */
@Component
public class RegisterPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public RegisterPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
    public void sendMessage(User user) {
        sendMessage(this, user);
    }

    public void sendMessage(Object source, User user) {

        applicationEventPublisher.publishEvent(new RegisterEvent(source, user));
    }
}
