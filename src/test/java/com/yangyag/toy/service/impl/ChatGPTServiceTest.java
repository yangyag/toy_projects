package com.yangyag.toy.service.impl;

import com.yangyag.toy.service.GenerativeAI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ChatGPTServiceTest {
    @Mock
    private GenerativeAI generativeAI;

    @Test
    @DisplayName("Chat GPT API 가 호출 될 수 있어야 한다.")
    void shouldCallApiOfGPT() {
        String prompt = "안녕하세요";
        String model = "GPT-3.5-turbo";

        //given
        given(generativeAI.question(anyString(), anyString())).willReturn("Chat GPT");

        //when
        generativeAI.question(prompt, model);

        //then
        then(generativeAI).should().question(anyString(), anyString());
    }
}