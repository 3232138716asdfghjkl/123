package com.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.user.pojo.Comment;
import com.user.pojo.CommentVo;

import java.util.List;


public interface CommentService extends IService<Comment> {
    List<CommentVo> page(int id, int current, int size);
    List<CommentVo> setUser(List<Comment> list);
}
