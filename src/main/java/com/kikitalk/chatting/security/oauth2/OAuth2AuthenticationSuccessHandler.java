package com.kikitalk.chatting.security.oauth2;

import com.kikitalk.chatting.security.jwt.JwtDto;
import com.kikitalk.chatting.security.jwt.JwtProvider;
import com.kikitalk.chatting.user.User;
import com.kikitalk.chatting.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                         Authentication authentication) throws IOException {
        log.info("OAuth2 로그인 성공");

        // OAuth2User에서 ID 추출
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        log.error("OAuth2User Attributes: {}", attributes);
//        String kakaoAuthId = oAuth2User.getAttribute("id").toString();
//        log.info(kakaoAuthId);
        User user = userRepository.findByKakaoAuthId(oAuth2User.getAttribute("id"));
        Long id = user.getId();

        // JWT 발급
        JwtDto jwtDto = jwtProvider.generateToken(id.toString());
        log.info("[KakaoOAuth2UserService] Jwt -> {}", jwtDto);
        // JWT를 응답 헤더에 추가
        response.setHeader("Authorization", "Bearer " + jwtDto.getAccessToken());
        response.setHeader("Refresh-Token", jwtDto.getRefreshToken());

        // 리다이렉트 처리 (필요에 따라 설정)
        getRedirectStrategy().sendRedirect(request, response, "/home");
    }
}
