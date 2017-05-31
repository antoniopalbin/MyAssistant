package com.master.antonio.myassistant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.master.antonio.myassistant.AsociarBeaconDispositivo;
import com.master.antonio.myassistant.R;
import com.master.antonio.myassistant.models.Dispositivo;
import com.siimkinks.sqlitemagic.Select;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.siimkinks.sqlitemagic.ActividadTable.ACTIVIDAD;

/**
 * Created by anton on 31/05/2017.
 */

public class ListDispositivosBeaconsActivity extends AppCompatActivity {

    FloatingActionButton fab;
    ListView ListDispositivos;

    List<Dispositivo> Dispositivos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_listdispositivobeacons);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();

        if (bd != null) {
            String IdBeacon = (String) bd.get("IdBeacon");
        }

        ListDispositivos = (ListView) findViewById(R.id.ListDispositivoBeacon);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsociarBeaconDispositivo();
            }
        });

        showLVRegister_Complejo();


    }


    private void AsociarBeaconDispositivo(){
        Intent intent = new Intent(this, AsociarBeaconDispositivo.class);
        startActivity(intent);
    }


    private void showLVRegister_Complejo() {
        ListDispositivos = (ListView) findViewById(R.id.ListDispositivoBeacon);
        ListDispositivos.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);


        //Dispositivos = Select.from().where(Dispositiv).queryDeep().execute();

        ArrayAdapter adaptadorVehiculos =
                new ArrayAdapter(this // Context
                        , R.layout.registeritemlayout //Resource
                        , Dispositivos // Vector o lista
                ) {
                    public View getView(int position
                            , View convertView
                            , ViewGroup parent) {
                        LayoutInflater inflater = (LayoutInflater) getContext()
                                .getSystemService(getContext().LAYOUT_INFLATER_SERVICE);

                        // Creamos la vista para cada fila
                        View fila = inflater.inflate(R.layout.registeritemlayout, parent, false);

                        // Creamos cada uno de los widgets que forman una fila
                        ImageView iconoView = (ImageView) fila.findViewById(R.id.imgIcono);
                        TextView TextTitulo = (TextView) fila.findViewById(R.id.textTitulo);
                        TextView TextTime = (TextView) fila.findViewById(R.id.textTime);

                        // Establecemos los valores que queremos que muestren los widgets
                       /* Actividad tmpV = Actividades.get(position);
                        iconoView.setImageResource(tmpV.getBeacon().getIcono());
                        TextTitulo.setText(tmpV.getBeacon().getDescripcion());
                        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                        String dateText = df2.format(new Date(tmpV.getTimestamp()));
                        TextTime.setText(dateText);*/
                        return fila;
                    }
                };

        ListDispositivos.setAdapter(adaptadorVehiculos);

        /*ListRegister.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                SparseBooleanArray checked = lvVehiculos.getCheckedItemPositions();
                if (checked == null) {
                    Toast.makeText(MainActivity.this, "Checked is null", Toast.LENGTH_LONG).show();
                    return;
                }
                String msg = "Items marcados: ";
                for (int i = 0; i < lvVehiculos.getCount(); ++i) {
                    msg += (checked.get(i)) ? i + ", " : "";
                }
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });*/
    }

}
