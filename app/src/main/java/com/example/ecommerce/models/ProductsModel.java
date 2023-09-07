package com.example.ecommerce.models;

public class ProductsModel {
    private int productId;
    private String productTitle, productImageUrl, categoryTitle;

    public ProductsModel() {
    }

    public ProductsModel(int productId, String categoryTitle, String productTitle, String productImageUrl) {
        this.productId = productId;
        this.categoryTitle = categoryTitle;
        this.productTitle = productTitle;
        this.productImageUrl = productImageUrl;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }
}
