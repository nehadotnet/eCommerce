package com.example.ecommerce.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.github.ybq.android.spinkit.SpinKitView;

import java.util.regex.Pattern;

public class Utils {

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    public static void navigateScreen(Context context, @NonNull Class<? extends Activity> activityClass) {
        Intent intent = new Intent(context, activityClass);
        context.startActivity(intent);
    }

    public static void showMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public static void showLoadingIndicator(SpinKitView spinKitView) {
        spinKitView.setVisibility(View.VISIBLE);
    }

    public static void hideLoadingIndicator(SpinKitView spinKitView) {
        spinKitView.setVisibility(View.GONE);
    }
}
