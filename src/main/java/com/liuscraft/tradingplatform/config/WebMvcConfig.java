package com.liuscraft.tradingplatform.config;

import com.liuscraft.tradingplatform.utils.JwtUtils;
import com.liuscraft.tradingplatform.utils.threadlocal.ThreadLocalServlet;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author LiusCraft
 * @date 2023/3/10 23:18
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(new HandlerInterceptor() {
           @Override
           public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
               ThreadLocalServlet.editor.setValue("userId", JwtUtils.getMemberClaimByJwtToken(request, JwtUtils.JwtClaim.ID));
               return true;
           }

           @Override
           public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
               HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
               ThreadLocalServlet.clear();
           }
       }).addPathPatterns("/**");
    }
}
