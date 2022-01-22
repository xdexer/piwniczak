package com.example.piwniczak;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductLab {
    private static ProductLab sProductLab;

    private List<Product> mProducts;
    private ProductDatabase productDatabase;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ProductLab get(Context context){
        if (sProductLab == null){
            sProductLab = new ProductLab(context);
        }

        return sProductLab;
    }
    
    @RequiresApi(api = Build.VERSION_CODES.O)
    private ProductLab(Context context){
        productDatabase = ProductDatabase.getInstance(context);
        mProducts = productDatabase.productDao().getAll();
    }

    public List<Product> getProducts(){
        return mProducts;
    }

    public ProductDatabase getProductDatabase(){
        return productDatabase;
    }

    public int getProductLabSize(){
        return mProducts.size();
    }

    public List<String> getProductNames(){
        List<String> productNames = new ArrayList<String>();
        for(Product product: mProducts){
            productNames.add(product.getTitle());
        }
        return productNames;
    }

    public Product getProduct(UUID id){
        for(Product product: mProducts){
            if(product.getUU().equals(id)){
                return product;
            }
        }
        return null;
    }

    public Product getProduct(int position){
        if(position <= this.getProductLabSize() && position >= 0)
            return mProducts.get(position);
        return null;
    }

    public void deleteProduct(UUID id){
        mProducts.removeIf(product -> product.getUU() == id);
    }
    public void addProduct(Product x){
        mProducts.add(x);
    }
}
