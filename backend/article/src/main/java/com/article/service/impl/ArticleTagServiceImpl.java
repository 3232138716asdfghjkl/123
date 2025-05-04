package com.article.service.impl;

import com.article.mapper.ArticleTagMapper;

import com.article.pojo.Article;
import com.article.pojo.Articletag;

import com.article.service.ArticleTagService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, Articletag> implements ArticleTagService {


    public Article tag(Article article) {
        List<Articletag> l = lambdaQuery()
                .eq(Articletag::getArticleId,article.getId())
                .list();
        List<Integer> tags = new ArrayList<>();
        for (Articletag articletag : l) {
            tags.add(articletag.getTagId());
        }
        article.setTags(tags);
        return article;
    }

    public List<Article> tag(List<Article> aarticle) {
        List<Integer> l = aarticle.stream()
                .map(Article::getId)
                .toList();
        List<Articletag> ll =  lambdaQuery()
                .in(Articletag::getArticleId,l)
                .list();
        Map<Integer,List<Integer>> m = new HashMap<>();
        for (Articletag articletag : ll) {
            List<Integer> lll =  m.getOrDefault(articletag.getArticleId(),new ArrayList<>());
            lll.add(articletag.getTagId());
            m.put(articletag.getArticleId(),lll);
        }
        aarticle.forEach(article -> article.setTags(m.get(article.getId())));
        return aarticle;
    }
}
