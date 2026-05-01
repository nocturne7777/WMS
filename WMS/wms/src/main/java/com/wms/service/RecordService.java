package com.wms.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wms.entity.GoodsType;
import com.wms.entity.Record;

/**
 * @author Adachi
 * @description 针对表【record】的数据库操作Service
 * @createDate 2025-06-17 23:05:55
 */
public interface RecordService extends IService<Record> {

    IPage pageC(IPage<Record> page);

    IPage pageCC(Page<Record> page, Wrapper wrapper);

}
