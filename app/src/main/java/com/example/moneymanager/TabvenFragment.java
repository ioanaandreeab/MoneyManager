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

public class TabvenFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tabven,container,false);
        ListView listView = v.findViewById(R.id.LVCategVen);
        ArrayAdapter<String> categVenAdapter = new ArrayAdapter<>(v.getContext(),android.R.layout.simple_list_item_1,v.getContext().getResources().getStringArray(R.array.elemVenituri));
        listView.setAdapter(categVenAdapter);
        return  v;
    }
}
