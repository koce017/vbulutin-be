package com.koce017.vbulutin.repository;

import com.koce017.vbulutin.data.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findBySlug(String slug);
    List<Board> findByOwnerUsernameOrIsHiddenFalseOrderByOwnerUsernameAscTitleAsc(String username);
}
