package com.liuscraft.tradingplatform.controller;


import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liuscraft.luckpermission.annotations.LuckVerify;
import com.liuscraft.tradingplatform.config.TradingProperties;
import com.liuscraft.tradingplatform.entity.Good;
import com.liuscraft.tradingplatform.entity.dto.GoodDto;
import com.liuscraft.tradingplatform.entity.dto.PageDto;
import com.liuscraft.tradingplatform.service.ICategoryService;
import com.liuscraft.tradingplatform.service.IGoodService;
import com.liuscraft.tradingplatform.utils.R;
import com.liuscraft.tradingplatform.utils.threadlocal.ThreadLocalServlet;

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
    public R getList(@PathVariable(value = "category") Integer categoryId, PageDto pageDto) {
        return goodService.getList(pageDto.getPage(), pageDto.getLimit(), categoryId, null);
    }

    @GetMapping("user/{category}")
    @LuckVerify
    public  R getUserGoodList(@PathVariable(value = "category") Integer categoryId, PageDto pageDto) {

        return goodService.getList(pageDto.getPage(), pageDto.getLimit(), categoryId, ThreadLocalServlet.getUserId());
    }

    @GetMapping("{id}")
    @LuckVerify(ignore = true)
    public R getGoodInfo(@PathVariable("id") Integer goodId) {
        return goodService.goodById(goodId);
    }

    @GetMapping(value = "img/{id}", produces={MediaType.IMAGE_JPEG_VALUE})
    @LuckVerify(ignore = true)
    public byte[] getGoodImg(@PathVariable Integer id) throws Exception {
        File file = new File(TradingProperties.getInstance().getSaveImgLocation(), "good-"+id+".jpg");
        if (!file.exists()) {
            file = new File(TradingProperties.getInstance().getSaveImgLocation(), "404.png");
            if (!file.exists()) {
                // 若指定的目录中未自定义404.png文件则加载包内默认的404.png文件
                file = new File(getClass().getClassLoader().getResource("404.png").getPath());
            }
        }
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] bytes = new byte[(int) file.length()];
            fileInputStream.read(bytes);
            return bytes;
        }catch(Exception e) {
            throw new Exception("该资源不存在");
        }
    }

    @PutMapping("{id}")
    public R updateGoodInfo(@PathVariable("id") Integer goodId, @Valid GoodDto goodDto) {
        return goodService.updateGoodById(goodId, goodDto);
    }

    @PostMapping
    public R addNewGood(@Valid GoodDto goodDto) {
        if (goodDto.getImg()==null || goodDto.getImg().isEmpty()) {
            return R.error().msg("请设置封面图");
        }
        return goodService.addGood(goodDto);
    }

    @DeleteMapping("{id}")
    public R deleteGood(@PathVariable("id") Integer goodId) {
        Boolean admin = ThreadLocalServlet.isAdmin();
        Good good = goodService.getById(goodId);
        if (good == null) {
            return R.error().msg("商品不存在！");
        }
        if ((good.getUserId().intValue() != ThreadLocalServlet.getUserId().intValue() && !admin)) {
            return R.error().msg("该商品不属于您！");
        }
        return goodService.deleteById(goodId);
    }

    @GetMapping("buy/{id}")
    public R buyGood(@PathVariable Integer id, Integer count) {
        return goodService.buyGood(id, count);
    }

    @GetMapping("hots")
    public R getHotsCarousel() {
        return goodService.getCarousel();
    }
}

