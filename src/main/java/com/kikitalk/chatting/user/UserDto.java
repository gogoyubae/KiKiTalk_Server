package com.kikitalk.chatting.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;                 // Auto-increment ID
    private String phone;            // Unique phone number
    private String name;             // Name from kakao
    private LocalDate birthDate;     // Birth date
    private String nickname;         // Nickname
    private String message;          // Status message
    private String profileImage;     // Profile image URL or path from kakao
}
