package com.article.service;

import com.article.pojo.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.common.pojo.Page;

import java.util.List;


public interface ArticleService extends IService<Article> {
    List<Article> list(int state, int top);

    Page<Article> page(int current, int size, int state);
}
