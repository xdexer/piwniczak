package com.example.piwniczak;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.UUID;

@Entity
public class Product implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "uuid")
    private UUID UU;

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "description")
    private String mDescription;

    @ColumnInfo(name = "quantity")
    private int mQuantity;

    @ColumnInfo(name = "image")
    private byte[] mImage;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UUID getUU() {
        return UU;
    }

    public void setUU(UUID mId) {
        this.UU = mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public int getQuantity(){
        return mQuantity;
    }

    public void setQuantity(int mQuantity){
        this.mQuantity = mQuantity;
    }

    public byte[] getImage() { return mImage; }

    public void setImage(byte[] image) { mImage = image; }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Product(){
        UU = UUID.randomUUID();
    }

    public Product(String title, String description, int quantity, byte[] image){
        UU = UUID.randomUUID();
        mTitle = title;
        mDescription = description;
        mQuantity = quantity;
        mImage = image;
    }

}
