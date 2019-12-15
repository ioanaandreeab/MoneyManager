package com.example.moneymanager;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
interface CategorieDAO {
    //select categorii venituri pt utilizatorul curent
    @Query("SELECT denumire FROM Categorii WHERE aditiva = 1 AND idUserCateg = :idUserCateg")
    List<String> selectCategoriiVenituri(int idUserCateg);

    //select categorii cheltuieli pt utilizatorul curent
    @Query("SELECT denumire FROM Categorii WHERE aditiva = 0 AND idUserCateg = :idUserCateg")
    List<String> selectCategoriiCheltuieli(int idUserCateg);

    //insert categorie
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertCategorie(Categorie categorie);
}
