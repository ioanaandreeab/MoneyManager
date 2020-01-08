package com.example.moneymanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private MoneyDatabase database;
    private String mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        database = Room.databaseBuilder(getApplicationContext(),MoneyDatabase.class,"moneyManager").allowMainThreadQueries().build();
    }

    public void initializareCategoriiPtUtilizator() {

        //se ia id-ul utilizatorului introdus in bd
        int idUser = database.getUserDAO().idUserMail(mail);

        //adaugare categorii prestabilite pt user - se insereaza o singura data, la register
        database.getCategorieDAO().insertCategorie(new Categorie(true,"Salariu",idUser));
        database.getCategorieDAO().insertCategorie(new Categorie(true,"Împrumuturi",idUser));
        database.getCategorieDAO().insertCategorie(new Categorie(true,"Cadouri",idUser));
        database.getCategorieDAO().insertCategorie(new Categorie(true,"Voucher",idUser));

        database.getCategorieDAO().insertCategorie(new Categorie(false,"Facturi",idUser));
        database.getCategorieDAO().insertCategorie(new Categorie(false,"Mâncare",idUser));
        database.getCategorieDAO().insertCategorie(new Categorie(false,"Transport",idUser));
        database.getCategorieDAO().insertCategorie(new Categorie(false,"Taxe",idUser));
        database.getCategorieDAO().insertCategorie(new Categorie(false,"Timp liber",idUser));
        database.getCategorieDAO().insertCategorie(new Categorie(false,"Educație",idUser));
    }

    public void cancel(View view) {
        finish();
    }

    public void createUser(View view) {
        EditText etNume = findViewById(R.id.ETNume);
        String nume = etNume.getText().toString();
        EditText etPrenume = findViewById(R.id.ETPrenume);
        String prenume = etPrenume.getText().toString();
        EditText etMail = findViewById(R.id.ETMail);
        mail = etMail.getText().toString();
        EditText etPass = findViewById(R.id.ETPass);
        String pass = etPass.getText().toString();
        User user = new User(mail,nume,prenume,pass,"",0);
        database.getUserDAO().insertUser(user);
        Toast.makeText(getApplicationContext(),"V-ați înregistrat cu succes!",Toast.LENGTH_LONG).show();

        initializareCategoriiPtUtilizator();

        finish();
    }
}
