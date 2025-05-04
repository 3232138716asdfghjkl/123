package com.article.controller;


import com.article.pojo.Article;
import com.article.pojo.Articletag;
import com.article.service.ArticleService;
import com.article.service.ArticleTagService;
import com.article.service.FileService;
import com.common.pojo.Result;
import com.common.service.MinioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;



@RestController
@RequestMapping("/article")
@Tag(name = "文章管理")
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @Autowired
    ArticleTagService articleTagService;


    @Operation(summary = "分页查询")
    @GetMapping("/page")
    public Result page(int current, int size,int state) {
        /*List<article> l = articleService.list();
        l.forEach(item->{
            item.setImage(item.getImage().replace("120.27.202.144","47.97.100.212"));
            item.setContent(item.getContent().replace("120.27.202.144","47.97.100.212"));
            articleService.updateById(item);
        });*/
        return Result.success(articleService.page(current, size, state));
    }

    @Operation(summary = "根据状态或置顶查询")
    @GetMapping("/list")
    public Result list(  @RequestParam(required = false,defaultValue = "3")int state,
                         @RequestParam(required = false,defaultValue = "3")int top) {
        return Result.success(articleTagService.tag(articleService.list(state,top)));

    }
    @Operation(summary = "根据id查询")
    @GetMapping("/get")
    public Result get(int id) {
        return Result.success(articleTagService.tag(articleService.getById(id)));
    }



    @Operation(summary = "根据分类id查询")
    @GetMapping("/list/byCateId")
    public Result getArticleByCateId(Integer id){
        return Result.success( articleService.lambdaQuery()
                .eq(Article::getCategoryId,id)
                .eq(Article::getState,1)
                .list());
    }
    
    @Operation(summary = "根据标签id查询")
    @GetMapping("/list/byTagId")
    public Result getArticleByTagId(Integer id){
        List<Articletag> l  =articleTagService.lambdaQuery()
                .eq(Articletag::getTagId,id)
                .list();
        List<Integer> ll = l.stream().map(Articletag::getArticleId).toList();
        return Result.success(articleService.lambdaQuery()
                .in(Article::getId,ll)
                .eq(Article::getState,1)
                .list());
    }

    @Operation(summary = "新增文章")
    @PostMapping("/add")
    public Result add(@RequestBody Article article) {
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        articleService.save(article);
        return Result.success();
    }
    @Operation(summary = "修改文章")
    @PutMapping("/update")
    public Result update(@RequestBody Article article) {
        if(!articleService.getById(article.getId()).getContent().equals(article.getContent())) {
            article.setUpdateTime(LocalDateTime.now());
        }
        List<Articletag> l = articleTagService.lambdaQuery()
                .eq(Articletag::getArticleId,article.getId())
                .list();
        List<Integer> database = new ArrayList<>(l.stream().map(Articletag::getTagId).toList());
        List<Integer> current = article.getTags();
        database.removeAll(current);
        if(!database.isEmpty()){
            articleTagService.lambdaUpdate().eq(Articletag::getArticleId,article.getId())
                    .in(Articletag::getTagId,database).remove();
        }
            current.removeAll(l.stream().map(Articletag::getTagId).toList());
            List<Articletag> s = current.stream().map(tagId -> {
                Articletag a = new Articletag();
                a.setTagId(tagId);
                a.setArticleId(article.getId());
                return a;
            }).toList();
            articleTagService.saveBatch(s);

        articleService.updateById(article);
        return Result.success();
    }
    @Autowired
    FileService fileService;

    MinioService minioService;

    @Autowired
    public ArticleController(MinioService minioService) {
        this.minioService = minioService;
        this.minioService.setBucketName("article");
    }

    @Operation(summary = "根据id删除")
    @DeleteMapping("/del")
    public Result del(int id) throws Exception {
        fileService.delAll(id);
        Article a = articleService.getById(id);
        int lastIndex = a.getImage().lastIndexOf("/");
        String fileName = a.getImage().substring(lastIndex + 1);
        minioService.del(fileName);
        articleTagService.lambdaUpdate().eq(Articletag::getArticleId,id).remove();
        articleService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "查询分类对应文章数")
    @GetMapping("/count")
    public Result count(int id){
        return Result.success(articleService.lambdaQuery().eq(Article::getCategoryId, id).eq(Article::getState,1).count());
    }

    @Operation(summary = "查询文章上下篇")
    @GetMapping("/around")
    public Result around(int id){
        List<Article> res = new ArrayList<>();
        res.add(articleService.lambdaQuery().lt(Article::getId,id).eq(Article::getState,1).orderByDesc(Article::getId) .last("LIMIT 1").one());
        res.add(articleService.lambdaQuery().gt(Article::getId,id).eq(Article::getState,1).last("LIMIT 1").one());
        return Result.success(res);
    }

    @Autowired
    StringRedisTemplate redisTemplate;

    @Operation(summary = "增加文章访问量")
    @PostMapping("/visit/{id}")
    public Result visit(@PathVariable("id") int id) {
        String key = "article:visit:" + id;
        redisTemplate.opsForValue().increment(key, 1);
        return Result.success();
    }
}
