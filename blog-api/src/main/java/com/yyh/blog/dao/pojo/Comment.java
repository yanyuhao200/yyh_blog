package com.yyh.blog.dao.pojo;

/**
 * @Author: yyh
 * @Date: 2022/04/22 16:25
 */

import lombok.Data;

@Data
public class Comment {

    private Long id;

    private String content;

    private Long createDate;

    private Long articleId;

    private Long authorId;

    private Long parentId;

    private Long toUid;

    private Integer level;
}
