package com.master.antonio.myassistant.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.master.antonio.myassistant.R;

/**
 * Created by Antonio on 15/06/2017.
 */

public class IconAdapter extends ArrayAdapter<String> {
    Context c;
    String[] estancias;
    int[] iconos;

    public IconAdapter(Context ctx, String[] estancias, int[] iconos){
        super(ctx, R.layout.estanciaitemlayout, estancias);

        this.c = ctx;
        this.estancias = estancias;
        this.iconos = iconos;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.estanciaitemlayout, null);
        }

        TextView estancia = (TextView) convertView.findViewById(R.id.txtEstancia);
        ImageView icono = (ImageView) convertView.findViewById(R.id.imgIconoEstancia);

        estancia.setText(estancias[position]);
        icono.setImageResource(iconos[position]);

        return convertView;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.estanciaitemlayout, null);
        }

        TextView estancia = (TextView) convertView.findViewById(R.id.txtEstancia);
        ImageView icono = (ImageView) convertView.findViewById(R.id.imgIconoEstancia);

        estancia.setText(estancias[position]);
        icono.setImageResource(iconos[position]);

        return convertView;
    }
}
