package com.master.antonio.myassistant.wizard;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.master.antonio.myassistant.R;
import com.master.antonio.myassistant.models.Beacon;

import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.persistence.ContextVariable;

/**
 * Created by anton on 24/05/2017.
 */

public class Step1 extends WizardStep {

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
    EditText InputMarca;
    EditText InputModelo;

    //You must have an empty constructor for every step
    public Step1() {

    }

    //Set your layout here
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.new_electrodomestico1, container, false);

        progress = (SeekBar) v.findViewById(R.id.seekBar);
        progress.setProgress(25);

        progress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        InputMarca = (EditText) v.findViewById(R.id.InputMarca);
        InputModelo = (EditText) v.findViewById(R.id.InputModelo);

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

        Marca = InputMarca.getText().toString();
        Modelo = InputModelo.getText().toString();

        System.out.println("Marca "+ Marca);
        System.out.println("Modelo "+ Modelo);

    }
}