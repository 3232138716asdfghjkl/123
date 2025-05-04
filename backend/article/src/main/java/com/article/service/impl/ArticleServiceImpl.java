package com.article.service.impl;

import com.article.mapper.ArticleMapper;
import com.article.pojo.Article;
import com.article.service.ArticleService;

import com.article.service.ArticleTagService;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.common.pojo.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService  {

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    ArticleTagService articleTagService;

    public List<Article> list(int state , int top){
        if (state == 3 && top == 3) {
            return lambdaQuery()
                    .list();
        }
        if(state == 3){
            return lambdaQuery()
                    .eq(Article::getTop, top)
                    .list();
        }
        if(top == 3) {
            return lambdaQuery()
                    .eq(Article::getState, state)
                    .list();
        }
        return lambdaQuery()
                .eq(Article::getState, state)
                .eq(Article::getTop, top)
                .list();
    }

    @Override
    public Page<Article> page(int current, int size, int state) {
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<Article> pa = com.baomidou.mybatisplus.extension.plugins.pagination.Page.of(current, size);
        pa.addOrder(new OrderItem("create_time", false));
        lambdaQuery().eq(Article::getState,state).page(pa);
        Page<Article> p = new Page<>(pa.getTotal(), pa.getRecords());
        p.setRecords(articleTagService.tag(p.getRecords()));
        return p;
    }


}
