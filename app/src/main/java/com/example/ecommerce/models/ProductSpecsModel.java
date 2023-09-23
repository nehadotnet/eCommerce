package com.example.ecommerce.models;

import java.util.List;

public class ProductSpecsModel {
    private List<String> colour;
    private List<String> size;

    public ProductSpecsModel() {
    }

    public List<String> getColour() {
        return colour;
    }

    public void setColour(List<String> colour) {
        this.colour = colour;
    }

    public List<String> getSize() {
        return size;
    }

    public void setSize(List<String> size) {
        this.size = size;
    }
}
