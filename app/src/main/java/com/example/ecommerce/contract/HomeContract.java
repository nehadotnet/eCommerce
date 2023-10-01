package com.example.ecommerce.contract;

import com.example.ecommerce.models.CategoryModel;

import java.util.ArrayList;

public interface HomeContract {
    interface Presenter {
        void loadItems(String categories);
    }

    interface View {
        void displayItems(ArrayList<CategoryModel> categoryModelArrayList);

        void showProgress();

        void hideProgress();

    }
}
