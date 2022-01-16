package com.example.piwniczak;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> implements Filterable {

    private ProductLab mData;
    private List<Product> productList;
    private List<Product> productListAll;

    public ProductAdapter(ProductLab data){
        this.mData = data;
        this.productListAll = mData.getProducts();
        this.productList = new ArrayList<>();
        productList.addAll(productListAll);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewName.setText(productList.get(position).getTitle());
        holder.textViewQuantity.setText(String.valueOf(productList.get(position).getQuantity()));
    }

    @Override
    public int getItemCount(){
        return productList.size();
    }

    @Override
    public Filter getFilter(){
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Product> filteredProducts = new ArrayList<>();

            if(constraint.toString().isEmpty()){
                filteredProducts.addAll(productListAll);
            }
            else{
                for (Product product: productListAll){
                    if(product.getTitle().toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredProducts.add(product);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredProducts;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            productList.clear();
            productListAll.addAll((Collection<? extends Product>) results.values);
            notifyDataSetChanged();
        }
    };
    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView textViewName;
        private final TextView textViewQuantity;
        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.textViewName = view.findViewById(R.id.productName);
            this.textViewQuantity = view.findViewById(R.id.productQuantity);
        }

        @Override
        public void onClick(View view) {
            Intent newActivity = new Intent(view.getContext(), ProductActivity.class);
            newActivity.putExtra("productID", productList.get(getLayoutPosition()).getUU().toString());
            view.getContext().startActivity(newActivity);
        }
    }
}
