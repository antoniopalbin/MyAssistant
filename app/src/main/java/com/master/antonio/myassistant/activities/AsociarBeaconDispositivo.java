package com.master.antonio.myassistant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.master.antonio.myassistant.R;
import com.master.antonio.myassistant.wizard.FormWizardAsociar;

/**
 * Created by anton on 24/05/2017.
 */

public class AsociarBeaconDispositivo extends AppCompatActivity {
    public String IdBeacon;
    private FormWizardAsociar fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beacon_electrodomestico);

        getSupportActionBar().setTitle("Nuevo dispositivo");

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();

        if (bd != null) {
            IdBeacon = (String) bd.get("IdBeacon");
        }

        fragment = (FormWizardAsociar) getSupportFragmentManager().findFragmentById(R.id.form_wizard_fragment);
        fragment.setIdBeacon(IdBeacon);
    }

}
