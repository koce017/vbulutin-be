package com.koce017.vbulutin.repository;

import com.koce017.vbulutin.data.entity.Board;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends CrudRepository<Board, Long> {
    List<Board> findAll();
    Optional<Board> findBySlug(String slug);
}
