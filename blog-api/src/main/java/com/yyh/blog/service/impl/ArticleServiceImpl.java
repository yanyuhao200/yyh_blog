package com.yyh.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyh.blog.dao.dos.Archives;
import com.yyh.blog.dao.mapper.ArticleBodyMapper;
import com.yyh.blog.dao.mapper.ArticleMapper;
import com.yyh.blog.dao.mapper.ArticleTagMapper;
import com.yyh.blog.dao.pojo.Article;
import com.yyh.blog.dao.pojo.ArticleBody;
import com.yyh.blog.dao.pojo.ArticleTag;
import com.yyh.blog.dao.pojo.SysUser;
import com.yyh.blog.service.*;
import com.yyh.blog.utils.UserThreadLocal;
import com.yyh.blog.vo.ArticleBodyVo;
import com.yyh.blog.vo.ArticleVo;
import com.yyh.blog.vo.Result;
import com.yyh.blog.vo.TagVo;
import com.yyh.blog.vo.params.ArticleParam;
import com.yyh.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private TagService tagService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ArticleTagMapper articleTagMapper;

    // 换为mybatis实现
    @Override
    public Result listArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        IPage<Article> articleIPage = articleMapper.listArticle(page, pageParams.getCategoryId(), pageParams.getTagId(), pageParams.getYear(), pageParams.getMonth());
        List<Article> records = articleIPage.getRecords();
        return Result.success(copyList(records, true, true));
    }

    /*@Override
    public Result listArticle(PageParams pageParams) {
        *//**
     * 1.分页查询 article数据库表
     *//*
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();


        if (pageParams.getCategoryId() != null) {
            // and category_id = #{categoryId)
            queryWrapper.eq(Article::getCategoryId, pageParams.getCategoryId());
        }

        // TODO bug 如果此标签下没有文章 会显示所有文章
        List<Long> articleIdList = new ArrayList<>();
        if (pageParams.getTagId() != null) {

            // 加入标签 条件查询
            // article表中中并没有标签字段 一篇文章中有多个标签
            // article_tag article_id 1:n tag_id
            LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
            articleTagLambdaQueryWrapper.eq(ArticleTag::getTagId,pageParams.getTagId());
            List<ArticleTag> articleTags = articleTagMapper.selectList(articleTagLambdaQueryWrapper);
            for (ArticleTag articleTag : articleTags) {
                articleIdList.add(articleTag.getArticleId());
            }
            if(articleIdList.size()>0){
                // and id in(1,2,3)
                queryWrapper.in(Article::getId,articleIdList);
            }
        }
        //是否进行排序
        //order by  create_date desc
        queryWrapper.orderByDesc(Article::getWeight, Article::getCreateDate);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<Article> records = articlePage.getRecords();
        //records不能直接返回  转成Vo对象返回
        List<ArticleVo> articleVos = copyList(records, true, true);
        return Result.success(articleVos);
    }*/

    /**
     * 最热文章
     *
     * @param limit
     * @return
     */
    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit " + limit); // 注意limit后面有空格
        // select id,title from article order by view_counts desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles, false, false));
    }

    /**
     * 最新文章
     *
     * @param limit
     * @return
     */
    @Override
    public Result newArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit " + limit); // 注意limit后面有空格
        // select id,title from article order by create_date desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles, false, false));
    }

    /**
     * 文章归档
     *
     * @return
     */
    @Override
    public Result listArchives() {
        List<Archives> archivesList = articleMapper.listArchives();
        return Result.success(archivesList);
    }

    /**
     * 查看文章详情
     *
     * @param articleId
     * @return
     */

    @Autowired
    private ThreadService threadService;

    @Override
    public Result findArticleById(Long articleId) {
        /**
         * 1. 根据id查询 文章信息
         * 2. 根据bodyId和categoryId 去做关联查询
         */
        Article article = articleMapper.selectById(articleId);
        ArticleVo articleVo = copy(article, true, true, true, true);
        // 查看完文章 新增阅读数
        // 查看完文章之后 本应该直接返回 但此时做了一个更新操作 更新时加写锁 会阻塞其他读操作 性能比较低
        // 更新 增加了此次接口的耗时 如果更新一旦出问题 不能查看文章操作
        // 线程池 可以把更新扔到线程池中去 这样就不影响主线程的工作了
        threadService.updateArticleViewCount(articleMapper, article);
        return Result.success(articleVo);
    }

    // 文章发布服务
    @Override
    public Result publish(ArticleParam articleParam) {
        // 此接口 要加入登录拦截器当中
        SysUser sysUser = UserThreadLocal.get();
        /**
         * 1. 发布文章 目的 构建Article对象
         * 2. 作者id 当前用户登录
         * 3. 标签 将标签加入到 关联列表当中
         * 4. body 内容存储 article bodyId
         */
        Article article = new Article();
        article.setAuthorId(sysUser.getId());
        article.setWeight(Article.Article_Common);
        article.setViewCounts(0);
        article.setTitle(articleParam.getTitle());
        article.setSummary(articleParam.getSummary());
        article.setCommentCounts(0);
        article.setCreateDate(System.currentTimeMillis());
        article.setCategoryId(Long.parseLong(articleParam.getCategory().getId()));
        // 插入之后 会生成一个文章id
        this.articleMapper.insert(article);

        // tag
        List<TagVo> tags = articleParam.getTags();
        if (tags != null) {
            for (TagVo tag : tags) {
                Long articleId = article.getId();
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(Long.parseLong(tag.getId()));
                articleTag.setArticleId(articleId);
                articleTagMapper.insert(articleTag);
            }

        }

        // body
        ArticleBody articleBody = new ArticleBody();
        articleBody.setArticleId(article.getId());
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBodyMapper.insert(articleBody);
        // 先插入body才会有bodyId  设置给article里面 然后执行更新操作
        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(article.getId().toString());
        /*Map<String,String> map = new HashMap<>();
        map.put("id",article.getId().toString());*/
        return Result.success(articleVo);
    }

    // article转换为articleVo对象
    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article recode : records) {
            articleVoList.add(copy(recode, isTag, isAuthor, false, false));
        }
        return articleVoList;
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article recode : records) {
            articleVoList.add(copy(recode, isTag, isAuthor, isBody, isCategory));
        }
        return articleVoList;
    }

    @Autowired
    private CategoryService categoryService;

    //使用StringUtils工具类转换
    private ArticleVo copy(Article article, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(String.valueOf(article.getId()));
        BeanUtils.copyProperties(article, articleVo);

        //时间不能直接复制 时间是long型 需要转换为String
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));

        //并不是所有的接口都需要标签 作者信息
        if (isTag) {
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        if (isAuthor) {
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
        }
        if (isBody) {
            Long bodyId = article.getBodyId();
            articleVo.setBody(findArticleBodyId(bodyId));
        }
        if (isCategory) {
            Long categoryId = article.getCategoryId();
            articleVo.setCategorys(categoryService.findCategoryById(categoryId));
        }
        return articleVo;
    }

    // 查询article_body数据
    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    private ArticleBodyVo findArticleBodyId(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }

}
