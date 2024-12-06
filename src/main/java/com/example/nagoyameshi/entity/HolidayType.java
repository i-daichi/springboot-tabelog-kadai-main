package com.example.nagoyameshi.entity;

import com.exmple.nagoyameshi.enumtype.RestaurantHolidayType;
import com.exmple.nagoyameshi.enumtype.WeekdayType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "holiday_types")
@Data
public class HolidayType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String typeName;

    public HolidayType(RestaurantHolidayType type) {
        this.id = type.getId();
        this.typeName = type.getName();
    }
}
