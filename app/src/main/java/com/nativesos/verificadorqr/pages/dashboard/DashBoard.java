/*
 * Copyright (c) $2019 NativeCode All Rights Reserved
 * This product is protected by copyright and distributed under licenses restricting copying,distribution, and decompilation.
 */

package com.nativesos.verificadorqr.pages.dashboard;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.nativesos.verificadorqr.R;
import com.nativesos.verificadorqr.helper.ConexionSqliteHelper;
import com.nativesos.verificadorqr.pages.history.History;
import com.nativesos.verificadorqr.pages.qr.ScannerQr;
import com.nativesos.verificadorqr.utility.Utility;

import org.w3c.dom.Text;


public class DashBoard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar app_bar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    TextView textEntradas;
    TextView textSalidas;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);


        navigationView = (NavigationView) findViewById(R.id.navigationView);
        app_bar = (Toolbar) findViewById(R.id.appBar);
        app_bar.setTitle("");
        app_bar.setSubtitle("");


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        textSalidas = (TextView) findViewById(R.id.textSalidas);
        textEntradas = (TextView) findViewById(R.id.textEntradas);


        // Start Import Data Sellers
        navigationView.setFitsSystemWindows(true);
        navigationView.setNavigationItemSelectedListener(this);

        setSupportActionBar(app_bar);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, app_bar, R.string.open, R.string.close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        totalEntradasYSalidas();
    }


    @Override
    protected void onResume() {
         final long PERIODO = 5000; // 60 segundos (60 * 1000 millisegundos)
        Handler handler;
        Runnable runnable;
        super.onResume();


        handler = new Handler();
        runnable = new Runnable(){
            @Override
            public void run(){

                totalEntradasYSalidas();
                handler.postDelayed(this, PERIODO);

            }
        };
        handler.postDelayed(runnable, PERIODO);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        final Intent history = new Intent(this, History.class);
        final Intent escanearQr = new Intent(this, ScannerQr.class);


        if(item.getItemId() == R.id.history) startActivity(history);
        if(item.getItemId() == R.id.escanear) startActivity(escanearQr);


        return false;

    }


    void totalEntradasYSalidas(){

        ConexionSqliteHelper conn = new ConexionSqliteHelper(this, "bd_history", null, 1);

        SQLiteDatabase db = conn.getReadableDatabase();
        ContentValues values = new ContentValues();

        //Consultamos los datos
        Cursor entradas = db.rawQuery("SELECT * FROM " + Utility.TABLA_HISTORY , null);

        Cursor salidas = db.rawQuery("SELECT * FROM " + Utility.TABLA_HISTORY + " WHERE " + Utility.DATE_OURSIDE +" like \"%:%\" ",null);


        textEntradas.setText(String.valueOf(entradas.getCount()));
        textSalidas.setText(String.valueOf(salidas.getCount()));
    }


}

