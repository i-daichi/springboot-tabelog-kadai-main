package com.exmple.nagoyameshi.enumtype;

public enum RestaurantHolidayType {
    DATE_SPECIFIED(1, "日付指定"),
    EVERY_WEEK(2, "毎週"),
    FIRST_WEEK(3, "第1週"),
    SECOND_WEEK(4, "第2週"),
    THIRD_WEEK(5, "第3週");

    private final int id;
    private final String name;

    RestaurantHolidayType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static RestaurantHolidayType fromId(int id) {
        for (RestaurantHolidayType type : RestaurantHolidayType.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }
}
