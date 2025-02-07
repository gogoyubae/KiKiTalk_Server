package com.kikitalk.chatting.relationship;

import com.kikitalk.chatting.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, Long> {
    boolean existsByUserAndFriend(User user, User friend);
    List<Relationship> findByUser(User user);
}
