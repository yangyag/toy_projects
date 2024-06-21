package com.yangyag.toy.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yangyag.toy.config.TestSecurityConfig;
import com.yangyag.toy.service.PostService;
import com.yangyag.toy.web.dto.post.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// PostController만 테스트하는 슬라이스 테스트 설정
@WebMvcTest(PostController.class)
// 테스트용 보안 설정 적용
@ContextConfiguration(classes = TestSecurityConfig.class)
class PostControllerSliceTest {

    // MockMvc: 컨트롤러 테스트를 위한 메인 진입점
    @Autowired
    private MockMvc mockMvc;

    // JSON 변환을 위한 ObjectMapper
    @Autowired
    private ObjectMapper objectMapper;

    // PostService의 모의 객체 생성
    @MockBean
    private PostService postService;

    // 테스트에 사용할 DTO 객체들
    private PostSaveRequest postSaveRequest;
    private PostDTO postDTO;

    // 각 테스트 전에 실행되는 설정 메소드
    @BeforeEach
    void setUp() {
        // 게시물 저장 요청 DTO 생성
        postSaveRequest = PostSaveRequest.builder()
                .title("Test Title")
                .contents("Test Contents")
                .author("Test Author")
                .build();

        // 게시물 응답 DTO 생성
        postDTO = PostDTO.builder()
                .id(1L)
                .title("Test Title")
                .contents("Test Contents")
                .author("Test Author")
                .build();
    }

    @Test
    @DisplayName("GET /posts should return list of posts")
    void shouldReturnPostListWhenRequested() throws Exception {
        // Given: 테스트 데이터 및 동작 설정
        Page<PostDTO> postPage = new PageImpl<>(List.of(postDTO));
        given(postService.getListWithReplies(any(Pageable.class))).willReturn(postPage);

        // When: API 호출 실행
        var resultActions = mockMvc.perform(get("/posts"));

        // Then: 결과 검증
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].title").value("Test Title"));

        then(postService).should().getListWithReplies(any(Pageable.class));
    }

    @Test
    @DisplayName("POST /posts should create a new post")
    void shouldCreatePostWhenValidDataProvided() throws Exception {
        // Given: PostService의 create 메소드가 아무것도 반환하지 않도록 설정
        willDoNothing().given(postService).create(any(PostSaveRequest.class));

        // When: 새 게시물 생성 요청 실행
        var resultActions = mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postSaveRequest)));

        // Then: 결과 검증
        resultActions
                .andExpect(status().isCreated());

        // PostService의 create 메소드가 호출되었는지 확인
        then(postService).should().create(any(PostSaveRequest.class));
    }

    @Test
    @DisplayName("DELETE /posts/{id} should delete the post")
    void shouldDeletePostWhenValidIdProvided() throws Exception {
        // Given: 삭제할 게시물 ID 설정
        Long postId = 1L;
        willDoNothing().given(postService).delete(anyLong());

        // When: 게시물 삭제 요청 실행
        var resultActions = mockMvc.perform(delete("/posts/" + postId));

        // Then: 결과 검증
        resultActions.andExpect(status().isOk());

        // PostService의 delete 메소드가 호출되었는지 확인
        then(postService).should().delete(anyLong());
    }
}