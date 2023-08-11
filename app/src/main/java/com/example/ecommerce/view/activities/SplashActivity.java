package com.example.ecommerce.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.example.ecommerce.BuildConfig;
import com.example.ecommerce.R;
import com.example.ecommerce.utils.Constants;
import com.example.ecommerce.utils.Utils;

public class SplashActivity extends AppCompatActivity {
    TextView tvVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tvVersion = findViewById(R.id.tv_version);
        tvVersion.setText(getString(R.string.version_prefix) + BuildConfig.VERSION_NAME);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Utils.navigateScreen(SplashActivity.this, AuthActivity.class);
                finishAffinity();
            }
        }, Constants.HANDLER_DELAY);

    }
}