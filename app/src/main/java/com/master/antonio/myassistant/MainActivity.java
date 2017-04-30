package com.master.antonio.myassistant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.activeandroid.ActiveAndroid;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Initializing Active Android
        ActiveAndroid.initialize(this);

        setContentView(R.layout.activity_main);
    }
}
