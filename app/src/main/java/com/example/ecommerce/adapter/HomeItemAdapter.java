package com.example.ecommerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerce.R;
import com.example.ecommerce.listeners.OnItemClickListener;
import com.example.ecommerce.models.HomeItemsModel;

import java.util.ArrayList;

public class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.ViewHolder> {
    Context context;
    ArrayList<HomeItemsModel> dataSet;
    OnItemClickListener onItemClickListener;

    public HomeItemAdapter(Context context, ArrayList<HomeItemsModel> dataSet, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.dataSet = dataSet;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_categories, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeItemAdapter.ViewHolder holder, int position) {
        holder.tvCategoryName.setText(dataSet.get(position).getCategoryTitle());
        Glide.with(context)
                .load(dataSet.get(position).getCategoryImageUrl())
                .placeholder(R.drawable.placeholder)
                .into(holder.categoryImage);

        holder.cvCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCategoryName;
        CardView cvCategory;
        ImageView categoryImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryName = itemView.findViewById(R.id.tv_category_name);
            cvCategory = itemView.findViewById(R.id.card_view_category);
            categoryImage = itemView.findViewById(R.id.iv_category);
        }
    }
}
