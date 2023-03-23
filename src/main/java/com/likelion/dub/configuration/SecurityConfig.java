package com.likelion.dub.configuration;


import com.likelion.dub.domain.Role;
import com.likelion.dub.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MemberService memberService;
    @Value("${jwt.token.secret}")
    private String secretKey;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic().disable()
                .csrf().disable()
                .cors().and()
                .authorizeRequests()
                .requestMatchers("/app/member/sign-up", "/app/member/sign-in").permitAll() //누구나 접근 가능
                .requestMatchers(HttpMethod.POST, "/app/member/test").hasRole(Role.ADMIN.name()) //admin 권한 필요
                .requestMatchers(HttpMethod.POST, "/app/board").hasAnyRole(Role.USER.name(),Role.CLUB.name()) //둘중 하나 권한 필요
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtTokenFilter(memberService, secretKey), UsernamePasswordAuthenticationFilter.class)
                .build();



    }
}

