package com.yangyag.toy.web;

import com.yangyag.toy.service.PostService;
import com.yangyag.toy.web.dto.post.PostRequest;
import com.yangyag.toy.web.dto.post.PostSaveRequest;
import com.yangyag.toy.web.dto.post.PostUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPost(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok().body(postService.getPost(id));
    }

    @PostMapping
    public ResponseEntity<Object> createPost(@RequestBody PostSaveRequest postSaveRequest) throws Exception {
        postService.create(postSaveRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePost(@PathVariable Long id,
                                             @RequestBody PostUpdateRequest postUpdateRequest) throws Exception {
        postService.update(id, postUpdateRequest);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable Long id) throws Exception {
        postService.delete(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Object> getList(PostRequest request, Pageable pageable) throws Exception {
        var results = postService.getList(request, pageable);

        return ResponseEntity.ok().body(results);
    }
}
