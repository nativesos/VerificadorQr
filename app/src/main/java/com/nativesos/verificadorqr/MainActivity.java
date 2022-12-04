/*
 * Copyright (c) 2019 NativeSoS All Rights Reserved
 * This product is protected by copyright and distributed under licenses restricting copying,distribution, and decompilation.
 */

package com.nativesos.verificadorqr;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;

import com.nativesos.verificadorqr.pages.dashboard.DashBoard;


public class MainActivity extends AppCompatActivity {


    EditText emailLogin;
    EditText passwordLogin;
    Button loginButton;


    SharedPreferences userPreferences;
    SharedPreferences.Editor editor;



    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Esto es para que la orientacion de la pantalla siempre sea verticcal.
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        localInitialization();

        final Intent dashboard = new Intent(this, DashBoard.class);

        // Login Client or Seller
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(dashboard);

            }
        });


    }



    // Inicializacion de componentes basicos
    private  void localInitialization(){

        userPreferences = this.getPreferences(Context.MODE_PRIVATE);
        editor = userPreferences.edit();

        loginButton     = (Button)   findViewById(R.id.loginButton);


        passwordLogin   = (EditText) findViewById(R.id.passwordLogin);
        passwordLogin.setInputType(129);

        emailLogin      = (EditText) findViewById(R.id.emailLogin);
        emailLogin.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

    }



}