package com.yyh.blog.service;

import com.yyh.blog.vo.Result;
import com.yyh.blog.vo.params.CommentParam;

/**
 * @Author: yyh
 * @Date: 2022/04/22 16:37
 */

public interface CommentsService {

    /**
     * 根据文章id 查询所有的评论列表
     * @param id
     * @return
     */
    Result commentsByArticleId(Long id);

    Result comment(CommentParam commentParam);
}
