package com.example.nagoyameshi.service;

import java.util.List;

import com.example.nagoyameshi.entity.RestaurantHoliday;
import com.example.nagoyameshi.repository.RestaurantHolidayRepository;

public class RestaurantHolidayService {
    private final RestaurantHolidayRepository restaurantHolidayRepository;

    public RestaurantHolidayService(RestaurantHolidayRepository restaurantHolidayRepository) {
        this.restaurantHolidayRepository = restaurantHolidayRepository;
    }

    public List<RestaurantHoliday> findByRestaurantId(Integer restaurantId) {
        return restaurantHolidayRepository.findByRestaurantId(restaurantId);
    }
}
