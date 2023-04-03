package com.liuscraft.tradingplatform.entity.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

/**
 * @author LiusCraft
 * @date 2023/3/9 16:58
 */
@Data
public class GoodDto {
    @Min(value = 1,message =  "请为商品选择分类")
    private Integer categoryId;
    @Range(max = 100, min = 1, message = "商品数量最少为一")
    private Integer count;
    @NotBlank(message = "请完成商品名称")
    private String name;
    @NotBlank(message = "请完善商品描述")
    private String description;

    MultipartFile img;
}
