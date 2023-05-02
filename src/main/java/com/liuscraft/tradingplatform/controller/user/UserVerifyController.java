package com.liuscraft.tradingplatform.controller.user;


import com.liuscraft.luckpermission.annotations.LuckVerify;
import com.liuscraft.tradingplatform.entity.CacheObject;
import com.liuscraft.tradingplatform.entity.dto.UserVerifyDto;
import com.liuscraft.tradingplatform.entity.dto.groups.EmailGroup;
import com.liuscraft.tradingplatform.entity.dto.groups.LoginGroup;
import com.liuscraft.tradingplatform.entity.dto.groups.RegisterGroup;
import com.liuscraft.tradingplatform.service.IUserService;
import com.liuscraft.tradingplatform.utils.R;
import com.liuscraft.tradingplatform.utils.threadlocal.ThreadLocalServlet;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LiusCraft
 * @since 2023-03-09
 */
@RestController
@RequestMapping("user")
public class UserVerifyController {
    public static final Map<String, CacheObject<String>> REGISTER_EMAIL_CACHE = new ConcurrentHashMap<>();
    @Resource
    IUserService userService;

    @PostMapping("getCode")
    public R getCode(@Validated(EmailGroup.class) UserVerifyDto userVerifyDto) {
        return userService.getVerifyCode(userVerifyDto.getEmail());
    }
    @PostMapping("register")
    public R register(@RequestBody @Validated(RegisterGroup.class) UserVerifyDto registerDto) {
        CacheObject<String> verifyCodeCache = REGISTER_EMAIL_CACHE.get(registerDto.getEmail());
        if (verifyCodeCache == null ||
                verifyCodeCache.isExpire() ||
                !verifyCodeCache.getObj().equals(registerDto.getCode())) {
            if (verifyCodeCache != null) {
                REGISTER_EMAIL_CACHE.remove(registerDto.getEmail());
            }
            return R.error().msg("请获取验证码，该验证码已失效！");
        }
        return userService.registerUser(registerDto);
    }
    @PostMapping("login")
    public R login(@RequestBody @Validated(LoginGroup.class) UserVerifyDto userLoginDto) {
        return userService.login(userLoginDto);
    }
    @GetMapping
    @LuckVerify("获取用户自己的信息")
    public R getInfo() {
        return userService.getUserInfo(ThreadLocalServlet.getUserId());
    }
}

