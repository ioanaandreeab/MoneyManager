package com.example.moneymanager;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
interface CategorieDAO {
    //select categorii venituri
    @Query("SELECT denumire FROM Categorii WHERE aditiva = 1")
    List<String> selectCategoriiVenituri();

    //select categorii cheltuieli
    @Query("SELECT denumire FROM Categorii WHERE aditiva = 0")
    List<String> selectCategoriiCheltuieli();

    //insert categorie
    @Insert
    long insertCategorie(Categorie categorie);
}
