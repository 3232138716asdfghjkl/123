package com.user.controller;


import com.user.service.OauthService;
import io.swagger.v3.oas.annotations.tags.Tag;

import me.zhyd.oauth.model.AuthCallback;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;



@RestController
@RequestMapping("/oauth")
@Tag(name = "第三方登录管理")
public class OauthController {

    @Autowired
    OauthService oauthService;


    @GetMapping("/render/{source}")
    public RedirectView render(@PathVariable("source") String source) throws Exception {
       return oauthService.render(source);
}

    @Transactional
    @GetMapping("/callback/{source}")
    public RedirectView callback(@PathVariable("source") String source,
             @RequestParam String code,@RequestParam String state) throws Exception {
        AuthCallback callback = new AuthCallback();
        callback.setCode(code);
        callback.setState(state);
        return  oauthService.callback(source,callback);
    }

}
