package com.liuscraft.tradingplatform;

import com.liuscraft.tradingplatform.controller.OrderController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class TradingPlatformApplicationTests {

    @Resource
    OrderController orderController;
    @Test
    void contextLoads() {
    }

}
