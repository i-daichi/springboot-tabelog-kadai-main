package com.example.nagoyameshi.service;

import java.util.List;

import com.example.nagoyameshi.entity.HolidayType;
import com.example.nagoyameshi.repository.HolidayTypeRepository;

public class HolidayTypeService {
    private final HolidayTypeRepository holidayTypeRepository;

    public HolidayTypeService(HolidayTypeRepository holidayTypeRepository) {
        this.holidayTypeRepository = holidayTypeRepository;
    }

    public List<HolidayType> findAll() {
        return holidayTypeRepository.findAll();
    }
}
