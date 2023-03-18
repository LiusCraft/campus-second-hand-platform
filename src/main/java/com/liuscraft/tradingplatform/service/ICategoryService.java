package com.liuscraft.tradingplatform.service;

import com.liuscraft.tradingplatform.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liuscraft.tradingplatform.entity.vo.CategoryVo;
import com.liuscraft.tradingplatform.utils.R;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiusCraft
 * @since 2023-03-09
 */
public interface ICategoryService extends IService<Category> {

    R categoryByName(String categoryName);
    R cateGoryById(Integer id);
    List<CategoryVo> cateGoryList(Collection<Integer> ids);

    R getList(Integer currentPage, Integer limit);
}
