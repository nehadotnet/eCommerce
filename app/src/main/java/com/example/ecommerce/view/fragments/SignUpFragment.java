package com.example.ecommerce.view.fragments;

import static com.example.ecommerce.utils.Utils.isValidEmail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ecommerce.R;
import com.example.ecommerce.models.UserModel;
import com.example.ecommerce.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpFragment extends Fragment {
    TextView txtUserName, txtEmail, txtPassword;
    AppCompatButton btnSignUp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        iniUI(view);
        return view;
    }

    private void iniUI(View view) {
        txtUserName = view.findViewById(R.id.txt_username);
        txtEmail = view.findViewById(R.id.txt_email);
        txtPassword = view.findViewById(R.id.txt_password);
        btnSignUp = view.findViewById(R.id.btn_signup);
        setUI();
    }

    private void setUI() {
        btnSignUp.setOnClickListener(v -> {
            String userName = txtUserName.getText().toString();
            String email = txtEmail.getText().toString();
            String password = txtPassword.getText().toString();
            if (checkValidation(userName, email, password)) {
                doSignUp(userName, email, password);
            }
        });
    }

    private void doSignUp(String userName, String email, String password) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            UserModel userModel = new UserModel(firebaseUser.getUid(), userName, email, password);
                            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                            firestore.collection("users").document().set(userModel)
                                    .addOnSuccessListener(documentReference -> Log.e("TAG", "onSuccess: "))
                                    .addOnFailureListener(e -> Log.e("TAG", "onFailure: " ));

                            Utils.showMessage(getContext(), getString(R.string.user_registered_successfully));
                        }
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
}