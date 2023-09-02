package com.example.ecommerce.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.ecommerce.R;
import com.example.ecommerce.adapter.AccountItemAdapter;
import com.example.ecommerce.adapter.HomeItemAdapter;
import com.example.ecommerce.contract.HomeContract;
import com.example.ecommerce.contract.HomeContractImpl;
import com.example.ecommerce.listeners.OnItemClickListener;
import com.example.ecommerce.models.HomeItemsModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements HomeContract.View, OnItemClickListener {
    ImageSlider imageSlider;
    RecyclerView rvCategories;
    ArrayList<HomeItemsModel> homeItemsModelArrayList = new ArrayList<>();
    HomeItemAdapter homeItemAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        initUI(view);
        return view;


    }

    private void initUI(View view) {
        imageSlider = view.findViewById(R.id.image_slider);
        rvCategories = view.findViewById(R.id.rv_categories);

        setImageSlider();
        HomeContractImpl homeContract = new HomeContractImpl(this);
        homeContract.loadItems();

    }

    private void setImageSlider() {
        ArrayList<SlideModel> imageList = new ArrayList<>(); // Create image list
        imageList.add(new SlideModel(R.drawable.splash_image, ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://bit.ly/2BteuF2", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://bit.ly/3fLJf72", ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(imageList);
    }

    @Override
    public void displayItems(ArrayList<HomeItemsModel> homeItemsModelArrayList) {
        if (homeItemsModelArrayList.size() > 0 && homeItemsModelArrayList != null) {
            homeItemAdapter = new HomeItemAdapter(getContext(), homeItemsModelArrayList, this);
            rvCategories.setAdapter(homeItemAdapter);
        }
    }

    @Override
    public void onItemClick(int position) {

    }
}