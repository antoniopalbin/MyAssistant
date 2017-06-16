package com.master.antonio.myassistant.wizard;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.master.antonio.myassistant.R;
import com.master.antonio.myassistant.models.Beacon;
import com.master.antonio.myassistant.models.Dispositivo;
import com.siimkinks.sqlitemagic.Select;

import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.persistence.ContextVariable;

import java.util.ArrayList;

import static com.siimkinks.sqlitemagic.BeaconTable.BEACON;

/**
 * Created by anton on 28/05/2017.
 */

public class AsociarStep4 extends WizardStep {

    @ContextVariable
    private String Marca;
    @ContextVariable
    private String Modelo;
    @ContextVariable
    private String KeyVideo;
    @ContextVariable
    private byte[] img;
    @ContextVariable
    private String Manual;
    @ContextVariable
    private byte[] thumbnail;
    @ContextVariable
    private String IdBeacon;


    SeekBar progress;
    EditText InputManual;

    //You must have an empty constructor for every step
    public AsociarStep4() {
    }

    //Set your layout here
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.new_electrodomestico4, container, false);

        progress = (SeekBar) v.findViewById(R.id.seekBar);
        progress.setProgress(100);
        progress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        InputManual = (EditText) v.findViewById(R.id.InputManual);

        return v;
    }

    @Override
    public void onExit(int exitCode) {
        switch (exitCode) {
            case WizardStep.EXIT_NEXT:
                bindDataFields();
                break;
            case WizardStep.EXIT_PREVIOUS:
                //Do nothing...
                break;
        }
    }

    private void bindDataFields() {
        Manual = InputManual.getText().toString();

        //realizamos la inserccion del dispositivo
        Beacon aux = Select.from(BEACON).where(BEACON.ID_BEACON.is(IdBeacon)).takeFirst().execute();
        ArrayList<Dispositivo> dispositivos = new ArrayList<>();
        dispositivos.add(new Dispositivo("30", Marca, Modelo, KeyVideo, img, img, Manual, aux));
        Dispositivo.persist(dispositivos).ignoreNullValues().execute();
    }
}
