package com.yangyag.toy.domain.reply;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Optional<Reply> findByPostIdAndId(Long postId, Long id);
    Page<Reply> findAllByPostId(Long postId, Pageable pageable);
}
