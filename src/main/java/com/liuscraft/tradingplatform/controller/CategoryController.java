package com.liuscraft.tradingplatform.controller;
import com.liuscraft.tradingplatform.entity.dto.PageDto;
import com.liuscraft.tradingplatform.service.ICategoryService;
import com.liuscraft.tradingplatform.utils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("categorys")
public class CategoryController {

    @Resource
    ICategoryService categoryService;

    @GetMapping
    public R getList(PageDto pageDto) {
        return categoryService.getList(pageDto.getPage(), pageDto.getLimit());
    }
}

