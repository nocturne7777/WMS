package com.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.Goods;
import com.wms.entity.Menu;
import com.wms.entity.Record;
import com.wms.service.GoodsService;
import com.wms.service.MenuService;
import com.wms.service.RecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @Autowired
    private GoodsService goodsService;

    @PostMapping("/save")
    public Result save(@RequestBody Record record) {
        // 1. 根据传入的物品ID，从数据库获取最新的物品信息
        Goods goods = goodsService.getById(record.getGoods());
        int requestedCount = record.getCount(); // 用户希望入库或出库的数量

        // 2. 判断是入库还是出库
        if (record.getAction() == false) { // false 代表出库
            // 核心校验：如果要出库的数量 > 当前数据库中的库存
            if (goods.getCount() < requestedCount) {
                return Result.fail(); // 直接返回失败，并告知原因
            }

            // 计算新的库存，并更新 goods 表
            int newStock = goods.getCount() - requestedCount;
            goods.setCount(newStock);
            goodsService.updateById(goods);

            // 将 record 记录中的数量设置为负数，表示出库记录
            record.setCount(-requestedCount);
            return recordService.save(record) ? Result.success() : Result.fail();

        } else { // true 代表入库
            // 计算新的库存，并更新 goods 表
            int newStock = goods.getCount() + requestedCount;
            goods.setCount(newStock);
            goodsService.updateById(goods);

            // 保存正数的入库记录
            return recordService.save(record) ? Result.success() : Result.fail();
        }
    }
    @PostMapping("/listPage")
    public Result listPageC1(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String name = (String) param.get("name");
        String goodsType = (String) param.get("goodstype");
        String storage = (String) param.get("storage");
        String roleId = (String) param.get("roleId");
        String userId = (String) param.get("userId");


        Page<Record> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();

        if("2".equals(roleId)) {
            queryWrapper.apply("a.goods=b.id" + userId);
        }

        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            //queryWrapper.like(Record ::getName, name);
            queryWrapper.like("b.name",name);
        }

        if (StringUtils.isNotBlank(storage) && !"null".equals(storage)) {
            queryWrapper.eq("c.id", storage);
        }
        if (StringUtils.isNotBlank(goodsType) && !"null".equals(goodsType)) {
            queryWrapper.eq("d.id", goodsType);
        }

        IPage res = recordService.pageCC(page, queryWrapper);

        return Result.success(res.getRecords(), res.getTotal());


    }
}
