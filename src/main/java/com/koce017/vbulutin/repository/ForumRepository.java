package com.koce017.vbulutin.repository;

import com.koce017.vbulutin.data.entity.Forum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForumRepository extends JpaRepository<Forum, Long> {
    Optional<Forum> findBySlug(String slug);

    @Query("SELECT MAX(f.position) FROM Forum f WHERE f.category.id = :categoryId")
    Long findMaxPositionByCategoryIdAndParentForumId(@Param("categoryId") Long categoryId);
}
