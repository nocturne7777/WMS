package com.wms.service;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wms.entity.Goods;
import com.wms.entity.GoodsType;

/**
 * @author Adachi
 * @description 针对表【goods】的数据库操作Service
 * @createDate 2025-06-17 20:23:49
 */
public interface GoodsService extends IService<Goods> {

    IPage pageC(IPage<Goods> page);

    IPage pageCC(Page<Goods> page, Wrapper wrapper);

}