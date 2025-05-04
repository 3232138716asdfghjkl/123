package com.user.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.common.pojo.Result;
import com.user.pojo.Auth;
import com.user.pojo.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService  extends IService<User> {
    String login( Auth auth) throws Exception;
    String register(Auth auth) throws Exception;
    String  avatar(MultipartFile file, String email) throws Exception;
    void codeSend(String email);
}
