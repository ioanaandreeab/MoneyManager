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
import java.util.Collections;
import java.util.List;

public class StatsActivity extends AppCompatActivity {
    private MoneyDatabase database;
    List<Double> cheltuieli;
    List<Double> venituri;
    float venSuma;
    float cheltSuma;
    float venMed;
    float cheltMed;
    double cheltuieliLocale;
    double venituriLocale;
    double cheltuieliTransport;
    double cheltuieliTaxe;
    double cheltuieliEducatie;
    double cheltuieliTimpLiber;
    double alteCheltuieli;
    double cheltuieliMancare;
    double cheltuieliFacturi;
    double cheltuieliPredefinite;
    List<Double> cheltuieliPeCategorii;
    SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        int idUserCurent = sharedPref.loadCurrentUser();
        //preluare date din BD locala
        database = Room.databaseBuilder(this, MoneyDatabase.class,"moneyManager").allowMainThreadQueries().build();

        //pentru pie chart
        cheltuieliLocale = database.getTranzactieDAO().selectSumaCheltuieli(idUserCurent);
        venituriLocale = database.getTranzactieDAO().selectSumaVenituri(idUserCurent);

        //pentru bar chart
        cheltuieliTransport = database.getTranzactieDAO().selectCheltuieliTransport(idUserCurent) > 0 ? database.getTranzactieDAO().selectCheltuieliTransport(idUserCurent) : 0;
        cheltuieliEducatie = database.getTranzactieDAO().selectCheltuieliEducatie(idUserCurent);
        cheltuieliFacturi = database.getTranzactieDAO().selectCheltuieliFacturi(idUserCurent);
        cheltuieliMancare = database.getTranzactieDAO().selectCheltuieliMancare(idUserCurent);
        cheltuieliTaxe = database.getTranzactieDAO().selectCheltuieliTaxe(idUserCurent);
        cheltuieliTimpLiber = database.getTranzactieDAO().selectCheltuieliTimpLiber(idUserCurent);

        cheltuieliPredefinite += cheltuieliEducatie + cheltuieliTaxe + cheltuieliTimpLiber + cheltuieliTransport + cheltuieliMancare + cheltuieliFacturi;
        alteCheltuieli = database.getTranzactieDAO().selectSumaCheltuieli(idUserCurent) - cheltuieliPredefinite;

        cheltuieliPeCategorii = new ArrayList<>();
        cheltuieliPeCategorii.add(cheltuieliEducatie);
        cheltuieliPeCategorii.add(cheltuieliFacturi);
        cheltuieliPeCategorii.add(cheltuieliMancare);
        cheltuieliPeCategorii.add(cheltuieliTaxe);
        cheltuieliPeCategorii.add(cheltuieliTimpLiber);
        cheltuieliPeCategorii.add(cheltuieliTransport);
        cheltuieliPeCategorii.add(alteCheltuieli);

        LinearLayout pieChartLayout = findViewById(R.id.PieChartView);
        List<Double> valoriTranzactii = new ArrayList<>();
        valoriTranzactii.add(venituriLocale);
        valoriTranzactii.add(cheltuieliLocale);
        PieChartStat chartStat = new PieChartStat(this,valoriTranzactii);
        pieChartLayout.addView(chartStat);

        BarChart barChartStat = new BarChart(this, cheltuieliPeCategorii);
        LinearLayout barChartLayout = findViewById(R.id.BarChartView);
        barChartLayout.addView(barChartStat);

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
                    //ia valoarea pentru venit
                    double venit = d.child("sumaVenit").getValue(Float.class);
                    venSuma+=venit;
                    venituri.add(venit);
                    //ia valoarea pentru cheltuieli
                    double cheltuiala = d.child("sumaChelt").getValue(Float.class);
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
        //stiu ca mereu o sa am 2 valori - cheltuieli si venituri -- le desenez
        canvas.drawArc(20,20,700,700,unghiStart,(float)(valoriTranzactii.get(0)*360)/total,true,pensulaTranzactii);
        unghiStart+=valoriTranzactii.get(0)*360/total;
        pensulaTranzactii.setColor(getResources().getColor(R.color.myLightRed));
        canvas.drawArc(20,20, 700,700,unghiStart,(float)(valoriTranzactii.get(1)*360)/total,true,pensulaTranzactii);

        //legenda
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
    private List<Double> valoriBarChart;
    public BarChart(Context context, List<Double> valoriBarChart) {
        super(context);
        this.valoriBarChart = valoriBarChart;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint pensula = new Paint();
        pensula.setColor(Color.BLUE);
        double valoareMaxima = Collections.max(valoriBarChart);
        //canvas.drawRect(10,500,50,0,pensula);
        float valoareDePornire = 0;
        for (int i =0; i<valoriBarChart.size();i++){
            pensula.setColor(Color.rgb(25*i%255,36*i%255,191));
            canvas.drawRect(i*100 +10,canvas.getHeight()-(float)(valoriBarChart.get(i)*500/valoareMaxima),valoareDePornire+100,canvas.getHeight(), pensula);
            valoareDePornire+=100;
        }

        //legenda
        //educatie
        pensula.setColor(Color.rgb(25*0,36*0,191));
        canvas.drawRect(800,50,750,100,pensula);
        pensula.setColor(Color.WHITE);
        pensula.setTextSize(30);
        canvas.drawText("Educație",820,75,pensula);

        //facturi
        pensula.setColor(Color.rgb(25*1,36*1,191));
        canvas.drawRect(800,110,750,160,pensula);
        pensula.setColor(Color.WHITE);
        pensula.setTextSize(30);
        canvas.drawText("Facturi",820,135,pensula);

        //mancare
        pensula.setColor(Color.rgb(25*2,36*2,191));
        canvas.drawRect(800,170,750,220,pensula);
        pensula.setColor(Color.WHITE);
        pensula.setTextSize(30);
        canvas.drawText("Mâncare",820,195,pensula);

        //taxe
        pensula.setColor(Color.rgb(25*3,36*3,191));
        canvas.drawRect(800,230,750,280,pensula);
        pensula.setColor(Color.WHITE);
        pensula.setTextSize(30);
        canvas.drawText("Taxe",820,255,pensula);

        //timp liber
        pensula.setColor(Color.rgb(25*4%255,36*4%255,191));
        canvas.drawRect(800,290,750,340,pensula);
        pensula.setColor(Color.WHITE);
        pensula.setTextSize(30);
        canvas.drawText("Timp liber",820,315,pensula);

        //transport
        pensula.setColor(Color.rgb(25*5%255,36*5%255,191));
        canvas.drawRect(800,350,750,400,pensula);
        pensula.setColor(Color.WHITE);
        pensula.setTextSize(30);
        canvas.drawText("Transport",820,375,pensula);

        //altele
        pensula.setColor(Color.rgb(25*6%255,36*6%255,191));
        canvas.drawRect(800,410,750,460,pensula);
        pensula.setColor(Color.WHITE);
        pensula.setTextSize(30);
        canvas.drawText("Altele",820,435,pensula);
    }
}

