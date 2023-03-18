package com.liuscraft.tradingplatform.entity.vo;

import com.baomidou.mybatisplus.core.conditions.interfaces.Func;
import com.liuscraft.tradingplatform.entity.Category;
import jdk.nashorn.internal.objects.annotations.Function;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @author LiusCraft
 * @date 2023/3/9 21:43
 */
@Data
public class CategoryVo {
    private String name;
    private Integer id;
    private String description;

    @Function
    public static CategoryVo toVo(Category category) {
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }
}
