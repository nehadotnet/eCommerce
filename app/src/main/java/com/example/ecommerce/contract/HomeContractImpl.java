package com.example.ecommerce.contract;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ecommerce.models.AccountItemsModel;
import com.example.ecommerce.models.HomeItemsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeContractImpl implements HomeContract.Presenter {

    HomeContract.View view;

    public HomeContractImpl(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void loadItems() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("categories")
                .orderBy("categoryTitle", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<HomeItemsModel> homeItemsModelArrayList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.e("TAG", document.getId() + " => " + document.getData());
                                HomeItemsModel homeItemsModel = document.toObject(HomeItemsModel.class);
                                homeItemsModelArrayList.add(homeItemsModel);
                            }
                            view.displayItems(homeItemsModelArrayList);
                        } else {
                            Log.e("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }
}
