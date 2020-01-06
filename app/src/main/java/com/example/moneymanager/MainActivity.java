package com.example.moneymanager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteCursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.view.Menu;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    private static final String CSV_SEPARATOR = ",";
    private FloatingActionButton fab;
    private FloatingActionButton fabVenit;
    private FloatingActionButton fabCheltuiala;
    private boolean isOpen = false;
    private int requestCodeAdaugaVenit = 333;
    private int requestCodeAdaugaCheltuiala = 444;
    private int requestCodeEdit = 555;
    private List<Tranzactie> tranzactii = new ArrayList<>();
    ListView lv;
    SharedPref sharedPref;
    private MoneyDatabase database;
    private int userId;
    Dialog dialogExport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //pt dialog
        dialogExport = new Dialog(this);

        //initializarea bazei de date la onCreate
        database = Room.databaseBuilder(this,MoneyDatabase.class,"moneyManager").allowMainThreadQueries().build();

        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment()).commit();
        fab = findViewById(R.id.fab);
        fabVenit = findViewById(R.id.fab_venit);
        fabCheltuiala = findViewById(R.id.fab_cheltuiala);
        configNavigation();
        userId = sharedPref.loadCurrentUser();

    }

    //popularea listei se face doar dupa ce a fost primit fragmentul
    @Override
    protected void onStart() {
        super.onStart();
        lv = findViewById(R.id.listViewTranzactii);
        populateLV();

        //la longclick se sterge elementul
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Tranzactie tranzactie = tranzactii.get(i);
                database.getTranzactieDAO().deleteTranzactie(tranzactie);
                Toast.makeText(getApplicationContext(),"Tranzacție ștearsă",Toast.LENGTH_LONG).show();
                lv.invalidate();
                populateLV();
                return true;
            }
        });

        //la click se editeaza
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(getApplicationContext(),OperatiuneActivity.class);
                it.putExtra("edit",tranzactii.get(i)); //se trimite elementul de pe pozitia selectata
                it.putExtra("pozitie",i);
                startActivityForResult(it,requestCodeEdit);
            }
        });

        //set greeting
        String userName = database.getUserDAO().findUserName(userId);
        TextView greeting = findViewById(R.id.greeting);
        greeting.setText("Salut, "+userName);
    }

    //primirea, adaugarea & editarea tranzactiilor din formular in lista
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==requestCodeAdaugaVenit){
            if(resultCode == RESULT_OK) {
                //primesc venitul
                Tranzactie venit = data.getParcelableExtra("venit");
                long id = database.getTranzactieDAO().insertTranzactie(venit);
                int idTranz = (int)id;
                venit.setId(idTranz);
                lv.invalidate();
                populateLV();
                Toast.makeText(getApplicationContext(),"Ați introdus un venit",Toast.LENGTH_LONG).show();
            }
        }
        else if(requestCode == requestCodeAdaugaCheltuiala){
            if(resultCode == RESULT_OK) {
                //primesc cheltuiala
                Tranzactie cheltuiala = data.getParcelableExtra("cheltuiala");
                long id = database.getTranzactieDAO().insertTranzactie(cheltuiala);
                int idTranz = (int)id;
                cheltuiala.setId(idTranz);
                lv.invalidate();
                populateLV();
                Toast.makeText(getApplicationContext(),"Ați introdus o cheltuială",Toast.LENGTH_LONG).show();
            }
        }
        else if(requestCode == requestCodeEdit){
            if(resultCode == RESULT_OK) {
                Tranzactie tranzactie = data.getParcelableExtra("editat");
                database.getTranzactieDAO().updateTranzactie(tranzactie);
                lv.invalidate();
                populateLV();
                Toast.makeText(getApplicationContext(),"Ați editat o tranzacție",Toast.LENGTH_LONG).show();
            }
        }
    }

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
        double totalVenituri;
        double totalCheltuieli;
        double balanta;
        totalVenituri = database.getTranzactieDAO().selectSumaVenituri(userId);
        totalCheltuieli = database.getTranzactieDAO().selectSumaCheltuieli(userId);
        balanta = totalVenituri - totalCheltuieli;
        TextView venituri = findViewById(R.id.venitVal);
        venituri.setText(Double.toString(totalVenituri));

        TextView cheltuieli = findViewById(R.id.cheltuieliVal);
        cheltuieli.setText(Double.toString(totalCheltuieli));

        TextView balantaTV = findViewById(R.id.balantaVal);
        balantaTV.setText(Double.toString(balanta));
    }

    //popularea listview-ului & a statisticilor
    private void populateLV() {
        tranzactii = database.getTranzactieDAO().cautaTranzactiiDupaUserId(userId);
        calcSum();
        TranzactieAdapter adapter = new TranzactieAdapter(this,R.layout.tranzactie_layout,tranzactii);
        lv.setAdapter(adapter);
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

    private void saveToTextFile(List<Tranzactie> tranzactii) {
        try {
            FileOutputStream fileOutputStream = openFileOutput("raport.txt", Context.MODE_PRIVATE);
            DataOutputStream out = new DataOutputStream(fileOutputStream);
            for(Tranzactie tranzactie: tranzactii){
                if(tranzactie.isEsteAditiva()==true){
                    out.write("Venit: ".getBytes());
                }
                else if(tranzactie.isEsteAditiva() ==false){
                    out.write("Cheltuială: ".getBytes());
                }
                out.write(tranzactie.toString().getBytes());
                out.write("\n".getBytes());
            }
            out.close();
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToCsvFile(List<Tranzactie> tranzactii){
        try {
            FileOutputStream fileOutputStream = openFileOutput("raport.csv", Context.MODE_PRIVATE);
            DataOutputStream out = new DataOutputStream(fileOutputStream);
            for(Tranzactie tranzactie: tranzactii){
                out.write('"');
                out.write(String.valueOf(tranzactie.getId()).getBytes());
                out.write('"');
                out.write(CSV_SEPARATOR.getBytes());
                out.write('"');
                out.write(tranzactie.getCategorie().getBytes());
                out.write('"');
                out.write(CSV_SEPARATOR.getBytes());
                out.write('"');
                out.write(tranzactie.getData().getBytes());
                out.write('"');
                out.write(CSV_SEPARATOR.getBytes());
                out.write('"');
                out.write(tranzactie.getNatura().getBytes());
                out.write('"');
                out.write(CSV_SEPARATOR.getBytes());
                out.write('"');
                out.write(tranzactie.isEsteAditiva() ? "true".getBytes() : "false".getBytes());
                out.write('"');
                out.write("\n".getBytes());
            }
            out.close();
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void exportHandler(){
        TextView close = dialogExport.findViewById(R.id.txtclose);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogExport.dismiss();
            }
        });
        Button closebtn = dialogExport.findViewById(R.id.exportCancel);
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogExport.dismiss();
            }
        });
        Button okBtn = dialogExport.findViewById(R.id.exportOK);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioGroup optiuni = dialogExport.findViewById(R.id.optiuneExport);
                RadioButton optiuneSelectata = dialogExport.findViewById(optiuni.getCheckedRadioButtonId());
                String optiuneSelectataText = optiuneSelectata.getText().toString();
                if (optiuneSelectataText.equals(".txt")){
                    saveToTextFile(tranzactii);
                    Toast.makeText(getApplicationContext(),"Raportul a fost salvat cu succes într-un fișier .txt",Toast.LENGTH_LONG).show();
                    dialogExport.dismiss();
                }
                else if (optiuneSelectataText.equals(".csv")){
                    saveToCsvFile(tranzactii);
                    Toast.makeText(getApplicationContext(),"Raportul a fost salvat cu succes într-un fișier .csv",Toast.LENGTH_LONG).show();
                    dialogExport.dismiss();
                }

            }
        });
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
            case R.id.nav_convertor:
                Intent intent = new Intent(getApplicationContext(),ConvertorActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_export:
                //prelucrarile de la export
                dialogExport.setContentView(R.layout.popup_export);
                exportHandler();
                dialogExport.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogExport.show();
                break;
            case R.id.nav_logout:
                Toast.makeText(getApplicationContext(),"V-ați delogat",Toast.LENGTH_LONG).show();
                sharedPref.setIsLogged(false);
                Intent intentLogin = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intentLogin);
                break;
            case R.id.nav_deleteUser:
                sharedPref.setIsLogged(false);
                User user = database.getUserDAO().findUserById(userId);
                database.getUserDAO().deleteUser(user);
                Toast.makeText(getApplicationContext(),"Ați renunțat la cont",Toast.LENGTH_LONG).show();
                intentLogin = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intentLogin);
                break;
            case R.id.nav_stats:
                Intent intentStats = new Intent(getApplicationContext(), StatsActivity.class);
                startActivity(intentStats);
                break;
                default:
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment()).commit();
                    onStart();
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

        User user = database.getUserDAO().findUserById(userId);
        user.setRating(rating);
        user.setRating_text(detalii);
        database.getUserDAO().updateUser(user);
        Toast.makeText(this,"Feedback-ul dumneavoastră a fost înregistrat! Mulțumim!",Toast.LENGTH_LONG).show();
    }
}
