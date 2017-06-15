package com.master.antonio.myassistant.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.master.antonio.myassistant.R;
import com.master.antonio.myassistant.models.Beacon;
import com.master.antonio.myassistant.models.Dispositivo;
import com.siimkinks.sqlitemagic.Delete;
import com.siimkinks.sqlitemagic.Select;

import java.util.List;

import static com.siimkinks.sqlitemagic.BeaconTable.BEACON;
import static com.siimkinks.sqlitemagic.DispositivoTable.DISPOSITIVO;

/**
 * Created by anton on 31/05/2017.
 */

public class ListDispositivosBeaconsActivity extends AppCompatActivity {

    FloatingActionButton fab;
    ListView ListDispositivos;

    List<Dispositivo> Dispositivos;
    String IdBeacon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_listdispositivobeacons);

        getSupportActionBar().setTitle("Dispositivos");


        Intent intent = getIntent();
        Bundle bd = intent.getExtras();

        if (bd != null) {
            IdBeacon = (String) bd.get("IdBeacon");
        }

        ListDispositivos = (ListView) findViewById(R.id.ListDispositivoBeacon);

        fab = (FloatingActionButton) findViewById(R.id.addDispositivo);

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
        intent.putExtra("IdBeacon", IdBeacon);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        showLVRegister_Complejo();

    }

    public void editarDispositivo(String IDdisp){
        Intent intent = new Intent(this, EditarBeaconDispositivo.class);
        intent.putExtra("IdDispositivo", IDdisp);
        startActivity(intent);
    }

    private void showLVRegister_Complejo() {
        ListDispositivos = (ListView) findViewById(R.id.ListDispositivoBeacon);
        ListDispositivos.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);


        Beacon aux = Select.from(BEACON).where(BEACON.ID_BEACON.is(IdBeacon)).takeFirst().execute();
        Dispositivos = Select.from(DISPOSITIVO).where(DISPOSITIVO.BEACON.is(aux)).execute();

        ArrayAdapter adaptadorDispositivos =
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
                        TextView TextTitulo = (TextView) fila.findViewById(R.id.textTitulo);
                        TextView TextTime = (TextView) fila.findViewById(R.id.textTime);

                        // Establecemos los valores que queremos que muestren los widgets
                        Dispositivo tmpV = Dispositivos.get(position);

                        Bitmap bmp = BitmapFactory.decodeByteArray(tmpV.getThumbnail(), 0, tmpV.getThumbnail().length);
                        ImageView iconoView = (ImageView) fila.findViewById(R.id.imgIcono);
                        iconoView.setImageBitmap(bmp);
                        TextTitulo.setText(tmpV.getMarca());
                        TextTime.setText(tmpV.getModelo());
                        return fila;
                    }
                };

        ListDispositivos.setAdapter(adaptadorDispositivos);

        ListDispositivos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l){
                editarDispositivo(Dispositivos.get(position).getId()+"");
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editar_beacon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_editar:
                EditarBeacon();
        }
        return true;
    }

    public void EditarBeacon(){
    }

}
