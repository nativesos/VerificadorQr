package com.nativesos.verificadorqr.pages.qr;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.nativesos.verificadorqr.R;
import com.nativesos.verificadorqr.helper.ConexionSqliteHelper;
import com.nativesos.verificadorqr.pages.history.History;
import com.nativesos.verificadorqr.utility.Utility;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ScannerQr extends AppCompatActivity {

    LinearLayout buttonLayout;

    private String pdf = "";
    WebView webView;
    FloatingActionButton savePdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_qr);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        new IntentIntegrator(this).initiateScan();

        localInitialization();


        /// Intent para ir a historial

        final Intent historial = new Intent(this, History.class);

        savePdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isNotRegisteruser( historial );

            }
        });

    }

    private void registrarHitorial(Intent historyIntent) {


        try {

            ConexionSqliteHelper conn = new ConexionSqliteHelper(this, "bd_history", null, 1);
            SQLiteDatabase db = conn.getReadableDatabase();

            ContentValues values = new ContentValues();

            values.put(Utility.ID, String.valueOf(System.currentTimeMillis()));
            values.put(Utility.URL, getPdf() );
            values.put(Utility.DATE, getDay());
            values.put(Utility.DATE_INSIDE, getHour());
            values.put(Utility.DATE_OURSIDE, "");

            long result = db.insert(Utility.TABLA_HISTORY, Utility.ID, values);

            Toast.makeText(this, result + ". Registro realizado, hora de entrada: " + values.get(Utility.DATE_INSIDE), Toast.LENGTH_SHORT).show();

            db.close();

            startActivity(historyIntent);
            finish();

        }catch (Exception e) {
            Toast.makeText(this, "Error al intentar registrar el usuario ", Toast.LENGTH_SHORT).show();
        }

    }


    /// Visualizacion de datos que se obtienen del QR
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        setPdf(result.getContents());

        getWebView( result.getContents() );


    }



    @SuppressLint("SetJavaScriptEnabled")
    private void localInitialization(){

        webView = (WebView) findViewById(R.id.webview);


        savePdf = (FloatingActionButton) findViewById(R.id.savePdf);

        buttonLayout = (LinearLayout) findViewById(R.id.buttonLayout);

    }


    @SuppressLint("SetJavaScriptEnabled")
    private void getWebView(String url){

        // se muestra el dato en la pantalla
        try {

            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setAllowFileAccess(true);
            webView.getSettings().setAllowContentAccess(true);
            webView.getSettings().setAllowUniversalAccessFromFileURLs(true);

            webView.loadUrl(url);

        } catch (Exception e) {

            e.printStackTrace();

        }


        buttonLayout.setVisibility(View.VISIBLE);

    }



    private void isNotRegisteruser(Intent historyIntent){

        try {

            ConexionSqliteHelper conn = new ConexionSqliteHelper(this, "bd_history", null, 1);

            SQLiteDatabase db = conn.getReadableDatabase();
            ContentValues values = new ContentValues();

            //Consultamos los datos
            Cursor c = db.rawQuery("SELECT * FROM " + Utility.TABLA_HISTORY + " WHERE " + Utility.URL +" =?", new String[]{getPdf()});


            if ( c.getCount() > 0 ) {


                c.moveToFirst();

                do {

                    //Asignamos el valor en nuestras variables para usarlos en lo que necesitemos
                    @SuppressLint("Range") String id = c.getString(c.getColumnIndex(Utility.ID));
                    @SuppressLint("Range") String date = c.getString(c.getColumnIndex(Utility.DATE));
                    @SuppressLint("Range") String inside = c.getString(c.getColumnIndex(Utility.DATE_INSIDE));
                    @SuppressLint("Range") String outside = c.getString(c.getColumnIndex(Utility.DATE_OURSIDE));
                    @SuppressLint("Range") String url = c.getString(c.getColumnIndex(Utility.URL));


                    if(getPdf().equals(url) && getDay().equals(date) ){

                        if(outside.isEmpty()){

                            values.put(Utility.DATE_OURSIDE, getHour());

                            db.update(Utility.TABLA_HISTORY, values, Utility.ID +"=?" ,new String[]{id});

                            Toast.makeText(this, "Hora de salida: " + id, Toast.LENGTH_LONG).show();

                        }else{

                            Toast.makeText(this, "Hora de salida ya fue establecida para el dia: " + getDay(), Toast.LENGTH_LONG).show();
                        }
                    }else{

                        toDashboardOrRegister(historyIntent, c, db, 2);
                    }

                } while (c.moveToNext());


                toDashboardOrRegister(historyIntent, c, db, 1);

            } else {

                toDashboardOrRegister(historyIntent, c, db, 2);
            }

        }catch (Exception e) {

            Toast.makeText(this, "Error al consultar la base de datos", Toast.LENGTH_LONG).show();

        }

    }



    public String getPdf() {
        return pdf.replace("https://","");
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }



    public String getHour(){
        return new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis()));
    }


    public String getDay(){
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
    }

    void toDashboardOrRegister(Intent intent, Cursor c, SQLiteDatabase db, int type){

        c.close();
        db.close();
        if(type == 1){
            startActivity(intent);
        }else{
            registrarHitorial(intent);
        }
        finish();

    }
}