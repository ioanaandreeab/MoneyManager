package com.example.moneymanager;

import android.content.Context;
import  android.content.SharedPreferences;

public class SharedPref {
    SharedPreferences mySharedPreference;
    public  SharedPref(Context context) {
        mySharedPreference = context.getSharedPreferences("filename",Context.MODE_PRIVATE);
    }

    //salveaza starea pe care o are optiunea de dark mode -> true sau false
    public void setDarkMode(Boolean state){
        SharedPreferences.Editor editor = mySharedPreference.edit();
        editor.putBoolean("DarkMode",state);
        editor.commit();
    }

    //salveaza id-ul utilizatorului logat
    public void setUser(int idUser){
        SharedPreferences.Editor editor = mySharedPreference.edit();
        editor.putInt("IdUser", idUser);
        editor.commit();
    }

    //incarca starea de dark mode
    public Boolean loadDarkModeState() {
        Boolean state = mySharedPreference.getBoolean("DarkMode",false);
        return state;
    }

    //incarca id-ul utilizatorului logat
    public int loadCurrentUser() {
        int idUser = mySharedPreference.getInt("IdUser",0);
        return idUser;
    }

    //salveaza daca utilizatorul e logat
    public void setIsLogged(boolean state){
        SharedPreferences.Editor editor = mySharedPreference.edit();
        editor.putBoolean("Logat", state);
        editor.commit();
    }

    //incarca daca utilizatorul e logat
    //incarca starea de dark mode
    public Boolean loadIsLogged() {
        Boolean state = mySharedPreference.getBoolean("Logat",false);
        return state;
    }

}
