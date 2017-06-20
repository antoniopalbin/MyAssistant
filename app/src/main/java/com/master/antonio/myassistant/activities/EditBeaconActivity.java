package com.master.antonio.myassistant.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.master.antonio.myassistant.R;
import com.master.antonio.myassistant.adapters.IconAdapter;
import com.master.antonio.myassistant.models.Beacon;
import com.master.antonio.myassistant.utilities.MyAssistantUtilities;
import com.siimkinks.sqlitemagic.Select;

import java.util.Arrays;

import static com.siimkinks.sqlitemagic.BeaconTable.BEACON;

/**
 * Created by Antonio on 15/06/2017.
 */

public class EditBeaconActivity extends AppCompatActivity {
    Button btnGuardar;
    EditText inputDescripcion;
    EditText inputEstancia;
    int icono;
    Spinner sp;

    String idBeacon;
    Beacon beacon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editbeacon);

        getSupportActionBar().setTitle("Editar Beacon");

        Intent intent = getIntent();
        Bundle bdl = intent.getExtras();
        if (bdl != null) {
            idBeacon = (String) bdl.get("IdBeacon");
        }

        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        inputDescripcion = (EditText) findViewById(R.id.InputDescripcion);
        inputEstancia = (EditText) findViewById(R.id.InputEstancia);
        sp = (Spinner) findViewById(R.id.spinner);

        IconAdapter adapter = new IconAdapter(this, MyAssistantUtilities.getEstancias(), MyAssistantUtilities.getIconos());
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                icono = MyAssistantUtilities.getIconos()[position];
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
        LoadBeacon(idBeacon);
    }

    private void LoadBeacon(String idBeacon) {
        beacon = Select.from(BEACON).where(BEACON.ID_BEACON.is(idBeacon)).takeFirst().execute();

        inputDescripcion.setText(beacon.getDescripcion());
        inputEstancia.setText(beacon.getEstancia());
        sp.setSelection(Arrays.asList(MyAssistantUtilities.getIconos()).indexOf(beacon.getIcono()));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.eliminar_dispositivo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                EliminarBeacon();
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}