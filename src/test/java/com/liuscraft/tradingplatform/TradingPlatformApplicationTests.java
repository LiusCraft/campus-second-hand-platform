package com.liuscraft.tradingplatform;

import com.liuscraft.luckpermission.entity.LuckPermission;
import com.liuscraft.luckpermission.service.ILuckRolePermissionService;
import com.liuscraft.tradingplatform.controller.OrderController;
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

}
