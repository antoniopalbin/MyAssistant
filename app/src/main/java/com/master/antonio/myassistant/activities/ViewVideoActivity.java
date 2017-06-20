package com.master.antonio.myassistant.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.master.antonio.myassistant.R;
import com.master.antonio.myassistant.fragments.YouTubeFragment;

/**
 * Created by anton on 10/05/2017.
 */

public class ViewVideoActivity extends AppCompatActivity {

    Context cont;
    String videoId;

    private YouTubeFragment fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_movie);
        cont = this;

        fragment = (YouTubeFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_youtube);

        Intent intent = getIntent();
        Bundle bdl = intent.getExtras();
        if (bdl != null) {
            videoId = (String) bdl.get("videoId");
        }
        fragment.setVideoId(videoId);
    }
}
