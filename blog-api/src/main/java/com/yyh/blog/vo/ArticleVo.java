package com.yyh.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

@Data
public class ArticleVo {

    // 一定要记得加 要不然 会出现精度损失
    // @JsonSerialize(using = ToStringSerializer.class)
    // private Long id;

    // 使用了缓存优化处理后 redis给前端返回的id为long类型 还是会产生精度损失问题 所以把id改为String类型的
    private String  id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    private Integer weight;

    // 创建时间
    private String createDate;

    private String author;

    private ArticleBodyVo body;

    private List<TagVo> tags;

//    private List<CategoryVo> categorys;
    private CategoryVo categorys;
}
