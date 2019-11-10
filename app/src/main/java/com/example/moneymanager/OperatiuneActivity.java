package com.example.moneymanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OperatiuneActivity extends AppCompatActivity {
    Intent intent;
    String tip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operatiune);
        intent = getIntent();
        tip = intent.getStringExtra("tip");

        //prelucrari in functie de datele ce trebuie afisate pe formular
        if(tip.equals("adaugaCheltuiala")){
            TextView title = (TextView)findViewById(R.id.Title);
            title.setText(R.string.addCheltuialaTitle);
            Spinner spinner = (Spinner)findViewById(R.id.spinnerSelect);
            ArrayAdapter<String> spinnerAdapter =
                    new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                            getResources().getStringArray(R.array.elemCheltuieli));
            spinner.setAdapter(spinnerAdapter);
        }
        else if(tip.equals("adaugaVenit")) {
            TextView title = (TextView)findViewById(R.id.Title);
            title.setText(R.string.addVenitTitle);
            Spinner spinner = (Spinner)findViewById(R.id.spinnerSelect);
            ArrayAdapter<String> spinnerAdapter =
                    new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                            getResources().getStringArray(R.array.elemVenituri));
            spinner.setAdapter(spinnerAdapter);
        }

        //handler pentru click pe butonul de adauga
        Button okBtn = findViewById(R.id.BtnIdOK);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tip.equals("adaugaCheltuiala"))
                    adaugaCheltuiala(v);
                else if(tip.equals("adaugaVenit"))
                    adaugaVenit(v);
            }
        });
    }

    public void adaugaCheltuiala(View view) {
        //preluam datele din activitate
        //valoare - din EditText
        String valoareText = ((EditText)findViewById(R.id.ETvaloare)).getText().toString();
        Double valoare = Double.parseDouble(valoareText);

        //categorie din spinner
        String categorie = ((Spinner)findViewById(R.id.spinnerSelect)).getSelectedItem().toString();

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

    public void adaugaVenit(View view) {
        //preluam datele din activitate
        //valoare - din EditText
        String valoareText = ((EditText)findViewById(R.id.ETvaloare)).getText().toString();
        Double valoare = Double.parseDouble(valoareText);

        //categorie din spinner
        String categorie = ((Spinner)findViewById(R.id.spinnerSelect)).getSelectedItem().toString();

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

        //creare obiect venit
        Venit venit = new Venit(valoare,categorie,data,valuta,natura);
        intent.putExtra("venit",venit);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void cancelOperatiune(View view) {
        //setam rezultatul returnat si inchidem activitatea
        setResult(RESULT_CANCELED);
        finish();
    }
}
