package com.example.ecommerce.models;

public class CategoryModel {
    private int categoryId;
    private String categoryTitle, categoryImageUrl, categorySpecial;

    public CategoryModel() {
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public String getCategoryImageUrl() {
        return categoryImageUrl;
    }

    public String getCategorySpecial() {
        return categorySpecial;
    }
}
