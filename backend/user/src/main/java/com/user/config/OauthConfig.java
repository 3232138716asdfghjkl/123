package com.user.config;


import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.request.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OauthConfig {

    @Bean
    public AuthGiteeRequest GiteeAuthRequest(
             @Value("${oauth.gitee.client-id}") String clientId,
             @Value("${oauth.gitee.client-secret}") String clientSecret,
             @Value("${oauth.gitee.redirect-uri}")String redirectUri) {
        return new AuthGiteeRequest(AuthConfig.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .redirectUri(redirectUri)
                .build());
    }

//    @Bean
//    public AuthGithubRequest githubAuthRequest(
//            @Value("${oauth.github.client-id}") String clientId,
//            @Value("${oauth.github.client-secret}") String clientSecret,
//            @Value("${oauth.github.redirect-uri}") String redirectUri) {
//        return new AuthGithubRequest(AuthConfig.builder()
//                .clientId(clientId)
//                .clientSecret(clientSecret)
//                .redirectUri(redirectUri)
//                .httpConfig(HttpConfig.builder()
//                        .timeout(15000)
//                        .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 10080)))
//                        .build())
//                .build());
//    }

//    @Bean
//    public AuthQqRequest qqAuthRequest(
//            @Value("${oauth.qq.client-id}") String clientId,
//            @Value("${oauth.qq.client-secret}") String clientSecret,
//            @Value("${oauth.qq.redirect-uri}") String redirectUri) {
//        return new AuthQqRequest(AuthConfig.builder()
//                .clientId(clientId)
//                .clientSecret(clientSecret)
//                .redirectUri(redirectUri)
//                .build());
//    }
//    @Bean
//    public AuthWeChatMpRequest wechatAuthRequest(
//            @Value("${oauth.wechat.client-id}") String clientId,
//            @Value("${oauth.wechat.client-secret}") String clientSecret,
//            @Value("${oauth.wechat.redirect-uri}") String redirectUri) {
//        return new AuthWeChatMpRequest(AuthConfig.builder()
//                .clientId(clientId)
//                .clientSecret(clientSecret)
//                .redirectUri(redirectUri)
//                .build());
//    }
}
