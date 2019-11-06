package com.example.moneymanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AdaugaCheltuialaActivity extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adauga_cheltuiala);
        intent = getIntent();
    }

    public void adaugaCheltuiala(View view) {
        //preluam datele din activitate
        //valoare - din EditText
        String valoareText = ((EditText)findViewById(R.id.ETvaloare)).getText().toString();
        Double valoare = Double.parseDouble(valoareText);

        //categorie din spinner
        String categorie = ((Spinner)findViewById(R.id.spinnerCheltuieli)).getSelectedItem().toString();

        //data - din DatePicker + formatare
        DatePicker dp = findViewById(R.id.DP);
        Calendar c = Calendar.getInstance();
        c.set(dp.getYear(),dp.getMonth(),dp.getDayOfMonth());
        SimpleDateFormat s = new SimpleDateFormat("dd.MM.YYYY");
        String data = s.format(c.getTime());

        //RadioGroups - valuta & natura tranzactiei
        RadioGroup rgValuta = findViewById(R.id.RGvaluta);
        RadioButton rbValuta = findViewById(rgValuta.getCheckedRadioButtonId());
        String valuta = rbValuta.getText().toString();
        RadioGroup rgNatura = findViewById(R.id.RGNatura);
        RadioButton rbNatura = findViewById(rgNatura.getCheckedRadioButtonId());
        String natura = rbNatura.getText().toString();

        //creare obiect cheltuiala
        Cheltuiala cheltuiala = new Cheltuiala(valoare,categorie,data,valuta,natura);
        intent.putExtra("cheltuiala",cheltuiala);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void cancelCheltuiala(View view) {
        //setam rezultatul returnat si inchidem activitatea
        setResult(RESULT_CANCELED);
        finish();
    }
}
