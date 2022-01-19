package com.example.piwniczak;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Product.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ProductDatabase extends RoomDatabase {
    private static ProductDatabase instance;

    public static synchronized ProductDatabase getInstance(Context context) {
        if (instance == null){
            instance = Room.databaseBuilder(context, ProductDatabase.class, "ProductDatabase")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract ProductDao productDao();
}
