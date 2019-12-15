package com.example.moneymanager;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.PrimaryKey;

@Entity(tableName = "Tranzactii",
foreignKeys = @ForeignKey(entity = User.class,
parentColumns = "userId",
childColumns = "idUserTranz",
onDelete = ForeignKey.CASCADE), indices =
        {@Index("valoare"), @Index("idUserTranz")})
public class Tranzactie implements Parcelable {
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @NonNull
    @ColumnInfo(name = "valoare")
    private double valoare;
    @ColumnInfo(name = "data")
    private String data;
    @ColumnInfo(name = "natura")
    private String natura;
    @ColumnInfo(name = "categorie")
    private String categorie;
    @ColumnInfo(name = "aditiva")
    private boolean esteAditiva;

    @ColumnInfo(name = "idUserTranz")
    private int idUserTranz;


    public Tranzactie(@NonNull double valoare, String data, String natura, String categorie, boolean esteAditiva, int idUserTranz) {
        this.valoare = valoare;
        this.data = data;
        this.natura = natura;
        this.categorie = categorie;
        this.esteAditiva = esteAditiva;
        this.idUserTranz = idUserTranz;
    }

    protected Tranzactie(Parcel in) {
        id = in.readInt();
        valoare = in.readDouble();
        data = in.readString();
        natura = in.readString();
        categorie = in.readString();
        esteAditiva = in.readByte() != 0;
        idUserTranz = in.readInt();
    }

    public static final Creator<Tranzactie> CREATOR = new Creator<Tranzactie>() {
        @Override
        public Tranzactie createFromParcel(Parcel in) {
            return new Tranzactie(in);
        }

        @Override
        public Tranzactie[] newArray(int size) {
            return new Tranzactie[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @NonNull
    public double getValoare() {
        return valoare;
    }

    public void setValoare(@NonNull double valoare) {
        this.valoare = valoare;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNatura() {
        return natura;
    }

    public void setNatura(String natura) {
        this.natura = natura;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public boolean isEsteAditiva() {
        return esteAditiva;
    }

    public void setEsteAditiva(boolean esteAditiva) {
        this.esteAditiva = esteAditiva;
    }

    @Override
    public String toString() {
        return "valoare = " + valoare +
                ", data = '" + data + '\'' +
                ", natura = '" + natura + '\'' +
                ", categorie = '" + categorie + '\'';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q) //pentru writeBoolean
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeDouble(valoare);
        parcel.writeString(data);
        parcel.writeString(natura);
        parcel.writeString(categorie);
        parcel.writeBoolean(esteAditiva);
        parcel.writeInt(idUserTranz);
    }

    public Integer getIdUserTranz() {
        return idUserTranz;
    }

    public void setIdUserTranz(Integer idUserTranz) {
        this.idUserTranz = idUserTranz;
    }
}
