package com.master.antonio.myassistant.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.master.antonio.myassistant.AsociarBeaconDispositivo;
import com.master.antonio.myassistant.R;
import com.master.antonio.myassistant.models.Beacon;
import com.siimkinks.sqlitemagic.Select;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.siimkinks.sqlitemagic.ActividadTable.ACTIVIDAD;
import static com.siimkinks.sqlitemagic.BeaconTable.BEACON;

/**
 * Created by anton on 30/04/2017.
 */

public class AdminActivity extends AppCompatActivity {
    TabHost tabs;
    FloatingActionButton button;

    List<Beacon> beacons;
    ListView lvBeacons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_admin);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //Configurar tabhost
        tabs = (TabHost) findViewById(R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("Beacons");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Beacons", getDrawable(android.R.drawable.ic_btn_speak_now));
        tabs.addTab(spec);

        spec = tabs.newTabSpec("Avisos");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Avisos", getDrawable(android.R.drawable.ic_btn_speak_now));
        tabs.addTab(spec);

        for (int i = 0; i < tabs.getTabWidget().getChildCount(); i++) {
            TextView tv = (TextView) tabs.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.parseColor("#A9BCF5"));
            tv.setTextSize(10);
        }

        TextView tv = (TextView) tabs.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
        tv.setTextColor(Color.parseColor("#ffffff"));
        tv.setTextSize(16);

        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                for (int i = 0; i < tabs.getTabWidget().getChildCount(); i++) {
                    TextView tv = (TextView) tabs.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
                    tv.setTextColor(Color.parseColor("#A9BCF5"));
                    tv.setTextSize(10);
                }

                // selected
                TextView tv = (TextView) tabs.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
                tv.setTextColor(Color.parseColor("#ffffff"));
                tv.setTextSize(16);
            }
        });

        //Evento de añadir una nueva beacon
        button = (FloatingActionButton) findViewById(R.id.addBeacon);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                startAddNewBeacon();
            }
        });

        lvBeacons = (ListView) findViewById(R.id.ListBeacons);
        showLVBeacons();
    }

    private void startAddNewBeacon() {
        Intent intent = new Intent(this, AsociarBeaconDispositivo.class);
        startActivity(intent);
    }

    private void showLVBeacons() {
        lvBeacons = (ListView) findViewById(R.id.ListBeacons);
        lvBeacons.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        beacons = Select.from(BEACON).orderBy(BEACON.ESTANCIA.asc(),BEACON.DESCRIPCION.asc()).queryDeep().execute();

        ArrayAdapter adaptadorBeacons =
                new ArrayAdapter(this // Context
                        , R.layout.beaconitemadminlayout //Resource
                        , beacons // Vector o lista
                ) {
                    public View getView(int position
                            , View convertView
                            , ViewGroup parent) {
                        LayoutInflater inflater = (LayoutInflater) getContext()
                                .getSystemService(getContext().LAYOUT_INFLATER_SERVICE);

                        // Creamos la vista para cada fila
                        View fila = inflater.inflate(R.layout.beaconitemadminlayout, parent, false);

                        // Creamos cada uno de los widgets que forman una fila
                        ImageView iconoView = (ImageView) fila.findViewById(R.id.imgIcono);
                        TextView descripcionView = (TextView) fila.findViewById(R.id.beacon_description);
                        TextView estanciaView = (TextView) fila.findViewById(R.id.beacon_room);

                        // Establecemos los valores que queremos que muestren los widgets
                        Beacon tmpB = beacons.get(position);
                        iconoView.setImageResource(tmpB.getIcono());
                        descripcionView.setText(tmpB.getDescripcion());
                        estanciaView.setText(tmpB.getEstancia());
                        return fila;
                    }
                };
        lvBeacons.setAdapter(adaptadorBeacons);
//        lvBeacons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                SparseBooleanArray checked = lvBeacons.getCheckedItemPositions();
//                if (checked == null) {
//                    Toast.makeText(MainActivity.this, "Checked is null", Toast.LENGTH_LONG).show();
//                    return;
//                }
//                String msg = "Items marcados: ";
//                for (int i = 0; i < lvBeacons.getCount(); ++i) {
//                    msg += (checked.get(i)) ? i + ", " : "";
//                }
//                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
