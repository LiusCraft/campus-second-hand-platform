package com.liuscraft.tradingplatform.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liuscraft.luckpermission.annotations.LuckVerify;
import com.liuscraft.tradingplatform.entity.Category;
import com.liuscraft.tradingplatform.entity.Good;
import com.liuscraft.tradingplatform.entity.dto.GoodDto;
import com.liuscraft.tradingplatform.entity.dto.PageDto;
import com.liuscraft.tradingplatform.service.ICategoryService;
import com.liuscraft.tradingplatform.service.IGoodService;
import com.liuscraft.tradingplatform.utils.R;
import com.liuscraft.tradingplatform.utils.threadlocal.ThreadLocalServlet;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LiusCraft
 * @since 2023-03-09
 */
@RestController
@RequestMapping("goods")
@LuckVerify(children = true)
public class GoodController {
    @Resource
    IGoodService goodService;
    @Resource
    ICategoryService categoryService;
    @GetMapping("category/{category}")
    @LuckVerify(ignore = true)
    public R getList(@PathVariable(value = "category") Integer categoryId, Integer page, Integer limit) {
        if (page==null || page == 0) page = 1;
        if (limit == null || limit<10) limit = 10;
        return goodService.getList(page, limit, categoryId, null);
    }

    @GetMapping("user/{category}")
    @LuckVerify
    public  R getUserGoodList(@PathVariable(value = "category") Integer categoryId, PageDto pageDto) {
        String userId = ThreadLocalServlet.getValue("userId");
        assert userId != null;
        return goodService.getList(pageDto.getPage(), pageDto.getLimit(), categoryId, Integer.valueOf(userId));
    }

    @GetMapping("{id}")
    @LuckVerify(ignore = true)
    public R getGoodInfo(@PathVariable("id") Integer goodId) {
        return goodService.goodById(goodId);
    }

    @PutMapping("{id}")
    public R updateGoodInfo(@PathVariable("id") Integer goodId, @RequestBody GoodDto goodDto) {
        return goodService.updateGoodById(goodId, goodDto);
    }

    @PostMapping
    public R addNewGood(@RequestBody GoodDto goodDto) {
        return goodService.addGood(goodDto);
    }

    @DeleteMapping("{id}")
    public R deleteGood(@PathVariable("id") Integer goodId) {
        Boolean admin = ThreadLocalServlet.isAdmin();
        Good good = goodService.getById(goodId);
        if (good == null || (good.getUserId().intValue() != ThreadLocalServlet.getUserId().intValue() && !admin))
            return R.error().msg("商品不存在！");
        return goodService.deleteById(goodId);
    }

    @GetMapping("buy/{id}")
    public R buyGood(@PathVariable Integer id, Integer count) {
        return goodService.buyGood(id, count);
    }
}

