package com.example.moneymanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
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

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OperatiuneActivity extends AppCompatActivity {
    Intent intent;
    String tip;
    Tranzactie deEditat;
    int pozitieDeEditat;
    SharedPref sharedPref;
    int userTranzactie;
    MoneyDatabase database;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operatiune);
        database = Room.databaseBuilder(getApplicationContext(),MoneyDatabase.class,"trial10").allowMainThreadQueries().build();
        intent = getIntent();
        tip = intent.getStringExtra("tip");
        sharedPref = new SharedPref(this);
        userTranzactie = sharedPref.loadCurrentUser();
        user = database.getUserDAO().findUserById(userTranzactie);
        String text = "Faceti modificari pentru userul "+user.toString();
        Toast.makeText(getApplicationContext(),text, Toast.LENGTH_LONG).show();
        //prelucrari in functie de datele ce trebuie afisate pe formular
        if(tip != null) {
            if (tip.equals("adaugaCheltuiala")) {
                TextView title = (TextView) findViewById(R.id.Title);
                title.setText(R.string.addCheltuialaTitle);
                Spinner spinner = (Spinner) findViewById(R.id.spinnerSelect);
                ArrayAdapter<String> spinnerAdapter =
                        new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                                getResources().getStringArray(R.array.elemCheltuieli));
                spinner.setAdapter(spinnerAdapter);
            } else if (tip.equals("adaugaVenit")) {
                TextView title = (TextView) findViewById(R.id.Title);
                title.setText(R.string.addVenitTitle);
                Spinner spinner = (Spinner) findViewById(R.id.spinnerSelect);
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

        //s-a trimis activitatea pentru editare
        else {
            TextView title = (TextView)findViewById(R.id.Title);
            title.setText(R.string.editTranzactie);
            deEditat = intent.getParcelableExtra("edit");

            //setam categoria selectata in spinner
            Spinner spinner = (Spinner) findViewById(R.id.spinnerSelect);
            ArrayAdapter<String> spinnerAdapter;
            if (deEditat.isEsteAditiva()==false) {
                spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                                getResources().getStringArray(R.array.elemCheltuieli));
                spinner.setAdapter(spinnerAdapter);
            }
            else {
                spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                                getResources().getStringArray(R.array.elemVenituri));
                spinner.setAdapter(spinnerAdapter);
            }
            int categPosition = spinnerAdapter.getPosition(deEditat.getCategorie());
            spinner.setSelection(categPosition);

            ((EditText)findViewById(R.id.ETvaloare)).setText(String.valueOf(deEditat.getValoare()));
            if(deEditat.getNatura().equals("Cash"))
                ((RadioGroup)findViewById(R.id.RGNatura)).check(R.id.cashRB);
            else
                ((RadioGroup)findViewById(R.id.RGNatura)).check(R.id.cardRB);

            //data - din DatePicker + formatare
            DatePicker dp = findViewById(R.id.DP);
            Calendar c = Calendar.getInstance();
            SimpleDateFormat s = new SimpleDateFormat("dd.MM.YYYY");
            try {
                c.setTime(s.parse(deEditat.getData()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //butoane
            Button okBtn = findViewById(R.id.BtnIdOK);
            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editeazaTranzactie(v);
                }
            });

        }
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

        //RadioGroup - natura tranzactiei
        RadioGroup rgNatura = findViewById(R.id.RGNatura);
        RadioButton rbNatura = findViewById(rgNatura.getCheckedRadioButtonId());
        String natura = rbNatura.getText().toString();

        //creare obiect cheltuiala
        Tranzactie cheltuiala = new Tranzactie(valoare,data,natura,categorie,false, user.getId());
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

        //RadioGroup - natura tranzactiei
        RadioGroup rgNatura = findViewById(R.id.RGNatura);
        RadioButton rbNatura = findViewById(rgNatura.getCheckedRadioButtonId());
        String natura = rbNatura.getText().toString();

        //creare obiect venit
        Tranzactie venit = new Tranzactie(valoare, data, natura, categorie, true, user.getId());
        intent.putExtra("venit",venit);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void editeazaTranzactie(View view) {
        //preluam datele din activitate
        //valoare - din EditText
        String valoareText = ((EditText)findViewById(R.id.ETvaloare)).getText().toString();
        deEditat.setValoare(Double.parseDouble(valoareText));

        //categorie din spinner
        deEditat.setCategorie(((Spinner)findViewById(R.id.spinnerSelect)).getSelectedItem().toString());

        //data - din DatePicker + formatare
        DatePicker dp = findViewById(R.id.DP);
        Calendar c = Calendar.getInstance();
        c.set(dp.getYear(),dp.getMonth(),dp.getDayOfMonth());
        SimpleDateFormat s = new SimpleDateFormat("dd.MM.YYYY");
        String data = s.format(c.getTime());
        deEditat.setData(data);

        //RadioGroup - natura tranzactiei
        RadioGroup rgNatura = findViewById(R.id.RGNatura);
        RadioButton rbNatura = findViewById(rgNatura.getCheckedRadioButtonId());
        deEditat.setNatura(rbNatura.getText().toString());

        //creare obiect venit
        intent.putExtra("editat",deEditat);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void cancelOperatiune(View view) {
        //setam rezultatul returnat si inchidem activitatea
        setResult(RESULT_CANCELED);
        finish();
    }
}
