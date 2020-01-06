package com.example.moneymanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StatsActivity extends AppCompatActivity {
    List<Float> chelt;
    List<Float> ven;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //referinta nu se vrea doar la cadouri, ci la toata baza de date
        DatabaseReference myRef = database.getReference();
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //se preia colectia
                DataSnapshot ds = dataSnapshot.child("user-data");
                //toate subnodurile din colectie -> lista de inregistrari privind informatiile despre utilizatori
                Iterable<DataSnapshot> inregistrariDS = ds.getChildren();
                //reinitializez lista la fiecare modificare
                ven = new ArrayList<>();
                for (DataSnapshot d : inregistrariDS) {
                    //ia valoarea
                    Float venit = d.child("sumaVen").getValue(Float.class);
                    Toast.makeText(StatsActivity.this, String.valueOf(venit),Toast.LENGTH_LONG).show();
                    ven.add(venit);
                    //Toast.makeText(MainActivity.this, c.toString(), Toast.LENGTH_LONG).show();
                }
                /*
                ListView myLV = findViewById(R.id.LVcadouri);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,cadouri);
                myLV.setAdapter(adapter);*/
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("FAIL", "Failed to read value.", error.toException());
            }
        });
    }

    /*public void metodaInserareFirebase(View view) {
        Cadou cadou = new Cadou(4,45,true,"jucarie","Ioana");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //asta e nodul parinte
        DatabaseReference myRef = database.getReference("cadouri");
        //nod pentru fiecare cadou - identificat unic
        DatabaseReference nodCadou = myRef.child("C-"+cadou.getId());
        nodCadou.setValue(cadou);
    }*/
}
