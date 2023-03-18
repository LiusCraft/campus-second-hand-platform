package com.liuscraft.tradingplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuscraft.tradingplatform.entity.Category;
import com.liuscraft.tradingplatform.entity.vo.CategoryVo;
import com.liuscraft.tradingplatform.mapper.CategoryMapper;
import com.liuscraft.tradingplatform.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuscraft.tradingplatform.utils.R;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Override
    public R categoryByName(String categoryName) {
        Category category = getOne(new LambdaQueryWrapper<Category>()
                .eq(Category::getName, categoryName)
                .eq(Category::getDeleted, false));
        if (category == null) return R.error().msg("未找到该分类");
        return R.ok().data("data", category);
    }

    @Override
    public R cateGoryById(Integer id) {
        Category category = getById(id);
        if (category == null) return R.error().msg("未找到该分类");
        return R.ok().data("data", category);
    }

    @Override
    public List<CategoryVo> cateGoryList(Collection<Integer> ids) {
        if (ids.isEmpty()) return Collections.EMPTY_LIST;
        return listByIds(ids).stream().map(CategoryVo::toVo).collect(Collectors.toList());
    }

    @Override
    public R getList(Integer currentPage, Integer limit) {
        IPage<Category> page = page(new Page<Category>(currentPage, limit), new LambdaQueryWrapper<Category>().orderByDesc(Category::getId));
        return R.ok().data("data", page.getRecords().stream().map(CategoryVo::toVo)).data("count", page.getTotal());
    }
}
