package com.liuscraft.tradingplatform.service;

import com.liuscraft.tradingplatform.entity.Good;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liuscraft.tradingplatform.entity.dto.GoodDto;
import com.liuscraft.tradingplatform.entity.dto.PageDto;
import com.liuscraft.tradingplatform.entity.vo.GoodVo;
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
public interface IGoodService extends IService<Good> {
R getList(Integer currentPage, Integer limit, Integer categoryId, Integer userId);

    R goodById(Integer goodId);

    R updateGoodById(Integer goodId, GoodDto goodDto);

    R addGood(GoodDto goodDto);

    R deleteById(Integer goodId);
    List<GoodVo> goodVoList(Collection<Integer> ids);

    R buyGood(Integer id, Integer buyCount);
}
