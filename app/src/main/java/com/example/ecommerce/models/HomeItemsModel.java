package com.example.ecommerce.models;

public class HomeItemsModel {
    private int categoryId;
    private String categoryTitle, categoryImageUrl;

    public HomeItemsModel() {
    }

    public HomeItemsModel(int categoryId, String categoryTitle, String categoryImageUrl) {
        this.categoryId = categoryId;
        this.categoryTitle = categoryTitle;
        this.categoryImageUrl = categoryImageUrl;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getCategoryImageUrl() {
        return categoryImageUrl;
    }

    public void setCategoryImageUrl(String categoryImageUrl) {
        this.categoryImageUrl = categoryImageUrl;
    }
}
