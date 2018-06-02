package com.noesdev.ade.numbers.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by adeyds on 3/4/2018.
 */


public class DBHelper extends SQLiteOpenHelper {

    public static int DB_VERSION = 1;
    public static String DB_NAME = "Primen";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "create table " + DBContract.TABLE_FAV + " (" +
                DBContract.FavKolom.ID_NOMOR+ " integer primary key autoincrement, " +
                 DBContract.FavKolom.DESC_KOLOM+ " text not null, " +
                DBContract.FavKolom.NOMOR_KOLOM + " text not null );";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ DBContract.TABLE_FAV);
        onCreate(db);
    }
}

