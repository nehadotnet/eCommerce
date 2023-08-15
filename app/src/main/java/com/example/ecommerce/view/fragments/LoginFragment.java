package com.example.ecommerce.view.fragments;


import static android.content.Context.MODE_PRIVATE;
import static com.example.ecommerce.utils.Constants.PREF_FILENAME;
import static com.example.ecommerce.utils.Utils.isValidEmail;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecommerce.R;
import com.example.ecommerce.contract.AuthContract;
import com.example.ecommerce.contract.AuthContractImpl;
import com.example.ecommerce.models.UserModel;
import com.example.ecommerce.utils.Constants;
import com.example.ecommerce.utils.Utils;
import com.example.ecommerce.view.activities.DashboardActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseUser;


public class LoginFragment extends Fragment implements AuthContract.View {

    TextInputEditText txtEmail, txtPassword;
    AppCompatButton btnLogin;
    AuthContractImpl authContract;

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
        authContract = new AuthContractImpl(this);

        setUI();
    }

    private void setUI() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();
                if (checkValidation(email, password)) {
                    authContract.doLogin(email, password);
                }
            }
        });
    }

    private boolean checkValidation(String email, String password) {

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

//    @Override
//    public void onSuccess(UserModel userModel, boolean isUserDetailSaved) {
//        Utils.showMessage(getContext(), "Login Successfully");
//    }

    @Override
    public void onSuccess(UserModel userModel, boolean isUser) {

    }

    @Override
    public void onFailure(String message) {
        Utils.showMessage(getContext(), message);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onLoginSuccess(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREF_FILENAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constants.PREF_EMAIL, firebaseUser.getEmail());
            editor.putString(Constants.PREF_NAME, firebaseUser.getDisplayName());
            editor.putString(Constants.PREF_USER_ID, firebaseUser.getUid());
            editor.apply();
            Utils.showMessage(getContext(), getString(R.string.login_successfully));
            Utils.navigateScreen(getContext(), DashboardActivity.class);
            requireActivity().finishAffinity();
        }
    }
}