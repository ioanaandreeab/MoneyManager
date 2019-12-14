package com.example.moneymanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {
    SharedPref sharedPref;
    boolean isLogged;
    Intent it;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //verific daca utilizatorul s-a logat
        sharedPref = new SharedPref(this);
        isLogged = sharedPref.loadIsLogged();
        //splash screenul este afisat o secunda, prin intermediul unui thread care "doarme"
        //ulterior, portneste urmatoarea activitate - login
        Thread myThread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(1000);
                    if(isLogged == false) {
                        it = new Intent(getApplicationContext(), LoginActivity.class);
                    }
                    else {
                        it = new Intent(getApplicationContext(), MainActivity.class);
                    }
                    startActivity(it);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }
}
