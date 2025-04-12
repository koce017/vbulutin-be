package com.koce017.vbulutin.repository;

import com.koce017.vbulutin.data.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    Optional<Topic> findBySlug(String slug);

    @Query("""
            SELECT t FROM Topic t
            JOIN t.posts p
            WHERE t.forum.id = :forumId
            GROUP BY t.id
            ORDER BY MAX(p.createdAt) DESC
    """)
    List<Topic> findTopicsByForumOrderedByLastPost(@Param("forumId") Long forumId);
}
