package com.example.ecommerce.contract;

import com.example.ecommerce.models.ProductsModel;

import java.util.ArrayList;

public interface ProductContract {
    interface Presenter {
        void loadProducts(String categoryName);
    }

    interface View {
        void displayProducts(ArrayList<ProductsModel> productsModelArrayList);

        void showProgress();

        void hideProgress();

    }

}
