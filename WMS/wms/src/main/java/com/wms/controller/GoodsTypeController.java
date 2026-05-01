package com.wms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.GoodsType;
import com.wms.entity.User;
import com.wms.service.GoodsTypeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/goodsType")
public class GoodsTypeController {

    @Autowired
    private GoodsTypeService goodsTypeService;

    @PostMapping("/save")
    public Result save(@RequestBody GoodsType goodsType) {
        return goodsTypeService.save(goodsType) ? Result.success() : Result.fail();
    }


    @PostMapping("/modify")
    public Result modify(@RequestBody GoodsType goodsType){
        return goodsTypeService.updateById(goodsType) ? Result.success() : Result.fail();
    }


    @GetMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        return goodsTypeService.removeById(id) ? Result.success() : Result.fail();
    }

    @PostMapping("/listPage")
    public Result listPageC1(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String name = (String) param.get("name");


        Page<GoodsType> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<GoodsType> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            queryWrapper.like(GoodsType ::getName, name);
        }

        IPage res = goodsTypeService.pageCC(page, queryWrapper);

        return Result.success(res.getRecords(), res.getTotal());


    }

    @GetMapping("/list")
    public Result list(){
        List list = goodsTypeService.lambdaQuery().list();
        return Result.success(list);
    }
}
