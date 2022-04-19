package com.yyh.blog.controller;

import com.yyh.blog.service.LoginService;
import com.yyh.blog.vo.LoginUserVo;
import com.yyh.blog.vo.Result;
import com.yyh.blog.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: yyh
 * @Date: 2022/04/19 21:44
 */

@RestController
@RequestMapping("register")
public class RegisterController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result register(@RequestBody LoginParam loginParam){
        // sso 单点登录 后期如果把登录注册功能 提出去 （单独的服务 可以独立提供接口服务）
        return loginService.register(loginParam);
    }
}
