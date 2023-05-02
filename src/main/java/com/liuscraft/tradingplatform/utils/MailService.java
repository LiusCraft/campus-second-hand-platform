package com.liuscraft.tradingplatform.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author LiusCraft
 * @DateTime 2023/4/20 21:00
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MailService {
    Logger logger = LoggerFactory.getLogger(MailService.class);
    @Value("[${spring.mail.from}] ")
    private String fromName;
    @Value("${spring.mail.username}")
    private String userName;
    @Resource
    private JavaMailSender javaMailSender;
    public boolean sendSimpleMail(String to, String title, String content) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(userName);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(fromName + title);
        simpleMailMessage.setText(content);
        try {
            javaMailSender.send(simpleMailMessage);
        }catch (MailException e) {
            logger.error("发送邮件失败, 接收方:{}, 发送标题:{}", to, title);
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
