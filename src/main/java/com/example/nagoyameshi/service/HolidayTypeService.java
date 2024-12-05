package com.example.nagoyameshi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.nagoyameshi.entity.HolidayType;
import com.example.nagoyameshi.repository.HolidayTypeRepository;

@Service
public class HolidayTypeService {
    private final HolidayTypeRepository holidayTypeRepository;

    @Autowired
    public HolidayTypeService(HolidayTypeRepository holidayTypeRepository) {
        this.holidayTypeRepository = holidayTypeRepository;
    }

    public List<HolidayType> findAll() {
        return holidayTypeRepository.findAll();
    }
}
