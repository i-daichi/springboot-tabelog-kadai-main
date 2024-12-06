package com.example.nagoyameshi.dto;

import com.example.nagoyameshi.entity.HolidayType;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.RestaurantHoliday;
import com.example.nagoyameshi.entity.Weekday;
import com.exmple.nagoyameshi.enumtype.RestaurantHolidayType;
import com.exmple.nagoyameshi.enumtype.WeekdayType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestaurantHolidayDto {
    private RestaurantHolidayType holidayType; // Enum型
    private WeekdayType weekdayType; // weekdayのID
    private Integer dayOfMonth; // 例: 毎月15日の場合、"15"

    // フォームからエンティティに変換するためのメソッド
    public RestaurantHoliday toRestaurantHoliday(Restaurant restaurant) {
        RestaurantHoliday holiday = new RestaurantHoliday();
        holiday.setRestaurant(restaurant);
        holiday.setHolidayType(new HolidayType(holidayType));
        holiday.setWeekday(new Weekday(weekdayType));
        holiday.setDayOfMonth(this.dayOfMonth);
        return holiday;
    }
}
