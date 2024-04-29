package com.yangyag.toy.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith({MockitoExtension.class})
class DateDifferenceServiceTest {

    @InjectMocks
    private DateDifferenceService dateDifferenceService;

    @Test
    void shouldReturnMonthByCalculated() {
        //given
        String baseDate = "2022-10-05";

        //when
        String result = dateDifferenceService.calculateMonthsDifference(baseDate);

        //then
        assertEquals("18", result);
    }

    @Test
    void shouldReturnYearFromMonths() {
        // given
        int months = 251;

        // when
        String result = dateDifferenceService.getYearFromMonths(months);

        // then
        assertEquals("20.9", result, "The calculated years should be 20.9");
    }

    @Test
    void shouldHandleZeroMonths() {
        // given
        int months = 0;

        // when
        String result = dateDifferenceService.getYearFromMonths(months);

        // then
        assertEquals("0.0", result, "The calculated years should be 0.0");
    }
}