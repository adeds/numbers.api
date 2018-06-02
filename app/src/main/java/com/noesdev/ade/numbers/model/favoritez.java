package com.noesdev.ade.numbers.model;

import android.database.Cursor;

import static com.noesdev.ade.numbers.db.DBContract.FavKolom.DESC_KOLOM;
import static com.noesdev.ade.numbers.db.DBContract.FavKolom.ID_NOMOR;
import static com.noesdev.ade.numbers.db.DBContract.FavKolom.NOMOR_KOLOM;
import static com.noesdev.ade.numbers.db.DBContract.getColumnInt;
import static com.noesdev.ade.numbers.db.DBContract.getColumnString;


public class favoritez {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    String desc, nomor;

    public favoritez(Cursor cursor) {

        this.desc = getColumnString(cursor, DESC_KOLOM);
        this.nomor = getColumnString(cursor, NOMOR_KOLOM);
        this.id = getColumnInt(cursor, ID_NOMOR);

    }
}