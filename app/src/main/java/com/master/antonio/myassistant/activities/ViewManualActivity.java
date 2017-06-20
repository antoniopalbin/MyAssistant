package com.master.antonio.myassistant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.master.antonio.myassistant.R;

/**
 * Created by Antonio on 19/06/2017.
 */

public class ViewManualActivity extends AppCompatActivity {
    String manual;
    TextView txtManual;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizar_manual);

        Intent intent = getIntent();
        Bundle bdl = intent.getExtras();
        if (bdl != null) {
            manual = (String) bdl.get("manual");
        }

        txtManual = (TextView) findViewById(R.id.txtManual);

        txtManual.setText(manual);
    }
}
