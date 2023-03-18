package com.liuscraft.tradingplatform;

import com.liuscraft.luckpermission.annotations.LuckVerifyEnable;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan({"com.liuscraft.tradingplatform.mapper"})
@SpringBootApplication
@LuckVerifyEnable
public class TradingPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(TradingPlatformApplication.class, args);
    }

}
