package com.master.antonio.myassistant.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.master.antonio.myassistant.R;
import com.master.antonio.myassistant.wizard.FormWizardEditar;
import com.siimkinks.sqlitemagic.Delete;

import static com.siimkinks.sqlitemagic.DispositivoTable.DISPOSITIVO;

/**
 * Created by anton on 24/05/2017.
 */

public class EditBeaconDeviceActivity extends AppCompatActivity {
    public String IdDispositivo;
    public String IdBeacon;
    private FormWizardEditar fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beacon_electrodomesticoeditar);

        getSupportActionBar().setTitle("Editar dispositivo");

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();

        if (bd != null) {
            IdDispositivo = (String) bd.get("IdDispositivo");
            IdBeacon = (String) bd.get("IdBeacon");
        }

        fragment = (FormWizardEditar) getSupportFragmentManager().findFragmentById(R.id.form_wizard_fragment);
        fragment.setIdDispositivo(IdDispositivo, IdBeacon);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.eliminar_dispositivo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                EliminarDispositivo();
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    public void EliminarDispositivo() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Delete.from(DISPOSITIVO).where(DISPOSITIVO.ID.is(Long.parseLong(IdDispositivo))).execute();
                        fragment.onWizardComplete();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Estás seguro de eliminar este elemento?").setPositiveButton("Sí", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }
}
