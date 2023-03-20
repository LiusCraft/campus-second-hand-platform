package com.liuscraft.tradingplatform.config;

import com.liuscraft.luckpermission.service.LuckGetUserInfoService;
import com.liuscraft.tradingplatform.service.IUserService;
import com.liuscraft.tradingplatform.utils.JwtUtils;
import com.liuscraft.tradingplatform.utils.threadlocal.ThreadLocalServlet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * @author LiusCraft
 * @date 2023/3/9 12:49
 */
@Configuration
public class LuckVerifyConfig {

    @Bean
    @Primary
    public LuckGetUserInfoService getUserInfoService() {
        return new LuckGetUserInfoService() {
            @Override
            public Set<Integer> getUserId(HttpServletRequest request) {
                String userId = JwtUtils.getMemberClaimByJwtToken(request, JwtUtils.JwtClaim.ID);
                if (userId == null || userId.isEmpty()) return null;
                ThreadLocalServlet.editor.setValue("userId", Integer.parseInt(userId));
                return new HashSet<>(JwtUtils.getMemberClaimByJwtToken(request, JwtUtils.JwtClaim.ROLE_ID));
            }
        };
    }
}
