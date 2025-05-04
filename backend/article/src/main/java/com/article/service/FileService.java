package com.article.service;


import com.article.pojo.File;
import com.baomidou.mybatisplus.extension.service.IService;


public interface FileService  extends IService<File> {
    void delAll(int id);
}
