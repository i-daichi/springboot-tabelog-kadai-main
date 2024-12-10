package com.example.nagoyameshi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.RestaurantCategory;
import com.example.nagoyameshi.form.RestaurantEditForm;
import com.example.nagoyameshi.repository.RestaurantCategoryRepository;
import com.example.nagoyameshi.repository.RestaurantRepository;

@Service
public class RestaurantCategoryService {
    private final RestaurantCategoryRepository restaurantCategoryRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantCategoryService(
        RestaurantCategoryRepository restaurantCategoryRepository,
        RestaurantRepository restaurantRepository) {
        this.restaurantCategoryRepository = restaurantCategoryRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public void deleteByRestaurantId(Integer id){
        Restaurant restaurant = restaurantRepository.getReferenceById(id);
		restaurantCategoryRepository.deleteByRestaurantId(restaurant.getId());
		restaurant.setCategories(new ArrayList<RestaurantCategory>());

        restaurantRepository.save(restaurant);
    }

    public void insert(RestaurantEditForm restaurantEditForm) {
        List<RestaurantCategory> categoryList = restaurantEditForm.getCategoryIdList().stream()
                .map(categoryID -> new RestaurantCategory(restaurantEditForm.getId(), categoryID))
                .collect(Collectors.toList());

        restaurantCategoryRepository.saveAll(categoryList);
    }
}
