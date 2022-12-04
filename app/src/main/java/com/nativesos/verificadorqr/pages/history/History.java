package com.nativesos.verificadorqr.pages.history;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.nativesos.verificadorqr.R;
import com.nativesos.verificadorqr.helper.ConexionSqliteHelper;
import com.nativesos.verificadorqr.utility.Utility;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {


    TableLayout listTabletLayout;

    TableRow contentRow;

    TextView idTextView;
    TextView fechaTextView;
    TextView insideTextView;
    TextView outsideTextView;
    TextView urlTextView;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        consultarDatabase();
    }





    private void consultarDatabase(){

        try {

            ConexionSqliteHelper conn = new ConexionSqliteHelper(this, "bd_history", null, 1);

            SQLiteDatabase db = conn.getReadableDatabase();

            listTabletLayout = (TableLayout) findViewById(R.id.tableHistory);

            //Consultamos los datos
            Cursor c = db.rawQuery("SELECT *  FROM " + Utility.TABLA_HISTORY, null);


            if (c != null) {


                c.moveToFirst();
                do {


                    idTextView = (TextView) new TextView(getBaseContext());
                    fechaTextView = (TextView) new TextView(getBaseContext());
                    insideTextView = (TextView) new TextView(getBaseContext());
                    outsideTextView = (TextView) new TextView(getBaseContext());
                    urlTextView = (TextView) new TextView(getBaseContext());


                    contentRow = (TableRow) new TableRow(getBaseContext());

                    //Asignamos el valor en nuestras variables para usarlos en lo que necesitemos
                    @SuppressLint("Range") String id = c.getString(c.getColumnIndex(Utility.ID));
                    @SuppressLint("Range") String date = c.getString(c.getColumnIndex(Utility.DATE));
                    @SuppressLint("Range") String inside = c.getString(c.getColumnIndex(Utility.DATE_INSIDE));
                    @SuppressLint("Range") String outside = c.getString(c.getColumnIndex(Utility.DATE_OURSIDE));
                    @SuppressLint("Range") String url = c.getString(c.getColumnIndex(Utility.URL));


                    contentRow.addView(addDataToTextView(id, idTextView));
                    contentRow.addView(addDataToTextView(date, fechaTextView));
                    contentRow.addView(addDataToTextView(inside, insideTextView));
                    contentRow.addView(addDataToTextView(outside, outsideTextView));
                    contentRow.addView(addDataToTextView(url, urlTextView));

                    listTabletLayout.addView(contentRow);


                } while (c.moveToNext());

            }

            //Cerramos el cursor y la conexion con la base de datos
            assert c != null;
            c.close();
            db.close();

        }catch (Exception e) {

            e.fillInStackTrace();
        }
    }





    private TextView addDataToTextView(String data, TextView textView){

        textView.setPadding(10,5,10,5);
        textView.setBackgroundResource(R.color.white);
        textView.setTextSize(9);
        textView.setMaxLines(5);
        textView.setWidth(150);
        textView.setHeight(70);
        textView.setGravity(Gravity.CENTER);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setTextIsSelectable(true);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setText(data);

        return textView;
    }
}
