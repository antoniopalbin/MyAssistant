package com.master.antonio.myassistant;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ListActivity;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.UUID;

/**
 * Created by anton on 13/05/2017.
 */

public class DetectarBeaconsActivity extends AppCompatActivity {

    public static int REQUEST_BLUETOOTH = 1;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;

    private BluetoothAdapter mBluetoothAdapter;
    private Beacon listBeacons;
    private String telephoneAddress;
    private boolean manualScanning;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detectar_beacons);

        registerReceiver(receiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));

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
        telephoneAddress = android.provider.Settings.Secure.getString(DetectarBeaconsActivity.this.getContentResolver(), "bluetooth_address");


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

        /*listBeacons = new Beacon(DetectarBeaconsActivity.this.getLayoutInflater());
        setListAdapter(listBeacons);*/
        scanLeDevice(true);

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
               // listBeacons.clear();
                scanLeDevice(true);
                break;
            case R.id.menu_stop:
                scanLeDevice(false);
                break;
        }
        return true;
    }

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            //mBluetoothAdapter.stopLeScan(mLeScanCallback);
            manualScanning = true;
            mBluetoothAdapter.startDiscovery();
        } else {
            manualScanning = false;
           // mBluetoothAdapter.stopLeScan(mLeScanCallback);
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
                        final Beacon deviceAux = new Beacon(device.getAddress(),uuid,rssi,major,minor,txpw);
                        //listBeacons.addDevice(deviceAux);
                        //listBeacons.notifyDataSetChanged();

                        System.out.print(device.getAddress().toString() + "Detectado");
                    }}
            });
        }
    };

    private final BroadcastReceiver receiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)) {
                int rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);
                String name = intent.getStringExtra(BluetoothDevice.EXTRA_NAME);

                System.out.print(rssi + "Detectado "+ name);
            }
        }
    };

    public static UUID getGuidFromByteArray(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        UUID uuid = new UUID(bb.getLong(), bb.getLong());
        return uuid;
    }
}
