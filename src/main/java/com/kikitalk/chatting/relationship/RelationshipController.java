package com.kikitalk.chatting.relationship;


import com.kikitalk.chatting.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class RelationshipController {

    // 친구 추가
    private final RelationshipService relationshipService;
    @PostMapping("/{id}")
    public ResponseEntity<Relationship> addRelationship(@AuthenticationPrincipal User user, @PathVariable("id") long friendId){
        Relationship relationship = relationshipService.addRelationship(user.getId(), friendId);
        return ResponseEntity.ok(relationship);
    }
    @GetMapping
    public ResponseEntity<List<User>> getAllFriends(@AuthenticationPrincipal User user){
        log.info("Get relationships of user {}", user.getId());
        List<User> friends = relationshipService.getAllFriends(user.getId());
        return ResponseEntity.ok(friends);
    }
}
