package com.kikitalk.chatting.security.oauth2;

import com.kikitalk.chatting.user.User;
import com.kikitalk.chatting.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 카카오 사용자 정보 가져오기
        Map<String, Object> attributes = oAuth2User.getAttributes();
        Long kakaoAuthId = Long.valueOf(attributes.get("id").toString());
        log.info("[KakaoOAuth2UserService] Auth ID -> {}", kakaoAuthId);

        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        String nickname = (String) profile.get("nickname");
        String profileImage = (String) profile.get("profile_image_url");

        log.info("[KakaoOAuth2UserService] NickName -> {}", nickname);
        log.info("[KakaoOAuth2UserService] ProfileImageUrl -> {}", profileImage);

        // DB에 사용자 저장 (없으면 생성)
        User user = userRepository.findByKakaoAuthId(kakaoAuthId);
        if (user == null) {
            user = User.builder()
                    .kakaoAuthId(kakaoAuthId)
                    .name(nickname)
                    .profileImage(profileImage)
                    .build();
            userRepository.save(user);
        }

        // Spring Security에서 사용할 수 있도록 DefaultOAuth2User 반환
        return new DefaultOAuth2User(
                Collections.singleton(() -> "ROLE_USER"), // 권한 정보
                attributes,
                "id" // OAuth2User에서 기본 키로 사용할 필드
        );
    }
}
