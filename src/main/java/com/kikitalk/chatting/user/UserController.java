package com.kikitalk.chatting.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
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
    String id = authentication.getName(); // JWT에서 추출된 이메일
    log.info("auto id -> {}", id);
    Optional<User> user = userService.getUser(Long.parseLong(id));
    return ResponseEntity.ok().body(user);
}
}

