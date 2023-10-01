package com.example.ecommerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerce.OnProductItemClickListner;
import com.example.ecommerce.R;
import com.example.ecommerce.models.ProductsModel;

import java.util.ArrayList;

public class CategoryProductsAdapter extends RecyclerView.Adapter<CategoryProductsAdapter.ViewHolder> {
    Context context;
    ArrayList<ProductsModel> dataSet;
    OnProductItemClickListner onProductItemClickListner;
    ArrayList<ProductsModel> originalData;

    public CategoryProductsAdapter(Context context, ArrayList<ProductsModel> dataSet, OnProductItemClickListner onProductItemClickListner) {
        this.context = context;
        this.dataSet = dataSet;
        this.originalData = new ArrayList<>(dataSet);
        this.onProductItemClickListner = onProductItemClickListner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_products, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryProductsAdapter.ViewHolder holder, int position) {
        holder.tvProductName.setText(dataSet.get(position).getProductTitle());
        holder.tvProductPrice.setText(dataSet.get(position).getPrice());
        holder.btnProductRating.setText(context.getString(R.string.open_bracket) + dataSet.get(position).getRating() + context.getString(R.string.close_bracket));
        holder.tvUserRated.setText(dataSet.get(position).getUserRated());
        holder.tvProductOldPrice.setText(dataSet.get(position).getOldPrice());
        holder.tvProductOffer.setText(dataSet.get(position).getOffer());
        Glide.with(context)
                .load(dataSet.get(position).getProductImageUrl())
                .placeholder(R.drawable.placeholder)
                .into(holder.ivProduct);
        if (dataSet.get(position).isHeartType()) {
            holder.ibWishList.setImageDrawable(context.getDrawable(R.drawable.baseline_favorite));
        } else {
            holder.ibWishList.setImageDrawable(context.getDrawable(R.drawable.baseline_favorite_border));
        }

        holder.tvProductName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProductItemClickListner.onItemClick(holder.getAdapterPosition(), "tv_product_name");
            }
        });

        holder.ibWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProductItemClickListner.onItemClick(holder.getAdapterPosition(), "ib_wish_list");
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void filter(String query) {
        ArrayList<ProductsModel> filteredList = new ArrayList<>();
        if (query.isEmpty()) {
            dataSet.clear();
            dataSet.addAll(originalData);
        } else {
            for (ProductsModel productsModel : dataSet) {
                if (productsModel.getProductTitle().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(productsModel);
                }
            }
            dataSet.clear();
            dataSet.addAll(filteredList);
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvProductPrice, tvUserRated, tvProductOldPrice, tvProductOffer;
        CardView cvProduct;
        ImageView ivProduct;
        AppCompatButton btnProductRating;
        ImageButton ibWishList;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            cvProduct = itemView.findViewById(R.id.card_view_product);
            ivProduct = itemView.findViewById(R.id.iv_product);
            tvProductPrice = itemView.findViewById(R.id.tv_product_price);
            btnProductRating = itemView.findViewById(R.id.btn_product_rating);
            tvUserRated = itemView.findViewById(R.id.tv_product_user_rated);
            tvProductOldPrice = itemView.findViewById(R.id.tv_product_old_price);
            tvProductOffer = itemView.findViewById(R.id.tv_product_price_off);
            ibWishList = itemView.findViewById(R.id.ib_wish_list);
        }
    }
}
