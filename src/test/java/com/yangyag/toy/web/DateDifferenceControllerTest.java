package com.yangyag.toy.web;

import com.yangyag.toy.service.DateDifferenceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;


import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(DateDifferenceController.class)
class DateDifferenceControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DateDifferenceService dateDifferenceService;

    @Test
    @WithMockUser
    void shouldReturnMonthWhenRequired() throws Exception {

        given(dateDifferenceService.calculateDifference("2022-10-05")).willReturn("17");

        mockMvc
                .perform(
                        get("/date-difference")
                        .param("baseDate", "2022-10-05")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("17"))
                .andDo(print());
    }
}