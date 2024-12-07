package com.example.nagoyameshi.dto;

import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.RestaurantHoliday;
import com.example.nagoyameshi.entity.Weekday;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestaurantHolidayDto {
    private Weekday weekday;
    private Integer dayOfMonth; // 例: 毎月15日の場合、"15"

    public RestaurantHolidayDto(RestaurantHoliday holiday){
        this.weekday = holiday.getWeekday();
        this.dayOfMonth = holiday.getDayOfMonth();
    }

    public RestaurantHoliday toRestaurantHoliday(Restaurant restaurant) {
        RestaurantHoliday holiday = new RestaurantHoliday();
        holiday.setRestaurant(restaurant);
        holiday.setWeekday(weekday);
        holiday.setDayOfMonth(this.dayOfMonth);
        return holiday;
    }
}
