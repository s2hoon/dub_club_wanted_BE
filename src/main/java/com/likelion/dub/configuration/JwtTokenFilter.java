package com.likelion.dub.configuration;

import com.likelion.dub.common.util.JwtTokenUtil;
import com.likelion.dub.member.infrastructure.Member;
import com.likelion.dub.member.infrastructure.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {


    private final MemberRepository memberRepository;

    @Value("${jwt.token.secret}")
    private String secretKey;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        // 토큰이 있는지 확인
        if (checkHaveToken(request, response, filterChain, authorization)) {
            return;
        }
        //token 꺼내기
        String token = authorization.split(" ")[1];
        //token expired 면 종료
        if (checkIsExpired(request, response, filterChain, token)) {
            return;
        }
        String email = JwtTokenUtil.getEmail(token, secretKey);
        String role = JwtTokenUtil.getRole(token, secretKey);
        log.info("email:{}", email);
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("jwt에 있는 email 이 회원가입 되어있지 않습니다"));

        log.info("role:{}", role);

        String requestURI = request.getRequestURI();
        if (role != null) {
            // 권한 부여 후 Security Context Holder 에 추가
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(email, null,
                            List.of(new SimpleGrantedAuthority(role)));
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            log.info("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authenticationToken.getName(), requestURI);
        } else {
            log.info("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
        }
        filterChain.doFilter(request, response);
    }

    private boolean checkIsExpired(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain,
                                   String token) throws IOException, ServletException {
        if (JwtTokenUtil.isExpired(token, secretKey)) {
            log.error("token Expired");
            filterChain.doFilter(request, response);
            return true;
        }
        return false;
    }

    private boolean checkHaveToken(HttpServletRequest request, HttpServletResponse response,
                                   FilterChain filterChain,
                                   String authorization) throws IOException, ServletException {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            log.info("No Token or not Bearer token");
            filterChain.doFilter(request, response);
            return true;
        }
        return false;
    }

}
