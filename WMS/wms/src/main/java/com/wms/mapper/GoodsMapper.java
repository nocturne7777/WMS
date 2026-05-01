package com.wms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.wms.entity.Goods;
import com.wms.entity.GoodsType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Adachi
 * @description 针对表【goods】的数据库操作Mapper
 * @createDate 2025-06-17 20:23:49
 * @Entity .entity.Goods
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

    IPage pageC(IPage<Goods> page);
    IPage pageCC(IPage<Goods> page, @Param(Constants.WRAPPER) Wrapper wrapper);

}