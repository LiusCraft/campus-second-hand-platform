package com.liuscraft.tradingplatform.controller.admin;


import com.liuscraft.luckpermission.annotations.LuckVerify;
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
    public R updateGoodInfo(@PathVariable("id") Integer goodId, @RequestBody GoodDto goodDto) {
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

}

