package com.kikitalk.chatting.home;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal OAuth2User principal, Model model) {
        // OAuth2User 객체에서 닉네임을 가져와서 모델에 추가
        if (principal != null) {
            String nickname = principal.getAttribute("nickname"); // Kakao에서 닉네임 가져오기
            model.addAttribute("nickname", nickname);
        }
        return "home"; // home.html로 이동
    }
}
