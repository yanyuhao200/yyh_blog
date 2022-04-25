package com.yyh.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yyh.blog.dao.mapper.TagMapper;
import com.yyh.blog.dao.pojo.Tag;
import com.yyh.blog.service.TagService;
import com.yyh.blog.vo.Result;
import com.yyh.blog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;

    public TagVo copy(Tag tag) {
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag, tagVo);
        return tagVo;
    }

    public List<TagVo> copyList(List<Tag> tagList) {
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        //mybatisPlus无法进行多表查询
        List<Tag> tags = tagMapper.findTagsByArticleId(articleId);
        return copyList(tags);
    }

    // 最热标签
    @Override
    public Result hots(int limit) {
        /**
         * 1. 标签所拥有文章数量最多 最热标签
         * 2. 查询 根据tag_id 分组 计数  从大到小 计数  limit
         */
        List<Long> tagsIds = tagMapper.findHotsTagIds(limit);
        if(CollectionUtils.isEmpty(tagsIds)){
            return Result.success(Collections.emptyList());
        }
        // 需求的是tagId 和 tagName Tag对象
        // select * from tag where id in（1，2，3，4）
        List<Tag> tagList = tagMapper.findTagsByTagIds(tagsIds);
        return Result.success(tagList);
    }

    // 查询所有标签
    @Override
    public Result findAll() {
        // 用多少查多少
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getId,Tag::getTagName);
        List<Tag> tagList = tagMapper.selectList(queryWrapper);
        return Result.success(copyList(tagList));
    }

    @Override
    public Result findAllDetail() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        List<Tag> tagList = tagMapper.selectList(queryWrapper);
        return Result.success(copyList(tagList));
    }

    // 标签列表
    @Override
    public Result findAllDetailById(Long id) {
        Tag tag = tagMapper.selectById(id);
        return Result.success(copy(tag));
    }
}
