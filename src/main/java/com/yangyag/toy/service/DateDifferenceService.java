package com.yangyag.toy.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Service
public class DateDifferenceService {

    public String calculateDifference(String baseDate) {
        LocalDate startDate = LocalDate.parse(baseDate, DateTimeFormatter.ISO_DATE);
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(startDate, currentDate);

        int totalMonths = period.getYears() * 12 + period.getMonths();

        // 단순히 몇 개월인지 문자열로 반환
        return String.format("%d months", totalMonths);
    }
}
