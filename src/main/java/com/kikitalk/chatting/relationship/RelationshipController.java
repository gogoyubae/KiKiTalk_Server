package com.kikitalk.chatting.relationship;


import com.kikitalk.chatting.security.jwt.JwtProvider;
import com.kikitalk.chatting.user.User;
import com.kikitalk.chatting.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("api/v1/relationship")
@RequiredArgsConstructor
public class RelationshipController {
    @Autowired
    private JwtProvider jwtProvider;

    // 친구 추가
    private final RelationshipService relationshipService;
    @PostMapping("/add/{id}")
    public ResponseEntity<Relationship> addRelationship(@RequestHeader("Authorization") String token, @PathVariable("id") long friendId){
        String accessToken = token.replace("Bearer ", "");
        Long id = Long.parseLong(jwtProvider.getId(accessToken));
        Relationship relationship = relationshipService.addRelationship(id, friendId);
        return ResponseEntity.ok(relationship);
    }
    @GetMapping("/list")
    public ResponseEntity<List<User>> getAllFriends(@RequestHeader("Authorization") String token){
        String accessToken = token.replace("Bearer ", "");
        Long id = Long.parseLong(jwtProvider.getId(accessToken));
        log.info("Get relationships of user {}", id);
        List<User> friends = relationshipService.getAllFriends(id);
        return ResponseEntity.ok(friends);
    }
    @GetMapping("/is-friend/{id}")
    public ResponseEntity<Boolean> isFriend(@RequestHeader("Authorization") String token, @PathVariable("id") long friendId){
        String accessToken = token.replace("Bearer ", "");
        Long id = Long.parseLong(jwtProvider.getId(accessToken));
        boolean isFriend = relationshipService.isFriend(id, friendId);
        return ResponseEntity.ok(isFriend);
    }
}
