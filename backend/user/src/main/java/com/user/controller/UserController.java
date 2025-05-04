package com.user.controller;


import com.common.pojo.Result;

import com.user.pojo.Auth;
import com.user.pojo.User;
import com.user.service.UserService;
import com.user.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;


@RestController
@RequestMapping("/user")
@Tag(name = "用户管理")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Operation(summary = "登录")
    @PostMapping("/login")
    public Result login(@RequestBody Auth auth) throws Exception {
       return Result.success(userService.login(auth));
    }

    @Operation(summary = "注册")
    @PostMapping("/register")
    public Result register(@RequestBody Auth auth) throws Exception {
        return Result.success(userService.register(auth));
    }


    @Operation(summary = "获取用户信息")
    @GetMapping("/userInfo")
    public Result userInfo(@RequestHeader( "Authorization") String token) {
        return  Result.success(userService.getById(jwtUtils.parseJWT(token)));
    }



    @Operation(summary = "重置密码")
    @PostMapping("/reset")
    public Result reset(@RequestBody Auth auth) {
        String code = redisTemplate.opsForValue().get("code:"+auth.getEmail());
        if (!auth.getCode().equals(code)) {
            return Result.error("验证码错误");
        }
        userService.lambdaUpdate()
                .eq(User::getEmail, auth.getEmail())
                .set(User::getPassword, passwordEncoder.encode(auth.getPassword()))
                .update();
        return Result.success();
    }

    @Operation(summary = "上传头像")
    @PostMapping("/avatar")
    public Result avatar(@RequestParam("file") MultipartFile file,String email) throws Exception {
        return Result.success( userService.avatar(file,email));
    }

    @Operation(summary = "发送验证码")
    @GetMapping("/email")
    public Result emailSend(String email) {
        userService.codeSend(email);
        return Result.success();
    }
}
