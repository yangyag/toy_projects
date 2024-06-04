package com.yangyag.toy.service;

import com.yangyag.toy.domain.posts.Post;
import com.yangyag.toy.domain.posts.PostRepository;
import com.yangyag.toy.web.dto.post.PostDTO;
import com.yangyag.toy.web.dto.post.PostSaveRequest;
import com.yangyag.toy.web.dto.post.PostUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final Validator validator;

    @Transactional
    public void create(PostSaveRequest postSaveRequest) {
        var violations  = validator.validate(postSaveRequest);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        var post = Post.builder()
                .title(postSaveRequest.getTitle())
                .contents(postSaveRequest.getContents())
                .author(postSaveRequest.getAuthor())
                .build();

        postRepository.save(post);
    }

    @Transactional(readOnly=true)
    public Post getPost(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다"));
    }

    @Transactional
    public void update(Long id, PostUpdateRequest request) {
        var post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다"));

        post.setAuthor(request.getAuthor());
        post.setContents(request.getContents());
        post.setTitle(request.getTitle());

        postRepository.save(post);
    }

    @Transactional
    public void delete(Long id) {
        var post = postRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        postRepository.delete(post);
    }

    @Transactional(readOnly=true)
    public Page<Post> getList(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<PostDTO> getListWithReplies(Pageable pageable) {
        Page<Post> posts = postRepository.findAllWithReplies(pageable);
        return posts.map(post -> PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .contents(post.getContents())
                .author(post.getAuthor())
                .createdAt(post.getFormattedCreatedAt())
                .build());
    }

    /*
    @Transactional(readOnly=true)
    public Page<Post> getListWithReplies(Pageable pageable) {
        return postRepository.findAllWithReplies(pageable);
    }
    */
}
