package com.example.moneymanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class StatsActivity extends AppCompatActivity {
    private MoneyDatabase database;
    List<Float> cheltuieli;
    List<Float> venituri;
    float venSuma;
    float cheltSuma;
    float venMed;
    float cheltMed;
    double cheltuieliLocale;
    double venituriLocale;
    SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        int idUserCurent = sharedPref.loadCurrentUser();
        //preluare date din BD locala
        database = Room.databaseBuilder(this, MoneyDatabase.class,"moneyManager").allowMainThreadQueries().build();
        cheltuieliLocale = database.getTranzactieDAO().selectSumaCheltuieli(idUserCurent);
        venituriLocale = database.getTranzactieDAO().selectSumaVenituri(idUserCurent);

        LinearLayout chartLayout = findViewById(R.id.ChartView);
        List<Double> valoriTranzactii = new ArrayList<>();
        valoriTranzactii.add(venituriLocale);
        valoriTranzactii.add(cheltuieliLocale);
        PieChartStat chartStat = new PieChartStat(this,valoriTranzactii);
        chartLayout.addView(chartStat);

        //Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //referinta la intreaga baza de date
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
                venituri = new ArrayList<>();
                cheltuieli = new ArrayList<>();
                venSuma = 0;
                cheltSuma = 0;
                for (DataSnapshot d : inregistrariDS) {
                    //ia valoarea
                    float venit = d.child("sumaVenit").getValue(Float.class);
                    venSuma+=venit;
                    venituri.add(venit);
                    float cheltuiala = d.child("sumaChelt").getValue(Float.class);
                    cheltSuma+=cheltuiala;
                    cheltuieli.add(cheltuiala);
                }
                venMed = venSuma/venituri.size();
                cheltMed = cheltSuma/cheltuieli.size();
                TextView tvVenitMediu = findViewById(R.id.TVVenMed);
                tvVenitMediu.setText(String.valueOf(venMed));
                TextView tvCheltMedie = findViewById(R.id.TVCheltMed);
                tvCheltMedie.setText(String.valueOf(cheltMed));
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

//clase pentru desenare

class PieChartStat extends View {
    private List<Double> valoriTranzactii;
    public PieChartStat(Context context, List<Double> valoriTranzactii) {
        super(context);
        this.valoriTranzactii = valoriTranzactii;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint pensulaTranzactii = new Paint();
        pensulaTranzactii.setColor(getResources().getColor(R.color.myLightGreen));
        //valoarea pentru 100%
        float total = 0;
        for(int i=0;i<2;i++){
            total+= valoriTranzactii.get(i);
        }
        //unghiul de inceput
        float unghiStart = 0;
        canvas.drawArc(20,20,700,700,unghiStart,(float)(valoriTranzactii.get(0)*360)/total,true,pensulaTranzactii);
        unghiStart+=valoriTranzactii.get(0)*360/total;
        pensulaTranzactii.setColor(getResources().getColor(R.color.myLightRed));
        canvas.drawArc(20,20, 700,700,unghiStart,(float)(valoriTranzactii.get(1)*360)/total,true,pensulaTranzactii);

        canvas.drawRect(800,650,750,700,pensulaTranzactii);
        pensulaTranzactii.setColor(Color.WHITE);
        pensulaTranzactii.setTextSize(30);
        canvas.drawText("Cheltuieli",820,700,pensulaTranzactii);
        pensulaTranzactii.setColor(getResources().getColor(R.color.myLightGreen));
        canvas.drawRect(800,580,750,630,pensulaTranzactii);
        pensulaTranzactii.setColor(Color.WHITE);
        canvas.drawText("Venituri",820,630,pensulaTranzactii);
    }
}

class BarChart extends View {
    private List<Double> valoriCashCard;
    public BarChart(Context context, List<Double> valoriCashCard) {
        super(context);
        this.valoriCashCard = valoriCashCard;
    }
}

