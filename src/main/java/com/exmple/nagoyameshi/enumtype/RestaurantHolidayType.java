package com.exmple.nagoyameshi.enumtype;

public enum RestaurantHolidayType {
    DATE_SPECIFIED(1, "日付指定"),
    WEEKLY(2, "毎週"),
    FIRST_WEEK(3, "第1週"),
    SECOND_WEEK(4, "第2週"),
    THIRD_WEEK(5, "第3週");

    private final int id;
    private final String typeName;

    // コンストラクタ
    RestaurantHolidayType(int id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }

    // idを取得
    public int getId() {
        return id;
    }

    // typeNameを取得
    public String getTypeName() {
        return typeName;
    }

    // idからRestaurantHolidayTypeを取得
    public static RestaurantHolidayType fromId(int id) {
        for (RestaurantHolidayType type : RestaurantHolidayType.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid id: " + id);
    }
}
