package com.yangyag.toy.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yangyag.toy.domain.posts.Post;
import com.yangyag.toy.domain.posts.PostRepository;
import com.yangyag.toy.web.dto.PostUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(printOnlyOnFailure = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith({SpringExtension.class})
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired protected ObjectMapper objectMapper;

    @Autowired
    private PostRepository postRepository;

    private Post post;

    @BeforeEach
    void setUp() throws Exception {

        post = Post.builder()
                .title("This is title 1")
                .contents("This is contents 1")
                .author("This is author 1")
                .build();

        postRepository.save(post);
    }

    @Test
    @DisplayName("데이터 등록이 성공해야 한다")
    void should_be_able_to_create_request() throws Exception {

        var post = Post.builder()
                .title("This is title 2")
                .contents("This is contents 2")
                .author("This is author 2")
                .build();

        mockMvc
                .perform(
                        post("/posts")
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(post))
                )
                .andExpect(status().isCreated())
                .andDo(print());

    }

    @Test
    @DisplayName("조회 성공")
    void should_be_ok() throws Exception {

        mockMvc
                .perform(get("/posts/1"))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("데이터 수정이 성공해야 한다")
    void should_be_able_update_request() throws Exception {
        var postUpdateRequest = PostUpdateRequest.builder()
                .id(1L)
                .title("바뀐 타이틀")
                .contents("바뀐 내용")
                .author("바뀐 작성자")
                .build();

        mockMvc
                .perform(
                        put("/posts")
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(postUpdateRequest))
                )
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("데이터 삭제가 성공해야 한다")
    void should_be_able_delete_request() throws Exception {

        mockMvc
                .perform(delete("/posts/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }



}
