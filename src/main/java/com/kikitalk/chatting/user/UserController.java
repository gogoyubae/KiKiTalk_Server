package com.kikitalk.chatting.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;
//
//    @GetMapping("/me")
//    public ResponseEntity<Object> getCurrentUser(HttpServletRequest request) {
//        User user = userService.getUser(request);
//        return ResponseEntity.ok().body(user);
//    }
@GetMapping("/me")
public ResponseEntity<?> getUserInfo(Authentication authentication) {
    String kakaoAuthId = authentication.getName(); // JWT에서 추출된 이메일
    User user = userService.getUser(Long.parseLong(kakaoAuthId));
    return ResponseEntity.ok().body(user);
}
}

