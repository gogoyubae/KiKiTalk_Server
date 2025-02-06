package com.kikitalk.chatting.user;

import com.kikitalk.chatting.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    Optional<User> getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user;
    }
}
