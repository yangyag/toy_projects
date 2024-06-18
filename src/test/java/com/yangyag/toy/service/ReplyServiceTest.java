package com.yangyag.toy.service;

import com.yangyag.toy.domain.posts.Post;
import com.yangyag.toy.domain.posts.PostRepository;
import com.yangyag.toy.domain.reply.Reply;
import com.yangyag.toy.domain.reply.ReplyRepository;
import com.yangyag.toy.web.dto.reply.ReplySaveRequest;
import com.yangyag.toy.web.dto.reply.ReplyUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReplyServiceTest {

    @InjectMocks
    ReplyService replyService;

    @Mock
    ReplyRepository replyRepository;

    @Mock
    PostRepository postRepository;

    @Test
    @DisplayName("댓글 등록 기능이 정상적으로 작동해야 한다.")
    void should_be_able_create_reply() {
        // given
        var postId = 1L;
        var post = mock(Post.class);
        var request = mock(ReplySaveRequest.class);

        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));

        // when
        replyService.create(postId, request);

        // then
        then(postRepository).should().save(any(Post.class));
    }

    @Test
    @DisplayName("POST 의 ID값이 없을때 등록에 실패해야 한다.")
    void should_be_fail_create_reply_when_empty_parameter() {
        // given
        var postId = 1L;
        var request = mock(ReplySaveRequest.class);

        given(postRepository.findById(anyLong())).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(()-> replyService.create(postId, request)) .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("Update 동작이 정상적으로 수행 되어야 한다")
    void should_be_success_update_reply() {
        // given
        var id = 1L;
        var reply = mock(Reply.class);
        var request = mock(ReplyUpdateRequest.class);

        given(replyRepository.findById(anyLong())).willReturn(Optional.of(reply));

        // when
        replyService.update(id, request);

        // then
        then(replyRepository).should().save(any(Reply.class));
    }

    @Test
    @DisplayName("REPLY가 검색되지 않았을 때 없을때 수정에 실패해야 한다.")
    void should_be_throws_illegalException_when_update_by_id_is_empty() {
        // given
        var id = 1L;
        var request = mock(ReplyUpdateRequest.class);

        given(replyRepository.findById(anyLong())).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> replyService.update(id, request)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Reply 의 목록을 가지고 올 수 있어야 한다")
    void getList() throws Exception {

        var postId = 1L;
        var pageable = mock(Pageable.class);

        //when
        var list = replyService.getListBy(postId, pageable);

        //then
        then(replyRepository).should(atLeastOnce()).findAllByPostId(postId, pageable);
    }

    @Test
    @DisplayName("Delete 동작이 정상적으로 수행 되어야 한다")
    void delete() {
        // given
        var reply = mock(Reply.class);
        given(replyRepository.findById(any())).willReturn(Optional.of(reply));

        // when
        replyService.delete(anyLong());

        // then
        then(replyRepository).should(atLeastOnce()).delete(any());
    }

    @Test
    @DisplayName("Delete시 아이디가 없을 때 IllegalException 을 발생시켜야 한다.")
    void shouldBeThrowIllegalException_WhenIdIsEmpty() {
        // given
        var post = mock(Post.class);

        given(replyRepository.findById(any())).willReturn(Optional.empty());

        assertThatThrownBy(() -> replyService.delete(10L)).isInstanceOf(IllegalArgumentException.class);
    }
}
