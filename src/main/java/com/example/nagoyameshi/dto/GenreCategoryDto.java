package com.example.nagoyameshi.dto;

public class GenreCategoryDto {
    private String genreName;
    private String categoryName;

    public GenreCategoryDto(String genreName, String categoryName) {
        this.genreName = genreName;
        this.categoryName = categoryName;
    }

    public String getGenreName() {
        return genreName;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
