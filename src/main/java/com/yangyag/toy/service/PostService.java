package com.yangyag.toy.service;

import com.yangyag.toy.web.dto.PostUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import com.yangyag.toy.domain.posts.PostRepository;
import com.yangyag.toy.domain.posts.Post;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public void create(Post post) throws Exception {
        postRepository.save(post);
    }

    public Post getPost(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다"));
    }

    @Transactional
    public void update(PostUpdateRequest postUpdateRequest) {
        var id = postUpdateRequest.getId();

        postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다"));

        var after = Post.builder()
                .id(postUpdateRequest.getId())
                .title(postUpdateRequest.getTitle())
                .contents(postUpdateRequest.getContents())
                .author(postUpdateRequest.getAuthor())
                .build();

        postRepository.save(after);
    }

    @Transactional
    public void delete(Long id) {
        var post = postRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        postRepository.delete(post);
    }
}
