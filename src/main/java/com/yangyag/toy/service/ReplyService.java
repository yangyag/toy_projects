package com.yangyag.toy.service;

import com.yangyag.toy.domain.reply.Reply;
import com.yangyag.toy.domain.reply.ReplyRepository;
import com.yangyag.toy.web.dto.reply.ReplyRequest;
import com.yangyag.toy.web.dto.reply.ReplySaveRequest;
import com.yangyag.toy.web.dto.reply.ReplyUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReplyService {
    private final ReplyRepository replyRepository;

    @Transactional
    public void create(ReplySaveRequest replySaveRequest) throws Exception {

        var reply = Reply.builder()
                .author(replySaveRequest.getAuthor())
                .contents(replySaveRequest.getContents())
                .pw(replySaveRequest.getPw())
                .postId(replySaveRequest.getPostId())
                .build();

        replyRepository.save(reply);
    }

    @Transactional(readOnly=true)
    public Reply getReply(ReplyRequest replyRequest) {

        var postId = replyRequest.getPostId();
        var id = replyRequest.getId();

        return replyRepository.findByPostIdAndId(postId, id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다"));
    }

    @Transactional
    public void update(ReplyUpdateRequest replyUpdateRequest) {
        var id = replyUpdateRequest.getId();

        replyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다"));

        var reply = Reply.builder()
                .postId(replyUpdateRequest.getPostId())
                .id(replyUpdateRequest.getId())
                .contents(replyUpdateRequest.getContents())
                .author(replyUpdateRequest.getAuthor())
                .pw(replyUpdateRequest.getPw())
                .build();

        replyRepository.save(reply);
    }

    @Transactional
    public void delete(Long id) {
        var reply = replyRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        replyRepository.delete(reply);
    }

    @Transactional(readOnly=true)
    public Page<Reply> getList(Long postId, Pageable pageable) {
        return replyRepository.findAllByPostId(postId, pageable);
    }
}
