package com.master.antonio.myassistant.activities;

import android.content.Intent;
import android.os.Bundle;
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
import com.master.antonio.myassistant.utilities.MyAssistantUtilities;

/**
 * Created by Antonio on 15/06/2017.
 */

public class AddBeaconActivity extends AppCompatActivity {
    Button btnGuardar;
    EditText inputDescripcion;
    EditText inputEstancia;
    int icono;
    Spinner sp;
    String idBeacon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newbeacon);

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
    }

    private void GuardarBeacon() {
        Beacon beacon = new Beacon(idBeacon, inputEstancia.getText().toString(), inputDescripcion.getText().toString(), icono);
        beacon.insert().execute();

        Toast toast1 = Toast.makeText(getApplicationContext(), "Beacon creada correctamente", Toast.LENGTH_SHORT);
        toast1.show();

        Intent intent = new Intent(this, AdminActivity.class);
        startActivity(intent);
    }
}
