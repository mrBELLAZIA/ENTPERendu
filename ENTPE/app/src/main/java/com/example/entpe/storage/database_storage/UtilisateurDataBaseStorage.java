package com.example.entpe.storage.database_storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.entpe.model.Utilisateur;
import com.example.entpe.storage.utility.DataBaseStorage;

import java.io.IOException;

public class UtilisateurDataBaseStorage extends DataBaseStorage<Utilisateur> {
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
        private static final String DATABASE_NAME = "Utilisateur.db";

        private static final String SQL_CREATE_UTILISATEUR_ENTRIES = "CREATE TABLE " + TABLE_NAME + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY,"
                + NAME_NOM + " TEXT," + NAME_PRENOM + " TEXT," + NAME_NUMERO + " INTEGER,"
                + NAME_PASSWORD + " TEXT)";

        //////////////////////////////////////////////////////////////////
        //// Constructor /////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        public DataBaseHelper(Context context) { super(context, DATABASE_NAME, null, DATABASE_VERSION); }

        //////////////////////////////////////////////////////////////////
        //// Methods /////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        @Override
        public void onCreate(SQLiteDatabase db) { db.execSQL(SQL_CREATE_UTILISATEUR_ENTRIES); }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constants //////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public static final String TABLE_NAME = "utilisateur";
    private static final int NUM_ID = 0;
    private static final String NAME_NOM = "nom";
    private static final int NUM_NOM = 1;
    private static final String NAME_PRENOM = "prenom";
    private static final int NUM_PRENOM = 2;
    private static final String NAME_NUMERO = "numero";
    private static final int NUM_NUMERO = 3;
    private static final String NAME_PASSWORD = "password";
    private static final int NUM_PASSWORD = 4;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private static UtilisateurDataBaseStorage STORAGE;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor ////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Constructeur privé
     * @param context - Contexte de l'application
     * @throws IOException - En cas de problème lors de la création de la config
     */
    private UtilisateurDataBaseStorage(Context context) throws IOException { super(new DataBaseHelper(context), TABLE_NAME, context); }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Getters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public static UtilisateurDataBaseStorage get(Context context) throws IOException {
        if(STORAGE == null)
            STORAGE = new UtilisateurDataBaseStorage(context);

        return STORAGE;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    @NonNull
    @Override
    protected ContentValues objectToContentValues(int id, Utilisateur object) {
        ContentValues values = new ContentValues();
            values.put(NAME_NOM, object.getNom());
            values.put(NAME_PRENOM, object.getPrenom());
            values.put(NAME_NUMERO, object.getNumero());
            values.put(NAME_PASSWORD, object.getPassword());
        return values;
    }

    @Nullable
    @Override
    protected Utilisateur cursorToObject(Cursor cursor) {
        return new Utilisateur(cursor.getInt(NUM_ID),
                cursor.getString(NUM_NOM), cursor.getString(NUM_PRENOM),
                cursor.getInt(NUM_NUMERO), cursor.getString(NUM_PASSWORD));
    }
}
