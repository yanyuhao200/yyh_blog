package com.yyh.blog.admin.service;

import com.yyh.blog.admin.model.param.PageParam;
import com.yyh.blog.admin.pojo.Permission;
import vo.Result;

/**
 * @Author: yyh
 * @Date: 2022/04/25 13:03
 */

public interface PermissionService {

    Result listPermission(PageParam pageParam);

    Result add(Permission permission);

    Result update(Permission permission);

    Result delete(Long id);
}
