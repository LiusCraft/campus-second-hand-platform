package com.liuscraft.tradingplatform.mapper;

import com.liuscraft.tradingplatform.entity.Good;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LiusCraft
 * @since 2023-03-09
 */
public interface GoodMapper extends BaseMapper<Good> {

    Boolean setHotsById(@Param("id") Integer id, @Param("hotsTime") Timestamp hotsTime);

}
