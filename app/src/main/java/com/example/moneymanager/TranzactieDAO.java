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

    //select cheltuieli facturi
    @Query("SELECT SUM(valoare) FROM tranzactii where categorie='Facturi' and idUserTranz= :idUserTranz;")
    double selectCheltuieliFacturi(int idUserTranz);

    //select cheltuieli mancare
    @Query("SELECT SUM(valoare) FROM tranzactii where categorie='Mâncare' and idUserTranz= :idUserTranz;")
    double selectCheltuieliMancare(int idUserTranz);

    //select cheltuieli transport
    @Query("SELECT SUM(valoare) FROM tranzactii where categorie='Transport' and idUserTranz= :idUserTranz;")
    double selectCheltuieliTransport(int idUserTranz);

    //select cheltuieli taxe
    @Query("SELECT SUM(valoare) FROM tranzactii where categorie='Taxe' and idUserTranz= :idUserTranz;")
    double selectCheltuieliTaxe(int idUserTranz);

    //select cheltuieli timp liber
    @Query("SELECT SUM(valoare) FROM tranzactii where categorie='Timp liber' and idUserTranz= :idUserTranz;")
    double selectCheltuieliTimpLiber(int idUserTranz);

    //select cheltuieli educatie
    @Query("SELECT SUM(valoare) FROM tranzactii where categorie='Educație' and idUserTranz= :idUserTranz;")
    double selectCheltuieliEducatie(int idUserTranz);


    @Update(onConflict = OnConflictStrategy.IGNORE)
    void updateTranzactie(Tranzactie tranzactie);

    @Delete
    void deleteTranzactie(Tranzactie tranzactie);
}
