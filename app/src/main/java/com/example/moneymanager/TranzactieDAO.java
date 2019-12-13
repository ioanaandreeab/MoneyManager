package com.example.moneymanager;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TranzactieDAO {
    //definire a tuturor operatiilor asupra tabelei
    @Insert
    //insert
    public void insertTranzactie(Tranzactie tranzactie);

    //select
    @Query("SELECT * FROM Tranzactii;")
    public List<Tranzactie> selectToateTranzactiile();

    //select dupa id
    @Query("SELECT * FROM Tranzactii WHERE id = :id")
    public Tranzactie cautaTranzactieDupaId(int id);

    //select suma venituri
    @Query("SELECT SUM(valoare) FROM tranzactii where aditiva = 1;")
    public double selectSumaVenituri();
    //select suma cheltuieli
    @Query("SELECT SUM(valoare) FROM tranzactii where aditiva = 0;")
    public double selectSumaCheltuieli();

    //select prima tranzactie
    @Query("SELECT * FROM Tranzactii LIMIT 1")
    public Tranzactie selectPrimaTranzactie();
}
