package com.example.moneymanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabcheltFragment extends Fragment {
  List<String> categoriiCheltuieli;
  MoneyDatabase database;
  SharedPref sharedPref;
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    database= Room.databaseBuilder(getContext(),MoneyDatabase.class,"moneyManager").allowMainThreadQueries().build();
    sharedPref = new SharedPref(getContext());
    int user = sharedPref.loadCurrentUser();
    categoriiCheltuieli = database.getCategorieDAO().selectCategoriiCheltuieli(user);

    View v = inflater.inflate(R.layout.fragment_tabchelt,container,false);
    ListView listView = v.findViewById(R.id.LVCategChelt);

    ArrayAdapter<String> categCheltAdapter = new ArrayAdapter<>(v.getContext(),android.R.layout.simple_list_item_1,categoriiCheltuieli);
    listView.setAdapter(categCheltAdapter);
    return v;
  }
}
