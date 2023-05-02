package com.liuscraft.tradingplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liuscraft.tradingplatform.controller.user.UserVerifyController;
import com.liuscraft.tradingplatform.entity.CacheObject;
import com.liuscraft.tradingplatform.entity.User;
import com.liuscraft.tradingplatform.entity.dto.UserVerifyDto;
import com.liuscraft.tradingplatform.entity.vo.UserVo;
import com.liuscraft.tradingplatform.events.user.publish.RegisterPublisher;
import com.liuscraft.tradingplatform.events.user.publish.VerifyCodePublisher;
import com.liuscraft.tradingplatform.mapper.UserMapper;
import com.liuscraft.tradingplatform.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuscraft.tradingplatform.utils.JwtUtils;
import com.liuscraft.tradingplatform.utils.R;
import com.liuscraft.tradingplatform.utils.StrUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
     * <p>
     *  服务实现类
     * </p>
     *
     * @author LiusCraft
     * @since 2023-03-09
     */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private static final long VERIFY_CODE_EXPIRE = 6000*10;
    @Resource
    VerifyCodePublisher verifyCodePublisher;
    @Resource
    RegisterPublisher registerPublisher;
    @Override
    public R registerUser(UserVerifyDto registerDto) {
        String email = registerDto.getEmail();
        User user = getOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email));
        if (!ObjectUtils.isEmpty(user)){
            return R.error().msg("被注册了");
        }
        user = new User();
        BeanUtils.copyProperties(registerDto,user);
        user.setRoleId(2);
        save(user);
        registerPublisher.sendMessage(user);
        return R.ok().msg("注册成功!").data("data", user);
    }

    @Override
    public R login(UserVerifyDto userLoginDto) {
        String email = userLoginDto.getEmail();
        String password = userLoginDto.getPassword();
        User user = getOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email));
        if (user == null || !user.getPassword().equals(password)) {
            return R.error().msg("账号或密码错误!");
        }
        String jwtToken = JwtUtils.getJwtToken(String.valueOf(user.getId()), user.getNickname(), user.getRoleId());
        return R.ok().msg("登录成功!").data("token", jwtToken).data("data", UserVo.toVo(user));
    }

    @Override
    public List<UserVo> userList(Collection<Integer> collection) {
        if (collection.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return listByIds(collection).stream().map(UserVo::toVo).collect(Collectors.toList());
    }

    @Override
    public R getUserInfo(Integer userId) {
        if (userId == null || userId<1) {
            return R.error().msg("未查询到信息");
        }
        User user = getById(userId);
        return user == null ? R.error().msg("未查询到信息"):R.ok().data("data",UserVo.toVo(user));
    }

    @Override
    public R getVerifyCode(String email) {
        long currentTime = System.currentTimeMillis();
        long currentExpire = currentTime + VERIFY_CODE_EXPIRE;
        final Map<String, CacheObject<String>> registerEmailCache = UserVerifyController.REGISTER_EMAIL_CACHE;
        CacheObject<String> cacheObject = registerEmailCache.get(email);
        if(cacheObject != null) {
            if (!cacheObject.isExpire()) {
                return R.ok().msg("请不要重复获取验证码"+
                                (cacheObject.getExpireTime()-currentTime)/1000+
                                "秒后再试!")
                        .data("expire", cacheObject.getExpireTime())
                        .data("current", currentTime).state(false);
            }
            registerEmailCache.remove(email);
        }
        User user = getOne(
                new LambdaQueryWrapper<User>().eq(User::getEmail, email));
        if (user != null) {
            return R.error().msg("该邮箱已被注册").code(404);
        }
        String randomStr = StrUtils.random(5);
        registerEmailCache.put(email,
                new CacheObject<>(randomStr, currentExpire));
        verifyCodePublisher.sendMessage(email, randomStr,
                "账号注册验证码在60秒内有效，过期需重新获取！");
        return R.ok().msg("邮箱验证码已发送")
                .data("expire", currentExpire)
                .data("current", currentTime);
    }
}
