package com.liuscraft.tradingplatform.controller.admin;


import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuscraft.tradingplatform.entity.Good;
import com.liuscraft.tradingplatform.mapper.GoodMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liuscraft.luckpermission.annotations.LuckVerify;
import com.liuscraft.tradingplatform.entity.dto.GoodDto;
import com.liuscraft.tradingplatform.service.IGoodService;
import com.liuscraft.tradingplatform.utils.R;
import com.liuscraft.tradingplatform.utils.threadlocal.ThreadLocalServlet;

import java.sql.Timestamp;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LiusCraft
 * @since 2023-03-09
 */
@RestController
@RequestMapping("admin/goods")
public class AdminGoodController {
    @Resource
    IGoodService goodService;
    @GetMapping("category/{category}")
    @LuckVerify(ignore = true)
    public R getList(@PathVariable(value = "category") Integer categoryId, Integer page, Integer limit) {
        if (page==null || page == 0) page = 1;
        if (limit == null || limit<10) limit = 10;
        return goodService.getList(page, limit, categoryId, null);
    }
    @GetMapping("{id}")
    @LuckVerify(ignore = true)
    public R getGoodInfo(@PathVariable("id") Integer goodId) {
        return goodService.goodById(goodId);
    }

    @PutMapping("{id}")
    public R updateGoodInfo(@PathVariable("id") Integer goodId, GoodDto goodDto) {
        ThreadLocalServlet.editor.isAdmin();
        return goodService.updateGoodById(goodId, goodDto);
    }

    @PostMapping
    public R addNewGood(@RequestBody GoodDto goodDto) {
        return goodService.addGood(goodDto);
    }

    @DeleteMapping("{id}")
    public R deleteGood(@PathVariable("id") Integer goodId) {
        ThreadLocalServlet.editor.isAdmin();
        return goodService.deleteById(goodId);
    }

    final long MIN_HOT_EXPIRE = 24 * 60 * 60 * 1000;
    @PutMapping("hot/{goodId}")
    public R setGoodHot(@PathVariable Integer goodId, Long hotExpire) {
        GoodMapper baseMapper = (GoodMapper) goodService.getBaseMapper();
        if (hotExpire == null) {
            if(!baseMapper.setHotsById(goodId, new Timestamp(System.currentTimeMillis()-1000))){
                return R.error().msg("可能不存在该商品");
            }
            return R.ok().msg("该商品已取消主动推荐");
        }
        if (hotExpire < MIN_HOT_EXPIRE){
            return R.error().msg("请设置正常的推荐到期时间");
        }
        if(!baseMapper.setHotsById(goodId, new Timestamp(System.currentTimeMillis() + hotExpire))){
            return R.error().msg("可能不存在该商品");
        }
        return R.ok().msg("该商品已被主动推荐");
    }

}

