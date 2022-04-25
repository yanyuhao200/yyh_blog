package com.yyh.blog.admin.controller;

import com.yyh.blog.admin.model.param.PageParam;
import com.yyh.blog.admin.pojo.Permission;
import com.yyh.blog.admin.service.impl.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vo.Result;

/**
 * @Author: yyh
 * @Date: 2022/04/25 12:56
 */

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private PermissionService permissionService;

    @PostMapping("permission/permissionList")
    public Result permissionList(@RequestBody PageParam pageParam) {
        return permissionService.listPermission(pageParam);
    }

    @PostMapping("permission/add")
    public Result add(@RequestBody Permission permission) {
        return permissionService.add(permission);
    }

    @PostMapping("permission/update")
    public Result update(@RequestBody Permission permission) {
        return permissionService.update(permission);
    }

    @GetMapping("permission/delete/{id}")
    public Result delete(@PathVariable("id") Long id) {
        return permissionService.delete(id);
    }
}
