package com.example.ecommerce.contract;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ecommerce.models.AccountItemsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AccountContractImpl implements AccountContract.Presenter {
    AccountContract.View view;

    public AccountContractImpl(AccountContract.View view) {
        this.view = view;
    }


    @Override
    public void loadAccountItems() {
        view.showProgress();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("account_items")
                .orderBy("titleId", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            view.hideProgress();
                            ArrayList<AccountItemsModel> accountItemsModelArrayList=new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.e("TAG", document.getId() + " => " + document.getData());
                                AccountItemsModel accountItemModel = document.toObject(AccountItemsModel.class);
                                accountItemsModelArrayList.add(accountItemModel);
                            }
                            view.showAccountItems(accountItemsModelArrayList);
                        } else {
                            Log.e("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
