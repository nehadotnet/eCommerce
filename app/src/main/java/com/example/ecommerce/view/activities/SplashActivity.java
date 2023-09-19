package com.example.ecommerce.view.activities;

import static com.example.ecommerce.utils.Constants.PREF_EMAIL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.ecommerce.BuildConfig;
import com.example.ecommerce.R;
import com.example.ecommerce.utils.Constants;
import com.example.ecommerce.utils.Utils;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView tvVersion = findViewById(R.id.tv_version);
        tvVersion.setText(getString(R.string.version_prefix) + BuildConfig.VERSION_NAME);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_FILENAME, MODE_PRIVATE);
                String userId = sharedPreferences.getString(Constants.PREF_USER_ID, "");
                String email = sharedPreferences.getString(PREF_EMAIL, "");
                if (!TextUtils.isEmpty(userId) && !TextUtils.isEmpty(email)) {
                    Utils.navigateScreen(SplashActivity.this, DashboardActivity.class);
                } else {
                    Utils.navigateScreen(SplashActivity.this, AuthActivity.class);
                }
                finishAffinity();
            }
        }, Constants.HANDLER_DELAY);
    }
}