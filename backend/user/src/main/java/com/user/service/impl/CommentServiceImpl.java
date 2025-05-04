package com.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.common.pojo.Page;
import com.user.mapper.CommentMapper;
import com.user.pojo.Comment;
import com.user.pojo.CommentVo;
import com.user.pojo.Like;
import com.user.pojo.User;
import com.user.service.CommentService;
import com.user.service.LikeService;
import com.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    UserService userService;

    @Autowired
    LikeService likeService;

    @Override
    public List<CommentVo> page(int id, int current, int size) {
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<Comment> pa = com.baomidou.mybatisplus.extension.plugins.pagination.Page.of(current, size);
        pa.addOrder(new OrderItem("create_time", false));
        lambdaQuery().eq(Comment::getArticleId, id).isNull(Comment::getParentId).page(pa);
        Page<Comment> p = new Page<>(pa.getTotal(), pa.getRecords());
        return setUser(p.getRecords());
    }

    public List<CommentVo> setUser(List<Comment> l){
        List<CommentVo> ll =  new ArrayList<>();
        for(Comment c:l) {
            User u = userService.getById(c.getUserId());
            CommentVo cv = new CommentVo();
            BeanUtil.copyProperties(c, cv);
            cv.setLike(Math.toIntExact(likeService.lambdaQuery().eq(Like::getCommentId, c.getId()).count()));
            cv.setAvatar(u.getAvatar());
            cv.setUsername(u.getUsername());
            ll.add(cv);
        }
        return ll;
    }
}
