package com.yangyag.toy.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class DateDifferenceServiceTest {

    private DateDifferenceService dateDifferenceService;

    @Mock
    private Clock clock;

    @BeforeEach
    void setUp() {
        // 고정된 시간을 설정합니다 (예: 2023-01-01T00:00:00Z)
        Instant fixedInstant = Instant.parse("2024-01-01T00:00:00Z");
        when(clock.instant()).thenReturn(fixedInstant);
        when(clock.getZone()).thenReturn(ZoneId.of("UTC"));

        // Mock Clock을 사용하여 DateDifferenceService 인스턴스 생성
        dateDifferenceService = new DateDifferenceService(clock);
    }

    @Test
    void shouldReturnMonthByCalculated() {
        //given
        String baseDate = "2022-10-05";

        //when
        Integer result = Integer.valueOf(dateDifferenceService.calculateMonthsDifference(baseDate));

        //then
        assertInstanceOf(Integer.class, result);
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

    @Test
    void shouldReturnKoreanAge() {
        // given
        String birthDate = "1986-02-05";

        // when
        int koreanAge = dateDifferenceService.calculateKoreanAge(birthDate);

        // then
        int expectedAge = LocalDate.now().getYear() - 1986 + 1;
        assertEquals(expectedAge, koreanAge, "The calculated Korean age should be correct based on the current year.");
    }

    @Test
    void shouldReturnRemainingDateFromBirth() {
        // given
        String birthDate = "2022-10-05";
        int targetAge = 20;

        // when
        String remainingDate = dateDifferenceService.calculateTimeUntilBy(birthDate, targetAge);

        // then
        assertEquals("18년 9개월 4일", remainingDate);
    }
}