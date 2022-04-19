package com.yyh.blog.dao.pojo;

import lombok.Data;

@Data
public class SysUser {

    // @TableId(type = IdType.ASSIGN_ID) // 默认id类型
    // 用户多了 之后 需要分库分表 id就需要用分布式id
    // @TableId(type = IdType.AUTO) // 数据库自增
    private Long id;

    private String account;

    private Integer admin;

    private String avatar;

    private Long createDate;

    private Integer deleted;

    private String email;

    private Long lastLogin;

    private String mobilePhoneNumber;

    private String nickname;

    private String password;

    private String salt;

    private String status;
}
