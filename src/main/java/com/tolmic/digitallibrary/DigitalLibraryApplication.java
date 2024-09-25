package com.tolmic.digitallibrary;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;


@Configuration
@ComponentScan
@SpringBootApplication
public class DigitalLibraryApplication {

	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize(DataSize.ofKilobytes(500));
		factory.setMaxRequestSize(DataSize.ofKilobytes(500));
		return factory.createMultipartConfig();
	}

	public static void main(String[] args) {
		SpringApplication.run(DigitalLibraryApplication.class, args);
	}

}
