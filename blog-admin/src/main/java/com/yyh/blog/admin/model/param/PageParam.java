package com.yyh.blog.admin.model.param;

import lombok.Data;

/**
 * @Author: yyh
 * @Date: 2022/04/25 12:59
 */

@Data
public class PageParam {

    private Integer currentPage;

    private Integer pageSize;

    private String queryString;
}
