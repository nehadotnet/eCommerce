package com.example.ecommerce.view.fragments;


import static android.content.Context.MODE_PRIVATE;
import static com.example.ecommerce.utils.Constants.PREF_FILENAME;
import static com.example.ecommerce.utils.Utils.isValidEmail;
import static com.example.ecommerce.utils.Utils.replaceFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.Fragment;

import com.example.ecommerce.R;
import com.example.ecommerce.contract.AuthContract;
import com.example.ecommerce.contract.AuthContractImpl;
import com.example.ecommerce.models.UserModel;
import com.example.ecommerce.utils.Constants;
import com.example.ecommerce.utils.Utils;
import com.example.ecommerce.view.activities.DashboardActivity;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseUser;


public class LoginFragment extends Fragment implements AuthContract.View {

    private TextInputEditText txtEmail, txtPassword;
    private AppCompatButton btnLogin;
    private AuthContractImpl authContract;
    private AppCompatCheckBox chkRememberMe;
    private CircularProgressIndicator progressBar;
    private TextView tvSignUp;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        txtEmail = view.findViewById(R.id.txt_email);
        txtPassword = view.findViewById(R.id.txt_password);
        btnLogin = view.findViewById(R.id.btn_login);
        chkRememberMe = view.findViewById(R.id.chk_remember_me);
        progressBar = view.findViewById(R.id.progress_bar);
        tvSignUp = view.findViewById(R.id.tv_sign_up);

        authContract = new AuthContractImpl(this);

        setUI();
        // is user has enabled to remember credentials
        sharedPreferences = getContext().getSharedPreferences(Constants.PREF_FILENAME, Context.MODE_PRIVATE);
        boolean isRemember = sharedPreferences.getBoolean(Constants.PREF_REMEMBER_ME, false);
        if (isRemember) {
            String email = sharedPreferences.getString(Constants.PREF_EMAIL, "");
            String password = sharedPreferences.getString(Constants.PREF_PASSWORD, "");
            txtEmail.setText(email);
            txtPassword.setText(password);
        }
    }

    private void setUI() {
        btnLogin.setOnClickListener(v -> doUserLogin());
        tvSignUp.setOnClickListener(v -> replaceFragment(getActivity().getSupportFragmentManager(), R.id.fragment_container, new SignUpFragment()));
    }

    private void doUserLogin() {
        String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();
        if (checkValidation(email, password)) {
            authContract.doLogin(email, password);
        }
    }

    private boolean checkValidation(String email, String password) {

        if (TextUtils.isEmpty(email)) {
            txtEmail.setError(getString(R.string.enter_email));
            txtEmail.requestFocus();
            return false;
        }
        if (!isValidEmail(email)) {
            txtEmail.setError(getString(R.string.enter_valid_email_id));
            txtEmail.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(password)) {
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
    public void onSuccess(UserModel userModel, boolean isUser) {

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
        if (firebaseUser != null) {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREF_FILENAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constants.PREF_EMAIL, firebaseUser.getEmail());
            editor.putString(Constants.PREF_NAME, firebaseUser.getDisplayName());
            editor.putString(Constants.PREF_USER_ID, firebaseUser.getUid());
            editor.putBoolean(Constants.PREF_REMEMBER_ME, chkRememberMe.isChecked());
            if (chkRememberMe.isChecked()) {
                editor.putString(Constants.PREF_PASSWORD, txtPassword.getText().toString());
            }
            editor.apply();
            Utils.showMessage(getContext(), getString(R.string.login_successfully));
            Utils.navigateScreen(getContext(), DashboardActivity.class);
            requireActivity().finishAffinity();
        }
    }
}