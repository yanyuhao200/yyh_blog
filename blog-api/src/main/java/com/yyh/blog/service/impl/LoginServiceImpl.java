package com.yyh.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yyh.blog.dao.pojo.SysUser;
import com.yyh.blog.service.LoginService;
import com.yyh.blog.service.SysUserService;
import com.yyh.blog.utils.JWTUtils;
import com.yyh.blog.vo.ErrorCode;
import com.yyh.blog.vo.Result;
import com.yyh.blog.vo.params.LoginParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;


import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: yyh
 * @Date: 2022/04/18 15:38
 */

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    private static final String salt = "yyh!@#";

    /**
     * 登录功能
     *
     * @param loginParam
     * @return
     */
    @Override
    public Result login(LoginParam loginParam) {
        /**
         * 1. 检查参数是否合法
         * 2. 根据用户名和密码去user中查询 是否存在
         * 3. 如果不存在 登录失败
         * 4. 如果存在 使用jwt技术 生成token 返回给前端
         * 5. 把token放入redis当中  redis存储映射token: user信息 设置过期时间（登录认证的时候 先认证token字符串是否合法 再去redis认证是否存在）
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        password = DigestUtils.md5Hex(password + salt);

        SysUser sysUser = sysUserService.findUser(account, password);
        if(sysUser==null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }

        String token = JWTUtils.createToken(sysUser.getId());
        // 向redis中存入token：user信息
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);
    }

    //用户登录token校验
    @Override
    public SysUser checkToken(String token) {
        if (StringUtils.isBlank(token)){
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if (stringObjectMap == null){
            return null;
        }
        // 从redis中读取token: user信息
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)){
            return null;
        }
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;
    }

    /**
     * 退出登录
     *
     * @param token
     * @return
     */
    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_"+token);
        return Result.success(null);
    }

    //测试登录 添加密码
    public static void main(String[] args) {
        String password="admin";
        password = DigestUtils.md5Hex(password + salt);
        System.out.println("password:"+password);
    }
}
