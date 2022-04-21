package com.yyh.blog.controller;

import com.yyh.blog.service.LoginService;
import com.yyh.blog.vo.Result;
import com.yyh.blog.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yyh
 * @Date: 2022/04/18 15:32
 */

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping
    public Result longin(@RequestBody LoginParam loginParam) {
        // 登录 验证用户 访问用户表
        return loginService.login(loginParam);
    }
}
