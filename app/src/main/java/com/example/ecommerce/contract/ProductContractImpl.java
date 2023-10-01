package com.example.ecommerce.contract;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ecommerce.models.ProductsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    @Override
    public void addProductToWishList(String collection, String productDocumentId, boolean heartType) {
        view.showProgress();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(collection.toLowerCase()).document(productDocumentId)
                .update("heartType", !heartType)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        view.hideProgress();
                        view.onHeartTypeChecked(false);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        view.onHeartTypeChecked(false);
                    }
                });
        //Add to wishlist functionality
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = firebaseUser.getUid();
        ArrayList<String> items = new ArrayList<>();
        items.add(collection + "/" + productDocumentId);
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        map.put("items", items);
        db.collection("wishLists").document(userId)
                .set(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.e("TAG", "onSuccess: ");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", "onFailure: " + e.getMessage());
                    }
                });

    }
}
