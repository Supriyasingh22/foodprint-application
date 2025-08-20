package com.reewild.foodprint.foodprintapplication.config;

 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.security.config.annotation.web.builders.HttpSecurity;
 import org.springframework.security.web.SecurityFilterChain;
 import org.springframework.context.annotation.Bean;

 @Configuration
 public class SecurityConfig {

    @Value("${app.requireAuth:false}") boolean requireAuth;

    @Value("${app.apiToken:changeme}") String apiToken;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf-> csrf.disable());
        if (!requireAuth) {
            http.authorizeHttpRequests(auth->
            auth.anyRequest().permitAll());
            return http.build();
        }
        // Simple API token via header X-API-KEY (demo only)
        http.addFilterBefore(new SimpleApiKeyFilter(apiToken),
        org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
        .authorizeHttpRequests(a-> a.anyRequest().authenticated());
        return http.build();
    }
 }
