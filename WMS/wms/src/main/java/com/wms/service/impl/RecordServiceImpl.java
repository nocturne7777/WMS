package com.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wms.entity.GoodsType;
import com.wms.mapper.GoodsTypeMapper;
import com.wms.mapper.RecordMapper;
import com.wms.service.RecordService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.wms.entity.Record;

/**
 * @author Adachi
 * @description 针对表【record】的数据库操作Service实现
 * @createDate 2025-06-17 23:05:55
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record>
        implements RecordService {

    @Resource
    private RecordMapper recordMapper;
    @Override
    public IPage pageC(IPage<Record> page) {
        return recordMapper.pageC(page);
    }

    @Override
    public IPage pageCC(Page<Record> page, Wrapper wrapper) {
        return recordMapper.pageCC(page, wrapper);
    }


}
