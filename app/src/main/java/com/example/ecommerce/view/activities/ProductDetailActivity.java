package com.example.ecommerce.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.ecommerce.R;
import com.example.ecommerce.models.ProductsModel;
import com.example.ecommerce.utils.Utils;

import java.util.ArrayList;

public class ProductDetailActivity extends AppCompatActivity {
    ImageView ivBack,ivApproved;
    TextView tvProductName,tvProductPrice, tvUserRated, tvProductOldPrice, tvProductOffer,tvDescription,tvLocation;
    AppCompatButton btnProductRating;
    ImageSlider imageSlider;

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


        ProductsModel selectedProduct = (ProductsModel) getIntent().getSerializableExtra("selectedProduct");

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
        }

        ivBack.setOnClickListener(v -> Utils.navigateScreen(ProductDetailActivity.this, CategoryProductsActivity.class));

    }

}