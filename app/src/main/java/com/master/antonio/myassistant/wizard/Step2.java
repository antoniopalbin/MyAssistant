package com.master.antonio.myassistant.wizard;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.master.antonio.myassistant.R;
import com.master.antonio.myassistant.fragments.YouTubeFragment;

import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.persistence.ContextVariable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by anton on 24/05/2017.
 */

public class Step2 extends WizardStep {

    @ContextVariable
    private String KeyVideo;

    SeekBar progress;
    YouTubeFragment fragment;
    ImageButton update;
    EditText InputUrl;
    String videoId;


    //You must have an empty constructor for every step
    public Step2() {
    }

    //Set your layout here
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.new_electrodomestico2, container, false);

        fragment = (YouTubeFragment) getChildFragmentManager().findFragmentById(R.id.fragment_youtube);
        fragment.setVideoId("RjTSh0diNO4");


        progress = (SeekBar) v.findViewById(R.id.seekBar);
        progress.setProgress(50);

        progress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        InputUrl = (EditText) v.findViewById(R.id.InputUrl);

        update = (ImageButton) v.findViewById(R.id.imageButton);
        update.setImageResource(R.drawable.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!InputUrl.getText().equals("")){
                    setVideo(InputUrl.getText().toString());
                }

            }
        });



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

    private void setVideo(String url){
        String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(url);

        if(matcher.find()){
            videoId = matcher.group();
            fragment.setVideoId("RjTSh0diNO4");

        }


    }

    private void bindDataFields() {

        KeyVideo = videoId;

    }
}