package com.wms.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Adachi
* @description 针对表【user】的数据库操作Service
* @createDate 2025-06-15 17:32:42
*/
public interface UserService extends IService<User> {

    IPage pageC(IPage<User> page);

    IPage pageCC(Page<User> page, Wrapper wrapper);
}
