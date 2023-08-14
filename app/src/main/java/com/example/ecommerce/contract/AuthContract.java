package com.example.ecommerce.contract;

import com.example.ecommerce.models.UserModel;

public interface AuthContract {


    interface Presenter {
        void doSignUp(String userName, String email, String password);
        void saveUserDetails(UserModel userModel);
        void doLogin(String email, String password);
    }

    interface View {
        void onSuccess(UserModel userModel,boolean isUserDetailSaved);

        void onFailure(String message);

        void showProgress();
        void hideProgress();
    }

}
