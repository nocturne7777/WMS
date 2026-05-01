package com.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wms.entity.Storage;
import com.wms.entity.User;
import com.wms.mapper.UserMapper;
import com.wms.service.StorageService;
import com.wms.mapper.StorageMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class StorageServiceImpl extends ServiceImpl<StorageMapper, Storage>
        implements StorageService{

    @Resource
    private StorageMapper storageMapper;
    @Override
    public IPage pageC(IPage<Storage> page) {
        return storageMapper.pageC(page);
    }

    @Override
    public IPage pageCC(Page<Storage> page, Wrapper wrapper) {
        return storageMapper.pageCC(page, wrapper);
    }

}




