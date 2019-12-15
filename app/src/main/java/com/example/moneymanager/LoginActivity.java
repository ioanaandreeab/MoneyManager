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
        database = Room.databaseBuilder(getApplicationContext(),MoneyDatabase.class,"moneyManager").allowMainThreadQueries().build();
        sharedPref = new SharedPref(this);
    }

    //verificare login
    public void checkLogin(View view) {
        EditText ETmail = findViewById(R.id.mailLogin);
        String mail = ETmail.getText().toString();
        EditText ETpass = findViewById(R.id.passLogin);
        String pass = ETpass.getText().toString();
        int idDupaMail = database.getUserDAO().idUserMail(mail);
        int idDupaPass = database.getUserDAO().idUserPass(pass);


        //userul si parola exista in BD si corespund
        if(idDupaMail==idDupaPass && idDupaMail!=0 && idDupaPass!=0){
            //se salveaza in fisierul de preferinte id-ul userului curent din app si faptul ca s-a logat
            sharedPref.setUser(idDupaMail);
            sharedPref.setIsLogged(true);
            Intent it = new Intent(getApplicationContext(),MainActivity.class);
            finish();
            startActivity(it);
        }
        //userul nu exista
        else if(idDupaMail== 0 && idDupaPass== 0){
            Toast.makeText(this,"User inexistent! Înregistrați-vă!",Toast.LENGTH_LONG).show();
        }
        //parola sau adresa de mail gresita
        else Toast.makeText(this,"User sau parolă incorect/ă!",Toast.LENGTH_LONG).show();
    }

    public void openRegisterForm(View view) {
        Intent it = new Intent(this,RegisterActivity.class);
        startActivity(it);
    }
}
