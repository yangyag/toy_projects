package com.yangyag.toy.web;

import com.yangyag.toy.service.ReplyService;
import com.yangyag.toy.web.dto.reply.ReplyRequest;
import com.yangyag.toy.web.dto.reply.ReplySaveRequest;
import com.yangyag.toy.web.dto.reply.ReplyUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/posts/{postId}/replies")
@RequiredArgsConstructor
@Slf4j
public class ReplyController {
    private final ReplyService replyService;

    @GetMapping
    public ResponseEntity<Object> getList(@PathVariable Long postId, Pageable pageable) throws Exception {
        var list = replyService.getListBy(postId, pageable);

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getReply(@PathVariable Long postId, @PathVariable Long id) throws Exception {
        var reply = replyService.getReply(id);

        return ResponseEntity.ok().body(reply);
    }

    @PostMapping
    public ResponseEntity<Object> create(@PathVariable Long postId,
                                         @Valid @RequestBody ReplySaveRequest request) throws Exception {
        replyService.create(postId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long postId,
                                         @PathVariable Long id,
                                         @RequestBody ReplyUpdateRequest request) throws Exception {

        replyService.update(id, request);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long postId, @PathVariable Long id) throws Exception {
        replyService.delete(id);

        return ResponseEntity.ok().build();
    }
}
