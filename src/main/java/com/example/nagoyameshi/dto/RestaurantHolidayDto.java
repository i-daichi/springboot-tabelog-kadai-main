package com.example.nagoyameshi.dto;

import com.exmple.nagoyameshi.enumtype.RestaurantHolidayType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestaurantHolidayDto {
    private RestaurantHolidayType type; // Enum型
    private String value; // 具体的な日付や曜日情報
}
