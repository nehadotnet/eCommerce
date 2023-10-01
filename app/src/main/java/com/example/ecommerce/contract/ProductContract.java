package com.example.ecommerce.contract;

import com.example.ecommerce.models.ProductsModel;

import java.util.ArrayList;

public interface ProductContract {
    interface Presenter {
        void loadProducts(String categoryName);

        void addProductToWishList(String collection, String productDocumentId, boolean heartType);
    }

    interface View {
        void displayProducts(ArrayList<ProductsModel> productsModelArrayList);

        void onHeartTypeChecked(Boolean heartType);

        void showProgress();

        void hideProgress();

    }

}
