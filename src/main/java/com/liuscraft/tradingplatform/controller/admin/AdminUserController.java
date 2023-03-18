package com.liuscraft.tradingplatform.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuscraft.tradingplatform.entity.User;
import com.liuscraft.tradingplatform.entity.dto.PageDto;
import com.liuscraft.tradingplatform.entity.dto.UserDto;
import com.liuscraft.tradingplatform.entity.vo.UserVo;
import com.liuscraft.tradingplatform.service.IUserService;
import com.liuscraft.tradingplatform.utils.R;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author LiusCraft
 * @date 2023/3/18 12:35
 */
@RestController
@RequestMapping("admin/users")
public class AdminUserController {
    @Resource
    IUserService userService;
    @GetMapping
    public R getList(PageDto pageDto, Integer role) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if (role!=null)
            queryWrapper.eq(User::getRoleId, role);
        IPage<User> page = userService.page(
                new Page<User>(pageDto.getPage(), pageDto.getLimit()),
                queryWrapper);
        return R.ok().data("data", page.getRecords().stream().map(UserVo::toVo)).data("count", page.getTotal());
    }

    @PutMapping("{id}")
    public R updateUser(@PathVariable Integer id, @RequestBody UserDto user) {
        User byId = userService.getById(id);
        if (byId == null) return R.error().msg("没有这个用户");
        BeanUtils.copyProperties(user, byId);
        if(userService.updateById(byId)){
           return R.ok().msg("修改成功");
        }
        return R.error().msg("修改失败");
    }
}
