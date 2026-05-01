package com.wms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.User;
import com.wms.service.MenuService;
import com.wms.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.wms.entity.Menu;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public List<User> list() {
        return userService.list();
    }

    ///CURD
    //ADD
    @PostMapping("/save")
    public Result save(@RequestBody User user) {

        return userService.save(user) ? Result.success() : Result.fail();
    }

    //Change
    @PostMapping("/mod")
    public boolean mod(@RequestBody User user) {
        return userService.updateById(user);
    }

    @GetMapping("/findByNo")
    public Result getById(@RequestParam String no) {
       List list = userService.lambdaQuery().eq(User::getNo, no).list();
       return list.size() > 0 ? Result.success(list) : Result.fail();
    }

    @PostMapping("/modify")
    public Result modify(@RequestBody User user){
        return userService.updateById(user) ? Result.success() : Result.fail();
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        List list = userService.lambdaQuery().eq(User::getNo, user.getNo())
                .eq(User::getPassword, user.getPassword()).list();


        if(list.size()>0){
            User user1 = (User) list.get(0);
            List menuList = menuService.lambdaQuery().like(Menu::getMenuright, user1.getRoleId()).list();
            HashMap res = new HashMap();
            res.put("user", user1);
            res.put("menu",menuList);
            return Result.success(res);

        }


        return Result.fail();
    }

    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody User user) {
        return userService.saveOrUpdate(user);
    }

    //Delete
    @GetMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        return userService.removeById(id) ? Result.success() : Result.fail();
    }

    //Query, 模糊
    @PostMapping("/listP")
    public Result listP(@RequestBody User user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNoneBlank(user.getName())) {
            lambdaQueryWrapper.like(User::getName, user.getName());
        }
        return Result.success(userService.list(lambdaQueryWrapper));
    }

    @PostMapping("/listPage")
    public List<User> ListPage(@RequestBody QueryPageParam query) {

        HashMap param = query.getParam();
        return null;
    }

    @PostMapping("/listPageC")
    public Result listPageC(@RequestBody QueryPageParam query) {

        int pageNum = query.getPageNum();
        int pageSize = query.getPageSize();
        String name = (String) query.getParam().get("name");

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(User::getName, name);
//
//        HashMap param = query.getParam();
//        String name = (String)param.get("name");
//
//        System.out.println("name: " + name);

        Page<User> page = new Page(1, 2);
        page.setCurrent(pageNum);
        page.setSize(pageSize);


        IPage res = userService.pageCC(page, queryWrapper);

        System.out.println("TotalNum:" + res.getTotal());

        return Result.success(res.getRecords(), res.getTotal());

    }

    @PostMapping("/listPageC1")
    public Result listPageC1(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String name = (String) param.get("name");
        String sex = String.valueOf(param.get("sex"));

        String roleId = (String) param.get("roleId");


        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            queryWrapper.like(User::getName, name);
        }
        if (StringUtils.isNotBlank(sex)) {
            queryWrapper.eq(User::getSex, sex);

        }
        if (StringUtils.isNotBlank(roleId)) {
            queryWrapper.eq(User::getRoleId, roleId);
        }
        IPage res = userService.pageCC(page, queryWrapper);

        return Result.success(res.getRecords(), res.getTotal());


    }
}