package com.wms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.Goods;
import com.wms.entity.GoodsType;
import com.wms.service.GoodsService;
import com.wms.service.GoodsTypeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController
@RequestMapping("/Goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @PostMapping("/save")
    public Result save(@RequestBody Goods goods) {
        return goodsService.save(goods) ? Result.success() : Result.fail();
    }


    @PostMapping("/modify")
    public Result modify(@RequestBody Goods goods){
        return goodsService.updateById(goods) ? Result.success() : Result.fail();
    }


    @GetMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        return goodsService.removeById(id) ? Result.success() : Result.fail();
    }

    @PostMapping("/listPage")
    public Result listPageC1(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String name = (String) param.get("name");
        String goodsType = (String) param.get("goodstype");
        String storage = (String) param.get("storage");


        Page<Goods> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            queryWrapper.like(Goods ::getName, name);
        }

        if (StringUtils.isNotBlank(storage) && !"null".equals(storage)) {
            queryWrapper.eq(Goods ::getStorage, storage);
        }
        if (StringUtils.isNotBlank(goodsType) && !"null".equals(goodsType)) {
            queryWrapper.eq(Goods ::getGoodstype, goodsType);
        }

        IPage res = goodsService.pageCC(page, queryWrapper);

        return Result.success(res.getRecords(), res.getTotal());


    }
}
