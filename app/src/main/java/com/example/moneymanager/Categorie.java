package com.example.moneymanager;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Categorii",
foreignKeys = @ForeignKey(entity = User.class,
parentColumns = "userId", childColumns = "idUserCateg",onDelete = ForeignKey.CASCADE),
indices = {@Index("denumire"),@Index("idUserCateg")})
public class Categorie {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    private boolean aditiva;
    @NonNull
    @ColumnInfo(name = "denumire")
    private String denumire;
    @ColumnInfo(name = "idUserCateg")
    private int idUserCateg;

    public Categorie(boolean aditiva, @NonNull String denumire,int idUserCateg) {
        this.aditiva = aditiva;
        this.denumire = denumire;
        this.idUserCateg = idUserCateg;
    }

    @Ignore
    public Categorie(int id, boolean aditiva, @NonNull String denumire, int idUserCateg) {
        this.id =  id;
        this.aditiva = aditiva;
        this.denumire = denumire;
        this.idUserCateg = idUserCateg;
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

    public int getIdUserCateg() {
        return idUserCateg;
    }

    public void setIdUserCateg(int idUserCateg) {
        this.idUserCateg = idUserCateg;
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
