package com.example.ecommerce.view.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecommerce.R;
import com.example.ecommerce.adapter.CategoryListAdapter;
import com.example.ecommerce.contract.HomeContract;
import com.example.ecommerce.contract.HomeContractImpl;
import com.example.ecommerce.listeners.OnItemClickListener;
import com.example.ecommerce.models.CategoryModel;
import com.example.ecommerce.utils.Constants;
import com.example.ecommerce.utils.Utils;
import com.example.ecommerce.view.activities.CategoryProductsActivity;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.ArrayList;


public class CategoryFragment extends Fragment implements HomeContract.View, OnItemClickListener {
    RecyclerView rvCategoryList;
    CircularProgressIndicator progressBar;
    ArrayList<CategoryModel> categoriesList = new ArrayList<>();
    CategoryListAdapter categoryListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        rvCategoryList = view.findViewById(R.id.rv_category_list);
        progressBar = view.findViewById(R.id.progress_bar);

        HomeContractImpl homeContract = new HomeContractImpl(this);
        homeContract.loadItems(Constants.CATEGORIES_COL);

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void displayItems(ArrayList<CategoryModel> categoryModelArrayList) {
        this.categoriesList.clear();
        if (categoryModelArrayList.size() > 0) {
            this.categoriesList.addAll(categoryModelArrayList);
            if (categoryListAdapter == null) {
                categoryListAdapter = new CategoryListAdapter(getContext(), categoryModelArrayList, this);
                rvCategoryList.setAdapter(categoryListAdapter);
            } else {
                categoryListAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void showProgress() {
        Utils.showLoadingIndicator(progressBar);
    }

    @Override
    public void hideProgress() {
        Utils.hideLoadingIndicator(progressBar);
    }

    @Override
    public void onItemClick(int position) {
        if (position >= 0) {
            String categoryName = categoriesList.get(position).getCategoryTitle();
            Intent intent = new Intent(getContext(), CategoryProductsActivity.class);
            intent.putExtra("categoryName", categoryName);
            startActivity(intent);
        }
    }
}