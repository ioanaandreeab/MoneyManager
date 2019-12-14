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

import java.util.List;

public class TabvenFragment extends Fragment {
  List<String> categoriiVenituri;
  MoneyDatabase database;
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_tabven,container,false);
    database= Room.databaseBuilder(getContext(),MoneyDatabase.class,"trial14").allowMainThreadQueries().build();
    categoriiVenituri = database.getCategorieDAO().selectCategoriiVenituri();
    ListView listView = v.findViewById(R.id.LVCategVen);
    ArrayAdapter<String> categVenAdapter = new ArrayAdapter<>(v.getContext(),android.R.layout.simple_list_item_1,categoriiVenituri);
    listView.setAdapter(categVenAdapter);
    return  v;
  }
}
