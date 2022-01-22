package com.example.piwniczak;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AddProduct extends AppCompatActivity {

    Product newProduct;
    TextView productQuantityNum_textview;
    TextView productYear_textview;
    Utils utils;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        newProduct = new Product();

        productQuantityNum_textview = findViewById(R.id.product_quantitynum_edittext);
        productQuantityNum_textview.setText(String.format("Pozostało: %s", String.valueOf(newProduct.getQuantity())));

        productYear_textview = findViewById(R.id.product_year_textview);
        productYear_textview.setText(String.format("Rok produkcji: %s", String.valueOf(newProduct.getYear())));

        utils = new Utils();
    }

    public void cancel(View view) {
        finish();
    }

    public void manip_quantity(View view) {
        utils.manip_quantity_func(view, newProduct, productQuantityNum_textview);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void add_product(View view) {
        newProduct.setTitle(String.valueOf(((EditText) findViewById(R.id.product_name_edittext)).getText()));
        newProduct.setDescription(String.valueOf(((EditText) findViewById(R.id.product_description_edittext)).getText()));

        ProductLab.get(getApplicationContext()).getProductDatabase().productDao().insert(newProduct);
        ProductLab.get(getApplicationContext()).addProduct(newProduct);
        finish();
    }

    public void changeYear(View view) {
        utils.changeYearDialog(view, newProduct, productYear_textview, getSupportFragmentManager());
    }


}