package com.article;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@ComponentScan(basePackages = {"com.common","com.article"})
@EnableScheduling
public class ArticleApplication {

    public static void main(String[] args) {

        SpringApplication.run(ArticleApplication.class, args);
    }


}
