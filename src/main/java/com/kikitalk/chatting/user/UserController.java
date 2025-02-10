package com.kikitalk.chatting.user;


import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class UserController {


    @Autowired
    private UserService userService;

    // 전화번호로 전체 유저 검색
    @GetMapping("/search")
    public ResponseEntity<User> searchFriend(@AuthenticationPrincipal User user, String phone) {
        return ResponseEntity.ok(userService.getUserByPhone(phone));
    }
    // 유저 프로필
    @GetMapping("/profile")
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal User authenticatedUser) {
        Long id = authenticatedUser.getId();
        log.info("auto id -> {}", id);
        Optional<User> user = userService.getUser(id);
        if (user.isPresent()) {
            return ResponseEntity.ok().body(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}

