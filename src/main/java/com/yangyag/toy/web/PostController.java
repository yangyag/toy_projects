package com.yangyag.toy.web;

import com.yangyag.toy.domain.posts.Post;
import com.yangyag.toy.service.PostService;
import com.yangyag.toy.web.dto.PostSaveRequest;
import com.yangyag.toy.web.dto.PostUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPost(@PathVariable Long id) throws Exception {

        var post = postService.getPost(id);

        return ResponseEntity.ok().body(post);
    }

    @PostMapping
    public ResponseEntity<Object> createPost(@RequestBody PostSaveRequest postSaveRequest) throws Exception {

        postService.create(postSaveRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePost(@RequestBody PostUpdateRequest postUpdateRequest) throws Exception {

        postService.update(postUpdateRequest);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable Long id) throws Exception {

        postService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Object> getList(Pageable pageable) throws Exception {
        var size = pageable.getPageSize();
        var page = pageable.getPageNumber();
        var sort = Sort.by("id").descending();

        PageRequest pageRequest = PageRequest.of(page, size, sort);

        var list = postService.getList(pageRequest);

        return ResponseEntity.ok().body(list);
    }
}
