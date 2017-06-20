package com.master.antonio.myassistant.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.master.antonio.myassistant.R;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private DatePickerDialog datePickerDialog;
    private Context cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cont = this;

        //Obtenemos la fecha actual
        final Calendar c = Calendar.getInstance();
        int year_aux = c.get(Calendar.YEAR);
        int month_aux = c.get(Calendar.MONTH);
        int day_aux = c.get(Calendar.DAY_OF_MONTH);

        //Configuramos el datepicker con la fecha actual
        datePickerDialog = new DatePickerDialog(cont, android.R.style.Theme_DeviceDefault_Light_Dialog, MainActivity.this, year_aux, month_aux, day_aux);

        Button buttonAdmin = (Button) findViewById(R.id.buttonAdmin);
        Button buttonRegisterActivity = (Button) findViewById(R.id.buttonRegisterOfActivity);

        buttonAdmin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startAdministrator();
            }
        });

        buttonRegisterActivity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
    }

    private void startAdministrator() {
        Intent intent = new Intent(this, AdminActivity.class);
        startActivity(intent);
    }

    private void startGestorOfActivity(int year, int month, int dayOfMonth) {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("dia", dayOfMonth);
        intent.putExtra("mes", month);
        intent.putExtra("año", year);

        startActivity(intent);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //Lanzamos la actividad
        startGestorOfActivity(year, month, dayOfMonth);
    }
}