package com.example.moneymanager;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Categorii")
public class Categorie {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int id;
    private boolean aditiva;
    @NonNull
    private String denumire;

    public Categorie(int id, boolean aditiva, @NonNull String denumire) {
        this.id = id;
        this.aditiva = aditiva;
        this.denumire = denumire;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAditiva() {
        return aditiva;
    }

    public void setAditiva(boolean aditiva) {
        this.aditiva = aditiva;
    }

    @NonNull
    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(@NonNull String denumire) {
        this.denumire = denumire;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "id=" + id +
                ", aditiva=" + aditiva +
                ", denumire='" + denumire + '\'' +
                '}';
    }
}
