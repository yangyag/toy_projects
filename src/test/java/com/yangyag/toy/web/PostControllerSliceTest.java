package com.yangyag.toy.web;

import com.yangyag.toy.service.PostService;
import com.yangyag.toy.service.HelloService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
public class PostControllerSliceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @MockBean
    private HelloService helloService;

    @Test
    @WithMockUser
    void shouldBeOkWhenRequested() throws Exception {
        mockMvc
                .perform(get("/posts"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
