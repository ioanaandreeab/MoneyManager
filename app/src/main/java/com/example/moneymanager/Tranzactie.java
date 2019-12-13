package com.example.moneymanager;

import android.os.Parcel;
import android.os.Parcelable;

public class Tranzactie implements Parcelable {
    private double valoare;
    private String data;
    private String natura;
    private String categorie;
    private boolean esteAditiva;

    public Tranzactie(double valoare, String data, String natura, String categorie, boolean esteAditiva) {
        this.valoare = valoare;
        this.data = data;
        this.natura = natura;
        this.categorie = categorie;
        this.esteAditiva = esteAditiva;
    }

    protected Tranzactie(Parcel in) {
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

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(valoare);
        parcel.writeString(categorie);
        parcel.writeString(data);
        parcel.writeString(natura);
        parcel.writeBoolean(esteAditiva);
    }
}
