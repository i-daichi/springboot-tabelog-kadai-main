package com.example.nagoyameshi.service;

import com.example.nagoyameshi.repository.RestaurantHolidayRepository;

public class RestaurantHolidayService {
    private final RestaurantHolidayRepository restaurantHolidayRepository;

    public RestaurantHolidayService(RestaurantHolidayRepository restaurantHolidayRepository) {
        this.restaurantHolidayRepository = restaurantHolidayRepository;
    }
}
