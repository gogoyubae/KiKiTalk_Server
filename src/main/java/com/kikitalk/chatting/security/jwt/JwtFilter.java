package com.kikitalk.chatting.security.jwt;

import com.kikitalk.chatting.security.jwt.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";   // 끝에 공백 있음

    private final JwtProvider jwtProvider;

    //토큰의 인증 정보를 request에 저장
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            String token = bearerToken.substring(BEARER_PREFIX.length());
            // 토큰 유효성 검사
            if (jwtProvider.validateAccessToken(token)) {
                // 토큰에서 사용자 정보 추출
                Long kakaoAuthId = Long.parseLong(jwtProvider.getId(token));
                request.setAttribute("kakaoAuthId", kakaoAuthId);
                //  Authentication 객체 구성 후 SecurityContext에 저장
                Authentication auth = new UsernamePasswordAuthenticationToken(kakaoAuthId, null, List.of());
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                log.warn("Invalid JWT token: {}", token);
            }
        }
        filterChain.doFilter(request, response);
    }

}
