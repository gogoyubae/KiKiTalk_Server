package com.kikitalk.chatting.relationship;

import com.kikitalk.chatting.user.User;
import com.kikitalk.chatting.user.UserRepository;
import com.kikitalk.chatting.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RelationshipService {
    private final RelationshipRepository relationshipRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public Relationship addRelationship(Long userId, Long friendId) {

        if (relationshipRepository.existsByUserIdAndFriendId(userId, friendId)) {
            throw new RuntimeException("이미 친구로 등록된 사용자입니다.");
        }
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("사용자를 찾을 수 없습니다."));
        User friend = userRepository.findById(friendId).orElseThrow(()->new RuntimeException("사용자를 찾을 수 없습니다."));
        return relationshipRepository.save(new Relationship(user,friend));
    }
    public List<User> getAllFriends(Long userId) {
        return relationshipRepository.findFriendsByUserId(userId);
    }
}
