package com.example.ecommerce.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ecommerce.R;
import com.example.ecommerce.adapter.CategoryProductsAdapter;
import com.example.ecommerce.adapter.HomeItemAdapter;
import com.example.ecommerce.contract.EditProfileContractImpl;
import com.example.ecommerce.contract.HomeContractImpl;
import com.example.ecommerce.contract.ProductContract;
import com.example.ecommerce.contract.ProductContractImpl;
import com.example.ecommerce.listeners.OnItemClickListener;
import com.example.ecommerce.models.HomeItemsModel;
import com.example.ecommerce.models.ProductsModel;
import com.example.ecommerce.utils.Constants;
import com.example.ecommerce.utils.Utils;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.ArrayList;

public class CategoryProductsActivity extends AppCompatActivity implements ProductContract.View, OnItemClickListener {

    TextView tvCategoryName;
    SearchView searchView;
    CircularProgressIndicator progressbar;
    RecyclerView rvProducts;
    ImageView ivBackArrow;
    ArrayList<ProductsModel> productsModelArrayList = new ArrayList<>();
    CategoryProductsAdapter categoryProductsAdapter;
    SharedPreferences sharedPreferences;
    ProductContractImpl productContract;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_products);

        tvCategoryName = findViewById(R.id.tv_category_name);
        searchView = findViewById(R.id.searchView);
        progressbar = findViewById(R.id.progress_bar);
        rvProducts = findViewById(R.id.rv_products);
        ivBackArrow = findViewById(R.id.iv_back_arrow);

        String categoryName = getIntent().getStringExtra("categoryName");
        tvCategoryName.setText(categoryName);

        ProductContractImpl productContract = new ProductContractImpl(this);
        productContract.loadProducts(categoryName);

        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.navigateScreen(CategoryProductsActivity.this, DashboardActivity.class);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (categoryProductsAdapter != null) {
                    categoryProductsAdapter.filter(newText);
                }
                return true;
            }
        });

    }

    @Override
    public void displayProducts(ArrayList<ProductsModel> productsModelArrayList) {
        this.productsModelArrayList.clear();
        if (productsModelArrayList != null) {
            this.productsModelArrayList.addAll(productsModelArrayList);
        }
        if (categoryProductsAdapter == null) {
            categoryProductsAdapter = new CategoryProductsAdapter(this, productsModelArrayList, this);
            rvProducts.setAdapter(categoryProductsAdapter);
        } else {
            categoryProductsAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void showProgress() {
        Utils.showLoadingIndicator(progressbar);
    }

    @Override
    public void hideProgress() {
        Utils.hideLoadingIndicator(progressbar);
    }

    @Override
    public void onItemClick(int position) {

    }
}