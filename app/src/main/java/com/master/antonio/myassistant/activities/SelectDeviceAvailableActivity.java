package com.master.antonio.myassistant.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.master.antonio.myassistant.R;
import com.master.antonio.myassistant.adapters.BeaconAdapter;
import com.master.antonio.myassistant.adapters.GridViewAdapter;
import com.master.antonio.myassistant.models.Actividad;
import com.master.antonio.myassistant.models.Beacon;
import com.master.antonio.myassistant.models.Dispositivo;
import com.siimkinks.sqlitemagic.Select;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.siimkinks.sqlitemagic.BeaconTable.BEACON;
import static com.siimkinks.sqlitemagic.DispositivoTable.DISPOSITIVO;

/**
 * Created by Antonio on 19/06/2017.
 */

public class SelectDeviceAvailableActivity extends AppCompatActivity {
    public static int REQUEST_BLUETOOTH = 1;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private BluetoothAdapter mBluetoothAdapter;
    List<Dispositivo> dispositivos = new ArrayList<>();
    private boolean manualScanning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_device_available);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Dispositivos detectados");

        //Chequeamos los permisos
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                builder.setTitle("Se necesitan permisos");
                builder.setMessage("Por favor, otorgue acceso a la ubicación para que esta aplicación pueda detectar beacons.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @TargetApi(Build.VERSION_CODES.M)
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                    }
                });
                builder.show();
            }
        }

        //Inicializamos el Bluetooth
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        // Si el dispositivo no soporta Bluetooth, notificamos al usuario
        if (mBluetoothAdapter == null) {
            new AlertDialog.Builder(this)
                    .setTitle("No compatible")
                    .setMessage("Su teléfono no dispone de Bluetooth")
                    .setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        //Si el Bluetooth esta desconectado
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBT, REQUEST_BLUETOOTH);
        }

        scanLeDevice(true);

        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(this, R.layout.grid_device_layout, dispositivos);
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Dispositivo item = (Dispositivo) parent.getItemAtPosition(position);

                Beacon aux = Select.from(BEACON).where(BEACON.ID.is(item.getBeacon().id)).takeFirst().execute();
                Actividad actividad = new Actividad(new Date().getTime(), aux);
                actividad.persist().execute();

                if (item.getIdYoutube() != null && (conectadoRedMovil() || conectadoWifi())) {
                    //Create intent
                    Intent intent = new Intent(SelectDeviceAvailableActivity.this, VisualizarVideoActivity.class);
                    intent.putExtra("videoId", item.getIdYoutube());

                    //Start details activity
                    startActivity(intent);
                } else {
                    //Create intent
                    Intent intent = new Intent(SelectDeviceAvailableActivity.this, VisualizarManualActivity.class);
                    intent.putExtra("manual", item.getManual());

                    //Start details activity
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detectar_beacons, menu);
        if (!manualScanning) {
            menu.findItem(R.id.menu_stop).setVisible(false);
            menu.findItem(R.id.menu_scan).setVisible(true);
            menu.findItem(R.id.menu_refresh).setActionView(null);
        } else {
            menu.findItem(R.id.menu_stop).setVisible(true);
            menu.findItem(R.id.menu_scan).setVisible(false);
            menu.findItem(R.id.menu_refresh).setActionView(R.layout.actionbar_progress_scan);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_scan:
                dispositivos.clear();
                scanLeDevice(true);
                break;
            case R.id.menu_stop:
                scanLeDevice(false);
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
            manualScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            manualScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);

        }
        invalidateOptionsMenu();
    }

    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (scanRecord[7] == 0x02 && scanRecord[8] == 0x15) {
                        UUID uuid = getGuidFromByteArray(Arrays.copyOfRange(scanRecord, 9, 25));
                        int major = (scanRecord[25] & 0xff) * 0x100 + (scanRecord[26] & 0xff);
                        int minor = (scanRecord[27] & 0xff) * 0x100 + (scanRecord[28] & 0xff);
                        byte txpw = scanRecord[29];
                        final BeaconAdapter deviceAux = new BeaconAdapter(device.getAddress(), uuid, rssi, major, minor, txpw);

                        Beacon aux = Select.from(BEACON).where(BEACON.ID_BEACON.is(device.getAddress().toString())).takeFirst().execute();
                        if (aux != null) { //existe en la BD
                            List<Dispositivo> auxDispositivos = Select.from(DISPOSITIVO).where(DISPOSITIVO.BEACON.is(aux)).execute();
                            if (auxDispositivos != null) {
                                for (Dispositivo daux : auxDispositivos) {
                                    boolean incluido = false;
                                    for (Dispositivo d : dispositivos) {
                                        if (d.getId() == daux.getId())
                                            incluido = true;
                                    }

                                    if (!incluido) {
                                        dispositivos.add(daux);
                                        gridAdapter.notifyDataSetChanged();
                                        gridView.setAdapter(gridAdapter);
                                    }
                                }
                            }
                            System.out.println(device.getAddress().toString() + " Detectado. Nº dispositivos: " + dispositivos.size() + " #########################################");
                        }
                    }
                }
            });
        }
    };

    public static UUID getGuidFromByteArray(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        UUID uuid = new UUID(bb.getLong(), bb.getLong());
        return uuid;
    }

    protected Boolean conectadoWifi() {
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

    protected Boolean conectadoRedMovil() {
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }
}
