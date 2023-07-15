package com.discphy.example.oauth2.config;

import com.discphy.example.oauth2.userinfo.service.CustomOAuth2UserService;
import com.discphy.example.oauth2.userinfo.service.CustomOidcUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService userService;
    private final CustomOidcUserService oidcUserService;

    @Bean
    public SecurityFilterChain config(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests()
                    .antMatchers("/login").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .oauth2Login()
                    .userInfoEndpoint()
                        .userService(userService)
                        .oidcUserService(oidcUserService)
                        .and()
                    .defaultSuccessUrl("/user")
                    .and()
                .build();
    }
}
