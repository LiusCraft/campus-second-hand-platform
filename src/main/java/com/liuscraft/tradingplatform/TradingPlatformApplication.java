package com.liuscraft.tradingplatform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@MapperScan({"com.liuscraft.tradingplatform.mapper"})
@SpringBootApplication
@EnableAsync
public class TradingPlatformApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(TradingPlatformApplication.class, args);
    }

}
