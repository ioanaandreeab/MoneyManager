package com.example.moneymanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AddCategActivity extends AppCompatActivity {

    Intent it;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_categ);
        TextView tvCateg = findViewById(R.id.titluAddCateg);

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
        it.putExtra("numeCategorie",numeCategorie);
        setResult(RESULT_OK,it);
        finish();
    }

    public void metodaCancel(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }
}
