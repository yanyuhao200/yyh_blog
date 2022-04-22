package com.yyh.blog.controller;

import com.yyh.blog.service.CommentsService;
import com.yyh.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yyh
 * @Date: 2022/04/22 16:28
 */


@RestController
@RequestMapping("comments")
public class commentsController {

    @Autowired
    private CommentsService commentsService;
    @GetMapping("article/{id}")
    public Result comments(@PathVariable("id") Long id){
        return commentsService.commentsByArticleId(id);
    }
}
