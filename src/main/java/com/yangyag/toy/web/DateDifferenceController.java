package com.yangyag.toy.web;

import com.yangyag.toy.service.DateDifferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DateDifferenceController {

    private final DateDifferenceService dateDifferenceService;

    @GetMapping("/date-difference")
    public String getDateDifference(@RequestParam("baseDate") String baseDate) {
        return dateDifferenceService.calculateMonthsDifference(baseDate);
    }

    @GetMapping("/calculateYear")
    public String getYearFromMonths(@RequestParam("baseMonths") int baseMonths) {
        return dateDifferenceService.getYearFromMonths(baseMonths);
    }
}
