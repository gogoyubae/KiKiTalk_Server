package com.kikitalk.chatting.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // JPA findBy 규칙
    // select * from user_master where kakao_email = ?
    public User findByKakaoAuthId(Long kakaoAuthId);

    //public User findByUserCode(String userCode);
}
