package com.example.piwniczak;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class ProductActivity extends AppCompatActivity {

    EditText productName_edittext;
    EditText productDescription_edittext;
    TextView productQuantityNum_textview;
    ImageView currentImageView;
    ProductLab productList;
    Product currentProduct;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        productList = ProductLab.get(this);
        Intent intent = getIntent();

        UUID ProductID = UUID.fromString(intent.getStringExtra("productID"));
        currentProduct = productList.getProduct(ProductID);

        productName_edittext = findViewById(R.id.product_name_edittext);
        productName_edittext.setText(currentProduct.getTitle());

        productDescription_edittext = findViewById(R.id.product_description_edittext);
        productDescription_edittext.setText(currentProduct.getDescription());

        productQuantityNum_textview = findViewById(R.id.product_quantitynum_edittext);
        productQuantityNum_textview.setText(String.valueOf(currentProduct.getQuantity()));

        currentImageView = findViewById(R.id.image_view_picture);
        Bitmap bmp = BitmapFactory.decodeByteArray(currentProduct.getImage(), 0, currentProduct.getImage().length);
        currentImageView.setImageBitmap(bmp);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void deleteButtonClicked(View view) {
        productList.deleteProduct(currentProduct.getUU());
        //delete record in DB (DELETE)
        class DeleteProduct extends AsyncTask<Void, Void, Void> {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected Void doInBackground(Void... voids) {
                ProductLab.get(getApplicationContext()).getProductDatabase().productDao().delete(currentProduct);
                System.out.println("DELETE CRIME " + currentProduct.getId());
                return null;
            }
        }
        DeleteProduct dc = new DeleteProduct();
        dc.execute();
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onPause() {
        super.onPause();
        currentProduct.setTitle(String.valueOf(((EditText) findViewById(R.id.product_name_edittext)).getText()));
        currentProduct.setDescription(String.valueOf(((EditText) findViewById(R.id.product_description_edittext)).getText()));

        //update record in DB
        class UpdateProduct extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                ProductLab.get(getApplicationContext()).getProductDatabase().productDao().update(currentProduct);
                System.out.println("UPDATE PRODUCT " + currentProduct.getId());

                return null;
            }
        }
        UpdateProduct uc = new UpdateProduct();
        uc.execute();
    }

    ActivityResultLauncher<Intent> launchCamera = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        assert result.getData() != null;
                        Bundle extras = result.getData().getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        currentImageView.setImageBitmap(imageBitmap);
                        currentProduct.setImage(stream.toByteArray());
                        System.out.println("ADDED BITMAP");
                    }
                }
            }
    );

    public void takeAPhoto(View view) {
        Dexter.withContext(this).withPermission(
                Manifest.permission.CAMERA
        ).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                //Toast.makeText(CrimeActivity.this, "READ permission granted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                launchCamera.launch(intent);
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                showRationaleDialog();
            }
        }).onSameThread().check();
    }



    private void showRationaleDialog() {
        new AlertDialog.Builder(this)
                .setMessage("This feature requires permissions")
                .setPositiveButton("Ask me", (dialog, which) -> {
                    try{
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }catch(ActivityNotFoundException e){
                        e.printStackTrace();
                    }
                })
                .setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
