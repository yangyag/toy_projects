package com.yangyag.toy.service;

import com.yangyag.toy.domain.posts.Post;
import com.yangyag.toy.domain.reply.Reply;
import com.yangyag.toy.domain.reply.ReplyRepository;
import com.yangyag.toy.web.dto.post.PostUpdateRequest;
import com.yangyag.toy.web.dto.reply.ReplySaveRequest;
import com.yangyag.toy.web.dto.reply.ReplyUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    @Test
    @DisplayName("댓글 등록 기능이 정상적으로 작동해야 한다.")
    void should_be_able_create_reply() throws Exception {

        // given
        var replySaveRequest = ReplySaveRequest.builder()
                .postId(1L)
                .author("Reply Author")
                .contents("Reply Contents")
                .pw("1234")
                .build();

        // when
        replyService.create(replySaveRequest);

        // then
        then(replyRepository).should(atLeastOnce()).save(any(Reply.class));
    }

    @Test
    @DisplayName("필수 값이 없는경우 댓글 등록 기능이 실패해야 한다.")
    void should_be_fail_create_reply_when_empty_parameter() throws Exception {
        // given
        var replySaveRequest = mock(ReplySaveRequest.class);

        // then
        assertThatThrownBy(()-> replyService.create(replySaveRequest)) .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Update 동작이 정상적으로 수행 되어야 한다")
    void should_be_success_update_reply() throws Exception {

        // given
        var reply = Reply.builder()
                .postId(1L)
                .id(1L)
                .contents("This is contents")
                .author("This is author")
                .pw("1234")
                .build();

        var replyUpdateRequest = ReplyUpdateRequest.builder()
                .postId(1L)
                .id(1L)
                .contents("This is contents")
                .author("This is author")
                .pw("1234")
                .build();

        given(replyRepository.findById(anyLong())).willReturn(Optional.of(reply));

        // when
        replyService.update(replyUpdateRequest);

        // then
        then(replyRepository).should(atLeastOnce()).save(any(Reply.class));
    }

    @Test
    @DisplayName("필수 값이 없는경우 update 가 실패해야 한다.")
    void should_be_fail_update_reply_when_empty_parameter() throws Exception {

        // given
        var reply = mock(Reply.class);
        var replyUpdateRequest = mock(ReplyUpdateRequest.class);

        given(replyRepository.findById(anyLong())).willReturn(Optional.of(reply));

        // then
        assertThatThrownBy(() -> replyService.update(replyUpdateRequest)).isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Update 수행시 ID 가 없을때 IllegalException 이 발생해야 한다")
    void should_be_throws_illegalException_when_update_by_id_is_empty() throws Exception {
        var replyUpdateRequest = mock(ReplyUpdateRequest.class);

        given(replyRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> replyService.update(replyUpdateRequest)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Reply 의 목록을 가지고 올 수 있어야 한다")
    void getList() throws Exception {

        //given
        int page = 0;
        int size = 5;
        Long postId = 1L;
        PageRequest pageRequest = PageRequest.of(page, size);

        //when
        var list = replyService.getList(postId, pageRequest);

        //then
        then(replyRepository).should(atLeastOnce()).findAllByPostId(postId, pageRequest);
    }

    @Test
    void delete() throws Exception {
        // given
        var reply = mock(Reply.class);
        given(replyRepository.findById(any())).willReturn(Optional.of(reply));

        // when
        replyService.delete(anyLong());

        // then
        then(replyRepository).should(atLeastOnce()).delete(any());
    }

    @Test
    @DisplayName("아이디가 없을 때 IllegalException 을 발생시켜야 한다.")
    void shouldBeThrowIllegalException_WhenIdIsEmpty() {
        // given
        var post = mock(Post.class);

        given(replyRepository.findById(any())).willReturn(Optional.empty());

        assertThatThrownBy(() -> replyService.delete(10L)).isInstanceOf(IllegalArgumentException.class);
    }
}
