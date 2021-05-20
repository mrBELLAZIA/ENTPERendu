package com.example.entpe.storage.database_storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.entpe.model.Vehicule;
import com.example.entpe.storage.utility.DataBaseStorage;

import java.io.IOException;

public class VehiculeDataBaseStorage extends DataBaseStorage<Vehicule> {
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
        private static final String DATABASE_NAME = "Vehicule.db";

        private static final String SQL_CREATE_VEHICULE_ENTRIES = "CREATE TABLE " + TABLE_NAME + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY,"
                + NAME_PLAQUE + " TEXT," + NAME_MARQUE + " TEXT," + NAME_MODELE + " TEXT,"
                + NAME_TYPE + " INTEGER," + NAME_CATEGORIE + " INTEGER," + NAME_MOTORISATION + " INTEGER,"
                + NAME_PTAC + " REAL," + NAME_PTRA + " REAL," + NAME_POIDS_VIDE + " REAL,"
                + NAME_CHARGE_UTILE + " REAL," + NAME_AGE + " INTEGER," + NAME_SURFACE_SOL + " REAL,"
                + NAME_LARGEUR + " REAL," + NAME_LONGUEUR + " REAL," + NAME_HAUTEUR + " REAL,"
                + NAME_ETABLISSEMENT_ID + " INTEGER NOT NULL,"
                + NAME_CRITAIR  + " INTEGER,"
                + NAME_PHOTO +" TEXT,"
                + "FOREIGN KEY (" + NAME_ETABLISSEMENT_ID + ") REFERENCES "
                + EtablissementDataBaseStorage.TABLE_NAME + " (" + BaseColumns._ID + "))";

        //////////////////////////////////////////////////////////////////
        //// Constructor /////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        public DataBaseHelper(Context context) { super(context, DATABASE_NAME, null, DATABASE_VERSION); }

        //////////////////////////////////////////////////////////////////
        //// Methods /////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        @Override
        public void onCreate(SQLiteDatabase db) { db.execSQL(SQL_CREATE_VEHICULE_ENTRIES); }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constants //////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public static final String TABLE_NAME = "vehicule";
    private static final int NUM_ID = 0;
    
    private static final String NAME_PLAQUE = "plaque";
    private static final int NUM_PLAQUE = 1;
    private static final String NAME_MARQUE = "marque";
    private static final int NUM_MARQUE = 2;
    private static final String NAME_MODELE = "modele";
    private static final int NUM_MODELE = 3;
    private static final String NAME_TYPE = "type";
    private static final int NUM_TYPE = 4;
    private static final String NAME_CATEGORIE = "categorie";
    private static final int NUM_CATEGORIE = 5;
    private static final String NAME_MOTORISATION = "motorisation";
    private static final int NUM_MOTORISATION = 6;

    private static final String NAME_PTAC = "ptac";
    private static final int NUM_PTAC = 7;
    private static final String NAME_PTRA = "ptra";
    private static final int NUM_PTRA = 8;
    private static final String NAME_POIDS_VIDE = "poidsVide";
    private static final int NUM_POIDS_VIDE = 9;
    private static final String NAME_CHARGE_UTILE = "chargeUtile";
    private static final int NUM_CHARGE_UTILE = 10;
    
    private static final String NAME_AGE = "age";
    private static final int NUM_AGE = 11;
    private static final String NAME_SURFACE_SOL = "surfaceSol";
    private static final int NUM_SURFACE_SOL = 12;
    private static final String NAME_LARGEUR = "largeur";
    private static final int NUM_LARGEUR = 13;
    private static final String NAME_LONGUEUR = "longueur";
    private static final int NUM_LONGUEUR = 14;
    private static final String NAME_HAUTEUR = "hauteur";
    private static final int NUM_HAUTEUR = 15;

    private static final String NAME_ETABLISSEMENT_ID = "etablissement_id";
    private static final int NUM_ETABLISSEMENT_ID = 16;

    private static final String NAME_CRITAIR = "critair";
    private static final int NUM_CRITAIR = 17;

    private static final String NAME_PHOTO = "photo";
    private static final int NUM_PHOTO = 18;
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private static VehiculeDataBaseStorage STORAGE;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor ////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Constructeur privé
     * @param context - Contexte de l'application
     * @throws IOException - En cas de problème lors de la création de la config
     */
    private VehiculeDataBaseStorage(Context context) throws IOException { super(new DataBaseHelper(context), TABLE_NAME, context); }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Getters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public static VehiculeDataBaseStorage get(Context context) throws IOException {
        if(STORAGE == null)
            STORAGE = new VehiculeDataBaseStorage(context);

        return STORAGE;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    @NonNull
    @Override
    protected ContentValues objectToContentValues(int id, Vehicule object) {
        ContentValues values = new ContentValues();
            values.put(NAME_PLAQUE, object.getPlaque());
            values.put(NAME_MARQUE, object.getMarque());
            values.put(NAME_MODELE, object.getModele());
            values.put(NAME_TYPE, object.getType());
            values.put(NAME_CATEGORIE, object.getCategorie());
            values.put(NAME_MOTORISATION, object.getMotorisation());

            values.put(NAME_PTAC, object.getPtac());
            values.put(NAME_PTRA, object.getPtra());
            values.put(NAME_POIDS_VIDE, object.getPoidsVide());
            values.put(NAME_CHARGE_UTILE, object.getChargeUtile());

            values.put(NAME_AGE, object.getAge());
            values.put(NAME_SURFACE_SOL, object.getSurfaceSol());
            values.put(NAME_LARGEUR, object.getLargeur());
            values.put(NAME_LONGUEUR, object.getLongueur());
            values.put(NAME_HAUTEUR, object.getHauteur());
            values.put(NAME_CRITAIR,object.getCritAir());
            values.put(NAME_ETABLISSEMENT_ID, object.getEtablissementId());
            values.put(NAME_PHOTO,object.getPhoto());
        return values;
    }

    @Nullable
    @Override
    protected Vehicule cursorToObject(Cursor cursor) {
        return new Vehicule(
                cursor.getInt(NUM_ID),
                cursor.getString(NUM_PLAQUE), cursor.getString(NUM_MARQUE),
                cursor.getString(NUM_MODELE), cursor.getInt(NUM_TYPE),
                cursor.getInt(NUM_CATEGORIE), cursor.getInt(NUM_MOTORISATION),
                /*-------------------------------------------------------------------------------*/
                cursor.getLong(NUM_PTAC), cursor.getLong(NUM_PTRA),
                cursor.getLong(NUM_POIDS_VIDE), cursor.getLong(NUM_CHARGE_UTILE),
                /*-------------------------------------------------------------------------------*/
                cursor.getInt(NUM_AGE), cursor.getLong(NUM_SURFACE_SOL),
                cursor.getLong(NUM_LARGEUR), cursor.getLong(NUM_LONGUEUR),
                cursor.getLong(NUM_HAUTEUR), cursor.getInt(NUM_ETABLISSEMENT_ID),
                cursor.getInt(NUM_CRITAIR), cursor.getString(NUM_PHOTO));
    }


}
