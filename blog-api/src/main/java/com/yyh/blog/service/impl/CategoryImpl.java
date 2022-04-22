package com.yyh.blog.service.impl;

import com.yyh.blog.dao.mapper.CategoryMapper;
import com.yyh.blog.dao.pojo.Category;
import com.yyh.blog.service.CategoryService;
import com.yyh.blog.vo.CategoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: yyh
 * @Date: 2022/04/22 14:08
 */

@Service
public class CategoryImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public CategoryVo findCategoryById(Long categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }
}
