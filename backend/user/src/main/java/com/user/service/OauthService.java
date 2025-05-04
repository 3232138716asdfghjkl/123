package com.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.user.pojo.Oauth;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.RedirectView;


public interface OauthService  extends IService<Oauth> {
    RedirectView render(String source) throws Exception;
    RedirectView callback(String source, AuthCallback callback) throws Exception;
}
