package com.yangyag.toy.service;

import com.yangyag.toy.web.dto.post.PostRequest;
import com.yangyag.toy.web.dto.post.PostSaveRequest;
import com.yangyag.toy.web.dto.post.PostUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import com.yangyag.toy.domain.posts.PostRepository;
import com.yangyag.toy.domain.posts.Post;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostSupport postSupport;

    @Transactional
    public void create(PostSaveRequest postSaveRequest) {

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
    public Page<PostRequest> getList(PostRequest request, Pageable pageable) {
        return postSupport.findAllBy(request, pageable);
    }
}
