package com.liuscraft.tradingplatform.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.liuscraft.tradingplatform.entity.Order;
import com.liuscraft.tradingplatform.entity.enums.OrderStatus;
import jdk.nashorn.internal.objects.annotations.Function;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author LiusCraft
 * @date 2023/3/10 14:59
 */
@Data
public class OrderVo {
    private Integer id;
    private GoodVo goodVo;
    private UserVo buyUserVo;
    private UserVo sellUserVo;
    private Integer count;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;
    private OrderStatus orderStatus;

    @Function
    public static OrderVo toVo(Order order) {
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(order, orderVo);
        orderVo.setOrderStatus(OrderStatus.getStatusByCode(order.getStatus()));
        return orderVo;
    }
}
