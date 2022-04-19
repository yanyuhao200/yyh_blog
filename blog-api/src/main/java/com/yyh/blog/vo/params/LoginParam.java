package com.yyh.blog.vo.params;

import lombok.Data;

/**
 * @Author: yyh
 * @Date: 2022/04/18 15:35
 */

@Data
public class LoginParam {

    private String account;

    private String password;

    private String nickname;
}
