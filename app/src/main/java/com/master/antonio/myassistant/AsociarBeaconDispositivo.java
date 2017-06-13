package com.master.antonio.myassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.master.antonio.myassistant.fragments.YouTubeFragment;
import com.master.antonio.myassistant.wizard.FormWizard;

/**
 * Created by anton on 24/05/2017.
 */

public class AsociarBeaconDispositivo extends AppCompatActivity {
    public String IdBeacon;
    private FormWizard fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beacon_electrodomestico);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();

        if (bd != null) {
            IdBeacon = (String) bd.get("IdBeacon");
        }

        fragment = (FormWizard) getSupportFragmentManager().findFragmentById(R.id.form_wizard_fragment);
        fragment.setIdBeacon(IdBeacon);
    }

}
