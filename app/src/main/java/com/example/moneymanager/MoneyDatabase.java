package com.example.moneymanager;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {User.class, Tranzactie.class, Categorie.class}, version = 2, exportSchema = false)
public abstract class MoneyDatabase extends RoomDatabase {
    public abstract TranzactieDAO getTranzactieDAO();
    public abstract  UserDAO getUserDAO();
    public abstract CategorieDAO getCategorieDAO();
}

