package com.liuscraft.tradingplatform.service;

import com.liuscraft.tradingplatform.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liuscraft.tradingplatform.entity.dto.UserVerifyDto;
import com.liuscraft.tradingplatform.entity.vo.UserVo;
import com.liuscraft.tradingplatform.utils.R;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiusCraft
 * @since 2023-03-09
 */
public interface IUserService extends IService<User> {

    R registerUser(UserVerifyDto registerDto);

    R login(UserVerifyDto userLoginDto);

    List<UserVo> userList(Collection<Integer> collection);

    R getUserInfo(Integer userId);

    R getVerifyCode(String email);
}
