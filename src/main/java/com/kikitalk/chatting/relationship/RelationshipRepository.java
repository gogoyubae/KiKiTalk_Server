package com.kikitalk.chatting.relationship;

import com.kikitalk.chatting.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, Long> {
    boolean existsByUserIdAndFriendId(Long userId, Long friendId);

    @Query("SELECT r.friend FROM Relationship r WHERE r.user.id = :userId")
    List<User> findFriendsByUserId(Long userId);
}
