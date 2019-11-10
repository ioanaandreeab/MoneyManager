package com.example.moneymanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class CategoriiActivity extends AppCompatActivity {

    private ViewPager myViewPager;
    private int requestCodeAddCategVen = 323;
    private int requestCodeAddCategChelt = 434;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorii);
        //bara de back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //setup viewpager cu pageAdapter
        myViewPager = findViewById(R.id.viewpager);
        setupViewPager(myViewPager);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(myViewPager);
    }

    //in functie de tab-ul selectat, afiseaza fragmentul
    private void setupViewPager(ViewPager viewPager) {
        PageAdapter adapter = new PageAdapter(getSupportFragmentManager());
        adapter.addFragment(new TabcheltFragment(), "Cheltuieli");
        adapter.addFragment(new TabvenFragment(), "Venituri");
        viewPager.setAdapter(adapter);
    }

    //handler pentru click pe buton
    public void addCateg(View view) {
        //verificam daca fragmentul afisat era cel de cheltuieli ori de venituri
        if(myViewPager.getCurrentItem() == 0) {
            Intent it = new Intent(this, AddCategActivity.class);
            it.putExtra("categorie","cheltuiala");
            startActivityForResult(it,requestCodeAddCategChelt);
        }
        if(myViewPager.getCurrentItem() == 1) {
            Intent it = new Intent(this, AddCategActivity.class);
            it.putExtra("categorie","venit");
            startActivityForResult(it,requestCodeAddCategVen);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //rezultatele vor fi adaugate in lista de categorii din formular atunci cand se vor folosi BD
        if (requestCode == requestCodeAddCategChelt){
            if(resultCode == RESULT_OK) {
                String categorieNoua = data.getStringExtra("numeCategorie");
                Toast.makeText(this,categorieNoua, Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == requestCodeAddCategVen){
            if(resultCode == RESULT_OK) {
                String categorieNoua = data.getStringExtra("numeCategorie");
                Toast.makeText(this, categorieNoua,Toast.LENGTH_LONG).show();
            }
        }
    }
}
