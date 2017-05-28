package com.master.antonio.myassistant.wizard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.master.antonio.myassistant.R;

import org.codepond.wizardroid.WizardStep;

/**
 * Created by anton on 28/05/2017.
 */

public class Step4 extends WizardStep {

    SeekBar progress;

    //You must have an empty constructor for every step
    public Step4() {
    }

    //Set your layout here
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.new_electrodomestico4, container, false);

        progress = (SeekBar) v.findViewById(R.id.seekBar);
        progress.setProgress(100);

        return v;
    }

    @Override
    public void onExit(int exitCode) {
        switch (exitCode) {
            case WizardStep.EXIT_NEXT:
                bindDataFields();
                break;
            case WizardStep.EXIT_PREVIOUS:
                //Do nothing...
                break;
        }
    }

    private void bindDataFields() {
        //Do some work
        //...
        //The values of these fields will be automatically stored in the wizard context
        //and will be populated in the next steps only if the same field names are used.

    }
}
