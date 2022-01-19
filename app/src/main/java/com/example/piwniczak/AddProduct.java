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

    int number_of_products = 0;
    Product newProduct;
    TextView productQuantityNum_textview;
    TextView productYear_textview;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        newProduct = new Product();

        productQuantityNum_textview = findViewById(R.id.product_quantitynum_edittext);
        productQuantityNum_textview.setText("Pozostało: " + String.valueOf(newProduct.getQuantity()));

        productYear_textview = findViewById(R.id.product_year_textview);
        productYear_textview.setText("Rok produkcji: " + String.valueOf(newProduct.getYear()));
    }

    public void cancel(View view) {
        finish();
    }

    public void manip_quantity(View view) {
        if(((Button)view).getText().toString().equals("+")){
            newProduct.setQuantity(newProduct.getQuantity() + 1);
        }
        else if(((Button)view).getText().toString().equals("-")){
            newProduct.setQuantity(newProduct.getQuantity() - 1);
        }
        productQuantityNum_textview.setText("Pozostało: " + String.valueOf(newProduct.getQuantity()));
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
        SwitchDateTimeDialogFragment dateTimeDialogFragment = SwitchDateTimeDialogFragment.newInstance("Select Crime Date",
                "OK",
                "Cancel");

        dateTimeDialogFragment.startAtYearView();
        dateTimeDialogFragment.set24HoursMode(true);
        dateTimeDialogFragment.setMinimumDateTime(new GregorianCalendar(1999, Calendar.JANUARY, 1).getTime());
        dateTimeDialogFragment.setMaximumDateTime(new GregorianCalendar(2099, Calendar.DECEMBER, 31).getTime());
        dateTimeDialogFragment.setDefaultDateTime(new GregorianCalendar().getTime());

        dateTimeDialogFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                // Date is get on positive button click
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                newProduct.setYear(cal.get(Calendar.YEAR));
                productYear_textview.setText("Rok produkcji: " + String.valueOf(newProduct.getYear()));
            }

            @Override
            public void onNegativeButtonClick(Date date) {
                // Date is get on negative button click
            }
        });

        dateTimeDialogFragment.show(getSupportFragmentManager(), "dialog_time");
    }


}