package com.yyh.blog.controller;

import com.yyh.blog.service.CategoryService;
import com.yyh.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yyh
 * @Date: 2022/04/22 21:44
 */


@RestController
@RequestMapping("categorys")

public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public Result categories(){
        return categoryService.findAll();
    }
}
