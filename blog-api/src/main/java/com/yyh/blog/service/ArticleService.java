package com.yyh.blog.service;

import com.yyh.blog.vo.Result;
import com.yyh.blog.vo.params.PageParams;

public interface ArticleService {

    /**
     * 分页查询 文章列表
     * 首页 文章列表
     * @param pageParams
     * @return
     */
    Result listArticle(PageParams pageParams);


    /**
     * 最热文章
     * @param limit
     * @return
     */
    Result hotArticle(int limit);

    /**
     * 最新文章
     * @param limit
     * @return
     */
    Result newArticle(int limit);

    /**
     * 文章归档
     * @return
     */
    Result listArchives();

    /**
     * 查看文章详情
     * @param articleId
     * @return
     */
    Result findArticleById(Long articleId);
}
