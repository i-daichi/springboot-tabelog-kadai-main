package com.example.nagoyameshi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.nagoyameshi.entity.RestaurantHoliday;

@Repository
public interface RestaurantHolidayRepository extends JpaRepository<RestaurantHoliday, Integer> {
    List<RestaurantHoliday> findByRestaurantId(Integer restaurantId);

    @Query("SELECT h FROM RestaurantHoliday h WHERE h.dayOfMonth = :date")
    List<RestaurantHoliday> findByHolidayDate(@Param("date") Integer date);
}
