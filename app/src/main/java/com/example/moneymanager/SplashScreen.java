package com.example.moneymanager;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

//pentru a incarca imaginea de pe internet trebuie realizata o operatie asincrona
//motiv pentru care se creeaza urmatoarea clasa
class GetImageFromURL extends AsyncTask<String, Void, Bitmap> {
    ImageView myImageView;
    //constructorul primeste imageview-ul in care va fi pusa imaginea
    public GetImageFromURL(ImageView myImageView) {
        this.myImageView = myImageView;
    }
    protected Bitmap doInBackground(String... imageURL) {
        Bitmap myBitmap = null;
        InputStream in;
        try {
            //se obtine bitmapul pe baza URL-ului
            in = new URL(imageURL[0]).openStream();
            myBitmap = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myBitmap;
    }
    protected void onPostExecute(Bitmap result) {
        myImageView.setImageBitmap(result);
    }
}

public class SplashScreen extends AppCompatActivity {
    SharedPref sharedPref;
    boolean isLogged;
    Intent it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new GetImageFromURL((ImageView)findViewById(R.id.imageViewSplashScreen)).execute("https://images.vexels.com/media/users/3/145791/isolated/preview/ecf1c7a0acfccf40772794a591a57738-pig-money-box-by-vexels.png");
        //verific daca utilizatorul s-a logat
        sharedPref = new SharedPref(this);
        isLogged = sharedPref.loadIsLogged();
        //splash screenul este afisat o secunda, prin intermediul unui thread care "doarme"
        //ulterior, porneste urmatoarea activitate - login
        Thread myThread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(1500);
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
