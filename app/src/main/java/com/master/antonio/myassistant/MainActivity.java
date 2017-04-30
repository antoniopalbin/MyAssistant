package com.master.antonio.myassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.activeandroid.ActiveAndroid;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Initializing Active Android
        ActiveAndroid.initialize(this);
        setContentView(R.layout.activity_main);

        Button buttonAdmin = (Button) findViewById(R.id.buttonAdmin);
        Button buttonRegisterActivity = (Button) findViewById(R.id.buttonRegisterOfActivity);

        buttonAdmin.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startAdministrator();
            }
        });

        buttonRegisterActivity.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startGestorOfActivity();
            }
        });
    }

    private void  startAdministrator(){
        Intent intent = new Intent(this, AdminActivity.class);
        startActivity(intent);
    }

    private void  startGestorOfActivity(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);

    }
}