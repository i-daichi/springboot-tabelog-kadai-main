package com.example.nagoyameshi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.nagoyameshi.entity.RestaurantHoliday;
import com.example.nagoyameshi.repository.RestaurantHolidayRepository;

@Service
public class RestaurantHolidayService {
    private final RestaurantHolidayRepository restaurantHolidayRepository;

    @Autowired
    public RestaurantHolidayService(RestaurantHolidayRepository restaurantHolidayRepository) {
        this.restaurantHolidayRepository = restaurantHolidayRepository;
    }

    public List<RestaurantHoliday> findByRestaurantId(Integer restaurantId) {
        return restaurantHolidayRepository.findByRestaurantId(restaurantId);
    }

    public void update(List<Integer> holidayIdList) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
}
