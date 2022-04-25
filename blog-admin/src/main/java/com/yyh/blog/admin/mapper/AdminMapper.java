package com.yyh.blog.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yyh.blog.admin.pojo.Admin;
import com.yyh.blog.admin.pojo.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: yyh
 * @Date: 2022/04/25 15:25
 */

@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

    // select * from `ms_permission` where id in(select `permission_id` from `ms_admin_permission` where id = 1)
    @Select("select * from ms_permission where id in(select permission_id from ms_admin_permission where id = #{admin})")
    List<Permission> findPermissionByAdminId(Long adminId);
}
