package com.example.nagoyameshi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "restaurant_holidays")
@Data
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
