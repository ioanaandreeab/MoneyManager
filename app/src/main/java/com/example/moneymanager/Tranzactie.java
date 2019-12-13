package com.example.moneymanager;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

public class Tranzactie implements Parcelable {
    private int id;
    private double valoare;
    private String data;
    private String natura;
    private String categorie;
    private boolean esteAditiva;

    public Tranzactie(int id, double valoare, String data, String natura, String categorie, boolean esteAditiva) {
        this.id = id;
        this.valoare = valoare;
        this.data = data;
        this.natura = natura;
        this.categorie = categorie;
        this.esteAditiva = esteAditiva;
    }

    protected Tranzactie(Parcel in) {
        id = in.readInt();
        valoare = in.readDouble();
        data = in.readString();
        natura = in.readString();
        categorie = in.readString();
        esteAditiva = in.readByte() != 0;
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

    public double getValoare() {
        return valoare;
    }

    public void setValoare(double valoare) {
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
        return "Tranzactie{" +
                "valoare=" + valoare +
                ", data='" + data + '\'' +
                ", natura='" + natura + '\'' +
                ", categorie='" + categorie + '\'' +
                ", esteAditiva=" + esteAditiva +
                '}';
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
    }
}
