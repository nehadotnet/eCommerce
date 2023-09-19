package com.example.ecommerce.models;

import java.io.Serializable;
import java.util.List;

public class ProductsModel implements Serializable {
    private int productId;
    private String productTitle, productImageUrl, categoryTitle, rating, userRated, price, oldPrice, offer,description;
    private List<String> productImages;

    public ProductsModel() {
    }

    public ProductsModel(int productId, String productTitle, String productImageUrl, String categoryTitle, String rating, String userRated, String price, String oldPrice, String offer) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.productImageUrl = productImageUrl;
        this.categoryTitle = categoryTitle;
        this.rating = rating;
        this.userRated = userRated;
        this.price = price;
        this.oldPrice = oldPrice;
        this.offer = offer;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getUserRated() {
        return userRated;
    }

    public void setUserRated(String userRated) {
        this.userRated = userRated;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<String> productImages) {
        this.productImages = productImages;
    }
}
