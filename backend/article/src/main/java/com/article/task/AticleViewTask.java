package com.article.task;

import com.article.pojo.Article;
import com.article.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.util.Set;

@Component
@Slf4j
public class AticleViewTask {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    ArticleService articleService;


    @Scheduled(fixedRate = 300000)
    public void vistitSave() {
        Set<String> keys =  redisTemplate.keys("article:visit:*");
        if (keys != null) {
            for (String key : keys) {
                int articleId = Integer.parseInt(key.split(":")[2]);
                String s = redisTemplate.opsForValue().get(key);
                Article aarticle= articleService.lambdaQuery()
                        .eq(Article::getId,articleId)
                        .select(Article::getVisitCount).one();
                int visitCount = aarticle.getVisitCount();
                if (s != null) {
                    visitCount = Integer.parseInt(s);
                }
                articleService.lambdaUpdate().
                        eq(Article::getId,articleId).
                        set(Article::getVisitCount,visitCount).update();
            }
        }
    }

}
