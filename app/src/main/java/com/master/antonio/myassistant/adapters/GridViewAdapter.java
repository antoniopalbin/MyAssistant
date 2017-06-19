package com.master.antonio.myassistant.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.master.antonio.myassistant.R;
import com.master.antonio.myassistant.models.Dispositivo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antonio on 19/06/2017.
 */

public class GridViewAdapter extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    private List<Dispositivo> data = new ArrayList();

    public GridViewAdapter(Context context, int layoutResourceId, List<Dispositivo> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = (TextView) row.findViewById(R.id.text);
            holder.image = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Dispositivo item = (Dispositivo) data.get(position);
        holder.imageTitle.setText(item.getMarca() + " - " + item.getModelo());

        Bitmap bitmap = BitmapFactory.decodeByteArray(item.getThumbnail(), 0, item.getThumbnail().length);
        holder.image.setImageBitmap(bitmap);
        return row;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }
}