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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    DrawerLayout drawerLayout;
    private FloatingActionButton fab;
    private FloatingActionButton fabVenit;
    private FloatingActionButton fabCheltuiala;
    private boolean isOpen = false;
    private int requestCodeAdaugaVenit = 333;
    private int requestCodeAdaugaCheltuiala = 444;
    private List<Object> tranzactii = new ArrayList<>();


    //functii pentru expandarea & colapsarea butoanelor
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

    private void initLV() {

        tranzactii.add(new Cheltuiala(20,"Mâncare","07.11.2019","RON","Cash"));
        tranzactii.add(new Cheltuiala(80,"Mâncare","07.11.2019","RON","Cash"));
        tranzactii.add(new Venit(1000,"Salariu","07.11.2019","RON","Cash"));
        tranzactii.add(new Cheltuiala(70,"Mâncare","07.11.2019","RON","Cash"));
        tranzactii.add(new Cheltuiala(30,"Mâncare","07.11.2019","RON","Card"));
        tranzactii.add(new Venit(100,"Împrumut","07.11.2019","RON","Cash"));
    }

    private void populateLV() {
        if(tranzactii.size()==0){
            initLV();
        }
        ListView lv = findViewById(R.id.listViewTranzactii);
        lv.invalidate();
        TranzactieAdapter adapter = new TranzactieAdapter(this,R.layout.tranzactie_layout,tranzactii);
        lv.setAdapter(adapter);
        calcSum();
    }

    private void showFabs(){
        isOpen = true;
        fabVenit.animate().translationY(-getResources().getDimension(R.dimen.fab_55));
        fabCheltuiala.animate().translationY(-getResources().getDimension(R.dimen.fab_105));
    }

    private void closeFabs() {
        isOpen = false;
        fabVenit.animate().translationY(0);
        fabCheltuiala.animate().translationY(0);
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

    @Override
    protected void onStart() {
        super.onStart();
        //initLV();
        populateLV();
    }

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

    public void fabClicked(View v) {
        if(v.getId() == R.id.fab) {
            if (!isOpen) {
                showFabs();
            } else {
                closeFabs();
            }
        }
        else if (v.getId() == R.id.fab_cheltuiala) {
            Intent it = new Intent(getApplicationContext(),OperatiuneActivity.class);
            it.putExtra("tip","adaugaCheltuiala");
            startActivityForResult(it,requestCodeAdaugaCheltuiala);
        }
        else if(v.getId() == R.id.fab_venit){
            Intent it = new Intent(getApplicationContext(), OperatiuneActivity.class);
            it.putExtra("tip","adaugaVenit");
            startActivityForResult(it,requestCodeAdaugaVenit);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==requestCodeAdaugaVenit){
           if(resultCode == RESULT_OK) {
               //primesc venitul
               Intent it = getIntent();
               Venit venit = data.getParcelableExtra("venit");
               //Toast.makeText(this, venit.toString(), Toast.LENGTH_LONG).show();
               tranzactii.add(venit);
               populateLV();
           }
        }
        else if(requestCode == requestCodeAdaugaCheltuiala){
            if(resultCode == RESULT_OK) {
                //primesc cheltuiala
                Intent it = getIntent();
                Cheltuiala cheltuiala = data.getParcelableExtra("cheltuiala");
                //Toast.makeText(this, cheltuiala.toString(), Toast.LENGTH_LONG).show();
                tranzactii.add(cheltuiala);
                populateLV();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment()).commit();
                onStart();
                showBtns();
                break;
            case R.id.nav_chart:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new ChartFragment()).commit();
                hideBtns();
                break;
            case R.id.nav_categ:
                Intent it = new Intent(getApplicationContext(),CategoriiActivity.class);
                startActivity(it);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
