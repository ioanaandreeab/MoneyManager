package com.example.moneymanager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    private FloatingActionButton fab;
    private FloatingActionButton fabVenit;
    private FloatingActionButton fabCheltuiala;
    private boolean isOpen = false;
    private int requestCodeAdaugaVenit = 333;
    private int requestCodeAdaugaCheltuiala = 444;
    private List<Object> tranzactii = new ArrayList<>();
    ListView lv;


    //functii privind comportamentul fabs
    public void hideBtns() {
        fab.hide();
        fabCheltuiala.hide();
        fabVenit.hide();
    }

    private void showBtns() {
        fab.show();
        fabCheltuiala.show();
        fabVenit.show();
    }

    private void openFabs(){
        isOpen = true;
        fabVenit.animate().translationY(-getResources().getDimension(R.dimen.fab_55));
        fabCheltuiala.animate().translationY(-getResources().getDimension(R.dimen.fab_105));
    }

    private void closeFabs() {
        isOpen = false;
        fabVenit.animate().translationY(0);
        fabCheltuiala.animate().translationY(0);
    }

    //calcul total - statistici
    private void calcSum() {
        double totalVenituri = 0;
        double totalCheltuieli = 0;
        double balanta = 0;
        for(Object tranzactie : tranzactii){
            if(tranzactie instanceof Cheltuiala)
                totalCheltuieli += ((Cheltuiala) tranzactie).getValoare();
            else if (tranzactie instanceof Venit)
                totalVenituri += ((Venit) tranzactie).getValoare();
        }
        balanta = totalVenituri - totalCheltuieli;
        TextView venituri = findViewById(R.id.venitVal);
        venituri.setText(Double.toString(totalVenituri));

        TextView cheltuieli = findViewById(R.id.cheltuieliVal);
        cheltuieli.setText(Double.toString(totalCheltuieli));

        TextView balantaTV = findViewById(R.id.balantaVal);
        balantaTV.setText(Double.toString(balanta));
    }

    //initializare cu date hardcodate a listei
    private void initLV() {
        tranzactii.add(new Cheltuiala(20,"Mâncare","07.11.2019","RON","Cash"));
        tranzactii.add(new Cheltuiala(80,"Mâncare","07.11.2019","RON","Cash"));
        tranzactii.add(new Venit(1000,"Salariu","07.11.2019","RON","Cash"));
        tranzactii.add(new Cheltuiala(70,"Mâncare","07.11.2019","RON","Cash"));
        tranzactii.add(new Cheltuiala(30,"Mâncare","07.11.2019","RON","Card"));
        tranzactii.add(new Venit(100,"Împrumut","07.11.2019","RON","Cash"));
    }

    //popularea listview-ului
    private void populateLV() {
        if(tranzactii.size()==0){
            initLV();
        }
        TranzactieAdapter adapter = new TranzactieAdapter(this,R.layout.tranzactie_layout,tranzactii);
        lv.setAdapter(adapter);
        calcSum();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment()).commit();
        fab = findViewById(R.id.fab);
        fabVenit = findViewById(R.id.fab_venit);
        fabCheltuiala = findViewById(R.id.fab_cheltuiala);
        configNavigation();
    }

    //popularea listei se face doar dupa ce a fost primit fragmentul
    @Override
    protected void onStart() {
        super.onStart();
        lv = findViewById(R.id.listViewTranzactii);
        populateLV();
    }

    //configurarea meniului
    private void configNavigation() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    //metoda handler pentru evenimentul de click
    public void fabClicked(View v) {
        if(v.getId() == R.id.fab) {
            if (!isOpen) {
                openFabs();
            } else {
                closeFabs();
            }
        }
        else if (v.getId() == R.id.fab_cheltuiala) {
            Intent it = new Intent(getApplicationContext(),OperatiuneActivity.class);
            it.putExtra("tip","adaugaCheltuiala");
            closeFabs();
            startActivityForResult(it,requestCodeAdaugaCheltuiala);
        }
        else if(v.getId() == R.id.fab_venit){
            Intent it = new Intent(getApplicationContext(), OperatiuneActivity.class);
            it.putExtra("tip","adaugaVenit");
            closeFabs();
            startActivityForResult(it,requestCodeAdaugaVenit);
        }
    }
    //primirea si adaugarea tranzactiilor din formular in lista
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==requestCodeAdaugaVenit){
           if(resultCode == RESULT_OK) {
               //primesc venitul
               Venit venit = data.getParcelableExtra("venit");
               tranzactii.add(venit);
               lv.invalidate();
               populateLV();
           }
        }
        else if(requestCode == requestCodeAdaugaCheltuiala){
            if(resultCode == RESULT_OK) {
                //primesc cheltuiala
                Cheltuiala cheltuiala = data.getParcelableExtra("cheltuiala");
                tranzactii.add(cheltuiala);
                lv.invalidate();
                populateLV();
            }
        }
    }

    //handler pentru selectarea butoanelor din meniu
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment()).commit();
                onStart();
                showBtns();
                break;
            case R.id.nav_categ:
                Intent it = new Intent(getApplicationContext(),CategoriiActivity.class);
                startActivity(it);
                break;
            case R.id.nav_rate:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new RatingFragment()).commit();
                hideBtns();
                break;
                //pentru restul sectiunilor ce vor fi implementate in fazele urmatoare afisez un mesaj ce se afla in acelasi fragment
                default:
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new ChartFragment()).commit();
                    hideBtns();
                    break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addFeedback(View view) {
        float rating = ((RatingBar)findViewById(R.id.ratingApp)).getRating();
        EditText etDetalii = findViewById(R.id.etDetalii);
        String detalii = etDetalii.getText().toString();

        //luam toate datele
        StringBuilder sb = new StringBuilder();
        sb.append(rating).append(" - ").append(detalii);
        String textFinal = sb.toString();
        Toast.makeText(this, textFinal,Toast.LENGTH_LONG).show();
    }
}
