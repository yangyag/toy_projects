package com.yangyag.toy.service;

import com.yangyag.toy.domain.posts.PostRepository;
import com.yangyag.toy.domain.reply.Reply;
import com.yangyag.toy.domain.reply.ReplyRepository;
import com.yangyag.toy.web.dto.reply.ReplySaveRequest;
import com.yangyag.toy.web.dto.reply.ReplyUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public Page<Reply> getListBy(Long postId, Pageable pageable) {
        return replyRepository.findAllByPostId(postId, pageable);
    }

    @Transactional
    public void create(Long postId, ReplySaveRequest replySaveRequest) throws Exception {
        var post = postRepository.findById(postId).orElseThrow(RuntimeException::new);

        var reply = Reply.builder()
                .author(replySaveRequest.getAuthor())
                .contents(replySaveRequest.getContents())
                .pw(replySaveRequest.getPw())
                .build();

        post.addReply(reply);

        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public Reply getReply(Long id) {
        return replyRepository.findById(id)
                .orElseThrow(RuntimeException::new);
    }

    @Transactional
    public void update(Long id, ReplyUpdateRequest request) {
        var reply = replyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다"));

        reply.setContents(request.getContents());
        reply.setAuthor(reply.getAuthor());
        reply.setPw(reply.getPw());

        replyRepository.save(reply);
    }

    @Transactional
    public void delete(Long id) {
        var reply = replyRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        replyRepository.delete(reply);
    }
}
