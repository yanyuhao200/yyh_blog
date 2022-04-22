package com.yyh.blog.dao.pojo;

/**
 * @Author: yyh
 * @Date: 2022/04/22 13:18
 */

import lombok.Data;

@Data
public class ArticleBody {

    private Long id;
    private String content;
    private String contentHtml;
    private Long articleId;
}
