package com.yangyag.toy.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Service
public class DateDifferenceService {

    public String calculateMonthsDifference(String baseDate) {
        LocalDate startDate = LocalDate.parse(baseDate, DateTimeFormatter.ISO_DATE);
        LocalDate currentDate = LocalDate.now();
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
        LocalDate currentDate = LocalDate.now();

        return currentDate.getYear() - birthDate.getYear() + 1;
    }

}
