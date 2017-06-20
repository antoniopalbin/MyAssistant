package com.master.antonio.myassistant;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.master.antonio.myassistant.models.Actividad;
import com.master.antonio.myassistant.models.Beacon;
import com.siimkinks.sqlitemagic.Select;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.siimkinks.sqlitemagic.ActividadTable.ACTIVIDAD;
import static com.siimkinks.sqlitemagic.BeaconTable.BEACON;

/**
 * Created by anton on 20/06/2017.
 */

public class CheckRules extends Service {
    Boolean esperar = false;
    static final int UPDATE_INTERVAL = 60000;
    private Timer timer = new Timer();

    Boolean regla1 = false;
    Boolean regla2 = false;
    Boolean regla3 = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bd = intent.getExtras();
        if (bd != null) {
            regla1 = (Boolean) bd.get("Regla1");
            regla2 = (Boolean) bd.get("Regla2");
            regla3 = (Boolean) bd.get("Regla3");
        }
        doSomethingRepeatedly();
        return START_STICKY;
    }

    private void doSomethingRepeatedly() {
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                check();
            }

        }, 0, UPDATE_INTERVAL);
    }

    private void check() {
        Boolean comprobado = false;
        do {
            if (esperar == false) {
                esperar = true;
                if (regla1) {
                    Boolean Boolrule1 = CheckRule1();
                    if (Boolrule1) {
                        System.out.println("Regla1+++++++++++++++++++++++++++++");
                        enviarNotificacion("ajcr0007@gmail.com", "MyAssistan Notificación", "El usuario ha visitado frecuentemente el baño");
                    }
                }
                if (regla2) {
                    Boolean Boolrule2 = CheckRule2();
                    if (Boolrule2) {
                        System.out.println("Regla2+++++++++++++++++++++++++++++");
                        enviarNotificacion("ajcr0007@gmail.com", "MyAssistan Notificación", "EL usuario no ha realizado actividad");
                    }
                }
                if (regla3) {
                    Boolean Boolrule3 = CheckRule3();
                    if (Boolrule3) {
                        System.out.println("Regla3+++++++++++++++++++++++++++++");
                        enviarNotificacion("ajcr0007@gmail.com", "MyAssistan Notificación", "EL usuario no ha vistado la cocina");
                    }
                }
                esperar = false;
                comprobado = true;
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while (comprobado == false);
    }

    public Boolean CheckRule1() {
        //comprueba si visita el baño 3 veces o más en la última hora
        Date hasta = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(hasta);
        calendar.add(Calendar.HOUR, -1);
        Date desde = calendar.getTime();

        List<Beacon> beacons = Select.from(BEACON).where(BEACON.ICONO.is(R.drawable.bano)).execute();

        List<Actividad> actividades = new ArrayList<>();
        for (Beacon b : beacons) {
            List<Actividad> auxActividades = Select.from(ACTIVIDAD).where(ACTIVIDAD.TIMESTAMP.greaterThan(desde.getTime()).and(ACTIVIDAD.TIMESTAMP.lessThan(hasta.getTime())).and(ACTIVIDAD.BEACON.is(b))).orderBy(ACTIVIDAD.TIMESTAMP.asc()).queryDeep().execute();
            if (auxActividades != null) {
                actividades.addAll(auxActividades);
            }
        }

        if (actividades.size() >= 3)
            return true;
        else
            return false;
    }

    public Boolean CheckRule2() {
        //comprueba si no visita nada en las últimas 3 horas
        Date hasta = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(hasta);
        calendar.add(Calendar.HOUR, -3);
        Date desde = calendar.getTime();

        List<Actividad> actividades = Select.from(ACTIVIDAD).where(ACTIVIDAD.TIMESTAMP.greaterThan(desde.getTime()).and(ACTIVIDAD.TIMESTAMP.lessThan(hasta.getTime()))).orderBy(ACTIVIDAD.TIMESTAMP.asc()).queryDeep().execute();
        if (actividades == null) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean CheckRule3() {
        //comprueba si visita la cocina 3 veces o más en la última hora
        Date hasta = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(hasta);
        calendar.add(Calendar.HOUR, -1);
        Date desde = calendar.getTime();

        List<Beacon> beacons = Select.from(BEACON).where(BEACON.ICONO.is(R.drawable.cocina)).execute();

        List<Actividad> actividades = new ArrayList<>();
        for (Beacon b : beacons) {
            List<Actividad> auxActividades = Select.from(ACTIVIDAD).where(ACTIVIDAD.TIMESTAMP.greaterThan(desde.getTime()).and(ACTIVIDAD.TIMESTAMP.lessThan(hasta.getTime())).and(ACTIVIDAD.BEACON.is(b))).orderBy(ACTIVIDAD.TIMESTAMP.asc()).queryDeep().execute();
            if (auxActividades != null) {
                actividades.addAll(auxActividades);
            }
        }

        if (actividades.size() >= 3)
            return true;
        else
            return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }

    private void enviarNotificacion(String Email, String Subject, String Contenido) {
        SendMail sm = new SendMail(this, Email, Subject, Contenido);

        //Executing sendmail to send email
        sm.execute();
    }
}
