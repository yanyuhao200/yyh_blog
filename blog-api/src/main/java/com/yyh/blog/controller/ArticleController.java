package com.yyh.blog.controller;

import com.yyh.blog.common.aop.LogAnnotation;
import com.yyh.blog.common.cache.Cache;
import com.yyh.blog.service.ArticleService;
import com.yyh.blog.vo.Result;
import com.yyh.blog.vo.params.ArticleParam;
import com.yyh.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 首页 文章列表
     */
    @PostMapping
    // 加上此注解 代表要对此接口记录日志
    @LogAnnotation(module = "文章", operator = "获取文章列表")
    @Cache(expire = 5*60*1000,name = "listArticle")
    public Result listArticle(@RequestBody PageParams pageParams) {
        return articleService.listArticle(pageParams);
    }

    /**
     * 首页 最热文章
     */
    @PostMapping("hot")
    // 使切面可以拿到 ArticleController 和 hotArticle
    @Cache(expire = 5 * 60 * 1000, name = "hot_article")
    public Result hotArticle() {
        int limit = 5;
        return articleService.hotArticle(limit);
    }

    /**
     * 首页 最热文章
     */
    @PostMapping("new")
    @Cache(expire = 5 * 60 * 1000, name = "news_article")
    public Result newArticle() {
        int limit = 5;
        return articleService.newArticle(limit);
    }

    /**
     * 首页 文章归档
     */
    @PostMapping("listArchives")
    public Result listArchives() {
        int limit = 5;
        return articleService.listArchives();
    }

    /**
     * 根据id 查询文章
     *
     * @param articleId
     * @return
     */
    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable("id") Long articleId) {
        return articleService.findArticleById(articleId);
    }

    /**
     * 文章发布服务
     */
    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam) {
        return articleService.publish(articleParam);
    }

}
