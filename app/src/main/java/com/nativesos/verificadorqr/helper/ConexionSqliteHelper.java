package com.nativesos.verificadorqr.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.nativesos.verificadorqr.utility.Utility;

import java.util.Locale;

public class ConexionSqliteHelper extends SQLiteOpenHelper {




    public ConexionSqliteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Utility.CREATE_TABLE_HISTORY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS history");
        onCreate(db);
    }

    public void insertData(@Nullable Context context,ConexionSqliteHelper conn, String getPdf, String date){


        SQLiteDatabase db = conn.getReadableDatabase();

        ContentValues values = new ContentValues();

        values.put(Utility.ID,    String.valueOf(System.currentTimeMillis()));
        values.put(Utility.URL,   getPdf.toLowerCase(Locale.ROOT));
        values.put(Utility.DATE,  date);
        values.put(Utility.DATE_INSIDE,date);
        values.put(Utility.DATE_OURSIDE,date);

        long result = db.insert(Utility.TABLA_HISTORY, Utility.ID, values);

        Toast.makeText(context, "Id registro"+result, Toast.LENGTH_SHORT).show();

        db.close();
    }

}
