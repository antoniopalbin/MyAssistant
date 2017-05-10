package com.master.antonio.myassistant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;



/**
 * Created by anton on 10/05/2017.
 */

public class VisualizarVideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{

    Context cont;
    public String videoId;
    private YouTubePlayerFragment fragment;

    private static final int RECOVERY_DIALOG_REQUEST =1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_movie);
        cont = this;


        fragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.fragment_youtube_lectura);

        //Prueba de visualización de video
        videoId = "WkoKLmqYQUo";

        //fragment.initialize(getString(R.string.Developer_key),VisualizarVideoActivity.this);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if(!b){
            youTubePlayer.cueVideo(videoId);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if(requestCode == RECOVERY_DIALOG_REQUEST){
           getYouTubePlayerProvider().initialize(getString(R.string.Developer_key),VisualizarVideoActivity.this);
       }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider(){
        return (YouTubePlayerView) findViewById(R.id.fragment_youtube_lectura);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if(youTubeInitializationResult.isUserRecoverableError()){
            youTubeInitializationResult.getErrorDialog(this,RECOVERY_DIALOG_REQUEST).show();
        }else{
            String errormensaje = "Se ha producido un error al inicializar el reproductor de YouTube";
            Toast.makeText(this, errormensaje, Toast.LENGTH_SHORT).show();
        }
    }
}
