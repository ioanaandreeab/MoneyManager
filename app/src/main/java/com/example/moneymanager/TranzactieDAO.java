package com.example.moneymanager;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TranzactieDAO {
    //definire a tuturor operatiilor asupra tabelei
    @Insert
    public void insertTranzactie(Tranzactie tranzactie);

    //select dupa id
    @Query("SELECT * FROM Tranzactii WHERE id = :id")
    public Tranzactie cautaTranzactieDupaId(int id);

    //select dupa id-ul userului
    @Query("SELECT * FROM Tranzactii WHERE idUserTranz = :idUserTranz")
    public List<Tranzactie> cautaTranzactiiDupaUserId(int idUserTranz);

    //select suma venituri
    @Query("SELECT SUM(valoare) FROM tranzactii where aditiva = 1 and idUserTranz= :idUserTranz;")
    public double selectSumaVenituri(int idUserTranz);
    //select suma cheltuieli
    @Query("SELECT SUM(valoare) FROM tranzactii where aditiva = 0 and idUserTranz= :idUserTranz;")
    public double selectSumaCheltuieli(int idUserTranz);

    //select prima tranzactie
    @Query("SELECT * FROM Tranzactii LIMIT 1")
    public Tranzactie selectPrimaTranzactie();

    @Update
    public void updateTranzactie(Tranzactie... tranzactii);

    @Delete
    void deleteTranzactie(Tranzactie... tranzactii);


}
