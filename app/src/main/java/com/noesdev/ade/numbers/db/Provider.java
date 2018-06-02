package com.noesdev.ade.numbers.db;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.noesdev.ade.numbers.db.DBContract.AUTHORITY;
import static com.noesdev.ade.numbers.db.DBContract.CONTENT_URI;
import static com.noesdev.ade.numbers.db.DBContract.TABLE_FAV;


/**
 * Created by adeyds on 3/4/2018.
 */

public class Provider extends ContentProvider{
    private static final int FAV = 1;
    private static final int FAV_ID = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(AUTHORITY,TABLE_FAV+ "/#",FAV_ID);
        sUriMatcher.addURI(AUTHORITY,TABLE_FAV+ "",FAV);
    }

    private FavHelper favHelper;


    @Override
    public boolean onCreate() {
        favHelper = new FavHelper(getContext());
        favHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch(sUriMatcher.match(uri)){
            case FAV:
                cursor = favHelper.queryProvider();
                break;
            case FAV_ID:
                cursor = favHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null){
            cursor.setNotificationUri(getContext().getContentResolver(),uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long added ;

        switch (sUriMatcher.match(uri)){
            case FAV:
                added = favHelper.insertProvider(values);
                break;
            default:
                added = 0;
                break;
        }

        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case FAV_ID:
                deleted =  favHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri,  ContentValues values,
                      String selection, String[] selectionArgs) {
        int updated ;
        switch (sUriMatcher.match(uri)) {
            case FAV_ID:
                updated =  favHelper.updateProvider(uri.getLastPathSegment(),values);
                //  updated = FavHelper.updateP
                break;
            default:
                updated = 0;
                break;
        }

        if (updated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }
}
