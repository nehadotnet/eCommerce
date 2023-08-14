package com.example.ecommerce.contract;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ecommerce.models.UserModel;
import com.example.ecommerce.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class AuthContractImpl implements AuthContract.Presenter {
    AuthContract.View view;
    private Context context;
    private String email;
    private String uid;

    public AuthContractImpl(AuthContract.View view) {
        this.view = view;
    }

    @Override
    public void doSignUp(String userName, String email, String password) {
        view.showProgress();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            view.hideProgress();
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            UserModel userModel = new UserModel(firebaseUser.getUid(), userName, email, password);
                            view.onSuccess(userModel, false);

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        view.hideProgress();
                        view.onFailure(e.getMessage());
                    }
                });

    }

    @Override
    public void saveUserDetails(UserModel userModel) {
        view.showProgress();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("users").document().set(userModel)
                .addOnSuccessListener(documentReference -> {
                    view.hideProgress();
                    view.onSuccess(new UserModel(), true);
                })
                .addOnFailureListener(e -> {
                    view.hideProgress();
                    view.onFailure(e.getMessage());
                });
    }

    @Override
    public void doLogin(String email, String password) {
        view.showProgress();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            view.hideProgress();
                            view.onLoginSuccess(firebaseAuth.getCurrentUser());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        view.hideProgress();
                        view.onFailure(e.getMessage());
                    }
                });
    }

}
