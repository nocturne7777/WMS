package com.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wms.entity.GoodsType;
import com.wms.entity.Storage;
import com.wms.mapper.GoodsTypeMapper;
import com.wms.mapper.StorageMapper;
import com.wms.service.GoodsTypeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class GoodsTypeServiceImpl extends ServiceImpl<GoodsTypeMapper, GoodsType>
        implements GoodsTypeService {

    @Resource
    private GoodsTypeMapper goodsTypeMapper;
    @Override
    public IPage pageC(IPage<GoodsType> page) {
        return goodsTypeMapper.pageC(page);
    }

    @Override
    public IPage pageCC(Page<GoodsType> page, Wrapper wrapper) {
        return goodsTypeMapper.pageCC(page, wrapper);
    }


}

