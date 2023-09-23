package com.example.ecommerce.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.ecommerce.R;
import com.example.ecommerce.adapter.ProductSpecsAdapter;
import com.example.ecommerce.contract.ProductContractImpl;
import com.example.ecommerce.contract.ProductSpecsContract;
import com.example.ecommerce.contract.ProductSpecsContractImpl;
import com.example.ecommerce.models.ProductSpecsModel;
import com.example.ecommerce.models.ProductsModel;
import com.example.ecommerce.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity implements ProductSpecsContract.View {
    ImageView ivBack, ivApproved;
    TextView tvProductName, tvProductPrice, tvUserRated, tvProductOldPrice, tvProductOffer, tvDescription, tvLocation;
    AppCompatButton btnProductRating;
    ImageSlider imageSlider;
    RecyclerView rvProductSpecs;
    private int selectedProdSpecsIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        imageSlider = findViewById(R.id.image_slider);
        ivBack = findViewById(R.id.iv_back);
        tvProductName = findViewById(R.id.tv_product_name);
        tvProductPrice = findViewById(R.id.tv_product_price);
        tvUserRated = findViewById(R.id.tv_product_user_rated);
        tvProductOldPrice = findViewById(R.id.tv_product_old_price);
        tvProductOffer = findViewById(R.id.tv_product_price_off);
        btnProductRating = findViewById(R.id.btn_product_rating);
        tvDescription = findViewById(R.id.tv_description);
        tvLocation = findViewById(R.id.tv_location);
        ivApproved = findViewById(R.id.iv_approved);
        rvProductSpecs = findViewById(R.id.rv_product_specs);


        ProductsModel selectedProduct = (ProductsModel) getIntent().getSerializableExtra("selectedProduct");
        Log.e("TAG", String.valueOf(selectedProduct));

        if (selectedProduct != null) {
            tvProductName.setText(selectedProduct.getProductTitle());
            tvProductOffer.setText(selectedProduct.getOffer());
            tvUserRated.setText(selectedProduct.getUserRated());
            tvProductPrice.setText(selectedProduct.getPrice());
            tvProductOldPrice.setText(selectedProduct.getOldPrice());
            btnProductRating.setText(selectedProduct.getRating());
            tvDescription.setText(selectedProduct.getDescription());
            tvLocation.setText(selectedProduct.getLocation());

            ArrayList<SlideModel> imageList = new ArrayList<>();
            imageList.add(new SlideModel(selectedProduct.getProductImageUrl(), ScaleTypes.FIT));
            if (selectedProduct.getProductImages() != null) {
                for (String imageUrl : selectedProduct.getProductImages()) {
                    imageList.add(new SlideModel(imageUrl, ScaleTypes.FIT));
                }
            }

            imageSlider.setImageList(imageList);

            if (selectedProduct.getSpecs() != null) {
                String documentId = selectedProduct.getSpecs().get(0);
                selectedProdSpecsIndex = Integer.parseInt(selectedProduct.getSpecs().get(1));
                ProductSpecsContractImpl productSpecsContract = new ProductSpecsContractImpl(this);
                productSpecsContract.getProductSpecs(documentId);
            }

        }
        ivBack.setOnClickListener(v -> Utils.navigateScreen(ProductDetailActivity.this, CategoryProductsActivity.class));
    }

    @Override
    public void showProductSpecs(ProductSpecsModel productSpecsModel) {
        List<String> types = null;
        switch (selectedProdSpecsIndex) {
            case 0:
                types = productSpecsModel.getColour();
                break;
            case 1:
                types = productSpecsModel.getSize();
                break;
        }
        if (types.size() > 0) {
            ProductSpecsAdapter productSpecsAdapter = new ProductSpecsAdapter(this, types);
            rvProductSpecs.setAdapter(productSpecsAdapter);
            rvProductSpecs.setVisibility(View.VISIBLE);
        } else {
            rvProductSpecs.setVisibility(View.GONE);
        }
    }
}