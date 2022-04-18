package com.yyh.blog.config;

import com.yyh.blog.service.LoginService;
import com.yyh.blog.service.SysUserService;
import com.yyh.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: yyh
 * @Date: 2022/04/18 20:29
 */

@RestController
@RequestMapping("users")
public class UsersController {

    @Autowired
    private SysUserService sysUserService;



    // /users/currentUser
    @GetMapping("currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token){
        return sysUserService.findUserByToken(token);
    }
}
