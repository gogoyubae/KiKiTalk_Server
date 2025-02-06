package com.kikitalk.chatting.user;

import com.kikitalk.chatting.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User getUser(Long kakaoAuthId) {
        User user = userRepository.findByKakaoAuthId(kakaoAuthId);
        return user;
    }
}
