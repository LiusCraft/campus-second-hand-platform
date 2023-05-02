package com.liuscraft.tradingplatform;

import com.liuscraft.luckpermission.entity.LuckPermission;
import com.liuscraft.luckpermission.service.ILuckRolePermissionService;
import com.liuscraft.tradingplatform.controller.OrderController;
import com.liuscraft.tradingplatform.entity.User;
import com.liuscraft.tradingplatform.entity.dto.UserDto;
import com.liuscraft.tradingplatform.events.user.listeners.RegisterListener;
import com.liuscraft.tradingplatform.events.user.publish.RegisterPublisher;
import com.liuscraft.tradingplatform.events.user.publish.VerifyCodePublisher;
import com.liuscraft.tradingplatform.utils.StrUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class TradingPlatformApplicationTests {

    @Resource
    OrderController orderController;
    @Resource
    ILuckRolePermissionService luckRolePermissionService;

    @Test
    void contextLoads() {
        List<LuckPermission> rolePermission = luckRolePermissionService.getRolePermission(1);
        rolePermission.forEach(System.out::println);
    }

    @Resource
    RegisterPublisher registerPublisher;

    @Test
    void testRegisterEvent() {
        User userDto = new User();
        userDto.setEmail("liuscraft@qq.com");
        userDto.setNickname("测试账号");
        registerPublisher.sendMessage(userDto);
    }
    @Resource
    VerifyCodePublisher verifyCodePublisher;
    @Test
    void testVerifyCodeMail() {
        verifyCodePublisher.sendMessage("liuscraft@qq.com", StrUtils.random(5));
    }

}
