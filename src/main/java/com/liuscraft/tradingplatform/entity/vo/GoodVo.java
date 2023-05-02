package com.liuscraft.tradingplatform.entity.vo;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.liuscraft.tradingplatform.entity.Good;

import jdk.nashorn.internal.objects.annotations.Function;
import lombok.Data;

/**
 * @author LiusCraft
 * @date 2023/3/9 18:51
 */
@Data
public class GoodVo {
    private Integer id;
    private String name;
    private String description;
    private Integer count;
    private String profileDisplay;
    private UserVo user;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModified;
    private CategoryVo category;
    private boolean isHot = false;
    @Function
    public static GoodVo toVo(Good good){
        GoodVo goodVo= new GoodVo();
        if (good.getHots() != null) {
            goodVo.setHot(good.getHots().getTime() - System.currentTimeMillis()>1000);
        }
        BeanUtils.copyProperties(good, goodVo);
        return goodVo;
    }
}
