package com.yyh.blog.service;

import com.yyh.blog.vo.Result;
import com.yyh.blog.vo.TagVo;

import java.util.List;

public interface TagService {

    List<TagVo> findTagsByArticleId(Long articleId);

    // 最热标签
    Result hots(int limit);
}
