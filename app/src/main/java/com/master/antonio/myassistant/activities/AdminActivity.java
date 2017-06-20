package com.master.antonio.myassistant.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TextView;

import com.master.antonio.myassistant.CheckRules;
import com.master.antonio.myassistant.R;
import com.master.antonio.myassistant.models.Aviso;
import com.master.antonio.myassistant.models.Beacon;
import com.siimkinks.sqlitemagic.Select;

import java.util.ArrayList;
import java.util.List;

import static com.siimkinks.sqlitemagic.AvisoTable.AVISO;
import static com.siimkinks.sqlitemagic.BeaconTable.BEACON;

/**
 * Created by anton on 30/04/2017.
 */

public class AdminActivity extends AppCompatActivity {
    TabHost tabs;
    FloatingActionButton button;
    Switch Regla1;
    Switch Regla2;
    Switch Regla3;
    ListView cuentas;
    Button AddCuenta;

    List<Beacon> beacons;
    ListView lvBeacons;
    Aviso aviso;

    Context cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_admin);
        cont = this;

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
        loadAvisos();

        Regla1 = (Switch) findViewById(R.id.Regla1);
        Regla1.setChecked(aviso.isTipoAviso1());
        Regla1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ConfigCheckRules();
            }
        });

        Regla2 = (Switch) findViewById(R.id.Regla2);
        Regla2.setChecked(aviso.isTipoAviso2());
        Regla2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ConfigCheckRules();
            }
        });

        Regla3 = (Switch) findViewById(R.id.Regla3);
        Regla3.setChecked(aviso.isTipoAviso3());
        Regla3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ConfigCheckRules();
            }
        });

        AddCuenta = (Button) findViewById(R.id.AddCuenta);
        AddCuenta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                showInputDialog();
            }
        });

        cuentas = (ListView) findViewById(R.id.ListCuentas);
        showLVCuentas();
    }

    protected void showInputDialog() {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(AdminActivity.this);
        View promptView = layoutInflater.inflate(R.layout.add_mail, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AdminActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.txtEmail);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        aviso.setEmails(editText.getText().toString());
                        aviso.update().execute();
                        showLVCuentas();
                    }
                })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private void ConfigCheckRules() {
        aviso.setTipoAviso1(Regla1.isChecked());
        aviso.setTipoAviso2(Regla2.isChecked());
        aviso.setTipoAviso3(Regla3.isChecked());
        aviso.update().execute();

        stopService(new Intent(cont, CheckRules.class));

        Intent intent = new Intent(cont, CheckRules.class);
        intent.putExtra("Regla1", Regla1.isChecked());
        intent.putExtra("Regla2", Regla2.isChecked());
        intent.putExtra("Regla3", Regla3.isChecked());

        startService(intent);
    }

    private void startAddNewBeacon() {
        Intent intent = new Intent(this, SearchBeaconsActivity.class);
        startActivity(intent);
    }

    private void startListDispositivosBeacons(String idBeacon) {
        Intent intent = new Intent(this, ListBeaconDeviceActivity.class);
        intent.putExtra("IdBeacon", idBeacon);
        startActivity(intent);
    }

    private void loadAvisos() {
        aviso = Select.from(AVISO).takeFirst().execute();
    }

    private void showLVBeacons() {
        lvBeacons = (ListView) findViewById(R.id.ListBeacons);
        lvBeacons.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        beacons = Select.from(BEACON).orderBy(BEACON.ESTANCIA.asc(), BEACON.DESCRIPCION.asc()).queryDeep().execute();

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

        lvBeacons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                startListDispositivosBeacons(beacons.get(position).getIdBeacon());
            }
        });
    }

    private void showLVCuentas() {
        cuentas = (ListView) findViewById(R.id.ListCuentas);
        cuentas.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        List<String> auxCuentas = new ArrayList<>();
        if (aviso.getEmails() != null && !aviso.getEmails().isEmpty()) {
            auxCuentas.add(aviso.getEmails());
            AddCuenta.setVisibility(View.GONE);
        } else {
            AddCuenta.setVisibility(View.VISIBLE);
        }

        ArrayAdapter adaptadorCuentas =
                new ArrayAdapter(this // Context
                        , R.layout.email_item_layout //Resource
                        , auxCuentas // Vector o lista
                ) {
                    public View getView(int position
                            , View convertView
                            , ViewGroup parent) {
                        LayoutInflater inflater = (LayoutInflater) getContext()
                                .getSystemService(getContext().LAYOUT_INFLATER_SERVICE);

                        // Creamos la vista para cada fila
                        View fila = inflater.inflate(R.layout.email_item_layout, parent, false);

                        // Creamos cada uno de los widgets que forman una fila
                        TextView cuentaView = (TextView) fila.findViewById(R.id.email);

                        // Establecemos los valores que queremos que muestren los widgets
                        cuentaView.setText(aviso.getEmails());
                        return fila;
                    }
                };
        cuentas.setAdapter(adaptadorCuentas);

        cuentas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                showEditDialog();
            }
        });

    }

    protected void showEditDialog() {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(AdminActivity.this);
        View promptView = layoutInflater.inflate(R.layout.add_mail, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AdminActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.txtEmail);
        editText.setText(aviso.getEmails());
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        aviso.setEmails(editText.getText().toString());
                        aviso.update().execute();
                        showLVCuentas();
                    }
                })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}