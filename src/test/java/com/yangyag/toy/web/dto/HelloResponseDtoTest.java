package com.yangyag.toy.web.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

public class HelloResponseDtoTest {

    @Test
    public void lombok_function_test() throws Exception{
        String name = "test";
        int amount = 1000;

        var hello = HelloResponseDto.builder()
                .name(name)
                .amount(amount)
                .build();


        assertThat(hello.getName()).isEqualTo(name);
        assertThat(hello.getAmount()).isEqualTo(amount);

    }
}
