package com.bkacad.nnt.demoandroidroomd04;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

// Anotation
@Dao
public interface ContactDAO {
    // Lấy toàn bộ
    @Query("SELECT * FROM contacts")
    public List<Contact> getAll();

    // Thêm bản ghi
    @Insert
    public void insertAll(Contact... contacts);

    // Cập nhật bản ghi
    @Update
    public void updateAll(Contact... contacts);

    // Xoá bản ghi
    @Delete
    public void deleteAll(Contact... contacts);
}
