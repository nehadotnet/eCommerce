package com.example.ecommerce.adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.R;
import com.example.ecommerce.contract.ProductSpecsContract;

import java.util.List;

public class ProductSpecsAdapter extends RecyclerView.Adapter<ProductSpecsAdapter.ViewHolder> {

    Context context;
    List<String> dataSet;

    public ProductSpecsAdapter(Context context, List<String> dataSet) {
        this.context = context;
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_product_specs_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvProductSpecs.setText(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductSpecs;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvProductSpecs = itemView.findViewById(R.id.tv_product_specs);
        }
    }
}
