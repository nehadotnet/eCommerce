package com.example.ecommerce.contract;

import com.example.ecommerce.models.HomeItemsModel;

import java.util.ArrayList;

public interface HomeContract {
    interface Presenter{
        void loadItems();
    }
    interface View{
        void displayItems(ArrayList<HomeItemsModel> homeItemsModelArrayList);

    }
}
