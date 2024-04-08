package com.yangyag.toy.web;

import com.yangyag.toy.service.StrategyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(StrategyController.class)
class StrategyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Map<String, StrategyService> strategyServiceMap;

    @Test
    @WithMockUser
    void shouldCalledStrategy1WhenGivenStrategy1() throws Exception {
        //given
        StrategyService mockStrategyService = strategyName -> {};
        given(strategyServiceMap.get("strategy1")).willReturn(mockStrategyService);

        // When & Then
        mockMvc.perform(get("/strategy/strategy1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Strategy executed successfully"));

        verify(strategyServiceMap).get("strategy1");
    }

    @Test
    @WithMockUser
    void shouldReturnBadRequestWhenGivenInvalidStrategy() throws Exception {
        //given
        given(strategyServiceMap.get("invalidStrategy")).willReturn(null);

        // When & Then
        mockMvc.perform(get("/strategy/invalidStrategy").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid strategy: invalidStrategy"));

        verify(strategyServiceMap).get("invalidStrategy");
    }
}