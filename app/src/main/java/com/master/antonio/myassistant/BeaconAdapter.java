package com.master.antonio.myassistant;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.UUID;

class BeaconAdapter extends BaseAdapter {
    private ArrayList<BeaconAdapter> mLeDevices;
    private LayoutInflater mInflator;

    private String mac;
    private UUID uuid;
    private int rrsi;
    private int major;
    private int minor;
    private int txpw;

    public BeaconAdapter(LayoutInflater layaoutInflater) {
        super();
        mLeDevices = new ArrayList<BeaconAdapter>();
        mInflator = layaoutInflater;
    }

    public BeaconAdapter(String mac, UUID uuid, int rrsi, int major, int minor, int txpw) {
        this.mac = mac;
        this.uuid = uuid;
        this.rrsi = rrsi;
        this.major = major;
        this.minor = minor;
        this.txpw = txpw;
    }

    public String getMac() {

        return mac;
    }

    public UUID getUuid() {

        return uuid;
    }

    public int getRrsi() {

        return rrsi;
    }

    public int getMajor() {

        return major;
    }

    public int getMinor() {

        return minor;
    }

    public int getTxpw() {

        return txpw;
    }

    public void addDevice(BeaconAdapter device) {
        boolean insert = false;
        for(int i=0;i<mLeDevices.size();i++){
            if(mLeDevices.get(i).getMac().equals(device.getMac())){
                mLeDevices.set(i,device);
                insert = true;
            }
        }
        if(!insert){
            mLeDevices.add(device);
        }
    }

    public BeaconAdapter getDevice(int position) {

        return mLeDevices.get(position);
    }

    public void clear() {
        mLeDevices.clear();
    }

    @Override
    public int getCount() {

        return mLeDevices.size();
    }

    @Override
    public Object getItem(int i) {

        return mLeDevices.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view == null) {
            view = mInflator.inflate(R.layout.beaconitemlayout, null);
            viewHolder = new ViewHolder();
            viewHolder.deviceAddress = (TextView) view.findViewById(R.id.device_address);
            viewHolder.deviceName = (TextView) view.findViewById(R.id.device_name);
            viewHolder.dataInfo =  (TextView) view.findViewById(R.id.data_Info);
            viewHolder.icon = (ImageView) view.findViewById(R.id.imgIcono);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }


        BeaconAdapter device = mLeDevices.get(i);
        viewHolder.deviceName.setText(device.getMac());
        viewHolder.deviceAddress.setText("Major: " + device.getMajor() + " Minor: " + device.getMinor());
        viewHolder.dataInfo.setText("RSSI: " + device.getRrsi() + " TXPW " + device.getTxpw());
        viewHolder.icon.setImageResource(R.mipmap.beacon);


        return view;
    }

    static class ViewHolder {
        TextView deviceName;
        TextView deviceAddress;
        TextView dataInfo;
        ImageView icon;
    }

}

