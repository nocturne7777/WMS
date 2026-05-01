package com.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wms.entity.GoodsType;
import com.wms.mapper.GoodsMapper;
import com.wms.mapper.GoodsTypeMapper;
import com.wms.service.GoodsService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.wms.entity.Goods;

/**
 * @author Adachi
 * @description 针对表【goods】的数据库操作Service实现
 * @createDate 2025-06-17 20:23:49
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods>
        implements GoodsService {
    @Resource
    private GoodsMapper goodsMapper;
    @Override
    public IPage pageC(IPage<Goods> page) {
        return goodsMapper.pageC(page);
    }

    @Override
    public IPage pageCC(Page<Goods> page, Wrapper wrapper) {
        return goodsMapper.pageCC(page, wrapper);
    }

}
