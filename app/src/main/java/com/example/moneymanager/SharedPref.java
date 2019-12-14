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

    public int loadCurrentUser() {
        int idUser = mySharedPreference.getInt("IdUser",0);
        return idUser;
    }


}
