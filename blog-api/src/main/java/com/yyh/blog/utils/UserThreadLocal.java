package com.yyh.blog.utils;

import com.yyh.blog.dao.pojo.SysUser;

/**
 * @Author: yyh
 * @Date: 2022/04/20 14:38
 */

public class UserThreadLocal {

    private UserThreadLocal(){}

    // 线程变量隔离的
    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void put(SysUser sysUser){
        LOCAL.set(sysUser);
    }

    public static SysUser get(){
        return LOCAL.get();
    }

    public static void remove(){
        LOCAL.remove();
    }

}
