package com.example.ecommerce.contract;

import com.example.ecommerce.models.ProductSpecsModel;

import java.util.List;

public interface ProductSpecsContract {
    interface Presenter {
        void getProductSpecs(String documentId);
    }

    interface View {
        void showProductSpecs(ProductSpecsModel productSpecsModel);
    }
}
