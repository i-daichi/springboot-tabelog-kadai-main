package com.example.nagoyameshi.service;

import com.example.nagoyameshi.repository.WeekdayRepository;

public class WeekdayService {
    private final WeekdayRepository weekdayRepository;

    public WeekdayService(WeekdayRepository weekdayRepository) {
        this.weekdayRepository = weekdayRepository;
    }
}
