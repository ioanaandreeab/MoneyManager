package com.example.moneymanager;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Cheltuiala implements Parcelable {
  private double valoare;
  private String categorie;
  private String data;
  private String valuta;
  private String naturaTranzactie;

  protected Cheltuiala(Parcel in) {
    valoare = in.readDouble();
    categorie = in.readString();
    data = in.readString();
    valuta = in.readString();
    naturaTranzactie = in.readString();
  }

  public static final Creator<Cheltuiala> CREATOR = new Creator<Cheltuiala>() {
    @Override
    public Cheltuiala createFromParcel(Parcel in) {
      return new Cheltuiala(in);
    }

    @Override
    public Cheltuiala[] newArray(int size) {
      return new Cheltuiala[size];
    }
  };

  public double getValoare() {
    return valoare;
  }

  public void setValoare(double valoare) {
    this.valoare = valoare;
  }

  public String getCategorie() {
    return categorie;
  }

  public void setCategorie(String categorie) {
    this.categorie = categorie;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public String getValuta() {
    return valuta;
  }

  public void setValuta(String valuta) {
    this.valuta = valuta;
  }

  public String getNaturaTranzactie() {
    return naturaTranzactie;
  }

  public void setNaturaTranzactie(String naturaTranzactie) {
    this.naturaTranzactie = naturaTranzactie;
  }

  public Cheltuiala(double valoare, String categorie, String data, String valuta, String naturaTranzactie) {
    this.valoare = valoare;
    this.categorie = categorie;
    this.data = data;
    this.valuta = valuta;
    this.naturaTranzactie = naturaTranzactie;
  }


  @Override
  public String toString() {
    return "Cheltuiala{" +
            "valoare=" + valoare +
            ", categorie='" + categorie + '\'' +
            ", data='" + data + '\'' +
            ", valuta='" + valuta + '\'' +
            ", naturaTranzactie='" + naturaTranzactie + '\'' +
            '}';
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeDouble(valoare);
    dest.writeString(categorie);
    dest.writeString(data);
    dest.writeString(valuta);
    dest.writeString(naturaTranzactie);
  }
}
