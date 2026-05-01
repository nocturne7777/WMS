package com.wms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.entity.User;
import org.apache.ibatis.annotations.Mapper;
import com.wms.entity.Storage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Adachi
 * @description 针对表【storage】的数据库操作Mapper
 * @createDate 2025-06-17 17:54:13
 * @Entity storage.entity.Storage
 */

@Mapper
public interface StorageMapper extends BaseMapper<Storage> {

    IPage pageC(IPage<Storage> page);
    IPage pageCC(IPage<Storage> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}




