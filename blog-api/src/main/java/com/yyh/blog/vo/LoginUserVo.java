package com.yyh.blog.vo;

import lombok.Data;

/**
 * @Author: yyh
 * @Date: 2022/04/18 21:00
 */

@Data
public class LoginUserVo {

    private Long id;

    private String account;

    private String nickname;

    private String avatar;
}
