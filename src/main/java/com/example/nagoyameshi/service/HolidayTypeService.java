package com.example.nagoyameshi.service;

import com.example.nagoyameshi.repository.HolidayTypeRepository;

public class HolidayTypeService {
    private final HolidayTypeRepository holidayTypeRepository;

    public HolidayTypeService(HolidayTypeRepository holidayTypeRepository) {
        this.holidayTypeRepository = holidayTypeRepository;
    }
}
