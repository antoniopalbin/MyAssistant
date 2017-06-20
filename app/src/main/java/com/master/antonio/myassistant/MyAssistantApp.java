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
            Aviso aviso = new Aviso(true, false, true, true, "apar0001@red.ujaen.es");
            aviso.insert().execute();

            //INSERT Beacons
            ArrayList<Beacon> beacons = new ArrayList<Beacon>();
            beacons.add(new Beacon("11:11:11:11:11:11", "Salón", "salon1", R.drawable.tv));
            beacons.add(new Beacon("22:22:22:22:22:22", "Cocina", "cocina1", R.drawable.cocina));
            beacons.add(new Beacon("33:33:33:33:33:33", "Baño", "baño1", R.drawable.bano));
            beacons.add(new Beacon("44:44:44:44:44:44", "Sala de Estar", "estar1", R.drawable.estar));
            beacons.add(new Beacon("55:55:55:55:55:55", "Lavadero", "lavadero", R.drawable.lavadero));
            beacons.add(new Beacon("66:66:66:66:66:66", "Dormitorio 1", "dormitorio1", R.drawable.dormitorio));
            beacons.add(new Beacon("77:77:77:77:77:77", "Dormitorio 2", "dormitorio2", R.drawable.dormitorio));
            beacons.add(new Beacon("88:88:88:88:88:88", "Dormitorio 3", "dormitorio3", R.drawable.dormitorio));
            beacons.add(new Beacon("99:99:99:99:99:99", "Despacho", "despacho1", R.drawable.despacho));
            beacons.add(new Beacon("00:00:00:00:00:00", "Cochera", "cochera1", R.drawable.garaje));
            beacons.add(new Beacon("10:10:10:10:10:10", "Gimnasio", "gimnasio1", R.drawable.gimnasio));

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
            Drawable drawable = getResources().getDrawable(R.drawable.device);
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] thumbnail = stream.toByteArray();

            drawable = getResources().getDrawable(R.drawable.device);
            bitmap = ((BitmapDrawable) drawable).getBitmap();
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] foto = stream.toByteArray();

            ArrayList<Dispositivo> dispositivos = new ArrayList<>();
            dispositivos.add(new Dispositivo("LG", "XX44DD", "YugLyGDbo1k", thumbnail, foto, "Manual de uso del dispositivo", beacons.get(0)));
            dispositivos.add(new Dispositivo("SAMSUNG", "JJJASDF", "y8HPKOQIo3s", thumbnail, foto, "Manual de uso del dispositivo", beacons.get(0)));
            dispositivos.add(new Dispositivo("SONY", "QWEREWQR33", "I8_tY_xA-IQ", thumbnail, foto, "Manual de uso del dispositivo", beacons.get(0)));
            dispositivos.add(new Dispositivo("LG", "NGSDGFN", "YugLyGDbo1k", thumbnail, foto, "Manual de uso del dispositivo", beacons.get(1)));
            dispositivos.add(new Dispositivo("BOSCH", "4QWRERT", "2Pp9DvWHZ70", thumbnail, foto, "Manual de uso del dispositivo", beacons.get(1)));
            dispositivos.add(new Dispositivo("TAURUS", "564645HDF", "WoLF-eYu9gk", thumbnail, foto, "Manual de uso del dispositivo", beacons.get(2)));
            dispositivos.add(new Dispositivo("MOULINEX", "BXCXVBBBCVX", "IlZOh4R_2nY", thumbnail, foto, "Manual de uso del dispositivo", beacons.get(2)));
            dispositivos.add(new Dispositivo("APPLE", "WETYRTWU", "B8UR7gftFF4", thumbnail, foto, "Manual de uso del dispositivo", beacons.get(3)));
            dispositivos.add(new Dispositivo("TOSHIBA", "MNVBVMN", "322XHZJtEjo", thumbnail, foto, "Manual de uso del dispositivo", beacons.get(3)));
            dispositivos.add(new Dispositivo("AIRIS", "75575", "eFgO_Obz8YU", thumbnail, foto, "Manual de uso del dispositivo", beacons.get(6)));
            dispositivos.add(new Dispositivo("WHIRPOOL", "WERTRTE", "4cdXedN-1cQ", thumbnail, foto, "Manual de uso del dispositivo", beacons.get(7)));
            Dispositivo.persist(dispositivos).ignoreNullValues().execute();

            Beacon.persist(beacons).execute();
        }
    }
}
