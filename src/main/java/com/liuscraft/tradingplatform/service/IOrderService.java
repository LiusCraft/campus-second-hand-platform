package com.liuscraft.tradingplatform.service;

import com.liuscraft.tradingplatform.entity.Good;
import com.liuscraft.tradingplatform.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liuscraft.tradingplatform.entity.enums.OrderStatus;
import com.liuscraft.tradingplatform.utils.R;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiusCraft
 * @since 2023-03-09
 */
public interface IOrderService extends IService<Order> {
    R getList(Integer type, Integer currentPage, Integer limit, OrderStatus statusByCode);

    R updateStatus(Integer orderId, OrderStatus statusByCode);
    R addOrder(Good good, Integer count);
}
