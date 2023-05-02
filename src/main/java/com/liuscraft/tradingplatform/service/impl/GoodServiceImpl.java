package com.liuscraft.tradingplatform.service.impl;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuscraft.tradingplatform.config.TradingProperties;
import com.liuscraft.tradingplatform.entity.Category;
import com.liuscraft.tradingplatform.entity.Good;
import com.liuscraft.tradingplatform.entity.User;
import com.liuscraft.tradingplatform.entity.dto.GoodDto;
import com.liuscraft.tradingplatform.entity.vo.CategoryVo;
import com.liuscraft.tradingplatform.entity.vo.GoodVo;
import com.liuscraft.tradingplatform.entity.vo.UserVo;
import com.liuscraft.tradingplatform.mapper.GoodMapper;
import com.liuscraft.tradingplatform.service.ICategoryService;
import com.liuscraft.tradingplatform.service.IGoodService;
import com.liuscraft.tradingplatform.service.IOrderService;
import com.liuscraft.tradingplatform.service.IUserService;
import com.liuscraft.tradingplatform.utils.R;
import com.liuscraft.tradingplatform.utils.threadlocal.ThreadLocalServlet;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LiusCraft
 * @since 2023-03-09
 */
@Service
public class GoodServiceImpl extends ServiceImpl<GoodMapper, Good> implements IGoodService {

    @Resource
    ICategoryService categoryService;

    @Resource
    IUserService userService;

    @Resource
    @Lazy
    IOrderService orderService;

    @Override
    public R getList(Integer currentPage, Integer limit, Integer category, Integer userId) {
        final boolean noCateGoryId;
        noCateGoryId = category == null || category < 1;
        IPage<Good> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(limit);
        LambdaQueryWrapper<Good> queryWrapper = new LambdaQueryWrapper<Good>();
        queryWrapper.eq(userId!=null, Good::getUserId, userId);
        queryWrapper.eq(!noCateGoryId, Good::getCategoryId, category);
        queryWrapper.ge(Good::getCount, 1);
        queryWrapper.orderByDesc(Good::getGmtCreate);
        IPage<Good> results = page(page, queryWrapper);
        if (results.getRecords().isEmpty()) {
            return R.error().msg("未查询到相关商品").data("data", Collections.EMPTY_LIST);
        }
        Set<Integer> userIds = new HashSet<>();
        Set<Integer> categoryIds = new HashSet<>();
        results.getRecords().forEach(v->{
            userIds.add(v.getUserId());
            if (noCateGoryId) {
                categoryIds.add(v.getCategoryId());
            }
        });
        if (!noCateGoryId) {
            categoryIds.add(category);
        }
        HashMap<Integer, UserVo> userHashMap = new HashMap<>();
        userService.userList(userIds)
                .forEach(userVo -> userHashMap.put(userVo.getId(), userVo));
        HashMap<Integer, CategoryVo> categoryMap = new HashMap<>();
        categoryService.cateGoryList(categoryIds)
                .forEach(v-> categoryMap.put(v.getId(), v));
        List<GoodVo> goodVoList = results.getRecords().stream().map(good -> {
            GoodVo goodVo = GoodVo.toVo(good);
            goodVo.setUser(userHashMap.get(good.getUserId()));
            goodVo.setCategory(categoryMap.get(good.getCategoryId()));
            goodVo.setDescription("");
            return goodVo;
        }).collect(Collectors.toList());
        return R.ok().msg("查询到相关商品").data("data", goodVoList).data("count", results.getTotal()); }
    @Override
    public R goodById(Integer goodId) {
        Good good = getById(goodId);
        if (good == null || good.getDeleted()) {
            return R.error().msg("该商品不存在！");
        }
        GoodVo goodVo = GoodVo.toVo(good);
        User user = userService.getById(good.getUserId());
        if (user == null) {
            return R.error().msg("该商品信息存在错误");
        }
        Category category = categoryService.getById(good.getCategoryId());
        if (category == null) {
            return R.error().msg("该商品信息存在错误");
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        goodVo.setUser(userVo);
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        goodVo.setCategory(categoryVo);
        return R.ok().data("data", goodVo); }

    @Override
    @Transactional
    public R updateGoodById(Integer goodId, GoodDto goodDto) {
        int userId = ThreadLocalServlet.getUserId();
        boolean admin = ThreadLocalServlet.isAdmin();
        R r = goodById(goodId);
        if (!r.getState()) {
            return r;
        }
        R categoryR = categoryService.cateGoryById(goodDto.getCategoryId());
        if (!categoryR.getState()) {
            return categoryR;
        }
        GoodVo goodVo = ((GoodVo) r.getData().get("data"));
        if(goodVo.getUser().getId() != userId && !admin) {
            return R.error().msg("您没有权限这么做！");
        }
        Good good = new Good();
        BeanUtils.copyProperties(goodVo, good);
        BeanUtils.copyProperties(goodDto, good);
        good.setId(goodId);
        if(!ObjectUtils.isEmpty(goodDto.getImg())) {
            if(!saveImg(good, goodDto.getImg())){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return R.error().msg("封面图存储失败");
            }
            good.setProfileDisplay("good-"+goodId+".jpg");
        }
        if (!updateById(good)) {
            return R.error().msg("更新商品信息失败");
        }
        return R.ok().msg("商品信息更新成功");
    }

    @Override
    @Transactional
    public R addGood(GoodDto goodDto) {
       Good good = new Good();
        R categoryR = categoryService.cateGoryById(goodDto.getCategoryId());
        if (!categoryR.getState()) {
            return categoryR;
        }
        BeanUtils.copyProperties(goodDto, good);
        Integer userId = ThreadLocalServlet.getUserId();
        good.setUserId(userId);
        good.setProfileDisplay("good-"+good.getId()+".jpg");
        if (!save(good)) {
            return R.error().msg("添加商品失败");
        }
        if(!saveImg(good, goodDto.getImg())) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return R.ok().msg("添加商品成功").data("data", good);
    }

    @Override
    public R deleteById(Integer goodId) {
        boolean admin = ThreadLocalServlet.isAdmin();
        Good good = getById(goodId);
        if (good == null) {
            return R.error().msg("该商品不存在!");
        }
        if(good.getUserId().intValue() != ThreadLocalServlet.getUserId().intValue() && !admin) {
            return R.error().msg("您没有权限删除该商品");
        }
        if(!removeById(goodId)) {
            return R.error().msg("删除该商品失败");
        }
        return R.ok().msg("删除成功").data("data", good);
    }

    @Override
    public List<GoodVo> goodVoList(Collection<Integer> ids) {
        if (ids.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
       return listByIds(ids).stream().map(GoodVo::toVo).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public R buyGood(Integer id, Integer buyCount) {
        if (buyCount<1) {
            return R.error().msg("请正确填写购买数量~");
        }
        synchronized ((id.intValue()+"").intern()){
            Good good = getById(id);
            Integer count = good.getCount();
            count -= buyCount;
            if (count < 0) {
                return R.error().msg("该商品已被售空啦~");
            }
            Object savepoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
            good.setCount(count);
            R orderR = orderService.addOrder(good, buyCount);
            if(!orderR.getState()) {
                TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savepoint);
                return orderR;
            }
            if (!updateById(good)) {
                TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savepoint);
                return R.error().msg("购买失败");
            }else {
                return R.ok().msg("购买成功");
            }
        }

    }
    @Override
    public boolean saveImg(Good good, MultipartFile getImg) {
        String goodImgName = "good-"+good.getId()+".jpg";
        good.setProfileDisplay(goodImgName);
         try {
            getImg.transferTo(new File(TradingProperties.getInstance().getSaveImgLocation(), goodImgName));
             updateById(good);
             return true;
         } catch (IOException e) {
             return false;
         }
        
    }

    /**
     * 最大推送数量
     */
    private static final long HOT_MAX_SIZE = 5;

    @Override
    public R getCarousel() {
        LambdaQueryWrapper<Good> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.gt(Good::getHots, new Timestamp(System.currentTimeMillis()));
        lambdaQueryWrapper.orderByDesc(Good::getGmtModified);
        List<GoodVo> resultData = new LinkedList<>();
        IPage<Good> result = page(new Page<Good>(1, HOT_MAX_SIZE), lambdaQueryWrapper);
        for (int i = 0; i < result.getRecords().size(); i++) {
            resultData.add(GoodVo.toVo(result.getRecords().get(i)));
        }
        // 若主动指定的推荐查询后还有空余位置则拿最新发布商品填补
        long dCount = HOT_MAX_SIZE - result.getRecords().size();
        if (dCount > 0) {
            IPage<Good> resultFree = page(new Page<Good>(1, dCount),
                    new LambdaQueryWrapper<Good>().orderByDesc(Good::getGmtCreate)
                            .notIn(!resultData.isEmpty(), Good::getId, resultData.stream()
                                    .map(GoodVo::getId).collect(Collectors.toSet()))
            );
            for (int i = 0; i < resultFree.getRecords().size(); i++) {
                resultData.add(GoodVo.toVo(resultFree.getRecords().get(i)));
            }
        }
        return R.ok().data("data", resultData);
    }

}
