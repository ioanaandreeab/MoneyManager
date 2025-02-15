package com.example.moneymanager;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


@Dao
public interface UserDAO {
    //definire a tuturor operatiilor asupra tabelei

    //select idUser dupa mail
    @Query("SELECT userId FROM Useri WHERE email = :mail;")
    int idUserMail(String mail);

    //select idUser dupa parola
    @Query("SELECT userId FROM Useri WHERE pass = :pass;")
    int idUserPass(String pass);

    //get user by id
    @Query("SELECT * FROM useri WHERE userId = :id LIMIT 1;")
    User findUserById(int id);

    //get user name
    @Query("SELECT prenume FROM useri WHERE userId = :id;")
    String findUserName(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertUser(User user);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);


}
