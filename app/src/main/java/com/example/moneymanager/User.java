package com.example.moneymanager;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "Useri",indices = {@Index(value = "email",unique = true)})
public class User {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userId")
    private int id;
    @NonNull
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "nume")
    private String nume;
    @ColumnInfo(name = "prenume")
    private String prenume;
    @NonNull
    @ColumnInfo(name = "pass")
    private String pass;
    @ColumnInfo(name = "rating_text")
    private String rating_text;
    @ColumnInfo(name = "rating")
    private float rating;

    public User(@NonNull String email, String nume, String prenume, @NonNull String pass, String rating_text, float rating) {
        this.email = email;
        this.nume = nume;
        this.prenume = prenume;
        this.pass = pass;
        this.rating_text = rating_text;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    @NonNull
    public String getPass() {
        return pass;
    }

    public void setPass(@NonNull String pass) {
        this.pass = pass;
    }

    public String getRating_text() {
        return rating_text;
    }

    public void setRating_text(String rating_text) {
        this.rating_text = rating_text;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", pass='" + pass + '\'' +
                ", rating_text='" + rating_text + '\'' +
                ", rating=" + rating +
                '}';
    }

}
