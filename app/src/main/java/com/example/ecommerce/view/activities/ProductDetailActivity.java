package com.example.ecommerce.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ecommerce.R;
import com.example.ecommerce.models.ProductsModel;
import com.example.ecommerce.utils.Utils;

public class ProductDetailActivity extends AppCompatActivity {
    ImageView ivProduct, ivBack;
    TextView tvProductName,tvProductPrice, tvUserRated, tvProductOldPrice, tvProductOffer;
    AppCompatButton btnProductRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        ivProduct = findViewById(R.id.iv_product);
        ivBack = findViewById(R.id.iv_back);
        tvProductName = findViewById(R.id.tv_product_name);
        tvProductPrice = findViewById(R.id.tv_product_price);
        tvUserRated = findViewById(R.id.tv_product_user_rated);
        tvProductOldPrice = findViewById(R.id.tv_product_old_price);
        tvProductOffer = findViewById(R.id.tv_product_price_off);
        btnProductRating = findViewById(R.id.btn_product_rating);


        ProductsModel selectedProduct = (ProductsModel) getIntent().getSerializableExtra("selectedProduct");

        if (selectedProduct != null) {
            tvProductName.setText(selectedProduct.getProductTitle());
            tvProductOffer.setText(selectedProduct.getOffer());
            tvUserRated.setText(selectedProduct.getUserRated());
            tvProductPrice.setText(selectedProduct.getPrice());
            tvProductOldPrice.setText(selectedProduct.getOldPrice());
            btnProductRating.setText(selectedProduct.getRating());
            Glide.with(this)
                    .load(selectedProduct.getProductImageUrl())
                    .placeholder(R.drawable.placeholder)
                    .into(ivProduct);

        }

        ivBack.setOnClickListener(v -> Utils.navigateScreen(getApplicationContext(), CategoryProductsActivity.class));

    }
}