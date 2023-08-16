package com.example.ecommerce.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.ecommerce.R;
import com.example.ecommerce.utils.Constants;
import com.example.ecommerce.utils.Utils;
import com.example.ecommerce.view.fragments.AccountFragment;
import com.example.ecommerce.view.fragments.CartFragment;
import com.example.ecommerce.view.fragments.CategoryFragment;
import com.example.ecommerce.view.fragments.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    String title = "Home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.bn_view);

        setSupportActionBar(toolbar);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Fragment fragment = null;
                if (id == R.id.nav_home) {
                    fragment = new HomeFragment();
                    title = "Home";
                } else if (id == R.id.nav_account) {
                    fragment = new AccountFragment();
                    title = "Account";
                } else if (id == R.id.nav_cart) {
                    fragment = new CartFragment();
                    title = "Cart";
                } else if (id == R.id.nav_category) {
                    fragment = new CategoryFragment();
                    title = "Categories";
                }
                if (fragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frame_container, fragment);
                    ft.commit();
                    if (title.length() != 0 && getSupportActionBar() != null) {
                        getSupportActionBar().setTitle(title);
                    }
                }
                return true;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }


    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.option_menu, menu);
        if (menu instanceof MenuBuilder) {
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.opt_profile) {
            Utils.showMessage(this, "Profile menu");
        } else if (itemId == R.id.opt_setting) {
            Utils.showMessage(this, "Setting menu");
        } else if (itemId == R.id.opt_logout) {
            userLogout();
        }
        return super.onOptionsItemSelected(item);
    }

    private void userLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.logout))
                .setMessage(getString(R.string.are_you_sure_you_want_to_logout))
                .setPositiveButton(getString(R.string.yes), (dialog, which) -> {
                    SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_FILENAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                    Utils.navigateScreen(DashboardActivity.this, SplashActivity.class);
                    finishAffinity();
                }).setNegativeButton(getString(R.string.no), (dialog, which) -> {
                });
        builder.show();
    }
}