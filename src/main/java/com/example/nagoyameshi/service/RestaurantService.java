package com.example.nagoyameshi.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.hibernate.query.sqm.mutation.internal.temptable.RestrictedDeleteExecutionDelegate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.form.RestaurantEditForm;
import com.example.nagoyameshi.form.RestaurantRegisterForm;
import com.example.nagoyameshi.repository.RestaurantRepository;

@Service
public class RestaurantService {
	private final RestaurantRepository restaurantRepository;

	public RestaurantService(RestaurantRepository restaurantRepository) {
		this.restaurantRepository = restaurantRepository;
	}

	@Transactional
	public void create(RestaurantRegisterForm restaurantRegisterForm) {
		MultipartFile imageFile = restaurantRegisterForm.getImageFile();

		String hashedImageName = "";
		String imageName="";

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
		MultipartFile imageFile = restaurantEditForm.getImageFile();

		String hashedImageName = "";
		String imageName="";

		if (!imageFile.isEmpty()) {
			imageName = imageFile.getOriginalFilename();
			hashedImageName = generateNewFileName(imageName);
			Path filePath = Path.of("src/main/resources/static/storage/" + hashedImageName);
			copyImageFile(imageFile, filePath);
		}

		restaurantRepository.save(new Restaurant(restaurantEditForm,hashedImageName));
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
}
