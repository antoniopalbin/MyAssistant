package com.master.antonio.myassistant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.codepond.wizardroid.WizardStep;

/**
 * Created by anton on 24/05/2017.
 */

public class Step1 extends WizardStep {

    //You must have an empty constructor for every step
    public Step1() {
    }

    //Set your layout here
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.new_electrodomestico1, container, false);
        TextView tv = (TextView) v.findViewById(R.id.textView);
        tv.setText("This is an example of Step 1 in the wizard. Press the Next " +
                "button to proceed to the next step. Hit the back button to go back to the calling activity.");

        return v;
    }
}