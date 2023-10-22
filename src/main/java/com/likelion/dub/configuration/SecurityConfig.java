package com.likelion.dub.configuration;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic().disable()
                .csrf().disable()
                .cors().disable()
                .authorizeRequests()
                .requestMatchers("/app/post/write-post", "/app/post/delete-post", "/app/post/rewrite-post")
                .hasRole("CLUB") // Post 작성 Club 권한 필요
                .requestMatchers("/app/club/**").hasRole("CLUB") // Club 에 관한거 CLUB 권한 필요
                .anyRequest().permitAll() // 나머지는 누구나 접근가능
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 사용 비활성화 -> restful api 기반 토큰 이므로 세션 필요x
                .and()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
        
    }
}

