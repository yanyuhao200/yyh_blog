package com.yyh.blog.vo.params;

/**
 * @Author: yyh
 * @Date: 2022/04/22 20:20
 */

import lombok.Data;

@Data
public class CommentParam {

    private Long articleId;

    private String content;

    private Long parent;

    private Long toUserId;
}