package com.master.antonio.myassistant;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.master.antonio.myassistant.models.Actividad;
import com.master.antonio.myassistant.models.Aviso;
import com.master.antonio.myassistant.models.Beacon;
import com.master.antonio.myassistant.models.Dispositivo;
import com.siimkinks.sqlitemagic.SqliteMagic;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;

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
            //INSERT Aviso
            Aviso aviso = new Aviso("1", true, false, true, true, "apar0001@red.ujaen.es,ajcr0007@red.ujaen.es");
            aviso.insert().execute();

            //INSERT Beacons
            ArrayList<Beacon> beacons = new ArrayList<Beacon>();
            beacons.add(new Beacon("1111111111", "Salón", "salon1", R.mipmap.tv));
            beacons.add(new Beacon("2222222222", "Cocina", "cocina1", R.mipmap.cocina));
            beacons.add(new Beacon("3333333333", "Baño", "baño1", R.mipmap.bano));
            beacons.add(new Beacon("4444444444", "Baño", "baño2", R.mipmap.bano));
            beacons.add(new Beacon("5555555555", "Estar", "estar1", R.mipmap.tv));
            beacons.add(new Beacon("6666666666", "Cocina", "cocina2", R.mipmap.cocina));
            beacons.add(new Beacon("7777777777", "Lavadero", "lavadero1", R.mipmap.cocina));
            beacons.add(new Beacon("8888888888", "Dormitorio", "dormitorio1", R.mipmap.dormitorio));
            beacons.add(new Beacon("9999999999", "Dormitorio", "dormitorio2", R.mipmap.dormitorio));
            beacons.add(new Beacon("0000000000", "Dormitorio", "dormitorio3", R.mipmap.dormitorio));
            beacons.add(new Beacon("1010101010", "Estudio", "estudio1", R.mipmap.dormitorio));
            beacons.add(new Beacon("2020202020", "Despacho", "despacho1", R.mipmap.dormitorio));
            beacons.add(new Beacon("3030303030", "Cochera", "cochera1", R.mipmap.dormitorio));
            beacons.add(new Beacon("4040404040", "Gimnasio", "gimnasio1", R.mipmap.dormitorio));

            //INSERT Actividades
            ArrayList<Actividad> actividades = new ArrayList<>();
            actividades.add(new Actividad(new Date().getTime(), beacons.get(0)));
            actividades.add(new Actividad(new Date().getTime(), beacons.get(4)));
            actividades.add(new Actividad(new Date().getTime(), beacons.get(3)));
            actividades.add(new Actividad(new Date().getTime(), beacons.get(0)));
            actividades.add(new Actividad(new Date().getTime(), beacons.get(5)));
            actividades.add(new Actividad(new Date().getTime(), beacons.get(6)));
            actividades.add(new Actividad(new Date().getTime(), beacons.get(7)));
            actividades.add(new Actividad(new Date().getTime(), beacons.get(6)));
            actividades.add(new Actividad(new Date().getTime(), beacons.get(5)));
            actividades.add(new Actividad(new Date().getTime(), beacons.get(2)));
            actividades.add(new Actividad(new Date().getTime(), beacons.get(3)));
            actividades.add(new Actividad(new Date().getTime(), beacons.get(2)));
            actividades.add(new Actividad(new Date().getTime(), beacons.get(0)));
            Actividad.persist(actividades).execute();

            //INSERT Dispositivos
            Drawable drawable = getResources().getDrawable(R.mipmap.dormitorio);
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] thumbnail = stream.toByteArray();

            drawable = getResources().getDrawable(R.drawable.microondas);
            bitmap = ((BitmapDrawable) drawable).getBitmap();
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] foto = stream.toByteArray();

            ArrayList<Dispositivo> dispositivos = new ArrayList<>();
            dispositivos.add(new Dispositivo("1", "LG", "XX44DD", "TPzWuOcmIIg", thumbnail, foto, "Este sería el manual", beacons.get(0)));
            dispositivos.add(new Dispositivo("2", "SAMSUNG", "JJJASDF", "TPzWuOcmIIg", thumbnail, foto, "Este sería el manual", beacons.get(0)));
            dispositivos.add(new Dispositivo("3", "SONY", "QWEREWQR33", "TPzWuOcmIIg", thumbnail, foto, "Este sería el manual", beacons.get(0)));
            dispositivos.add(new Dispositivo("4", "LG", "NGSDGFN", "TPzWuOcmIIg", thumbnail, foto, "Este sería el manual", beacons.get(1)));
            dispositivos.add(new Dispositivo("5", "BOSCH", "4QWRERT", "TPzWuOcmIIg", thumbnail, foto, "Este sería el manual", beacons.get(1)));
            dispositivos.add(new Dispositivo("6", "TAURUS", "564645HDF", "TPzWuOcmIIg", thumbnail, foto, "Este sería el manual", beacons.get(2)));
            dispositivos.add(new Dispositivo("7", "MOULINEX", "BXCXVBBBCVX", "TPzWuOcmIIg", thumbnail, foto, "Este sería el manual", beacons.get(2)));
            dispositivos.add(new Dispositivo("8", "APPLE", "WETYRTWU", "TPzWuOcmIIg", thumbnail, foto, "Este sería el manual", beacons.get(3)));
            dispositivos.add(new Dispositivo("9", "TOSHIBA", "MNVBVMN", "TPzWuOcmIIg", thumbnail, foto, "Este sería el manual", beacons.get(3)));
            dispositivos.add(new Dispositivo("10", "AIRIS", "75575", "TPzWuOcmIIg", thumbnail, foto, "Este sería el manual", beacons.get(6)));
            dispositivos.add(new Dispositivo("11", "WHIRPOOL", "WERTRTE", "TPzWuOcmIIg", thumbnail, foto, "Este sería el manual", beacons.get(7)));
            Dispositivo.persist(dispositivos).ignoreNullValues().execute();

            Beacon.persist(beacons).execute();
        }
    }
}
