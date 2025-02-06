package com.kikitalk.chatting.user;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_user_info") // 실제 DB 테이블명
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment 설정
    @Column(name = "id") // DB 컬럼명
    private Long id;

    @Column(name = "kakao_auth_id", unique = true, nullable = false)
    private Long kakaoAuthId;

    @Column(name = "phone")
    private String phone;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "message")
    private String message;

    @Column(name = "profile_image")
    private String profileImage;
}

