package com.example.moneymanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void checkLogin(View view) {
        EditText ETmail = findViewById(R.id.mailLogin);
        String mail = ETmail.getText().toString();
        EditText ETpass = findViewById(R.id.passLogin);
        String pass = ETpass.getText().toString();
        if(mail.equals("user") && pass.equals("user")){
            Intent it = new Intent(getApplicationContext(),MainActivity.class);
            finish();
            startActivity(it);
        }
        else Toast.makeText(this,"User și/sau parolă incorecte.",Toast.LENGTH_LONG);
    }
}
