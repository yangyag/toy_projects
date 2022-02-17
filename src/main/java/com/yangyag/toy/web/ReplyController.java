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

@RestController
@RequestMapping("/replies")
@RequiredArgsConstructor
@Slf4j
public class ReplyController {
    private final ReplyService replyService;

    @GetMapping
    public ResponseEntity<Object> getReply(ReplyRequest replyRequest) throws Exception {

        var reply = replyService.getReply(replyRequest);

        return ResponseEntity.ok().body(reply);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody ReplySaveRequest replySaveRequest) throws Exception {

        replyService.create(replySaveRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody ReplyUpdateRequest replyUpdateRequest) throws Exception {

        replyService.update(replyUpdateRequest);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) throws Exception {

        replyService.delete(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/list/{postId}")
    public ResponseEntity<Object> getList(@PathVariable Long postId, Pageable pageable) throws Exception {
        var list = replyService.getList(postId, pageable);

        return ResponseEntity.ok().body(list);
    }
}
