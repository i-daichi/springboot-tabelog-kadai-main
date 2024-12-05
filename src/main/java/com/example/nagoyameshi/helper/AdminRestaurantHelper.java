package com.example.nagoyameshi.helper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.example.nagoyameshi.entity.HolidayType;
import com.example.nagoyameshi.entity.RestaurantHoliday;
import com.example.nagoyameshi.entity.Weekday;
import com.example.nagoyameshi.form.RestaurantEditForm;
import com.example.nagoyameshi.service.CategoryService;
import com.example.nagoyameshi.service.HolidayTypeService;
import com.example.nagoyameshi.service.RestaurantHolidayService;
import com.example.nagoyameshi.service.WeekdayService;

@Component
public class AdminRestaurantHelper {

    private final CategoryService categoryService;
    private final HolidayTypeService holidayTypeService;
    private final WeekdayService weekdayService;
    private final RestaurantHolidayService restaurantHolidayService;

    public AdminRestaurantHelper(
        CategoryService categoryService,
        HolidayTypeService holidayTypeService,
        WeekdayService weekdayService,
        RestaurantHolidayService restaurantHolidayService
    ) {
        this.categoryService = categoryService;
        this.holidayTypeService = holidayTypeService;
        this.weekdayService = weekdayService;
        this.restaurantHolidayService = restaurantHolidayService;
    }

    public void populateEditFormModel(Integer restaurantId, Model model, RestaurantEditForm restaurantEditForm) {
        // 定休日取得
        List<RestaurantHoliday> restaurantHolidays = restaurantHolidayService.findByRestaurantId(restaurantId);
        restaurantEditForm.setHolidays(restaurantHolidays);

        // マスタデータ取得
        List<HolidayType> holidayTypes = holidayTypeService.findAll();
        List<Weekday> weekdays = weekdayService.findAll();

        // 必要なデータをモデルに追加
        model.addAttribute("restaurantEditForm", restaurantEditForm);
        model.addAttribute("categories", categoryService.getAllCategories()); // カテゴリリスト
        model.addAttribute("holidayTypes", holidayTypes); // 定休日の種別リスト
        model.addAttribute("weekdays", weekdays); // 曜日マスタリスト
        model.addAttribute("restaurantHolidays", restaurantHolidays); // 設定済みの定休日

        // 時間データ
        model.addAttribute("hours",
            IntStream.range(0, 24)
                .mapToObj(i -> String.format("%02d", i))
                .collect(Collectors.toList())
        );
        model.addAttribute("minutes",
            Arrays.asList(0, 15, 30, 45)
                .stream()
                .map(m -> String.format("%02d", m))
                .collect(Collectors.toList())
        );
    }
}
