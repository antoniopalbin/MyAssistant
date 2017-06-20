package com.master.antonio.myassistant;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

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
        Boolean comprobado= false;
        do{

            if (esperar == false) {
                esperar = true;

                    if(regla1){
                        Boolean Boolrule1 = CheckRule1();
                        if (Boolrule1) {
                            System.out.println("Regla1+++++++++++++++++++++++++++++");
                            enviarNotificacion("ajcr0007@gmail.com", "MyAssistan Notificación", "El usuario ha visitado frecuentemente el baño" );
                        }
                    }
                    if(regla2){
                        Boolean Boolrule2 = CheckRule2();
                        if (Boolrule2) {
                            System.out.println("Regla2+++++++++++++++++++++++++++++");
                            enviarNotificacion("ajcr0007@gmail.com", "MyAssistan Notificación", "EL usuario no ha realizado actividad" );
                        }
                    }
                    if(regla3){
                        Boolean Boolrule3 = CheckRule3();
                        if (Boolrule3) {
                            System.out.println("Regla3+++++++++++++++++++++++++++++");
                            enviarNotificacion("ajcr0007@gmail.com", "MyAssistan Notificación", "EL usuario no ha vistado la cocina" );
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
        }while(comprobado== false);
    }

    public Boolean CheckRule1(){
        return true;
    }

    public Boolean CheckRule2(){
        return true;
    }
    public Boolean CheckRule3(){
        return true;
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
