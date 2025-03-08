package com.kikitalk.chatting.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    Optional<User> getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user;
    }

    String getUsername(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return user.getName();
    }

    User getUserByPhone(String phone) {
        User user = userRepository.findByPhone(phone);
        if (user == null) {
            log.info("No user found with phone number: {}", phone);
            return null;
        }
        return user;
    }

}
