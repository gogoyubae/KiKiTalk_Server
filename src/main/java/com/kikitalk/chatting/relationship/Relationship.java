package com.kikitalk.chatting.relationship;

import com.kikitalk.chatting.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tbl_relationship")
public class Relationship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 친구관계 ID (PK)

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // 사용자 ID (FK)

    @ManyToOne
    @JoinColumn(name = "friend_id", nullable = false)
    private User friend;  // 친구 ID (FK)
}
