package com.yyh.blog.controller;

import com.yyh.blog.dao.pojo.SysUser;
import com.yyh.blog.utils.UserThreadLocal;
import com.yyh.blog.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping
    public Result test(){
        SysUser sysUser =  UserThreadLocal.get();
        System.out.println(sysUser);
        return Result.success(null);
    }
}

