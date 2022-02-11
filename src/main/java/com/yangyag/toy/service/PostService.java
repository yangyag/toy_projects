package com.yangyag.toy.service;

import com.yangyag.toy.web.dto.PostSaveRequest;
import com.yangyag.toy.web.dto.PostUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import com.yangyag.toy.domain.posts.PostRepository;
import com.yangyag.toy.domain.posts.Post;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public Long create(PostSaveRequest postSaveRequest) throws Exception {
        var post =
                Post.builder()
                        .contents(postSaveRequest.getContents())
                        .author(postSaveRequest.getAuthor())
                        .title(postSaveRequest.getTitle())
                        .build();

        return postRepository.save(post).getId();
    }

    @Transactional
    public Post getPost(Long id) throws Exception {
        var post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다"));;

        return post;
    }

    @Transactional
    public Long update(PostUpdateRequest postUpdateRequest) throws Exception {
        var id = postUpdateRequest.getId();
        var post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다"));

        post.builder()
                .title(postUpdateRequest.getTitle())
                .contents(postUpdateRequest.getContents())
                .author(postUpdateRequest.getAuthor())
                .build();

        return id;
    }

    @Transactional
    public void delete(Long id) throws Exception {
        var post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다"));

        postRepository.delete(post);
    }
}
