package com.koce017.vbulutin.repository;

import com.koce017.vbulutin.data.entity.Forum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForumRepository extends JpaRepository<Forum, Long> {
    Optional<Forum> findBySlug(String slug);
}
