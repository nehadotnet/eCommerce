package com.example.ecommerce.contract;

import android.graphics.Bitmap;

import com.example.ecommerce.models.UserModel;

public interface EditProfileContract {
    interface Presenter {
        void getUserDetails(String email);

        void updateUserProfile(String fullName, String phoneNumber, String address);
        void uploadImage(Bitmap imageBitmap);

    }

    interface View {
        void sendUserDetails(UserModel userModel);

        void showUpdateSuccess(String result, boolean isSuccess);

        void showProgress();

        void hideProgress();
        void onImageUploadSuccess(String imageUrl, String message); // Pass the image URL here

        void onImageUploadFailure();
    }

}
