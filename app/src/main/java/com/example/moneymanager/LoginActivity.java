package com.example.moneymanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {
    private MoneyDatabase database;
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        database = Room.databaseBuilder(getApplicationContext(),MoneyDatabase.class,"trial10").allowMainThreadQueries().build();
        sharedPref = new SharedPref(this);
    }

    //pentru moment doar se verifica niste date hardcodate
    public void checkLogin(View view) {
        EditText ETmail = findViewById(R.id.mailLogin);
        String mail = ETmail.getText().toString();
        EditText ETpass = findViewById(R.id.passLogin);
        String pass = ETpass.getText().toString();
        int idDupaMail = database.getUserDAO().idUserMail(mail);
        int idDupaPass = database.getUserDAO().idUserPass(pass);
        if(idDupaMail==idDupaPass){
            sharedPref.setUser(idDupaMail);
            Intent it = new Intent(getApplicationContext(),MainActivity.class);
            finish();
            startActivity(it);
        }
        else Toast.makeText(this,"User și/sau parolă incorecte.",Toast.LENGTH_LONG);
    }

    public void openRegisterForm(View view) {
        Intent it = new Intent(this,RegisterActivity.class);
        startActivity(it);
    }
}
