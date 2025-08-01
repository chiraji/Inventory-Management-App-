package com.example.inventory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private ArrayList<Product> productList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    public ProductAdapter(ArrayList<Product> productList, OnItemClickListener listener) {
        this.productList = productList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        // Bind data to TextViews
        holder.textViewProductId.setText("ID: " + product.getId());
        holder.textViewProductName.setText("Name: " + product.getName());
        holder.textViewQuantity.setText("Quantity: " + product.getQuantity());
        holder.textViewExpireDate.setText("Expire Date: " + product.getExpireDate());

        // Set click listener if needed
        holder.itemView.setOnClickListener(v -> listener.onItemClick(product));
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView textViewProductId, textViewProductName, textViewQuantity, textViewExpireDate;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewProductId = itemView.findViewById(R.id.textViewProductId);
            textViewProductName = itemView.findViewById(R.id.textViewProductName);
            textViewQuantity = itemView.findViewById(R.id.textViewQuantity);
            textViewExpireDate = itemView.findViewById(R.id.textViewExpireDate);
        }
    }
}
