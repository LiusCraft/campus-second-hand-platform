package com.liuscraft.tradingplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuscraft.tradingplatform.entity.Good;
import com.liuscraft.tradingplatform.entity.Order;
import com.liuscraft.tradingplatform.entity.enums.OrderStatus;
import com.liuscraft.tradingplatform.entity.vo.GoodVo;
import com.liuscraft.tradingplatform.entity.vo.OrderVo;
import com.liuscraft.tradingplatform.entity.vo.UserVo;
import com.liuscraft.tradingplatform.mapper.OrderMapper;
import com.liuscraft.tradingplatform.service.IGoodService;
import com.liuscraft.tradingplatform.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuscraft.tradingplatform.service.IUserService;
import com.liuscraft.tradingplatform.utils.R;
import com.liuscraft.tradingplatform.utils.threadlocal.ThreadLocalServlet;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

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
@Log4j2
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Resource
    IGoodService goodService;
    @Resource
    IUserService userService;
    @Override
    public R getList(Integer type, Integer currentPage, Integer limit, OrderStatus statusByCode) {
        boolean isAdmin = ThreadLocalServlet.isAdmin();
        IPage<Order> page = new Page<>();
        page.setSize(limit);
        page.setCurrent(currentPage);
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<Order>();
        if (isAdmin)
            log.warn("有管理正在操作订单信息...");
        else if (type == 1)  // bug
            queryWrapper.eq(Order::getUserId, ThreadLocalServlet.getUserId());
        else
            queryWrapper.eq(Order::getGoodUserId, ThreadLocalServlet.getUserId());
        if (statusByCode!=null) {
           queryWrapper.eq(Order::getStatus, statusByCode.getCode());
        }
        queryWrapper.orderByDesc(Order::getStatus);
        IPage<Order> pageData = page(page, queryWrapper);
        if (pageData.getSize() == 0L) R.error().msg("未查询到相关订单").data("count", pageData.getTotal());
        Set<Integer> goodIds = new HashSet<>();
        Set<Integer> buyUserIds = new HashSet<>();
        Set<Integer> sellUserIds = new HashSet<>();
        pageData.getRecords().forEach(v->{
            goodIds.add(v.getGoodId());
            buyUserIds.add(v.getUserId());
            sellUserIds.add(v.getGoodUserId());
        });
        HashMap<Integer,GoodVo> goodVoHashMap = new HashMap<>();
        HashMap<Integer, UserVo> userVoHashMap = new HashMap<>();
        userService.userList(buyUserIds).forEach(v-> userVoHashMap.put(v.getId(), v));
        userService.userList(sellUserIds).forEach(v-> userVoHashMap.put(v.getId(), v));
        goodService.goodVoList(goodIds).forEach(v->goodVoHashMap.put(v.getId(), v));
        List<OrderVo> orderVoList = page.getRecords().stream().map(order -> {
            OrderVo orderVo = OrderVo.toVo(order);
            orderVo.setGoodVo(goodVoHashMap.get(order.getGoodId()));
            orderVo.setBuyUserVo(userVoHashMap.get(order.getUserId()));
            orderVo.setSellUserVo(userVoHashMap.get(order.getGoodUserId()));
            return orderVo;
        }).collect(Collectors.toList());
        return R.ok().data("data", orderVoList).data("count", pageData.getTotal());
    }

    private static final List<OrderStatus> ORDER_STATUSES_BUY = new LinkedList<>();
    private static final List<OrderStatus> ORDER_STATUSES_SELL = new LinkedList<>();
    static {
        ORDER_STATUSES_BUY.add(OrderStatus.WAIT_DELIVER);
        ORDER_STATUSES_BUY.add(OrderStatus.FINISH);
        ORDER_STATUSES_BUY.add(OrderStatus.WAIT);
        ORDER_STATUSES_SELL.add(OrderStatus.DELIVER);
    }

    @Override
    public R updateStatus(Integer orderId, OrderStatus statusByCode) {
        boolean isAdmin = ThreadLocalServlet.isAdmin();
        if (statusByCode == null) return R.error().msg("请设置正确的状态");
        int userId = ThreadLocalServlet.getUserId();
        Order order = getById(orderId);
        boolean buy = false;
        if (order == null)
            return R.error().msg("您没有该订单");
        else if (order.getUserId() == userId)
            buy = true;
        else if (order.getGoodUserId() == userId)
            buy = false;
        else if (!isAdmin)
            return R.error().msg("您没有该订单信息");
        boolean applyStatus = false;
        if (isAdmin) {
            applyStatus = ORDER_STATUSES_BUY.contains(statusByCode);
            if (!applyStatus)
                applyStatus = ORDER_STATUSES_SELL.contains(statusByCode);
        } else if (buy)
            applyStatus = ORDER_STATUSES_BUY.contains(statusByCode);
        else
            applyStatus = ORDER_STATUSES_SELL.contains(statusByCode);
        if (!applyStatus) return R.error().msg("您不能进行这种操作!");
        order.setStatus(statusByCode.getCode());
        if(!updateById(order)) return R.error().msg("更新状态失败");
        return R.ok().msg("订单状态已变更为:"+statusByCode.getMsg());
    }

    @Override
    public R addOrder(Good good, Integer buyCount) {
        int userId = ThreadLocalServlet.getUserId();
        if (good.getUserId() ==  userId) return R.error().msg("您不能购买自己的商品!");
        Order order = new Order();
        order.setStatus(OrderStatus.NO_APPLY.getCode());
        order.setCount(buyCount);
        order.setGoodUserId(good.getUserId());
        order.setUserId(userId);
        order.setGoodId(good.getId());
        if(!save(order))
            return R.error().msg("购买失败。");
        return R.ok().msg("购买成功");
    }
}
