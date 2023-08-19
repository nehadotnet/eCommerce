package com.example.ecommerce.models;

public class AccountItemsModel {

    private int titleId, imageId;
    private String itemName;

    public AccountItemsModel(int titleId, int imageId, String itemName) {
        this.titleId = titleId;
        this.imageId = imageId;
        this.itemName = itemName;
    }

    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
