package com.yyh.blog.vo.params;

/**
 * @Author: yyh
 * @Date: 2022/04/23 14:41
 */


import com.yyh.blog.vo.CategoryVo;
import com.yyh.blog.vo.TagVo;
import lombok.Data;

import java.util.List;

@Data
public class ArticleParam {

    private Long id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;

    private String title;
}