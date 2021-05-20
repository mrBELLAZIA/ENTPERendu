package com.example.entpe.storage.database_storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.entpe.model.Settings;
import com.example.entpe.storage.utility.DataBaseStorage;

import java.io.IOException;

public class SettingDataBaseStorage extends DataBaseStorage<Settings> {

    private static class DataBaseHelper extends SQLiteOpenHelper {
        //////////////////////////////////////////////////////////////////
        //// Constants ///////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "entpe.db";

        private static final String SQL_CREATE_ARRET_ENTRIES = "CREATE TABLE " + TABLE_NAME + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY," + NAME_SETTINGS + " TEXT,"
                + NAME_CONSOMMATION + " INTEGER," + NAME_PRECISION + " INTEGER," + NAME_PERIODE + " INTEGER"
                + ")";


        //////////////////////////////////////////////////////////////////
        //// Constructor /////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        public DataBaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        //////////////////////////////////////////////////////////////////
        //// Methods /////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ARRET_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constants //////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private static final String TABLE_NAME = "settings";
    private static final int NUM_ID = 0;
    private static final String NAME_SETTINGS= "nom";
    private static final int NUM_SETTINGS= 1;
    private static final String NAME_CONSOMMATION= "consommmation";
    private static final int NUM_CONSOMMATION= 2;
    private static final String NAME_PRECISION = "précision";
    private static final int NUM_PRECISION = 3;
    private static final String NAME_PERIODE = "intervalle";
    private static final int NUM_PERIODE = 4;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private static SettingDataBaseStorage STORAGE;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor ////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Constructeur privé
     * @param context - Contexte de l'application
     * @throws IOException - En cas de problème lors de la création de la config
     */
    private SettingDataBaseStorage(Context context) throws IOException { super(new SettingDataBaseStorage.DataBaseHelper(context), TABLE_NAME, context); }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Getters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public static SettingDataBaseStorage get(Context context) throws IOException {
        if(STORAGE == null)
            STORAGE = new SettingDataBaseStorage(context);

        return STORAGE;
    }

    @NonNull
    @Override
    protected ContentValues objectToContentValues(int id, Settings object) {
        ContentValues values = new ContentValues();
        values.put(NAME_SETTINGS, object.getNomSettings());
        values.put(NAME_CONSOMMATION, object.getConsommation());
        values.put(NAME_PRECISION, object.getPrecision());
        values.put(NAME_PERIODE, object.getPeriode());
        return values;
    }

    @Nullable
    @Override
    protected Settings cursorToObject(Cursor cursor) {
        return new Settings(cursor.getInt(NUM_ID),cursor.getString(NUM_SETTINGS), cursor.getInt(NUM_CONSOMMATION), cursor.getInt(NUM_PRECISION), cursor.getInt(NUM_PERIODE));
    }
}
