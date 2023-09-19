package com.example.ecommerce.view.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerce.R;
import com.example.ecommerce.utils.Utils;
import com.example.ecommerce.view.fragments.AuthFragment;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        Utils.replaceFragment(getSupportFragmentManager(), R.id.fragment_container, new AuthFragment());
    }
}