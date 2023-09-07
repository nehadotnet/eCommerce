package com.example.ecommerce.contract;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ecommerce.models.ProductsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ProductContractImpl implements ProductContract.Presenter {

    ProductContract.View view;

    public ProductContractImpl(ProductContract.View view) {
        this.view = view;
    }

    @Override
    public void loadProducts(String categoryName) {
        view.showProgress();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(categoryName.toLowerCase())
                .orderBy("productTitle", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            view.hideProgress();
                            ArrayList<ProductsModel> productsModelArrayList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.e("TAG", document.getId() + " => " + document.getData());
                                ProductsModel productsModel = document.toObject(ProductsModel.class);
                                productsModelArrayList.add(productsModel);
                            }
                            view.displayProducts(productsModelArrayList);
                        } else {
                            Log.e("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });


    }
}
