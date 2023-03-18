package com.liuscraft.tradingplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liuscraft.tradingplatform.entity.User;
import com.liuscraft.tradingplatform.entity.dto.UserLoginDto;
import com.liuscraft.tradingplatform.entity.dto.UserRegisterDto;
import com.liuscraft.tradingplatform.entity.vo.UserVo;
import com.liuscraft.tradingplatform.mapper.UserMapper;
import com.liuscraft.tradingplatform.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuscraft.tradingplatform.utils.JwtUtils;
import com.liuscraft.tradingplatform.utils.R;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
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

    @Override
    public R registerUser(UserRegisterDto registerDto) {
        String email = registerDto.getEmail();
        User user = getOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email));
        if (!ObjectUtils.isEmpty(user)){
            return R.error().msg("被注册了");
        }
        user = new User();
        BeanUtils.copyProperties(registerDto,user);
        user.setRoleId(2);
        save(user);
        return R.ok().msg("注册成功!").data("data", user);
    }

    @Override
    public R login(UserLoginDto userLoginDto) {
        String email = userLoginDto.getEmail();
        String password = userLoginDto.getPassword();
        User user = getOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email));
        if (user == null || !user.getPassword().equals(password)) return R.error().msg("账号或密码错误!");
        String jwtToken = JwtUtils.getJwtToken(String.valueOf(user.getId()), user.getNickname(), user.getRoleId());
        return R.ok().msg("登录成功!").data("token", jwtToken).data("data", UserVo.toVo(user));
    }

    @Override
    public List<UserVo> userList(Collection<Integer> collection) {
        if (collection.isEmpty()) return Collections.EMPTY_LIST;
        return listByIds(collection).stream().map(UserVo::toVo).collect(Collectors.toList());
    }

    @Override
    public R getUserInfo(Integer userId) {
        if (userId == null || userId<1) return R.error().msg("未查询到信息");
        User user = getById(userId);
        return user == null ? R.error().msg("未查询到信息"):R.ok().data("data",UserVo.toVo(user));
    }
}
