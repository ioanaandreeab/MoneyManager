package com.example.moneymanager;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDAO {
    //definire a tuturor operatiilor asupra tabelei

    //select idUser dupa mail
    @Query("SELECT userId FROM Useri WHERE email = :mail;")
    public int idUserMail(String mail);

    //select idUser dupa parola
    @Query("SELECT userId FROM Useri WHERE email = :pass;")
    public int idUserPass(String pass);

    //get user by id
    @Query("SELECT * FROM useri WHERE userId = :id LIMIT 1;")
    public User findUserById(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertUser(User user);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertUser(User... users);

    //select all users
    @Query("SELECT * from Useri;")
    List<User> getAllUsers();

}
