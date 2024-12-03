package com.example.nagoyameshi.form;

import org.springframework.web.multipart.MultipartFile;

import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.valueObject.HourMinute;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestaurantEditForm {

	@NotNull
	private Integer id;

	@NotBlank(message = "店舗名を入力してください。")
	private String name;

	private MultipartFile imageFile;

	@NotBlank(message = "説明を入力してください。")
	private String description;

	@NotNull(message = "価格帯を入力してください。")
	@Min(value = 100, message = "価格帯は100円以上に設定してください。")
	private Integer price;

	@NotBlank(message = "郵便番号を入力してください。")
	private String postalCode;

	@NotBlank(message = "住所を入力してください。")
	private String address;

	@NotBlank(message = "電話番号を入力してください。")
	private String phoneNumber;

	@NotBlank(message = "営業時間を入力してください。")
	private String businessHours;

	@NotNull(message = "開店時間を入力してください。")
	private HourMinute openTime;

	@NotNull(message = "閉店時間を入力してください。")
	private HourMinute closeTime;

	@NotBlank(message = "定休日を入力してください。")
	private String regularHoliday;

	@NotNull(message = "座席数を入力してください。")
	private Integer seats;

	@NotBlank(message = "カテゴリを入力してください。")
	private String category;

	public RestaurantEditForm(Restaurant restaurant){
		this.id = restaurant.getId();
		this.name = restaurant.getName();
		this.description = restaurant.getDescription();
		this.price = restaurant.getPrice();
		this.postalCode = restaurant.getPostalCode();
		this.address = restaurant.getAddress();
		this.phoneNumber = restaurant.getPhoneNumber();
		this.openTime = new HourMinute(restaurant.getOpeningTime());
		this.closeTime = new HourMinute(restaurant.getClosingTime());
		this.regularHoliday = restaurant.getRegularHoliday();
		this.seats = restaurant.getSeats();
		this.category = restaurant.getCategory();
	}
}
