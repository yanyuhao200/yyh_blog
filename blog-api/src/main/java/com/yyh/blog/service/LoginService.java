package com.yyh.blog.service;

import com.yyh.blog.dao.pojo.SysUser;
import com.yyh.blog.vo.Result;
import com.yyh.blog.vo.params.LoginParam;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: yyh
 * @Date: 2022/04/18 15:34
 */

@Mapper
public interface LoginService {

    /**
     * 登录功能
     * @param loginParam
     * @return
     */
    Result login(LoginParam loginParam);

    SysUser checkToken(String token);

    /**
     * 退出登录
     * @param token
     * @return
     */
    Result logout(String token);

    /**
     * 注册
     * @param loginParam
     * @return
     */
    Result register(LoginParam loginParam);
}
