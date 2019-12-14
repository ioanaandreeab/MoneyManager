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
    long insertTranzactie(Tranzactie tranzactie);

    //select dupa id-ul userului
    @Query("SELECT * FROM Tranzactii WHERE idUserTranz = :idUserTranz")
    List<Tranzactie> cautaTranzactiiDupaUserId(int idUserTranz);

    //select suma venituri
    @Query("SELECT SUM(valoare) FROM tranzactii where aditiva = 1 and idUserTranz= :idUserTranz;")
    double selectSumaVenituri(int idUserTranz);

    //select suma cheltuieli
    @Query("SELECT SUM(valoare) FROM tranzactii where aditiva = 0 and idUserTranz= :idUserTranz;")
    double selectSumaCheltuieli(int idUserTranz);


    @Update(onConflict = OnConflictStrategy.IGNORE)
    void updateTranzactie(Tranzactie tranzactie);

    @Delete
    void deleteTranzactie(Tranzactie tranzactie);
}
