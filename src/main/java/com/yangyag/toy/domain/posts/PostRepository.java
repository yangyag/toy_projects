package com.yangyag.toy.domain.posts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = "SELECT p FROM Post p LEFT JOIN FETCH p.replies",
            countQuery = "SELECT COUNT(p) FROM Post p")
    Page<Post> findAllWithReplies(Pageable pageable);
}
