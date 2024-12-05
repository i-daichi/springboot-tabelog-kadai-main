package com.example.nagoyameshi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.nagoyameshi.entity.HolidayType;

@Repository
public interface HolidayTypeRepository extends JpaRepository<HolidayType, Integer> {

}
