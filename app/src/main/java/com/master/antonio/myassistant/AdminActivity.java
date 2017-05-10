package com.master.antonio.myassistant;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;

import com.activeandroid.ActiveAndroid;

/**
 * Created by anton on 30/04/2017.
 */

public class AdminActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //Configurar tabhost
        TabHost tabs=(TabHost)findViewById(R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec=tabs.newTabSpec("Beacons");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Beacons",getDrawable(android.R.drawable.ic_btn_speak_now));
        tabs.addTab(spec);

        spec=tabs.newTabSpec("Avisos");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Avisos",getDrawable(android.R.drawable.ic_btn_speak_now));
        tabs.addTab(spec);

        tabs.getTabWidget().getChildAt(0).setBackgroundColor(getColor(R.color.colorPrimary));
        tabs.getTabWidget().getChildAt(0);

        tabs.setCurrentTab(0);
    }

}
