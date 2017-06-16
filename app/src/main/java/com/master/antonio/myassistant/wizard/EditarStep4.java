package com.master.antonio.myassistant.wizard;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;

import com.master.antonio.myassistant.R;
import com.master.antonio.myassistant.models.Beacon;
import com.master.antonio.myassistant.models.Dispositivo;
import com.siimkinks.sqlitemagic.Delete;
import com.siimkinks.sqlitemagic.Select;
import com.siimkinks.sqlitemagic.Update;

import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.persistence.ContextVariable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import static com.siimkinks.sqlitemagic.BeaconTable.BEACON;
import static com.siimkinks.sqlitemagic.DispositivoTable.DISPOSITIVO;

/**
 * Created by anton on 28/05/2017.
 */

public class EditarStep4 extends WizardStep {

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
    private long IdDispositivo;
    @ContextVariable
    private String  IdBeacon;

    SeekBar progress;
    EditText InputManual;

    //You must have an empty constructor for every step
    public EditarStep4() {
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

        InputManual.setText(Manual);

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

        //Modificamos el dispositivo

        Beacon aux = Select.from(BEACON).where(BEACON.ID_BEACON.is(IdBeacon)).takeFirst().execute();


        Delete.from(DISPOSITIVO).where(DISPOSITIVO.ID.is(IdDispositivo)).execute();

        ArrayList<Dispositivo> dispositivos = new ArrayList<>();
        dispositivos.add(new Dispositivo("30", Marca, Modelo, KeyVideo, img, img, Manual, aux));
        Dispositivo.persist(dispositivos).ignoreNullValues().execute();

        /*Update.table(DISPOSITIVO)
                .set(DISPOSITIVO.MARCA, Marca)
                .set(DISPOSITIVO.MODELO, Modelo)
                .set(DISPOSITIVO.ID_YOUTUBE, KeyVideo)
                .set(DISPOSITIVO.FOTO, img)
                .set(DISPOSITIVO.THUMBNAIL, img)
                .set(DISPOSITIVO.MANUAL, Manual)
                .where(DISPOSITIVO.ID.is(IdDispositivo))
                .execute();*/

    }
}
