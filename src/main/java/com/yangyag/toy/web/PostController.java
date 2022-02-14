package com.yangyag.toy.web;

import com.yangyag.toy.domain.posts.Post;
import com.yangyag.toy.service.PostService;
import com.yangyag.toy.web.dto.PostUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPost(@PathVariable Long id) throws Exception {

        var post = postService.getPost(id);

        return ResponseEntity.ok().body(post);
    }

    @PostMapping
    public ResponseEntity<Object> createPost(Post post) throws Exception {

        postService.create(post);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePost(
            @PathVariable Long id,
            PostUpdateRequest postUpdateRequest) throws Exception {

        postService.update(postUpdateRequest);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable Long id) throws Exception {

        postService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
