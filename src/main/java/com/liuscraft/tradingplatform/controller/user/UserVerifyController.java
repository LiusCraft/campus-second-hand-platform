package com.liuscraft.tradingplatform.controller.user;


import com.liuscraft.luckpermission.annotations.LuckVerify;
import com.liuscraft.tradingplatform.entity.dto.UserLoginDto;
import com.liuscraft.tradingplatform.entity.dto.UserRegisterDto;
import com.liuscraft.tradingplatform.service.IUserService;
import com.liuscraft.tradingplatform.utils.R;
import com.liuscraft.tradingplatform.utils.threadlocal.ThreadLocalServlet;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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

    @Resource
    IUserService userService;

    @PostMapping("register")
    public R register(@RequestBody UserRegisterDto registerDto) {
        return userService.registerUser(registerDto);
    }

    @PostMapping("login")
    public R login(@RequestBody UserLoginDto userLoginDto) {
        return userService.login(userLoginDto);
    }
    @GetMapping
    @LuckVerify("获取用户自己的信息")
    public R getInfo() {
        return userService.getUserInfo(ThreadLocalServlet.getUserId());
    }

}

