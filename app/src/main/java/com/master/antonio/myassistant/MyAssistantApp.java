package com.master.antonio.myassistant;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.master.antonio.myassistant.models.Beacon;
import com.siimkinks.sqlitemagic.SqliteMagic;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by Antonio on 27/05/2017.
 */

public final class MyAssistantApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SqliteMagic.init(this);
        populateDB();
    }

    private void populateDB() {
        File database = getApplicationContext().getDatabasePath("MyAssistant.db");
        if (!database.exists()) {
            //INSERT Beacons
            Drawable drawable = getResources().getDrawable(R.mipmap.beacon);
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] icono = stream.toByteArray();

            ArrayList<Beacon> beacons = new ArrayList<Beacon>();
            beacons.add(new Beacon("1111111111", "Salón", "salon1", icono));
            beacons.add(new Beacon("2222222222", "Cocina", "cocina1", icono));
            beacons.add(new Beacon("3333333333", "Baño", "baño1", icono));
            beacons.add(new Beacon("4444444444", "Baño", "baño2", icono));
            beacons.add(new Beacon("5555555555", "Estar", "estar1", icono));
            beacons.add(new Beacon("6666666666", "Cocina", "cocina2", icono));
            beacons.add(new Beacon("7777777777", "Lavadero", "lavadero1", icono));
            beacons.add(new Beacon("8888888888", "Dormitorio", "dormitorio1", icono));
            beacons.add(new Beacon("9999999999", "Dormitorio", "dormitorio2", icono));
            beacons.add(new Beacon("0000000000", "Dormitorio", "dormitorio3", icono));
            beacons.add(new Beacon("1010101010", "Estudio", "estudio1", icono));
            beacons.add(new Beacon("2020202020", "Despacho", "despacho1", icono));
            beacons.add(new Beacon("3030303030", "Cochera", "cochera1", icono));
            beacons.add(new Beacon("4040404040", "Gimnasio", "gimnasio1", icono));

            for (Beacon b : beacons) {
                b.insert().execute();
            }
        }
    }
}
