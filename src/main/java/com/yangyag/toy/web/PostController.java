package com.yangyag.toy.web;

import com.yangyag.toy.domain.posts.Post;
import com.yangyag.toy.service.PostService;
import com.yangyag.toy.web.dto.PostSaveRequest;
import com.yangyag.toy.web.dto.PostUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPost(@PathVariable Long id) throws Exception {

        var post = postService.getPost(id);

        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Object> createPost(PostSaveRequest postSaveRequest) throws Exception {

        var id = postService.create(postSaveRequest);

        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePost(PostUpdateRequest postUpdateRequest) throws Exception {
        var id = postService.update(postUpdateRequest);
        return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable Long id) throws Exception {
        postService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
