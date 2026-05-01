package com.wms.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.wms.entity.GoodsType;
import com.wms.entity.Storage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Adachi
 * @description 针对表【goodstype】的数据库操作Mapper
 * @createDate 2025-06-17 19:49:44
 * @Entity .entity.Goodstype
 */

@Mapper
public interface GoodsTypeMapper extends BaseMapper<GoodsType> {
    IPage pageC(IPage<GoodsType> page);
    IPage pageCC(IPage<GoodsType> page, @Param(Constants.WRAPPER) Wrapper wrapper);

}
