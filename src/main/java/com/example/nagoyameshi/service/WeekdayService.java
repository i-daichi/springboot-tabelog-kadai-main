package com.example.nagoyameshi.service;

import java.util.List;

import com.example.nagoyameshi.entity.Weekday;
import com.example.nagoyameshi.repository.WeekdayRepository;

public class WeekdayService {
    private final WeekdayRepository weekdayRepository;

    public WeekdayService(WeekdayRepository weekdayRepository) {
        this.weekdayRepository = weekdayRepository;
    }

    public List<Weekday> findAll() {
        return weekdayRepository.findAll();
    }
}
