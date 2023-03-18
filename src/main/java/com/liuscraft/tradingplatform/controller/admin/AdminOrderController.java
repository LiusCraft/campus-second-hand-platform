package com.liuscraft.tradingplatform.controller.admin;


import com.liuscraft.luckpermission.annotations.LuckVerify;
import com.liuscraft.tradingplatform.entity.dto.PageDto;
import com.liuscraft.tradingplatform.entity.enums.OrderStatus;
import com.liuscraft.tradingplatform.service.IOrderService;
import com.liuscraft.tradingplatform.utils.R;
import com.liuscraft.tradingplatform.utils.threadlocal.ThreadLocalServlet;
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
@RequestMapping("admin/orders")
public class AdminOrderController {
    @Resource
    IOrderService orderService;
    @GetMapping("{type}")
    public R getList(
            @PathVariable String type,
            PageDto pageDto,
            Integer orderStatus) {
        int typeNumber = 1;
        if ("sells".equalsIgnoreCase(type)) typeNumber = 2;
        ThreadLocalServlet.editor.isAdmin();
        return orderService.getList(typeNumber, pageDto.getPage(), pageDto.getLimit(), OrderStatus.getStatusByCode(orderStatus));
    }
    @PutMapping("{orderId}")
    public R updateOrderStatus(@PathVariable Integer orderId,@NotNull(message = "请设置状态") Integer orderStatus){
        ThreadLocalServlet.editor.isAdmin();
        return orderService.updateStatus(orderId, OrderStatus.getStatusByCode(orderStatus));
    }
}

