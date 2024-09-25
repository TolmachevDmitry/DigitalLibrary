package com.tolmic.digitallibrary.config;


import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;


@Configuration
public class FaviconConfiguration {

    @Bean
    protected ResourceHttpRequestHandler faviconRequestHandler() {
        ResourceHttpRequestHandler requestHandler
                = new ResourceHttpRequestHandler();
        ClassPathResource classPathResource
                = new ClassPathResource("./images/use-inter-images/icon.png");
                
        List<Resource> locations = Arrays.asList(classPathResource);
        requestHandler.setLocations(locations);
        return requestHandler;
    }

}
