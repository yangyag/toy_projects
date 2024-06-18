package com.yangyag.toy.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yangyag.toy.domain.posts.Post;
import com.yangyag.toy.domain.posts.PostRepository;
import com.yangyag.toy.domain.reply.Reply;
import com.yangyag.toy.domain.reply.ReplyRepository;
import com.yangyag.toy.web.dto.reply.ReplySaveRequest;
import com.yangyag.toy.web.dto.reply.ReplyUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(printOnlyOnFailure = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith({SpringExtension.class})
@ActiveProfiles("test")
public class ReplyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired protected ObjectMapper objectMapper;

    private Long savedPostId;
    private Long savedReplyId;

    @BeforeEach
    void setUp() {
        initializeData();
    }

    @DisplayName("작업 전 데이터 초기화")
    void initializeData() {
        replyRepository.deleteAll();
        postRepository.deleteAll();

        var post = Post.builder()
                .contents("Post Contents")
                .title("Post title")
                .author("Post author")
                .build();

        var reply_1 = Reply.builder()
                .contents("Reply Contents")
                .author("Reply author")
                .pw("1234")
                .build();

        var reply_2 = Reply.builder()
                .contents("Reply Contents")
                .author("Reply author")
                .pw("1234")
                .build();

        post.addReply(reply_1);
        post.addReply(reply_2);

        postRepository.save(post);

        savedPostId = post.getId();
        savedReplyId = post.getReplies().get(0).getId();
    }

    @Test
    @DisplayName("조회 성공")
    void should_be_ok() throws Exception {
        mockMvc
                .perform(
                        get("/posts/{postId}/replies/{id}", savedPostId, savedReplyId)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("데이터 등록이 성공해야 한다")
    void should_be_able_to_create_request() throws Exception {
        var replySaveRequest = ReplySaveRequest.builder()
                .contents("This is Contents")
                .author("This is author")
                .pw("1234")
                .build();

        mockMvc
                .perform(
                        post("/posts/{postId}/replies", savedPostId)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(replySaveRequest))
                )
                .andExpect(status().isCreated())
                .andDo(print());

    }


    @Test
    @DisplayName("데이터 수정이 성공해야 한다")
    void should_be_able_update_request() throws Exception {
        var replyUpdateRequest = ReplyUpdateRequest.builder()
                .contents("바뀐 내용")
                .author("바뀐 작성자")
                .pw("1234")
                .build();

        mockMvc
                .perform(
                        put("/posts/{postId}/replies/{id}", savedPostId, savedReplyId)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(replyUpdateRequest))
                )
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("데이터 삭제가 성공해야 한다")
    void should_be_able_delete_request() throws Exception {
        mockMvc
                .perform(delete("/posts/{postId}/replies/{id}", savedPostId, savedReplyId))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Reply 의 목록을 가지고 올 수 있어야 한다")
    void should_be_able_bring_reply_list() throws Exception {
        var page = 0;
        var size = 5;

        MultiValueMap<String, String> requestParams = this.ListRequestParams(page, size);

        mockMvc
                .perform(
                        get("/posts/{postId}/replies", savedPostId).params(requestParams)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageable.pageSize").value(size))
                .andExpect(jsonPath("$.pageable.pageNumber").value(page))
                .andDo(print());
    }

    private MultiValueMap<String, String> ListRequestParams(int page, int size) throws Exception {
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();

        requestParams.set("size", String.valueOf(size));
        requestParams.set("page", String.valueOf(page));

        return requestParams;
    }
}
