package com.example.ecommerce.view.fragments;

import static com.example.ecommerce.utils.Utils.isValidEmail;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
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

public class SignUpFragment extends Fragment implements AuthContract.View {
    TextView txtUserName, txtEmail, txtPassword;
    AppCompatButton btnSignUp;

    AuthContractImpl authContract;

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
        authContract = new AuthContractImpl(this);
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
            Utils.showMessage(getContext(),getString(R.string.user_registered_successfully));
        }
    }

    @Override
    public void onFailure(String message) {
        Utils.showMessage(getContext(),message);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}