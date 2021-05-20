package com.example.entpe.storage.database_storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.entpe.model.Etablissement;
import com.example.entpe.storage.utility.DataBaseStorage;

import java.io.IOException;

public class EtablissementDataBaseStorage extends DataBaseStorage<Etablissement> {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Private class //////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Classe pour gérer la BDD
     */
    private static class DataBaseHelper extends SQLiteOpenHelper {
        //////////////////////////////////////////////////////////////////
        //// Constants ///////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "Etablissement.db";

        private static final String SQL_CREATE_ETABLISSEMENT_ENTRIES = "CREATE TABLE " + TABLE_NAME + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY,"
                + NAME_SIRET + " TEXT," + NAME_NOM + " TEXT," + NAME_ADRESSE + " TEXT,"
                + NAME_CODE_POSTAL + " TEXT," + NAME_VILLE + " TEXT," + NAME_NATURE + " TEXT)";

        //////////////////////////////////////////////////////////////////
        //// Constructor /////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        public DataBaseHelper(Context context) { super(context, DATABASE_NAME, null, DATABASE_VERSION); }

        //////////////////////////////////////////////////////////////////
        //// Methods /////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        @Override
        public void onCreate(SQLiteDatabase db) { db.execSQL(SQL_CREATE_ETABLISSEMENT_ENTRIES); }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constants //////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public static final String TABLE_NAME = "etablissement";
    private static final int NUM_ID = 0;
    private static final String NAME_SIRET = "siret";
    private static final int NUM_SIRET = 1;
    private static final String NAME_NOM = "nom";
    private static final int NUM_NOM = 2;
    private static final String NAME_ADRESSE = "adresse";
    private static final int NUM_ADRESSE = 3;
    private static final String NAME_CODE_POSTAL = "code_postal";
    private static final int NUM_CODE_POSTAL = 4;
    private static final String NAME_VILLE = "ville";
    private static final int NUM_VILLE = 5;
    private static final String NAME_NATURE = "nature";
    private static final int NUM_NATURE = 6;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private static EtablissementDataBaseStorage STORAGE;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor ////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Constructeur privé
     * @param context - Contexte de l'application
     * @throws IOException - En cas de problème lors de la création de la config
     */
    private EtablissementDataBaseStorage(Context context) throws IOException { super(new DataBaseHelper(context), TABLE_NAME, context); }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Getters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public static EtablissementDataBaseStorage get(Context context) throws IOException {
        if(STORAGE == null)
            STORAGE = new EtablissementDataBaseStorage(context);

        return STORAGE;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    @NonNull
    @Override
    protected ContentValues objectToContentValues(int id, Etablissement object) {
        ContentValues values = new ContentValues();
        values.put(NAME_SIRET, object.getSiret());
        values.put(NAME_NOM, object.getNom());
        values.put(NAME_ADRESSE, object.getAdresse());
        values.put(NAME_CODE_POSTAL, object.getCodePostal());
        values.put(NAME_VILLE, object.getVille());
        values.put(NAME_NATURE, object.getNature());
        return values;
    }

    @Nullable
    @Override
    protected Etablissement cursorToObject(Cursor cursor) {
        return new Etablissement(cursor.getInt(NUM_ID),
                cursor.getString(NUM_SIRET), cursor.getString(NUM_NOM),
                cursor.getString(NUM_ADRESSE), cursor.getString(NUM_CODE_POSTAL),
                cursor.getString(NUM_VILLE), cursor.getString(NUM_NATURE));
    }
}
