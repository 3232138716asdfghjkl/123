package com.user.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.common.exception.KnownException;
import com.common.service.MinioService;
import com.user.mapper.UserMapper;


import com.user.pojo.Auth;
import com.user.pojo.Message;
import com.user.pojo.User;

import com.user.service.UserService;

import com.user.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtils jwtUtils;
    MinioService minioService;

    @Autowired
    public UserServiceImpl(MinioService minioService) {
        this.minioService = minioService;
        this.minioService.setBucketName("user");
    }

    @Autowired
    RabbitTemplate rabbitTemplate;


    public String login(Auth auth) throws Exception {
        User u;
        if(StringUtils.isBlank(auth.getCode())){
             u = lambdaQuery().eq(User::getUsername,auth.getUsername())
                    .one();
            if( u == null){
                throw new KnownException("用户不存在");
            }
            if(!passwordEncoder.matches(auth.getPassword(), u.getPassword() )){
                throw new KnownException("账号或密码错误");
            }
        }else{
             u = lambdaQuery().eq(User::getEmail,auth.getEmail())
                    .one();
            if( u == null){
                throw new KnownException("用户不存在");
            }
            String code = redisTemplate.opsForValue().get("code:"+auth.getEmail());
            if (!auth.getCode().equals(code)) {
                throw new KnownException("验证码错误");
            }
        }
        return jwtUtils.getJWT(u.getId().toString());
    }

    @Override
    public String register(Auth auth) throws Exception {
        String code = redisTemplate.opsForValue().get("code:"+auth.getEmail());
        if (!(auth.getCode() == null) && !auth.getCode().equals(code)) {
            throw new KnownException("验证码错误");
        }
        User u = lambdaQuery().eq(User::getEmail, auth.getEmail()).one();
        if (u != null) {
            throw new KnownException("邮箱已注册");
        }
        u = lambdaQuery().eq(User::getUsername, auth.getUsername()).one();
        if (u != null) {
            throw new KnownException("用户名重复");
        }
        u = new User();
        BeanUtils.copyProperties(auth, u);
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        u.setCreateTime(LocalDateTime.now());
        userMapper.insert(u);
        return jwtUtils.getJWT(lambdaQuery()
                .eq(User::getUsername, auth.getUsername()).one()
                .getId().toString());
    }

    @Override
    public String avatar(MultipartFile file, String email) throws Exception {
        String url = minioService.addFile(file);
        User u  = lambdaQuery()
                .eq(User::getEmail, email)
                .one();
        if(!u.getAvatar().isEmpty()){
            int lastIndex = u.getAvatar().lastIndexOf("/");
            String fileName = u.getAvatar().substring(lastIndex + 1);
            minioService.del(fileName);
        }
        lambdaUpdate()
                .eq(User::getEmail, email)
                .set(User::getAvatar,url).update();
        return url;
    }

    @Override
    public void codeSend(String email) {
        String code = String.valueOf(new Random().nextInt(9000) + 1000);
        redisTemplate.opsForValue().set("code:"+email, code, 10, TimeUnit.MINUTES);
        rabbitTemplate.convertAndSend("email","email",
                new Message(email,"<div style=\"background: #3b82f6; display: flex; flex-direction: column; justify-content: center; align-items: center; height: 200px; width: 100%; padding: 20px;\">" +
                        "<div style=\"font-size: 18px; color: white; margin-bottom: 10px;\">" +
                        "您的注册验证码为" +
                        "</div>" +
                        "<div style=\"font-size: 24px; font-weight: bold; color: white; margin-bottom: 10px;\">" +
                        "<span>"+code+"</span>" +
                        "</div>" +
                        "<div style=\"font-size: 16px; color: white;\">" +
                        "十分钟内有效" +
                        "</div>" +
                        "</div>"));
    }
}

