package com.kikitalk.chatting.security.oauth2;

import com.kikitalk.chatting.security.jwt.JwtDto;
import com.kikitalk.chatting.security.jwt.JwtProvider;
import com.kikitalk.chatting.user.User;
import com.kikitalk.chatting.user.UserDto;
import com.kikitalk.chatting.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class OAuth2Controller {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDto userDto,
                                    HttpServletRequest request, HttpServletResponse response,
                                    HttpSession session) throws IOException {
        // 세션에서 카카오 정보 가져오기
        Long kakaoAuthId = (Long) session.getAttribute("kakaoAuthId");
        String name = (String) session.getAttribute("name");
        String profileImage = (String) session.getAttribute("profileImage");

        if (kakaoAuthId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("세션이 만료되었습니다.");
        }

        // 사용자 저장
        User user = User.builder()
                .kakaoAuthId(kakaoAuthId)
                .name(name)
                .profileImage(profileImage)
                .nickname(userDto.getNickname())
                .phone(userDto.getPhone())
                .build();

        userRepository.save(user);

        // JWT 발급
        JwtDto jwtDto = jwtProvider.generateToken(user.getId().toString());
        log.info("accessToken -> {}", jwtDto.getAccessToken());
        // JWT를 응답 헤더에 추가
        response.setHeader("accessToken", jwtDto.getAccessToken());
        response.setHeader("refreshToken", jwtDto.getRefreshToken());
        // 세션 삭제 (더 이상 필요 없음)
        session.invalidate();

        return ResponseEntity.ok().body(Map.of("success", true));
    }
}
