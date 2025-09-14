package com.tolmic.digitallibrary.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((request) -> request
                                .requestMatchers("/", "/books", "/books/book", 
                                "/authors", "/authors/author", "/statistics", 
                                "/registration", "/login", "/division", "/images/**", 
                                "/styles/**", "/scripts/**", "/book/create", "/book_creation",
                                "/restore_password", "/add_book", "/api/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((formLogin) ->
                    formLogin
                        .loginPage("/login")
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

}
