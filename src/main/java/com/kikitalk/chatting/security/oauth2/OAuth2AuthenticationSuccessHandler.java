package com.kikitalk.chatting.security.oauth2;

import com.kikitalk.chatting.security.jwt.JwtDto;
import com.kikitalk.chatting.security.jwt.JwtProvider;
import com.kikitalk.chatting.user.User;
import com.kikitalk.chatting.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                         Authentication authentication) throws IOException {

        // 카카오 사용자 정보 가져오기
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        Long kakaoAuthId = (Long) attributes.get("id");
        String name = (String) profile.get("nickname");
        String profileImage = (String) profile.get("profile_image_url");

        User user = userRepository.findByKakaoAuthId(oAuth2User.getAttribute("id"));

        if (user == null) {
            log.info("[SuccessHandler] 신규 사용자, 회원가입 페이지로 이동");
            response.sendRedirect("http://localhost:5173/signup?kakaoAuthId=" + kakaoAuthId
                    + "&name=" + URLEncoder.encode(name, "UTF-8")
                    + "&profileImage=" + URLEncoder.encode(profileImage, "UTF-8"));
        } else {
        Long id = user.getId();
        // JWT 발급
        JwtDto jwtDto = jwtProvider.generateToken(id.toString());
        log.info("accessToken -> {}", jwtDto.getAccessToken());

            // 리다이렉트 처리
            response.sendRedirect("http://localhost:5173/home?accessToken=" + jwtDto.getAccessToken());
            log.info("response -> {}", response);
        }
    }
}
