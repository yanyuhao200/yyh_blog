package com.yyh.blog.dao.pojo;

/**
 * @Author: yyh
 * @Date: 2022/04/22 13:18
 */

import lombok.Data;

@Data
public class Category {

    private Long id;

    private String avatar;

    private String categoryName;

    private String description;
}
