package com.liuscraft.tradingplatform.config;

import com.liuscraft.luckpermission.entity.LuckAuthority;
import com.liuscraft.luckpermission.entity.LuckPermission;
import com.liuscraft.luckpermission.service.ILuckRolePermissionService;
import com.liuscraft.luckpermission.service.LuckAuthorityService;
import com.liuscraft.tradingplatform.utils.JwtUtils;
import com.liuscraft.tradingplatform.utils.threadlocal.ThreadLocalServlet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author LiusCraft
 * @date 2023/3/9 12:49
 */
@Configuration
public class LuckVerifyConfig {
    @Resource
    ILuckRolePermissionService luckRolePermissionService;


    @Bean
    public LuckAuthorityService<LuckAuthority> getUserInfoService() {
        return new LuckAuthorityService<LuckAuthority>() {
            @Override
            public LuckAuthority getUserAuthorization(HttpServletRequest request) {
                String userId = JwtUtils.getMemberClaimByJwtToken(request, JwtUtils.JwtClaim.ID);
                if (userId == null || userId.isEmpty()) return null;
                ThreadLocalServlet.editor.setValue("userId", Integer.parseInt(userId));
                Integer id = JwtUtils.getMemberClaimByJwtToken(request, JwtUtils.JwtClaim.ROLE_ID);
                List<LuckPermission> rolePermission = luckRolePermissionService.getRolePermission(JwtUtils.getMemberClaimByJwtToken(request, JwtUtils.JwtClaim.ROLE_ID));
                return new LuckAuthority(null, rolePermission);
            }
        };
    }
}
