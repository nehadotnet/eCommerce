package com.example.ecommerce.contract;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ecommerce.models.ProductSpecsModel;
import com.example.ecommerce.models.ProductsModel;
import com.example.ecommerce.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ProductSpecsContractImpl implements ProductSpecsContract.Presenter{

    ProductSpecsContract.View view;

    public ProductSpecsContractImpl(ProductSpecsContract.View view) {
        this.view = view;
    }

    @Override
    public void getProductSpecs(String documentId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.PRODUCT_SPECS_COL)
                .document(documentId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                              //  ArrayList<String> productSpecs = (ArrayList<String>) document.get("product_specs");
                                ProductSpecsModel productSpecsModel = document.toObject(ProductSpecsModel.class);
                                Log.e("TAGSAT", String.valueOf(document.getData()));
                                view.showProductSpecs(productSpecsModel);
                            } else {
                                Log.d("ProductSpecsContract", "Document does not exist");
                            }
                        } else {
                            Log.e("ProductSpecsContract", "Error getting document", task.getException());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });
    }

}
