package com.yangyag.toy.service;

import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Service
public class DateDifferenceService {

    private final LocalDate currentDate;

    public DateDifferenceService() {
        currentDate = LocalDate.now();
    }

    public DateDifferenceService(Clock clock) {
        currentDate = LocalDate.now(clock);
    }

    public String calculateMonthsDifference(String baseDate) {
        LocalDate startDate = LocalDate.parse(baseDate, DateTimeFormatter.ISO_DATE);
        Period period = Period.between(startDate, currentDate);

        int totalMonths = period.getYears() * 12 + period.getMonths();

        // 단순히 몇 개월인지 문자열로 반환
        return String.valueOf(totalMonths);
    }

    public String getYearFromMonths(int months) {
        double result = months / 12.0; // 12.0으로 나누어 소수점 결과를 유지

        // 소수점 첫째 자리까지 결과를 포맷팅하여 반환
        return String.format("%.1f", result);
    }

    public int calculateKoreanAge(String baseDate) {
        LocalDate birthDate = LocalDate.parse(baseDate, DateTimeFormatter.ISO_DATE);

        return currentDate.getYear() - birthDate.getYear() + 1;
    }

    public String calculateTimeUntilBy(String birthDate, int targetAge) {
        LocalDate birthday = LocalDate.parse(birthDate, DateTimeFormatter.ISO_DATE);
        LocalDate dateAtTarget = birthday.plusYears(targetAge); // 생일로부터 목표 년 후

        // 오늘 날짜와 계산하고자 하는 나이의 날짜 사이의 기간 계산
        Period period = Period.between(currentDate, dateAtTarget);

        // 결과 문자열 형성
        return formatPeriod(period);
    }

    private String formatPeriod(Period period) {
        // 년, 월, 일 각각이 0보다 큰 경우에만 문자열에 포함
        String result = "";
        if (period.getYears() > 0) {
            result += period.getYears() + "년 ";
        }
        if (period.getMonths() > 0) {
            result += period.getMonths() + "개월 ";
        }
        if (period.getDays() > 0) {
            result += period.getDays() + "일";
        }
        return result.trim(); // 문자열 양쪽의 공백 제거
    }
}
