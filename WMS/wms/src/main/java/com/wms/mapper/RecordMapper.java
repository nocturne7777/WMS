package com.wms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.wms.entity.Goods;
import org.apache.ibatis.annotations.Mapper;
import com.wms.entity.Record;
import org.apache.ibatis.annotations.Param;

/**
 * @author Adachi
 * @description 针对表【record】的数据库操作Mapper
 * @createDate 2025-06-17 23:05:55
 * @Entity .entity.Record
 */
@Mapper
public interface RecordMapper extends BaseMapper<Record> {


    IPage pageC(IPage<Record> page);
    IPage pageCC(IPage<Record> page, @Param(Constants.WRAPPER) Wrapper wrapper);

}

