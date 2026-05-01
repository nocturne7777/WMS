package com.wms.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wms.entity.GoodsType;
import com.wms.entity.Menu;
import com.wms.entity.Storage;

import java.security.Provider;

/**
 * @author Adachi
 * @description 针对表【menu】的数据库操作Service
 * @createDate 2025-06-17 15:29:25
 */
public interface GoodsTypeService extends IService<GoodsType> {

    IPage pageC(IPage<GoodsType> page);

    IPage pageCC(Page<GoodsType> page, Wrapper wrapper);

}
