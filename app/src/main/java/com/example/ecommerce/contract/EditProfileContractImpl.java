package com.example.ecommerce.contract;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ecommerce.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class EditProfileContractImpl implements EditProfileContract.Presenter {
    EditProfileContract.View view;
    private Context context;
    private String fullName;
    private String phoneNumber;
    private String address;


    public EditProfileContractImpl(EditProfileContract.View view) {
        this.view = view;
    }

    @Override
    public void getUserDetails(String email) {
        view.showProgress();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            view.hideProgress();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                UserModel userModel = document.toObject(UserModel.class);
                                view.sendUserDetails(userModel);
                                break;
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    @Override
    public void updateUserProfile(String fullName, String phoneNumber, String address) {
        view.showProgress();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = firebaseUser.getUid();
        Log.e("TAG", "updateUserProfile: " + userId);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(userId)
                .update("userName", fullName,
                        "phoneNumber", phoneNumber,
                        "address", address)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        view.hideProgress();
                        view.showUpdateSuccess("Profile Updated Successfully", true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        view.showUpdateSuccess("Something went wrong", false);
                    }
                });
    }
}
