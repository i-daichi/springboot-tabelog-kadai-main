package com.example.nagoyameshi.dto;

import com.example.nagoyameshi.entity.HolidayType;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.RestaurantHoliday;
import com.example.nagoyameshi.entity.Weekday;
import com.exmple.nagoyameshi.enumtype.RestaurantHolidayType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestaurantHolidayDto {
    private RestaurantHolidayType type; // Enum型
    private Integer weekdayId; // weekdayのID
    private Integer dayOfMonth; // 例: 毎月15日の場合、"15"

    // フォームからエンティティへ変換するためのコンストラクタ
    public RestaurantHoliday toRestaurantHoliday(Restaurant restaurant, HolidayType holidayType, Weekday weekday) {
        RestaurantHoliday holiday = new RestaurantHoliday();
        holiday.setRestaurant(restaurant);
        holiday.setHolidayType(holidayType);
        holiday.setWeekday(weekday);
        holiday.setDayOfMonth(this.dayOfMonth);
        return holiday;
    }
}
