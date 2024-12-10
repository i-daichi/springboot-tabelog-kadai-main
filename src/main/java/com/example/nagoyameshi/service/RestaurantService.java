package com.example.nagoyameshi.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.nagoyameshi.dto.RestaurantDTO;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.RestaurantCategory;
import com.example.nagoyameshi.entity.RestaurantHoliday;
import com.example.nagoyameshi.form.RestaurantEditForm;
import com.example.nagoyameshi.form.RestaurantRegisterForm;
import com.example.nagoyameshi.repository.RestaurantCategoryRepository;
import com.example.nagoyameshi.repository.RestaurantHolidayRepository;
import com.example.nagoyameshi.repository.RestaurantRepository;

@Service
public class RestaurantService {
	private final RestaurantRepository restaurantRepository;
	private final RestaurantCategoryRepository restaurantCategoryRepository;
	private final RestaurantHolidayRepository restaurantHolidayRepository;

	public RestaurantService(RestaurantRepository restaurantRepository,
			RestaurantCategoryRepository restaurantCategoryRepository,
			RestaurantHolidayRepository restaurantHolidayRepository) {
		this.restaurantRepository = restaurantRepository;
		this.restaurantCategoryRepository = restaurantCategoryRepository;
		this.restaurantHolidayRepository = restaurantHolidayRepository;
	}

	public Restaurant getReferenceById(Integer id) {
		return restaurantRepository.getReferenceById(id);
	}

	public Page<Restaurant> getRestaurants(String keyword, String category, Integer price, String order, Pageable pageable) {
		if (keyword != null && !keyword.isEmpty()) {
			return findByKeyword(keyword, order, pageable);
		} else if (category != null && !category.isEmpty()) {
			return findByCategory(category, order, pageable);
		} else if (price != null) {
			return findByPrice(price, order, pageable);
		} else {
			return findAll(order, pageable);
		}
	}

	private Page<Restaurant> findByKeyword(String keyword, String order, Pageable pageable) {
		if ("priceAsc".equals(order)) {
			return restaurantRepository.findByNameLikeOrAddressLikeOrderByPriceAsc("%" + keyword + "%", "%" + keyword + "%", pageable);
		} else {
			return restaurantRepository.findByNameLikeOrAddressLikeOrderByCreatedAtDesc("%" + keyword + "%", "%" + keyword + "%", pageable);
		}
	}

	private Page<Restaurant> findByCategory(String category, String order, Pageable pageable) {
		if ("priceAsc".equals(order)) {
			return restaurantRepository.findByCategoryLikeOrderByPriceAsc("%" + category + "%", pageable);
		} else {
			return restaurantRepository.findByCategoryLikeOrderByCreatedAtDesc("%" + category + "%", pageable);
		}
	}

	private Page<Restaurant> findByPrice(Integer price, String order, Pageable pageable) {
		if ("priceAsc".equals(order)) {
			return restaurantRepository.findByPriceLessThanEqualOrderByPriceAsc(price, pageable);
		} else {
			return restaurantRepository.findByPriceLessThanEqualOrderByCreatedAtDesc(price, pageable);
		}
	}

	private Page<Restaurant> findAll(String order, Pageable pageable) {
		if ("priceAsc".equals(order)) {
			return restaurantRepository.findAllByOrderByPriceAsc(pageable);
		} else {
			return restaurantRepository.findAllByOrderByCreatedAtDesc(pageable);
		}
	}

	public Page<RestaurantDTO> getRestaurants(Pageable pageable, String keyword) {
		Page<Restaurant> pages;
		if (keyword != null && !keyword.isEmpty()) {
			pages = restaurantRepository.findByNameLike("%" + keyword + "%", pageable);
		} else {
			pages = restaurantRepository.findAll(pageable);
		}

		return pages.map(restaurant -> new RestaurantDTO(restaurant));
	}

	@Transactional
	public void create(RestaurantRegisterForm restaurantRegisterForm) {
		MultipartFile imageFile = restaurantRegisterForm.getImageFile();

		String hashedImageName = "";
		String imageName = "";

		if (!imageFile.isEmpty()) {
			imageName = imageFile.getOriginalFilename();
			hashedImageName = generateNewFileName(imageName);
			Path filePath = Path.of("src/main/resources/static/storage/" + hashedImageName);
			copyImageFile(imageFile, filePath);
		}

		restaurantRepository.save(new Restaurant(restaurantRegisterForm));
	}

	@Transactional
	public void update(RestaurantEditForm restaurantEditForm) {
		Restaurant restaurant = restaurantRepository.getReferenceById(restaurantEditForm.getId());
		updateRestaurantFromForm(restaurant, restaurantEditForm);

		System.out.println("Holiday ID List: " + restaurantEditForm.getHolidayIdList());
		System.out.println("Category ID List: " + restaurantEditForm.getCategoryIdList());
		System.out.println("RestaurantID:" + restaurant.getId());

		List<RestaurantHoliday> restaurantHolidayList = restaurantEditForm.getHolidays().stream()
				.map(weekday -> new RestaurantHoliday(restaurant.getId(), restaurant, weekday.getId(), weekday))
				.collect(Collectors.toList());
		List<RestaurantCategory> restaurantCategoryList = restaurantEditForm.getCategories().stream()
				.map(category -> new RestaurantCategory(restaurant.getId(), restaurant, category.getId(), category))
				.collect(Collectors.toList());

		for (RestaurantHoliday holiday : restaurantHolidayList) {
			System.out.println("Holiday ID : " + holiday.getRestaurant_id());
			System.out.println("R ID :" + holiday.getRestaurant_id());
		}

		restaurant.setHolidays(restaurantHolidayList);
		restaurant.setCategories(restaurantCategoryList);

		restaurantRepository.save(restaurant);
	}

	// UUIDを使って生成したファイル名を返す
	public String generateNewFileName(String fileName) {
		String[] fileNames = fileName.split("\\.");
		for (int i = 0; i < fileNames.length - 1; i++) {
			fileNames[i] = UUID.randomUUID().toString();
		}
		String hashedFileName = String.join(".", fileNames);
		return hashedFileName;
	}

	// 画像ファイルを指定したファイルにコピーする
	public void copyImageFile(MultipartFile imageFile, Path filePath) {
		try {
			Files.copy(imageFile.getInputStream(), filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// レストランの情報をUpdateする
	public void updateRestaurantFromForm(Restaurant restaurant, RestaurantEditForm form) {
		// フォームのフィールドをレストランエンティティにセット
		restaurant.setName(form.getName());
		restaurant.setDescription(form.getDescription());
		restaurant.setPrice(form.getPrice());
		restaurant.setPostalCode(form.getPostalCode());
		restaurant.setAddress(form.getAddress());
		restaurant.setPhoneNumber(form.getPhoneNumber());
		restaurant.setSeats(form.getSeats());
		restaurant.setOpeningTime(form.getOpenTime().toLocalTime()); // HourMinute -> LocalTime変換
		restaurant.setClosingTime(form.getCloseTime().toLocalTime()); // HourMinute -> LocalTime変換
		restaurant.setImageName(getImageFile(form));
	}

	private String getImageFile(RestaurantEditForm restaurantEditForm) {
		String hashedImageName = "";
		String imageName = "";
		MultipartFile imageFile = restaurantEditForm.getImageFile();

		if (!imageFile.isEmpty()) {
			imageName = imageFile.getOriginalFilename();
			hashedImageName = generateNewFileName(imageName);
			Path filePath = Path.of("src/main/resources/static/storage/" + hashedImageName);
			copyImageFile(imageFile, filePath);
		}

		return hashedImageName;
	}
}
