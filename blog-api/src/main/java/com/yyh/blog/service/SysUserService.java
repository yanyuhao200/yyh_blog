package com.yyh.blog.service;

import com.yyh.blog.dao.pojo.SysUser;
import com.yyh.blog.vo.Result;

public interface SysUserService {

    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);

    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
    Result findUserByToken(String token);

    /**
     * 根据账户查找账户
     * @param account
     * @return
     */
    SysUser findUserByAccount(String account);

    /**
     * 保存
     * @param sysUser
     */
    void save(SysUser sysUser);
}
