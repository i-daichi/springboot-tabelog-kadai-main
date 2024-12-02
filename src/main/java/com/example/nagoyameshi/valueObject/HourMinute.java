package com.example.nagoyameshi.valueObject;

import java.time.LocalTime;

public class HourMinute {
    private String hour;
    private String minute;

    public HourMinute(String hour, String minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    // LocalTimeへの変換
    public LocalTime toLocalTime() {
        return LocalTime.of(Integer.parseInt(hour), Integer.parseInt(minute));
    }

    @Override
    public String toString() {
        return String.format("%s:%s", hour, minute);
    }
}
