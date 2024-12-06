package com.exmple.nagoyameshi.enumtype;

public enum WeekdayType {
    MONDAY(1, "月曜日"),
    TUESDAY(2, "火曜日"),
    WEDNESDAY(3, "水曜日"),
    THURSDAY(4, "木曜日"),
    FRIDAY(5, "金曜日"),
    SATURDAY(6, "土曜日"),
    SUNDAY(7, "日曜日");

    private final int id;
    private final String name;

    WeekdayType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static WeekdayType fromId(int id) {
        for (WeekdayType weekday : WeekdayType.values()) {
            if (weekday.getId() == id) {
                return weekday;
            }
        }
        return null;
    }
}
