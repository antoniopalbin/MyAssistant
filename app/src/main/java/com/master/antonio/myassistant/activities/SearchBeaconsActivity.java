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
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.master.antonio.myassistant.R;
import com.master.antonio.myassistant.adapters.BeaconAdapter;
import com.master.antonio.myassistant.models.Beacon;
import com.master.antonio.myassistant.utilities.MyAssistantUtilities;
import com.siimkinks.sqlitemagic.Select;

import java.util.Arrays;
import java.util.UUID;

import static com.siimkinks.sqlitemagic.BeaconTable.BEACON;

/**
 * Created by anton on 13/05/2017.
 */

public class SearchBeaconsActivity extends AppCompatActivity {
    public static int REQUEST_BLUETOOTH = 1;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;

    private BluetoothAdapter mBluetoothAdapter;
    private BeaconAdapter listBeacons;
    private boolean manualScanning;

    ListView ListaBeacons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detectar_beacons);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Nueva beacon");

        ListaBeacons = (ListView) findViewById(R.id.ListBeacons);

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

        listBeacons = new BeaconAdapter(SearchBeaconsActivity.this.getLayoutInflater());
        ListaBeacons.setAdapter(listBeacons);
        scanLeDevice(true);

        ListaBeacons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                startListDispositivosBeacons(listBeacons.getDevice(position).getMac().toString());
            }
        });
    }

    private void startListDispositivosBeacons(String idBeacon) {
        Intent intent = new Intent(this, AddBeaconActivity.class);
        intent.putExtra("IdBeacon", idBeacon);
        startActivity(intent);
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
                listBeacons.clear();
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
                        UUID uuid = MyAssistantUtilities.getGuidFromByteArray(Arrays.copyOfRange(scanRecord, 9, 25));
                        int major = (scanRecord[25] & 0xff) * 0x100 + (scanRecord[26] & 0xff);
                        int minor = (scanRecord[27] & 0xff) * 0x100 + (scanRecord[28] & 0xff);
                        byte txpw = scanRecord[29];
                        final BeaconAdapter deviceAux = new BeaconAdapter(device.getAddress(), uuid, rssi, major, minor, txpw);

                        Beacon aux = Select.from(BEACON).where(BEACON.ID_BEACON.is(device.getAddress().toString())).takeFirst().execute();
                        if (aux == null) {
                            listBeacons.addDevice(deviceAux);
                            listBeacons.notifyDataSetChanged();
                            System.out.println(device.getAddress().toString() + "Detectado #########################################");
                        }
                    }
                }
            });
        }
    };
}
