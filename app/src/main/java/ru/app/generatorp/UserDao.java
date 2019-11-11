package ru.app.generatorp;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAllUsers();

    @Query("SELECT foto_name FROM user WHERE id = :userId")
    String getFoto(String userId);

    @Query("SELECT first_name FROM user WHERE id = :userId")
    String getFirsName(String userId);

    @Query("SELECT last_name FROM user WHERE id = :userId")
    String getLastName(String userId);

    @Query("SELECT email FROM user WHERE id = :userId")
    String getEmail(String userId);

    @Query("SELECT age FROM user WHERE id = :userId")
    String getAge(String userId);

    @Query("SELECT work FROM user WHERE id = :userId")
    String getWork(String userId);

    @Query("SELECT gender FROM user WHERE id = :userId")
    String getGender(String userId);

    @Query("SELECT area FROM user WHERE id = :userId")
    String getArea(String userId);

    @Query("SELECT cars FROM user WHERE id = :userId")
    String getCars(String userId);

    @Insert
    void insertAll(User... users);

    @Update
    void updateAll(User... users);


}
