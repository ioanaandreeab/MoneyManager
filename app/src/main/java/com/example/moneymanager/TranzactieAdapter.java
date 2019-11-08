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

public class TranzactieAdapter extends ArrayAdapter<Object> {
    private int resursaID;
    public TranzactieAdapter(@NonNull Context context, int resource, @NonNull List<Object> objects) {
        super(context, resource, objects);
        resursaID = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Object o = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View v = inflater.inflate(resursaID,null);
        //am obtinut view-ul si il populez
        TextView categorieTranzactie = v.findViewById(R.id.categorieTranz);
        TextView dataTranzactie = v.findViewById(R.id.dataTranz);
        TextView valTranzactie = v.findViewById(R.id.valoareTranz);
        TextView natTranzactie = v.findViewById(R.id.naturaTranz);
        ImageView imgTranzactie = v.findViewById(R.id.imgTranz);

        if(o instanceof Cheltuiala) {
            categorieTranzactie.setText(((Cheltuiala) o).getCategorie());
            dataTranzactie.setText(((Cheltuiala) o).getData());
            valTranzactie.setText(Double.toString(((Cheltuiala) o).getValoare()));
            natTranzactie.setText(((Cheltuiala) o).getNaturaTranzactie());
            imgTranzactie.setImageResource(R.drawable.ic_remove_circle_red);
        }
        else if (o instanceof Venit) {
            categorieTranzactie.setText(((Venit) o).getCategorie());
            dataTranzactie.setText(((Venit) o).getData());
            valTranzactie.setText(Double.toString(((Venit) o).getValoare()));
            natTranzactie.setText(((Venit) o).getNaturaTranzactie());
            imgTranzactie.setImageResource(R.drawable.ic_add_circle_green);
        }
        return v;
    }
}

