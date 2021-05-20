package com.example.entpe.storage.database_storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.entpe.model.UtilisateurHasEtablissement;
import com.example.entpe.storage.utility.DataBaseStorage;

import java.io.IOException;

public class UtilisateurHasEtablissementDBStorage extends DataBaseStorage<UtilisateurHasEtablissement> {
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
        private static final String DATABASE_NAME = "UtilisateurHasEtablissement.db";

        private static final String SQL_CREATE_UTILISATEUR_HAS_ETABLISSEMENT_ENTRIES = "CREATE TABLE " + TABLE_NAME + " ("
                + NAME_ID_UTILISATEUR + " INTEGER," + NAME_ID_ETABLISSEMENT + " INTEGER," + NAME_EST_ADMIN + " INTEGER,"
                + " PRIMARY KEY (" + NAME_ID_UTILISATEUR + ", " + NAME_ID_ETABLISSEMENT + "),"
                + " FOREIGN KEY (" + NAME_ID_UTILISATEUR + ") REFERENCES " + UtilisateurDataBaseStorage.TABLE_NAME + " (" + BaseColumns._ID + "),"
                + " FOREIGN KEY (" + NAME_ID_ETABLISSEMENT + ") REFERENCES " + EtablissementDataBaseStorage.TABLE_NAME + " (" + BaseColumns._ID + "))";

        //////////////////////////////////////////////////////////////////
        //// Constructor /////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        public DataBaseHelper(Context context) { super(context, DATABASE_NAME, null, DATABASE_VERSION); }

        //////////////////////////////////////////////////////////////////
        //// Methods /////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        @Override
        public void onCreate(SQLiteDatabase db) { db.execSQL(SQL_CREATE_UTILISATEUR_HAS_ETABLISSEMENT_ENTRIES); }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constants //////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private static final String TABLE_NAME = "utilisateur_has_etablissement";
    private static final String NAME_ID_UTILISATEUR = "id_utilisateur";
    private static final int NUM_ID_UTILISATEUR = 0;
    private static final String NAME_ID_ETABLISSEMENT = "id_etablissement";
    private static final int NUM_ID_ETABLISSEMENT = 1;
    private static final String NAME_EST_ADMIN = "est_admin";
    private static final int NUM_EST_ADMIN = 2;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private static UtilisateurHasEtablissementDBStorage STORAGE;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor ////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Constructeur privé
     * @param context - Contexte de l'application
     * @throws IOException - En cas de problème lors de la création de la config
     */
    private UtilisateurHasEtablissementDBStorage(Context context) throws IOException { super(new UtilisateurHasEtablissementDBStorage.DataBaseHelper(context), TABLE_NAME, context); }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Getters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public static UtilisateurHasEtablissementDBStorage get(Context context) throws IOException {
        if(STORAGE == null)
            STORAGE = new UtilisateurHasEtablissementDBStorage(context);

        return STORAGE;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    @NonNull
    @Override
    protected ContentValues objectToContentValues(int id, UtilisateurHasEtablissement object) {
        ContentValues values = new ContentValues();
            values.put(NAME_ID_UTILISATEUR, object.getIdUtilisateur());
            values.put(NAME_ID_ETABLISSEMENT, object.getIdEtablissement());
            values.put(NAME_EST_ADMIN, object.estAdmin() ? 1 : 0);
        return values;
    }

    @Nullable
    @Override
    protected UtilisateurHasEtablissement cursorToObject(Cursor cursor) {
        return new UtilisateurHasEtablissement(cursor.getInt(NUM_ID_UTILISATEUR), cursor.getInt(NUM_ID_ETABLISSEMENT), cursor.getInt(NUM_EST_ADMIN) == 1);
    }
}
