package com.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.common.exception.KnownException;
import com.user.mapper.OauthMapper;
import com.user.pojo.Auth;
import com.user.pojo.Oauth;
import com.user.pojo.User;
import com.user.service.OauthService;
import com.user.service.UserService;

import com.user.utils.JwtUtils;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthGiteeRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import java.util.UUID;


@Service
public class OauthServiceImpl extends ServiceImpl<OauthMapper, Oauth> implements OauthService {

    @Autowired
    UserService userService;
    @Autowired
    OauthMapper oauthMapper;

    @Value("${front.host}")
    String host;
    @Value("${front.port}")
    String port;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AuthGiteeRequest giteeRequest;
//    @Autowired
//    AuthGithubRequest githubRequest;
//    @Autowired
//    AuthWeChatMpRequest weChatMpRequest;
//    @Autowired
//    AuthQqRequest qqRequest;

    @Override
    public RedirectView render(String source) throws Exception {
        AuthRequest authRequest = authRequest(source);
        String url = authRequest.authorize(AuthStateUtils.createState());
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(url);
        return redirectView;
    }

    @Override
    public RedirectView callback(String source, AuthCallback callback) throws Exception {
        AuthRequest authRequest = authRequest(source);
        AuthUser authUser = (AuthUser) authRequest.login(callback).getData();
        String token ;
        Oauth oauth = lambdaQuery().eq(Oauth::getOpenId,authUser.getUuid())
                .eq(Oauth::getPlatform,authUser.getSource()).one();

        if(oauth == null){
            Auth auth = new Auth();
            auth.setAvatar(authUser.getAvatar());
            //设置用户邮箱
            String email = authUser.getEmail();
            if (StringUtils.isBlank(email) || !email.contains("@")) {
                email = UUID.randomUUID() + "@null.com";
            }
            auth.setEmail(email);
            //设置用户名
            String basename = authUser.getNickname();
            int suffix = 1;
            String username = basename;
            if (StringUtils.isBlank(basename)) {
                basename = "无名氏";
            }
            while (userService.lambdaQuery().eq(User::getUsername,username).one() != null) {
                username = basename + suffix;
                suffix++;
            }
            auth.setUsername(username);

            auth.setPassword(UUID.randomUUID().toString());
            token =  userService.register(auth);
            //插入第三方登录映射表数据
            oauth = new Oauth();
            oauth.setOpenId(authUser.getUuid());
            oauth.setPlatform(source);
            User user = userService.lambdaQuery()
                    .eq(User::getEmail,email)
                    .one();
            oauth.setUserId(user.getId());
            oauthMapper.insert(oauth);

        }else{
            token =  jwtUtils.getJWT(oauth.getUserId().toString());
        }

        RedirectView redirectView = new RedirectView();
        String tokenUrl =  "http://"+host+":"+port+"/welcome/token?token=";
        redirectView.setUrl(tokenUrl  + token);
        return redirectView;
    }

    AuthRequest authRequest(String source) throws Exception {
        return switch (source) {
            case "gitee" -> giteeRequest;
//            case "qq" -> qqRequest;
//            case "github" -> githubRequest;
//            case "wechat" -> weChatMpRequest;
            default -> throw new KnownException("第三方验证错误: " + source);
        };
    }
}