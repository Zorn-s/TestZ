package ru.app.generatorp;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SettingsDao {

    @Query("SELECT * FROM setting WHERE id = :userId ")
    List<Setting> getAllSettings(String userId);

    @Query("SELECT period FROM setting")
    String getPeriod();

    @Query("SELECT age_min FROM setting")
    String getAge_Min();

    @Query("SELECT age_max FROM setting")
    String getAge_Max();

    @Insert
    void insertAll(Setting... settings);

    @Update
    void updateAll(Setting... settings);

}
