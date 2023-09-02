package com.example.ecommerce.view.activities;

import static com.example.ecommerce.utils.Constants.CAMERA_REQ_CODE;
import static com.example.ecommerce.utils.Constants.GALLERY_REQ_CODE;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.ecommerce.R;
import com.example.ecommerce.contract.EditProfileContract;
import com.example.ecommerce.contract.EditProfileContractImpl;
import com.example.ecommerce.models.UserModel;
import com.example.ecommerce.utils.Constants;
import com.example.ecommerce.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

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
    private Bitmap imageBitmap;

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
        setListeners();
        profileImage.setImageResource(R.drawable.profile);
    }

    private void setListeners() {
        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(EditProfileActivity.this);
                builderSingle.setIcon(R.drawable.baseline_camera);
                builderSingle.setTitle("Select an Option:");

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(EditProfileActivity.this, android.R.layout.select_dialog_item);
                arrayAdapter.add("Gallery");
                arrayAdapter.add("Camera");

                builderSingle.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            // Open gallery
                            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(galleryIntent, GALLERY_REQ_CODE);
                        } else if (which == 1) {
                            // Open camera
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, CAMERA_REQ_CODE);
                        }
                    }
                });
                builderSingle.show();
            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQ_CODE) {
                imageBitmap = (Bitmap) (data.getExtras().get("data"));
                profileImage.setImageBitmap(imageBitmap);
            }
            if (requestCode == GALLERY_REQ_CODE) {
                profileImage.setImageURI(data.getData());
                imageBitmap = getBitmapFromUri(data.getData());
            }
        }
    }

    private Bitmap getBitmapFromUri(Uri data) {
        try {
            return MediaStore.Images.Media.getBitmap(this.getContentResolver(), data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
            String profileImageUrl = userModel.getProfileImageUrl();
            if (profileImageUrl != null && !profileImageUrl.isEmpty()) {
                Glide.with(this)
                        .load(profileImageUrl)
                        .into(profileImage);
            } else {
                // If no profile image is available, you can set a default image
                profileImage.setImageResource(R.drawable.profile);
            }
        }
    }

    @Override
    public void showUpdateSuccess(String result, boolean isSuccess) {
        if (isSuccess) {
            editProfileContract.uploadImage(imageBitmap);
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

    @Override
    public void onImageUploadSuccess(String imageUrl, String message) {
        Utils.showMessage(this, message);
        Utils.navigateScreen(this, DashboardActivity.class);
    }

    @Override
    public void onImageUploadFailure() {

    }
}