package com.yyh.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yyh.blog.dao.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: yyh
 * @Date: 2022/04/22 16:47
 */

@Mapper
public interface CommentsMapper extends BaseMapper<Comment> {
}
