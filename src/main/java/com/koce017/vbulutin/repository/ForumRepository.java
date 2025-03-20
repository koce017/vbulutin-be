package com.koce017.vbulutin.repository;

import com.koce017.vbulutin.data.entity.Forum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumRepository extends CrudRepository<Forum, Long> {
}
