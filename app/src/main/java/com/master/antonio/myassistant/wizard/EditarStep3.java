package com.master.antonio.myassistant.wizard;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.master.antonio.myassistant.R;

import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.persistence.ContextVariable;

import java.io.ByteArrayOutputStream;

/**
 * Created by anton on 28/05/2017.
 */

public class EditarStep3 extends WizardStep {

    @ContextVariable
    private String Marca;
    @ContextVariable
    private String Modelo;
    @ContextVariable
    private String KeyVideo;
    @ContextVariable
    private byte[] img;
    @ContextVariable
    private String Manual;
    @ContextVariable
    private byte[] thumbnail;
    @ContextVariable
    private long IdDispositivo;
    @ContextVariable
    private String  IdBeacon;

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int REQUEST_CAMERA = 1;

    SeekBar progress;
    FloatingActionButton cam;
    Bitmap imageBitmap;
    ImageView thumbail;

    boolean captura;

    //You must have an empty constructor for every step
    public EditarStep3() {
    }

    //Set your layout here
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.new_electrodomestico3, container, false);

        captura = false;

        progress = (SeekBar) v.findViewById(R.id.seekBar);
        progress.setProgress(75);
        progress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        cam = (FloatingActionButton) v.findViewById(R.id.fab);

        thumbail = (ImageView) v.findViewById(R.id.foto);

        Bitmap bmp = BitmapFactory.decodeByteArray(img, 0, img.length);
        thumbail.setImageBitmap(bmp);

        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Check Permissions Now
                    // Callback onRequestPermissionsResult interceptado na Activity MainActivity
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CAMERA},
                            EditarStep3.REQUEST_CAMERA);
                }else{
                    dispatchTakePictureIntent();
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

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE ) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            thumbail.setImageBitmap(imageBitmap);
            captura = true;
        }
    }

    private void bindDataFields() {
        if(captura==true) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
            img = stream.toByteArray();
        }
    }
}
