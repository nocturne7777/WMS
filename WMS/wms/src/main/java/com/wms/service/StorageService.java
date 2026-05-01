package com.wms.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.entity.Storage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wms.entity.User;

/**
 * @author Adachi
 * @description 针对表【storage】的数据库操作Service
 * @createDate 2025-06-17 17:54:13
 */
public interface StorageService extends IService<Storage> {

    IPage pageC(IPage<Storage> page);

    IPage pageCC(Page<Storage> page, Wrapper wrapper);

}
