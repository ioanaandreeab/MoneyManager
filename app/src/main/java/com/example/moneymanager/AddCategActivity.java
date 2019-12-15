package com.example.moneymanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddCategActivity extends AppCompatActivity {
    MoneyDatabase database;
    Categorie categorie;
    SharedPref sharedPref;

    Intent it;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_categ);
        TextView tvCateg = findViewById(R.id.titluAddCateg);
        sharedPref = new SharedPref(this);

        database = Room.databaseBuilder(this,MoneyDatabase.class,"moneyManager").allowMainThreadQueries().build();

        it = getIntent();
        if(it.getStringExtra("categorie").equals("venit")){
            tvCateg.setText(R.string.titleAddVen);
        }
        if(it.getStringExtra("categorie").equals("cheltuiala")){
            tvCateg.setText(R.string.addCheltTitle);
        }

    }

    public void adaugaCategorie(View view) {
        EditText etNumeCategorie = findViewById(R.id.categName);
        String numeCategorie = etNumeCategorie.getText().toString();
        boolean aditiv = false;
        if(it.getStringExtra("categorie").equals("venit")){
            aditiv = true;
        }
        if(it.getStringExtra("categorie").equals("cheltuiala")){
            aditiv = false;
        }

        int idUser = sharedPref.loadCurrentUser();
        categorie = new Categorie(aditiv,numeCategorie,idUser);
        database.getCategorieDAO().insertCategorie(categorie);
        it.putExtra("numeCategorie",numeCategorie);
        setResult(RESULT_OK,it);
        finish();
    }

    public void metodaCancel(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }
}
