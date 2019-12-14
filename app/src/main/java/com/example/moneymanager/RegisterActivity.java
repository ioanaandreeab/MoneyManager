package com.example.moneymanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private MoneyDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        database = Room.databaseBuilder(getApplicationContext(),MoneyDatabase.class,"trial14").allowMainThreadQueries().build();

        //adaugare categorii prestabilite pt user
        database.getCategorieDAO().insertCategorie(new Categorie(true,"Salariu"));
        database.getCategorieDAO().insertCategorie(new Categorie(true,"Împrumuturi"));
        database.getCategorieDAO().insertCategorie(new Categorie(true,"Cadouri"));
        database.getCategorieDAO().insertCategorie(new Categorie(true,"Voucher"));

        database.getCategorieDAO().insertCategorie(new Categorie(false,"Facturi"));
        database.getCategorieDAO().insertCategorie(new Categorie(false,"Mâncare"));
        database.getCategorieDAO().insertCategorie(new Categorie(false,"Transport"));
        database.getCategorieDAO().insertCategorie(new Categorie(false,"Taxe"));
        database.getCategorieDAO().insertCategorie(new Categorie(false,"Timp liber"));
        database.getCategorieDAO().insertCategorie(new Categorie(false,"Educatie"));

    }

    public void cancel(View view) {
        finish();
    }

    public void createUser(View view) {
        EditText etNume = findViewById(R.id.ETNume);
        String nume = etNume.getText().toString();
        EditText etPreume = findViewById(R.id.ETPrenume);
        String prenume = etNume.getText().toString();
        EditText etMail = findViewById(R.id.ETMail);
        String mail = etNume.getText().toString();
        EditText etPass = findViewById(R.id.ETPass);
        String pass = etNume.getText().toString();
        User user = new User(mail,nume,prenume,pass,"",0);
        database.getUserDAO().insertUser(user);
        Toast.makeText(getApplicationContext(),"V-ați înregistrat cu succes!",Toast.LENGTH_LONG).show();
        finish();
    }
}
