package com.example.moneymanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AddCategActivity extends AppCompatActivity {
    MoneyDatabase database;
    Categorie categorie;

    Intent it;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_categ);
        TextView tvCateg = findViewById(R.id.titluAddCateg);

        database = Room.databaseBuilder(this,MoneyDatabase.class,"trial14").allowMainThreadQueries().build();

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

        categorie = new Categorie(aditiv,numeCategorie);
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
