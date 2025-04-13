package com.koce017.vbulutin.repository;

import com.koce017.vbulutin.data.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findFirstByTopicForumIdOrderByCreatedAtDesc(Long forumId);
}
