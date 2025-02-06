package com.kikitalk.chatting.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // JPA findBy 규칙
    public User findByKakaoAuthId(Long kakaoAuthId);
    Optional<User> findById(Long id);
}
