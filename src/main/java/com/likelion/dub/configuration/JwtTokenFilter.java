package com.likelion.dub.configuration;

import com.likelion.dub.service.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * JwtTokenFilter는 Spring Security에서 인증된 사용자의 요청에 대해 실행됩니다.
 * doFilterInternal() 메소드를 통해 요청에 대한 필터링이 이루어지며, 인증 토큰을 추출하여 이를 이용해 인증된 사용자의 권한을 설정합니다
 * 이후 요청이 컨트롤러로 전달됩니다. 따라서 JwtTokenFilter는 인증된 사용자가 보호된 자원에 접근할 때마다 실행됩니다.
 */
@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final MemberService memberService;
    private final String secretKey;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION); //요청 헤더에서 Authorization 헤더를 읽음
        log.info("authorization:{}", authorization);


        //토큰이 없거나, Bearer token 이 아니면 종료
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            log.error("No Token or not Bearer token");
            filterChain.doFilter(request, response);
            return;
        }
        //token 꺼내기
        String token = authorization.split(" ")[1];
        //token expired 면 종료
        if (JwtTokenUtil.isExpired(token, secretKey)) {
            log.error("token Expired");
            filterChain.doFilter(request, response);
            return;
        }

        //email Token 에서 꺼내기
        String email = JwtTokenUtil.getEmail(token, secretKey);
        log.info("email:{}", email);

        //name Token 에서 꺼내기
        String name = JwtTokenUtil.getName(token, secretKey);
        log.info("name:{}", name);

        //role Token 에서 꺼내기
        String role = JwtTokenUtil.getRole(token, secretKey);
        log.info("role:{}", role);

        if ( "USER".equals(role)){
            //권한 부여
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(email, null, List.of(new SimpleGrantedAuthority("ROLE_USER")));
            //detail 을 넣어줍니다
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
        }
       else if ("CLUB".equals(role)) {
            //권한 부여
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(email, null, List.of(new SimpleGrantedAuthority("ROLE_CLUB")));
            //detail 을 넣어줍니다
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
        }
       else if ("ADMIN".equals(role)) {
            //권한 부여
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(email, null, List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
            //detail 을 넣어줍니다
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
        }


    }
}
