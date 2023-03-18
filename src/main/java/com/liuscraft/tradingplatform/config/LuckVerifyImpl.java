package com.liuscraft.tradingplatform.config;

import com.alibaba.fastjson.JSON;
import com.liuscraft.luckpermission.entity.LuckAuthority;
import com.liuscraft.luckpermission.entity.LuckVerifyEntity;
import com.liuscraft.luckpermission.interceptors.LuckVerifyInterceptor;
import com.liuscraft.tradingplatform.utils.R;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

/**
 * @author LiusCraft
 * @date 2023/3/12 23:50
 */
public class LuckVerifyImpl implements LuckVerifyInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler, LuckVerifyEntity luckVerifyEntity, LuckAuthority luckAuthority) {
        if (luckAuthority == null) throw new RuntimeException("请先登录账号");
        if (!luckVerifyEntity.checkPermissions(luckAuthority.getLuckPermissions())){
            throw new RuntimeException("您没有权限这么做!"+ luckVerifyEntity.getMethodRoute());
        }
        return true;
    }
}
