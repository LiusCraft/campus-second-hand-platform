package com.liuscraft.tradingplatform.controller.admin;

import com.liuscraft.tradingplatform.entity.Category;
import com.liuscraft.tradingplatform.entity.dto.CategoryDto;
import com.liuscraft.tradingplatform.service.ICategoryService;
import com.liuscraft.tradingplatform.utils.R;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author LiusCraft
 * @date 2023/3/18 21:23
 */
@RestController
@RequestMapping("admin/category")
public class AdminCategoryController {
    @Resource
    ICategoryService categoryService;
    @PostMapping
    public R addCategory(@RequestBody CategoryDto categoryDto) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDto, category);
        if(!categoryService.save(category))
            return R.error().msg("添加失败");
        return R.ok().msg("添加成功");
    }
    @PutMapping("{id}")
    public R updateCategory(@PathVariable Integer id, @RequestBody CategoryDto categoryDto) {
        Category category = categoryService.getById(id);
        if (category == null) return R.error().msg("不存在该分类");
        BeanUtils.copyProperties(categoryDto, category);
        if(!categoryService.updateById(category))
            return R.error().msg("更新失败");
        return R.ok().msg("更新成功");
    }
    @DeleteMapping("{id}")
    public R deleteCategory(@PathVariable Integer id) {
        try {
            if(!categoryService.removeById(id))
                return R.error().msg("删除失败，可能它不存在！");
            return R.ok().msg("删除成功~");
        }catch (Exception e) {
            return R.error().msg("删除失败，当前分类可能存在商品");
        }
    }
}
