package com.example.ecommerce.contract;

import com.example.ecommerce.models.AccountItemsModel;

import java.util.ArrayList;

public interface AccountContract {
    interface Presenter {
        void loadAccountItems();
    }

    interface View {
        void showAccountItems(ArrayList<AccountItemsModel> accountItemsModelArrayList);
        void showProgress();

        void hideProgress();
    }
}
