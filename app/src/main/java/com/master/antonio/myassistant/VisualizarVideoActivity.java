package com.master.antonio.myassistant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;



/**
 * Created by anton on 10/05/2017.
 */

public class VisualizarVideoActivity extends AppCompatActivity{

    Context cont;
    public String videoId;
    private YouTubeFragment fragment;

    private static final int RECOVERY_DIALOG_REQUEST =1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_movie);
        cont = this;


        fragment = (YouTubeFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_youtube_lectura);

        //Prueba de visualización de video
        videoId = "WkoKLmqYQUo";
        fragment.setVideoId(videoId);


    }

}
