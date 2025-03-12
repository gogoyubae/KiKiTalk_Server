package com.kikitalk.chatting.user;


import com.kikitalk.chatting.security.jwt.JwtProvider;
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
    @Autowired
    private JwtProvider jwtProvider;

    // 전화번호로 전체 유저 검색
    @GetMapping("/search")
    public ResponseEntity<User> searchFriend(@RequestParam String phone) {
      Optional<User> user = userService.getUserByPhone(phone);
      log.info("Phone ->{}", phone);
      log.info("user->{}", user);
      return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(@RequestHeader("Authorization") String token) {
        log.info("String -> {}", token);
        String accessToken = token.replace("Bearer ", "");
        Long id = Long.parseLong(jwtProvider.getId(accessToken));
        Optional<User> user = userService.getUser(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    // 유저 이름 찾기
    @GetMapping("/username/{id}")
    public ResponseEntity<?> getUsername(@PathVariable("id") Long id) {
        Optional<User> user = userService.getUser(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get().getName());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}

