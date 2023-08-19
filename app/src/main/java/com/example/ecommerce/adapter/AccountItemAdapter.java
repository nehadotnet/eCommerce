package com.example.ecommerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.R;
import com.example.ecommerce.listeners.OnItemClickListener;
import com.example.ecommerce.models.AccountItemsModel;

import java.util.ArrayList;

public class AccountItemAdapter extends RecyclerView.Adapter<AccountItemAdapter.ViewHolder> {

    Context context;
    ArrayList<AccountItemsModel> dataSet;
    OnItemClickListener onItemClickListener;

    public AccountItemAdapter(Context context, ArrayList<AccountItemsModel> dataSet, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.dataSet = dataSet;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_account_item, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AccountItemAdapter.ViewHolder holder, int position) {
        holder.tvItemName.setText(dataSet.get(position).getItemName());
        holder.ivItemImage.setImageDrawable(context.getDrawable(dataSet.get(position).getImageId()));

        holder.llRow.setOnClickListener(new View.OnClickListener() {
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName;
        LinearLayout llRow;
        ImageView ivItemImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tv_item_name);
            llRow = itemView.findViewById(R.id.ll_row);
            ivItemImage = itemView.findViewById(R.id.iv_item_image);
        }
    }
}
