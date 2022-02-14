package com.yangyag.toy.service;

import com.yangyag.toy.domain.posts.Post;
import com.yangyag.toy.domain.posts.PostRepository;
import com.yangyag.toy.web.dto.PostUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class PostServiceTest {

    @InjectMocks
    private PostService service;

    @Mock
    private PostRepository postRepository;

    @Test
    void delete() throws Exception {
        // given
        var post = mock(Post.class);
        given(postRepository.findById(any())).willReturn(Optional.of(post));

        // when
        service.delete(anyLong());

        // then
        then(postRepository).should(atLeastOnce()).delete(any());
        verify(postRepository, atLeastOnce()).delete(any());
    }

    @Test
    @DisplayName("아이디가 없을 때 IllegalException 을 발생시켜야 한다.")
    void shouldBeThrowIllegalException_WhenIdIsEmpty() {
        // given
        var post = mock(Post.class);

        given(postRepository.findById(any())).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(10L)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Create 동작이 정상적으로 수행 되어야 한다")
    void create() throws Exception {

        // given
        var post = mock(Post.class);

        given(postRepository.save(post)).willReturn(post);

        // when
        service.create(post);

        // then
        then(postRepository).should(atLeastOnce()).save(post);
    }

    @Test
    @DisplayName("Update 동작이 정상적으로 수행 되어야 한다")
    void update() throws Exception {

        // given
        var post = mock(Post.class);
        var postUpdateRequest = mock(PostUpdateRequest.class);

        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));
        given(postRepository.save(any())).willReturn(post);

        // when
        service.update(postUpdateRequest);

        // then
        then(postRepository).should(atLeastOnce()).save(any());
    }

    @Test
    @DisplayName("Update 수행시 ID 가 없을때 IllegalException 이 발생해야 한다")
    void should_be_throws_illegalException_when_update_by_id_is_empty() throws Exception {
        var postUpdateRequest = mock(PostUpdateRequest.class);

        given(postRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(postUpdateRequest)).isInstanceOf(IllegalArgumentException.class);
    }
}