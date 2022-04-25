package com.yyh.blog.service;

import com.yyh.blog.vo.CategoryVo;
import com.yyh.blog.vo.Result;

/**
 * @Author: yyh
 * @Date: 2022/04/22 14:07
 */

public interface CategoryService {

    /**
     * 根据id查询类型
     * @param categoryId
     * @return
     */
    CategoryVo findCategoryById(Long categoryId);


    /**
     * 类别查询
     * @return
     */
    Result findAll();

    /**
     * 分类列表
     * @return
     */
    Result findAllDetail();

    /**
     * 分类列表显示
     * @param id
     * @return
     */
    Result categoryDetailById(Long id);
}
