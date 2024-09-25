package com.tolmic.digitallibrary.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${pictures.path}")
    private String authorPicturesPath;
    
    public void addViewController(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }
}
