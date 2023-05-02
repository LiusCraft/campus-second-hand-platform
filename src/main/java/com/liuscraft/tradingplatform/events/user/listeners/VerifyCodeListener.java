package com.liuscraft.tradingplatform.events.user.listeners;

import com.liuscraft.tradingplatform.events.user.VerifyCodeEvent;
import com.liuscraft.tradingplatform.utils.MailService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author LiusCraft
 * @DateTime 2023/5/1 12:17
 */
@Component
public class VerifyCodeListener implements ApplicationListener<VerifyCodeEvent> {
    private final ApplicationContext context;

    public VerifyCodeListener(ApplicationContext context) {
        this.context = context;
    }

    @Async
    @Override
    public void onApplicationEvent(VerifyCodeEvent event) {
        MailService mailService = context.getBean(MailService.class);
        mailService.sendSimpleMail(event.getEmail(),
                "请注意查收您的验证码",
                "您正在使用该平台验证码服务，验证码是:"+event.getCode() +
                "\n----------------------具体内容----------------------\n"+
                event.getMsg());
    }
}
