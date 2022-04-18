package com.yyh.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yyh.blog.dao.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    /**
     * 根据文章id查询标签
     *
     * @param articleId
     * @return
     */

    List<Tag> findTagsByArticleId(Long articleId);

    /**
     * 查询最热的标签  前n条
     * @param limit
     * @return
     */
    List<Long> findHotsTagIds(int limit);

    /**
     * 查询tags对象
     * @param tagsIds
     * @return
     */
    List<Tag> findTagsByTagIds(List<Long> tagsIds);


}
