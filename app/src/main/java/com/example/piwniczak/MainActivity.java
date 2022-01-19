package com.example.piwniczak;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume(){
        super.onResume();
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onRestart(){
        super.onRestart();
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    ProductAdapter cr;
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void init(){
        setContentView(R.layout.activity_main);
        ProductLab crimesList = ProductLab.get(this);
        cr = new ProductAdapter(crimesList);
        RecyclerView recyclerView = findViewById(R.id.rvProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cr);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        Context ctx = this;
        class GetProducts extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                List<Product> xd = ProductLab.get(getApplicationContext()).getProductDatabase().productDao().getAll();
                int i = 0;
//                for (Product x : xd) {
//                    System.out.println(i + " " + x.getTitle());
//                }
                return null;
            }
        }
        GetProducts gc = new GetProducts();
        gc.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                cr.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public void go_to_add_product(View view) {
        Intent myIntent = new Intent(MainActivity.this, AddProduct.class);
        MainActivity.this.startActivity(myIntent);
    }
}