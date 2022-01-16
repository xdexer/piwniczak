package com.example.piwniczak;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM product")
    List<Product> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Product crime);

    @Delete
    void delete(Product crime);

    @Update
    void update(Product crime);
}
