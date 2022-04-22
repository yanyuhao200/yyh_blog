package com.yyh.blog.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author: yyh
 * @Date: 2022/04/22 16:58
 */

@Data
public class CommentVo {

    private Long id;

    private UserVo author;

    private String content;

    private List<CommentVo> childrens;

    private String createDate;

    private Integer level;

    private UserVo toUser;
}
