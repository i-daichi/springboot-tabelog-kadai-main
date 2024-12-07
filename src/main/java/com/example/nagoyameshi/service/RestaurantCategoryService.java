package com.example.nagoyameshi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.nagoyameshi.entity.RestaurantCategory;
import com.example.nagoyameshi.form.RestaurantEditForm;
import com.example.nagoyameshi.repository.RestaurantCategoryRepository;

@Service
public class RestaurantCategoryService {
    private final RestaurantCategoryRepository restaurantCategoryRepository;

    @Autowired
    public RestaurantCategoryService(RestaurantCategoryRepository restaurantCategoryRepository) {
        this.restaurantCategoryRepository = restaurantCategoryRepository;
    }

    public void insert(RestaurantEditForm restaurantEditForm) {
        restaurantCategoryRepository.deleteByRestaurantId(restaurantEditForm.getId());
        List<RestaurantCategory> categoryList = restaurantEditForm.getCategoryIdList().stream()
                .map(categoryID -> new RestaurantCategory(restaurantEditForm.getId(), categoryID))
                .collect(Collectors.toList());

        restaurantCategoryRepository.saveAll(categoryList);
    }
}
