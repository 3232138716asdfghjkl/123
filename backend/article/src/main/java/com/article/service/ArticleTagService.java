package com.article.service;

import com.article.pojo.Article;
import com.article.pojo.Articletag;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ArticleTagService extends IService<Articletag> {
    Article tag(Article article) ;
    List<Article> tag(List<Article> aarticle);
}
