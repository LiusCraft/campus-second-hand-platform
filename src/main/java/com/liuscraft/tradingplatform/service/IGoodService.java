package com.liuscraft.tradingplatform.service;

import java.util.Collection;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuscraft.tradingplatform.entity.Good;
import com.liuscraft.tradingplatform.entity.dto.GoodDto;
import com.liuscraft.tradingplatform.entity.vo.GoodVo;
import com.liuscraft.tradingplatform.utils.R;

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

    boolean saveImg(Good good, MultipartFile img);

    R getCarousel();

}
