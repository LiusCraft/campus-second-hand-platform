package com.liuscraft.tradingplatform.events.user.listeners;

import com.liuscraft.tradingplatform.entity.User;
import com.liuscraft.tradingplatform.entity.dto.UserDto;
import com.liuscraft.tradingplatform.events.user.RegisterEvent;
import com.liuscraft.tradingplatform.utils.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author LiusCraft
 * @DateTime 2023/4/20 20:34
 */
@Component
public class RegisterListener implements ApplicationListener<RegisterEvent> {
    Logger logger = LoggerFactory.getLogger(RegisterListener.class);
    private final ApplicationContext context;

    public RegisterListener(ApplicationContext context) {
        this.context = context;
    }
    @Async
    @Override
    public void onApplicationEvent(RegisterEvent event) {
        User user = event.getMsg();
        assert(user != null);
        MailService mailService = context.getBean(MailService.class);
        boolean sendOk = mailService.sendSimpleMail(
                user.getEmail(),
                "感谢您的注册!",
                user.getNickname() + " 您已成功加入到该平台，现在您可以发布自己的商品或者购买TA人商品啦~"
        );
        logger.debug("发送邮件最终状态:"+sendOk);
    }
}
