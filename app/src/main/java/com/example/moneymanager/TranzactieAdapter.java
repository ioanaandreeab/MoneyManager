package com.example.moneymanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class TranzactieAdapter extends ArrayAdapter<Tranzactie> {
    private int resursaID;
    public TranzactieAdapter(@NonNull Context context, int resource, @NonNull List<Tranzactie> objects) {
        super(context, resource, objects);
        resursaID = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Tranzactie tranzactie = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View v = inflater.inflate(resursaID,null);
        //am obtinut view-ul si il populez
        TextView categorieTranzactie = v.findViewById(R.id.categorieTranz);
        TextView dataTranzactie = v.findViewById(R.id.dataTranz);
        TextView valTranzactie = v.findViewById(R.id.valoareTranz);
        TextView natTranzactie = v.findViewById(R.id.naturaTranz);
        ImageView imgTranzactie = v.findViewById(R.id.imgTranz);

        //punem campurile
        categorieTranzactie.setText(tranzactie.getCategorie());
        dataTranzactie.setText(tranzactie.getData());
        valTranzactie.setText(Double.toString(tranzactie.getValoare()));
        natTranzactie.setText(tranzactie.getNatura());

        //prelucrari in functie de tipul tranzactiei
        if(tranzactie.isEsteAditiva() == false)
            imgTranzactie.setImageResource(R.drawable.ic_remove_circle_red);
        else
            imgTranzactie.setImageResource(R.drawable.ic_add_circle_green);
        return v;
    }
}

