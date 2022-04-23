package com.yyh.blog.dao.pojo;

import lombok.Data;

/**
 * @Author: yyh
 * @Date: 2022/04/23 15:04
 */

@Data
public class ArticleTag {

    private Long id;

    private Long articleId;

    private Long tagId;

}
