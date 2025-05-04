package com.article.service.impl;


import com.article.mapper.FileMapper;

import com.article.pojo.File;

import com.article.service.FileService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.common.service.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {

    MinioService minioService;

    @Autowired
    public FileServiceImpl(MinioService minioService) {
        this.minioService = minioService;
        this.minioService.setBucketName("article");
    }

    @Override
    public void delAll(int id) {
        List<String> list =  lambdaQuery().eq(File::getArticleId,id)
                .list()
                .stream()
                .map(File::getFilename)
                .toList();
        minioService.delall(list);
        lambdaUpdate().eq(File::getArticleId,id)
                .remove();
    }
}
