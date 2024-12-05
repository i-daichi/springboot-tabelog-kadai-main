package com.example.nagoyameshi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

public class RestaurantHoliday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Restaurant restaurant;

    @ManyToOne
    private HolidayType holidayType;

    @ManyToOne(optional = true)
    private Weekday weekday;

    @Column(nullable = true)
    private Integer dayOfMonth; // 例: 毎月15日の場合、"15"

}
