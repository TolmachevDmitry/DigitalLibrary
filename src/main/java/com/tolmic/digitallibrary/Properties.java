package com.tolmic.digitallibrary;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix  = "application.properties")
public class Properties {
    private String temp;
}
