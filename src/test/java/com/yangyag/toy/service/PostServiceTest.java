package com.yangyag.toy.service;

import com.yangyag.toy.domain.posts.Post;
import com.yangyag.toy.domain.posts.PostRepository;
import com.yangyag.toy.web.dto.post.PostSaveRequest;
import com.yangyag.toy.web.dto.post.PostUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
    @DisplayName("Delete가 정상적으로 수행되어야 한다.")
    void delete() throws Exception {
        // given
        var post = mock(Post.class);
        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));

        // when
        service.delete(anyLong());

        // then
        then(postRepository).should(atLeastOnce()).delete(any());
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
        var request = mock(PostSaveRequest.class);

        // when
        service.create(request);

        // then
        then(postRepository).should(atLeastOnce()).save(any(Post.class));
    }

    @Test
    @DisplayName("Update 동작이 정상적으로 수행 되어야 한다")
    void update() throws Exception {
        var post = mock(Post.class);
        var request = mock(PostUpdateRequest.class);

        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));

        // when
        service.update(anyLong(), request);

        // then
        then(postRepository).should(atLeastOnce()).save(any(Post.class));
    }

    @Test
    @DisplayName("Update 수행시 ID 가 없을때 IllegalException 이 발생해야 한다")
    void should_be_throws_illegalException_when_update_by_id_is_empty() throws Exception {
        var postUpdateRequest = mock(PostUpdateRequest.class);
        var id = 1L;

        given(postRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(id, postUpdateRequest)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Post 의 목록을 가지고 올 수 있어야 한다")
    void getList() throws Exception {
        //given
        var pageable = mock(Pageable.class);

        //when
        var list = service.getList(pageable);

        //then
        then(postRepository).should(atLeastOnce()).findAll(pageable);
    }
}
