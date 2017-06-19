package com.master.antonio.myassistant.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.master.antonio.myassistant.R;
import com.master.antonio.myassistant.adapters.IconAdapter;
import com.master.antonio.myassistant.models.Beacon;
import com.siimkinks.sqlitemagic.Select;

import java.util.Arrays;

import static com.siimkinks.sqlitemagic.BeaconTable.BEACON;

/**
 * Created by Antonio on 15/06/2017.
 */

public class EditBeaconActivity extends AppCompatActivity {
    Button btnGuardar;
    Button btnEliminar;
    EditText inputDescripcion;
    EditText inputEstancia;
    int icono;
    Spinner sp;
    String[] estancias = {"Baño", "Cocina", "Salón", "Dormitorio"};
    int[] iconos = {R.mipmap.bano, R.mipmap.cocina, R.mipmap.tv, R.mipmap.dormitorio};

    String idBeacon;
    Beacon beacon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editbeacon);

        Intent intent = getIntent();
        Bundle bdl = intent.getExtras();
        if (bdl != null) {
            idBeacon = (String) bdl.get("IdBeacon");
        }

        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnEliminar = (Button) findViewById(R.id.btnEliminar);
        inputDescripcion = (EditText) findViewById(R.id.InputDescripcion);
        inputEstancia = (EditText) findViewById(R.id.InputEstancia);
        sp = (Spinner) findViewById(R.id.spinner);

        IconAdapter adapter = new IconAdapter(this, estancias, iconos);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                icono = iconos[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuardarBeacon();
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EliminarBeacon();
            }
        });
        LoadBeacon(idBeacon);
    }

    private void LoadBeacon(String idBeacon) {
        beacon = Select.from(BEACON).where(BEACON.ID_BEACON.is(idBeacon)).takeFirst().execute();

        inputDescripcion.setText(beacon.getDescripcion());
        inputEstancia.setText(beacon.getEstancia());
        sp.setSelection(Arrays.asList(iconos).indexOf(beacon.getIcono()));
    }

    private void GuardarBeacon() {
        beacon.setDescripcion(inputDescripcion.getText().toString());
        beacon.setEstancia(inputEstancia.getText().toString());
        beacon.setIcono(icono);

        beacon.update().execute();

        Toast toast1 = Toast.makeText(getApplicationContext(), "Beacon actualizada correctamente", Toast.LENGTH_SHORT);
        toast1.show();

        IrAListaBeacons();
    }

    private void IrAListaBeacons() {
        Intent intent = new Intent(this, AdminActivity.class);
        startActivity(intent);
    }

    private void EliminarBeacon() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        beacon.delete().execute();
                        IrAListaBeacons();
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