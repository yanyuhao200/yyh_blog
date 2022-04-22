package com.yyh.blog.service;

import com.yyh.blog.vo.CategoryVo;

/**
 * @Author: yyh
 * @Date: 2022/04/22 14:07
 */

public interface CategoryService {

    // 根据id查询类型
    CategoryVo findCategoryById(Long categoryId);
}
