package com.article.service.impl;


import com.article.mapper.CategoryMapper;
import com.article.pojo.Category;

import com.article.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}
