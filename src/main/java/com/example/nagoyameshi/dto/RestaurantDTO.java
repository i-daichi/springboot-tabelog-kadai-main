package com.example.nagoyameshi.dto;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.RestaurantCategory;
import com.example.nagoyameshi.entity.RestaurantHoliday;
import com.example.nagoyameshi.valueObject.HourMinute;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

public class RestaurantDTO {
    private Integer id;
    private String name;
    private String imageName;
    private String description;
    private Integer price;
    private Integer seats;
    private String postalCode;
    private String address;
    private String phoneNumber;
    private String category;
    private String regularHoliday;
    private String businessHours;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private HourMinute openingTime;
    private HourMinute closingTime;
    private List<Category> categories = new ArrayList<>();;
    private List<RestaurantHoliday> holidays = new ArrayList<>();

    public RestaurantDTO(Integer id, String name, String imageName, String description, Integer price, Integer seats,
            String postalCode, String address, String phoneNumber, String category, String regularHoliday,
            String businessHours, Timestamp createdAt, Timestamp updatedAt, LocalTime openingTime,
            LocalTime closingTime) {
        this.id = id;
        this.name = name;
        this.imageName = imageName;
        this.description = description;
        this.price = price;
        this.seats = seats;
        this.postalCode = postalCode;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.category = category;
        this.regularHoliday = regularHoliday;
        this.businessHours = businessHours;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.openingTime = new HourMinute(openingTime);
        this.closingTime = new HourMinute(closingTime);
    }

    public RestaurantDTO(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.imageName = restaurant.getImageName();
        this.description = restaurant.getDescription();
        this.price = restaurant.getPrice();
        this.seats = restaurant.getSeats();
        this.postalCode = restaurant.getPostalCode();
        this.address = restaurant.getAddress();
        this.phoneNumber = restaurant.getPhoneNumber();
        this.category = restaurant.getCategory();
        this.regularHoliday = restaurant.getRegularHoliday();
        this.businessHours = restaurant.getBusinessHours();
        this.createdAt = restaurant.getCreatedAt();
        this.updatedAt = restaurant.getUpdatedAt();
        this.openingTime = new HourMinute(restaurant.getOpeningTime());
        this.closingTime = new HourMinute(restaurant.getClosingTime());
        this.categories = restaurant.getCategories();
        this.holidays = restaurant.getHolidays();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRegularHoliday() {
        return regularHoliday;
    }

    public void setRegularHoliday(String regularHoliday) {
        this.regularHoliday = regularHoliday;
    }

    public String getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(String businessHours) {
        this.businessHours = businessHours;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public HourMinute getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(HourMinute openingTime) {
        this.openingTime = openingTime;
    }

    public HourMinute getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(HourMinute closingTime) {
        this.closingTime = closingTime;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setHolidays(List<RestaurantHoliday> holidays){
        this.holidays = holidays;
    }

    public List<RestaurantHoliday> getHolidays(){
        return holidays;
    }

    // メソッド
    public String getBusinessTime() {
        return openingTime.toString() + "~" + closingTime.toString();
    }

    public static String categoriesToString(List<Category> categories) {
        return categories.stream()
                .map(Category::getName)  // CategoryエンティティにgetName()がある前提
                .collect(Collectors.joining("、"));
    }

    public static String holidaysToString(List<RestaurantHoliday> holidays) {
        return holidays.stream()
                .map(h -> h.getWeekday().getName())  // WeekdayエンティティにgetName()がある前提
                .collect(Collectors.joining("、"));
    }
}
