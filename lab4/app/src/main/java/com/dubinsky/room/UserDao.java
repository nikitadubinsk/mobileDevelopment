package com.dubinsky.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Query("SELECT * FROM users")
    List<User> readAll();

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM users")
    void clear();


    @Query("SELECT COUNT(id) FROM users")
    int count();

    @Query("SELECT * FROM users WHERE id = :id")
    User readUser(int id);


}
