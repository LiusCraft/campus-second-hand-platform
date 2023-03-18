package com.liuscraft.tradingplatform.entity.dto;

import lombok.Data;

/**
 * @author LiusCraft
 * @date 2023/3/9 15:18
 */
@Data
public class PageDto {
    private Integer page = 1;
    private Integer limit = 10;
}
