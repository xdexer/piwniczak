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

//        Create initial data for DB (use only if not already created) (CREATE)
//        class SaveProduct extends AsyncTask<Void, Void, Void> {
//
//            @Override
//            protected Void doInBackground(Void... voids){
//                for (int i = 0; i < 30; i++){
//                    Product product = new Product();
//                    product.setTitle("Product #" + i);
//                    product.setDescription("fsgfdsgdasdweffsf");
//                    product.setQuantity(2137);
//                    product.setImage(new byte[0]);
//                    productDatabase.productDao().insert(product);
//                }
//                return null;
//            }
//        }
//        SaveProduct sc = new SaveProduct();
//        sc.execute();
//
//        //get data from DB (READ) NOT WORKING BECAUSE OF SYNC THREADS
//        class GetProducts extends AsyncTask<Void, Void, Void> {
//            @Override
//            protected Void doInBackground(Void... voids) {
//                mProducts = productDatabase.productDao().getAll();
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid){
//                super.onPostExecute(aVoid);
//            }
//        }
//        GetProducts gc = new GetProducts();
//        gc.execute();
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

}
