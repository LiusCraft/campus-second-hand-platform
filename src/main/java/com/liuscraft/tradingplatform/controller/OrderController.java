package com.liuscraft.tradingplatform.controller;


import com.liuscraft.luckpermission.annotations.LuckVerify;
import com.liuscraft.tradingplatform.entity.dto.PageDto;
import com.liuscraft.tradingplatform.entity.enums.OrderStatus;
import com.liuscraft.tradingplatform.service.IOrderService;
import com.liuscraft.tradingplatform.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LiusCraft
 * @since 2023-03-09
 */
@RestController
@RequestMapping("orders")
@LuckVerify(value = "用户订单管理接口",children = true)
public class OrderController {
    @Resource
    IOrderService orderService;
    @GetMapping("{type}")
    public R getList(
            @PathVariable String type,
            PageDto pageDto,
            Integer orderStatus) {
        int typeNumber = 1;
        if ("sells".equalsIgnoreCase(type)) typeNumber = 2;
        return orderService.getList(typeNumber, pageDto.getPage(), pageDto.getLimit(), OrderStatus.getStatusByCode(orderStatus));
    }
    @PutMapping("{orderId}")
    public R updateOrderStatus(@PathVariable Integer orderId,@NotNull(message = "请设置状态") Integer orderStatus){
        return orderService.updateStatus(orderId, OrderStatus.getStatusByCode(orderStatus));
    }
}

