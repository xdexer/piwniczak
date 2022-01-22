package com.example.piwniczak;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Utils {

    public void changeYearDialog(View view, Product newProduct, TextView productYear_textview, FragmentManager support) {
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
                productYear_textview.setText(String.format("Rok produkcji: %s", String.valueOf(newProduct.getYear())));
            }

            @Override
            public void onNegativeButtonClick(Date date) {
                // Date is get on negative button click
            }
        });
        dateTimeDialogFragment.show(support, "dialog_time");
    }


    public void manip_quantity_func(View view, Product currentProduct, TextView productQuantityNum_textview) {
        if(((Button)view).getText().toString().equals("+")){
            currentProduct.setQuantity(currentProduct.getQuantity() + 1);
        }
        else if(((Button)view).getText().toString().equals("-")){
            currentProduct.setQuantity(currentProduct.getQuantity() - 1);
        }
        productQuantityNum_textview.setText(String.format("Pozostało: %s", String.valueOf(currentProduct.getQuantity())));
    }
}
