package com.example.ecommerce.view.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.example.ecommerce.R;
import com.example.ecommerce.contract.EditProfileContract;
import com.example.ecommerce.contract.EditProfileContractImpl;
import com.example.ecommerce.models.UserModel;
import com.example.ecommerce.utils.Constants;
import com.example.ecommerce.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity implements EditProfileContract.View {

    private FloatingActionButton fabBtn;
    private TextInputEditText etFullName, etPhone, etEmail, etAddress;
    private AppCompatButton btnSave;
    private CircleImageView profileImage;
    private Toolbar toolbar;
    CircularProgressIndicator progressBar;
    private boolean isEditable = false;
    EditProfileContractImpl editProfileContract;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        initUI();
    }

    private void initUI() {
        fabBtn = findViewById(R.id.fab_btn);
        etFullName = findViewById(R.id.et_full_name);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        etAddress = findViewById(R.id.et_address);
        profileImage = findViewById(R.id.cv_profile_image);
        btnSave = findViewById(R.id.btn_save);
        toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progress_bar);
        etEmail.setEnabled(false);


        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        setUserDetails();
        updateUserProfile();
        profileImage.setImageResource(R.drawable.profile);
        // loadUserDetails();

    }

    private void updateUserProfile() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = etFullName.getText().toString();
                String phoneNumber = etPhone.getText().toString();
                String address = etAddress.getText().toString();
                editProfileContract.updateUserProfile(fullName, phoneNumber, address);

            }
        });
    }

    private void setUserDetails() {
        sharedPreferences = getSharedPreferences(Constants.PREF_FILENAME, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString(Constants.PREF_EMAIL, "");
        editProfileContract = new EditProfileContractImpl(this);
        editProfileContract.getUserDetails(email);
    }

    @Override
    public void sendUserDetails(UserModel userModel) {
        if (userModel != null) {
            etFullName.setText(userModel.getUserName());
            etEmail.setText(userModel.getEmail());
            etAddress.setText(userModel.getAddress());
            etPhone.setText(userModel.getPhoneNumber());
        }
    }

    @Override
    public void showUpdateSuccess(String result, boolean isSuccess) {
        Utils.showMessage(this, result);
        if (isSuccess) {
            Utils.navigateScreen(this, DashboardActivity.class);
        }
    }

    @Override
    public void showProgress() {
        Utils.showLoadingIndicator(progressBar);
    }

    @Override
    public void hideProgress() {
        Utils.hideLoadingIndicator(progressBar);
    }
}