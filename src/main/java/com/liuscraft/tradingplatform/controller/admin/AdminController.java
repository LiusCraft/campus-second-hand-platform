package com.liuscraft.tradingplatform.controller.admin;

import com.liuscraft.luckpermission.annotations.LuckVerify;
import com.liuscraft.tradingplatform.service.IGoodService;
import com.liuscraft.tradingplatform.service.IOrderService;
import com.liuscraft.tradingplatform.service.IUserService;
import com.liuscraft.tradingplatform.utils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author LiusCraft
 * @date 2023/3/18 0:22
 */
@RestController
@RequestMapping("admin")
@LuckVerify(children = true)
public class AdminController {
    @Resource
    IUserService userService;
    @Resource
    IGoodService goodService;
    @Resource
    IOrderService orderService;

    @GetMapping("statistics")
    public R getStatistics() {
        int goodCount = goodService.count();
        int orderCount = orderService.count();
        int userCount = userService.count();
        return R.ok().data("good", goodCount).data("order", orderCount).data("user", userCount);
    }
}
