package com.noesdev.ade.numbers.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.widget.BaseAdapter;

/**
 * Created by adeyds on 3/4/2018.
 */

public class DBContract {
    public static String TABLE_FAV = "favorit";
    public static final String AUTHORITY = "com.noesdev.ade.numbers";

    public static final class FavKolom implements BaseColumns{
        public static String ID_NOMOR= "id_nomor";
        public static String DESC_KOLOM = "desc_kolom";
        public static String NOMOR_KOLOM = "nomor_kolom";

    }

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_FAV)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }

}
