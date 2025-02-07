package com.kikitalk.chatting.relationship;


import com.kikitalk.chatting.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class RelationshipController {

    private final RelationshipService relationshipService;
    @PostMapping("/{id}")
    public ResponseEntity<Relationship> addRelationship(@AuthenticationPrincipal User user, @PathVariable("id") long friendId){
        Relationship relationship = relationshipService.addRelationship(user.getId(), friendId);
        return ResponseEntity.ok(relationship);
    }
}
