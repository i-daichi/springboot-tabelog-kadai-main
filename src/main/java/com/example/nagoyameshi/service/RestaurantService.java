package com.example.nagoyameshi.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.hibernate.query.sqm.mutation.internal.temptable.RestrictedDeleteExecutionDelegate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.RestaurantCategory;
import com.example.nagoyameshi.entity.RestaurantHoliday;
import com.example.nagoyameshi.entity.Weekday;
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

	public Restaurant getReferenceById(Integer id){
		return restaurantRepository.getReferenceById(id);
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

		List<RestaurantHoliday> restaurantHolidayList = restaurantEditForm.getHolidays().stream()
				.map(weekday -> new RestaurantHoliday(restaurant.getId(), weekday.getId()))
				.collect(Collectors.toList());

		List<RestaurantCategory> restaurantCategoryList = restaurantEditForm.getCategories().stream()
				.map(category -> new RestaurantCategory(restaurantEditForm.getId(),category.getId()))
				.collect(Collectors.toList());

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
