package com.example.ecommerce.view.fragments;

import static com.example.ecommerce.utils.Utils.isValidEmail;
import static com.example.ecommerce.utils.Utils.replaceFragment;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ecommerce.R;
import com.example.ecommerce.contract.AuthContract;
import com.example.ecommerce.contract.AuthContractImpl;
import com.example.ecommerce.models.UserModel;
import com.example.ecommerce.utils.Utils;
import com.example.ecommerce.view.activities.SplashActivity;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.auth.FirebaseUser;

public class SignUpFragment extends Fragment implements AuthContract.View {
    TextView txtUserName, txtEmail, txtPassword;
    AppCompatButton btnSignUp;

    AuthContractImpl authContract;
    CircularProgressIndicator progressBar;
    TextView tvSignIn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        txtUserName = view.findViewById(R.id.txt_username);
        txtEmail = view.findViewById(R.id.txt_email);
        txtPassword = view.findViewById(R.id.txt_password);
        btnSignUp = view.findViewById(R.id.btn_signup);
        progressBar = view.findViewById(R.id.progress_bar);
        tvSignIn = view.findViewById(R.id.tv_sign_in);

        authContract = new AuthContractImpl(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(getContext()
                    , Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions((Activity) getContext(),
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }
        setUI();
    }

    private void setUI() {
        btnSignUp.setOnClickListener(v -> {
            String userName = txtUserName.getText().toString();
            String email = txtEmail.getText().toString();
            String password = txtPassword.getText().toString();
            if (checkValidation(userName, email, password)) {
                authContract.doSignUp(userName, email, password);
            }
        });
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.fragment_container, new LoginFragment());
            }
        });
    }


    private boolean checkValidation(String userName, String email, String password) {
        if (userName.length() == 0) {
            txtUserName.setError(getString(R.string.enter_username));
            txtUserName.requestFocus();
            return false;
        }
        if (email.length() == 0) {
            txtEmail.setError(getString(R.string.enter_email));
            txtEmail.requestFocus();
            return false;
        }
        if (!isValidEmail(email)) {
            txtEmail.setError(getString(R.string.enter_valid_email_id));
            txtEmail.requestFocus();
            return false;
        }
        if (password.length() == 0) {
            txtPassword.setError(getString(R.string.enter_password));
            txtPassword.requestFocus();
            return false;
        }
        if (password.length() > 8) {
            txtPassword.setError(getString(R.string.password_can_not_exceed_8_characters));
            txtPassword.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void onSuccess(UserModel userModel, boolean isUserDetailSaved) {
        if (!isUserDetailSaved) {
            authContract.saveUserDetails(userModel);
        } else {
            Utils.showMessage(getContext(), getString(R.string.user_registered_successfully));
            clearControls();
            showNotification();
        }
    }

    private void clearControls() {
        txtUserName.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
        replaceFragment(getActivity().getSupportFragmentManager(), R.id.fragment_container, new LoginFragment());
    }

    private void showNotification() {
        String channelID = "CHANNEL_ID_NOTIFICATION";
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getContext(), channelID);
        builder.setSmallIcon(R.drawable.logo)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.thanks_for_registering))
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        Intent intent = new Intent(getContext(), SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(),
                0, intent, PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel =
                    notificationManager.getNotificationChannel(channelID);
            if (notificationChannel == null) {
                notificationChannel = new NotificationChannel(channelID,
                        getString(R.string.your_registration_has_been_successful), NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.setLightColor(Color.GREEN);
                notificationChannel.enableVibration(true);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        notificationManager.notify(0, builder.build());
    }

    @Override
    public void onFailure(String message) {
        Utils.showMessage(getContext(), message);
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
    public void onLoginSuccess(FirebaseUser firebaseUser) {

    }
}