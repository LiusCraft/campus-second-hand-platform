package com.liuscraft.tradingplatform.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.liuscraft.luckpermission.LuckPermissionBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LiusCraft
 * @date 2023/3/9 12:06
 */
@Configuration
public class MpConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setSqlParserList(LuckPermissionBuilder.getLuckAutoTableHandler());
        return paginationInterceptor;
    }
}
