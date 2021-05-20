package com.example.entpe.storage.database_storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.entpe.model.Arret;
import com.example.entpe.storage.utility.DataBaseStorage;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ArretDataBaseStorage extends DataBaseStorage<Arret> {
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
        private static final String DATABASE_NAME = "Arret.db";

        private static final String SQL_CREATE_ARRET_ENTRIES = "CREATE TABLE " + TABLE_NAME + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY,"+ NAME_ARRET +" INTEGER,"
                + NAME_HEURE_ARRIVEE+ " TEXT," + NAME_LIEU_STATIONNEMENT + " TEXT," + NAME_NUM_CLIENT + " INTEGER,"
                + NAME_ADRESSE+" TEXT,"
                + NAME_NATURE_LIEU + " TEXT," + NAME_NATURE_ACTION + " TEXT," + NAME_LIEU_ACTION + " TEXT,"
                + NAME_MOYEN_MANUTENTION + " TEXT," + NAME_NATURE_CONDITIONNEMENT + " TEXT," + NAME_NOMBRE_UNITE + " INTEGER,"
                + NAME_NATURE_MARCHANDISE + " TEXT," + NAME_POIDS + " REAL," + NAME_VOLUME + " REAL,"
                + NAME_OPERATION_EFFECTUEE + " TEXT," + NAME_HEURE_DEPART + " TEXT," + NAME_COMMENTAIRE + " TEXT,"
                + NAME_PHOTO +" BLOB,"+ NAME_LONGITUDE+" REAL,"+NAME_LATITUDE+" REAL,"
                + NAME_ENQUETE_ID + " INTEGER NOT NULL," + "FOREIGN KEY (" + NAME_ENQUETE_ID + ")"
                + "REFERENCES " + EnqueteDataBaseStorage.TABLE_NAME + " (" + BaseColumns._ID + "))";

        //////////////////////////////////////////////////////////////////
        //// Constructor /////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        public DataBaseHelper(Context context) { super(context, DATABASE_NAME, null, DATABASE_VERSION); }

        //////////////////////////////////////////////////////////////////
        //// Methods /////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        @Override
        public void onCreate(SQLiteDatabase db) { db.execSQL(SQL_CREATE_ARRET_ENTRIES); }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constants //////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private static final String TABLE_NAME = "arret";
    private static final int NUM_ID = 0;
    private static final String NAME_ARRET= "num_arret";
    private static final int NUM_ARRET= 1;
    private static final String NAME_HEURE_ARRIVEE= "heure_arrivee";
    private static final int NUM_HEURE_ARRIVEE= 2;
    private static final String NAME_LIEU_STATIONNEMENT = "lieu_stationnement";
    private static final int NUM_LIEU_STATIONNEMENT = 3;
    private static final String NAME_NUM_CLIENT = "num_client";
    private static final int NUM_NUM_CLIENT = 4;
    private static final String NAME_ADRESSE = "adresse";
    private static final int NUM_ADRESSE = 5;
    private static final String NAME_NATURE_LIEU = "nature_lieu";
    private static final int NUM_NATURE_LIEU = 6;
    private static final String NAME_NATURE_ACTION = "nature_action"; // "action" est un mot-clé SQLite
    private static final int NUM_NATURE_ACTION = 7;
    private static final String NAME_LIEU_ACTION = "lieu_action";
    private static final int NUM_LIEU_ACTION = 8;
    private static final String NAME_MOYEN_MANUTENTION = "moyen_manutention";
    private static final int NUM_MOYEN_MANUTENTION = 9;
    private static final String NAME_NATURE_CONDITIONNEMENT = "nature_conditionnement";
    private static final int NUM_NATURE_CONDITIONNEMENT = 10;
    private static final String NAME_NOMBRE_UNITE = "nombre_unite";
    private static final int NUM_NOMBRE_UNITE = 11;
    private static final String NAME_NATURE_MARCHANDISE = "nature_marchandise";
    private static final int NUM_NATURE_MARCHANDISE = 12;
    private static final String NAME_POIDS = "poids";
    private static final int NUM_POIDS = 13;
    private static final String NAME_VOLUME = "volume";
    private static final int NUM_VOLUME = 14;
    private static final String NAME_OPERATION_EFFECTUEE = "operation_effectuee";
    private static final int NUM_OPERATION_EFFECTUEE = 15;
    private static final String NAME_HEURE_DEPART = "heure_depart";
    private static final int NUM_HEURE_DEPART = 16;
    private static final String NAME_COMMENTAIRE = "commentaire";
    private static final int NUM_COMMENTAIRE = 17;
    private static final String NAME_PHOTO = "photo";
    private static final int NUM_PHOTO = 18;
    private static final String NAME_ENQUETE_ID = "enquete_id";
    private static final int NUM_ENQUETE_ID = 21;
    private static final String NAME_LONGITUDE = "longitude";
    private static final int NUM_LONGITUDE = 19;
    private static final String NAME_LATITUDE = "latitude";
    private static final int NUM_LATITUDE = 20;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private static ArretDataBaseStorage STORAGE;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor ////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Constructeur privé
     * @param context - Contexte de l'application
     * @throws IOException - En cas de problème lors de la création de la config
     */
    private ArretDataBaseStorage(Context context) throws IOException { super(new ArretDataBaseStorage.DataBaseHelper(context), TABLE_NAME, context); }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Getters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public static ArretDataBaseStorage get(Context context) throws IOException {
        if(STORAGE == null) STORAGE = new ArretDataBaseStorage(context);

        return STORAGE;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    @NonNull
    @Override
    protected ContentValues objectToContentValues(int id, Arret object) {
        ContentValues values = new ContentValues();
        values.put(NAME_ARRET, object.getNum_arret());
        values.put(NAME_HEURE_ARRIVEE, object.getHeureArrivee().toString());
        values.put(NAME_LIEU_STATIONNEMENT, object.getLieuStationnement());
        values.put(NAME_NUM_CLIENT, object.getNumClient());
        values.put(NAME_ADRESSE,object.getAdresseClient());
        values.put(NAME_NATURE_LIEU, object.getNatureLieu());
        values.put(NAME_NATURE_ACTION, object.getAction());
        values.put(NAME_LIEU_ACTION, object.getLieuAction());
        values.put(NAME_MOYEN_MANUTENTION, object.getMoyenManutention());
        values.put(NAME_NATURE_CONDITIONNEMENT, object.getNatureConditionnement());
        values.put(NAME_NOMBRE_UNITE, object.getNombreUnite());
        values.put(NAME_NATURE_MARCHANDISE, object.getNatureMarchandise());
        values.put(NAME_POIDS, object.getPoids());
        values.put(NAME_VOLUME, object.getVolume());
        values.put(NAME_OPERATION_EFFECTUEE, object.getOperationEffectuee());
        values.put(NAME_HEURE_DEPART, object.getHeureDepart().toString());
        values.put(NAME_COMMENTAIRE, object.getCommentaire());
        values.put(NAME_PHOTO,object.getPhoto());
        values.put(NAME_ENQUETE_ID, object.getEnqueteId());
        values.put(NAME_LONGITUDE, object.getLongitude());
        values.put(NAME_LATITUDE, object.getLatitude());
        return values;
    }

    @Nullable
    @Override
    protected Arret cursorToObject(Cursor cursor) {
        return new Arret(cursor.getInt(NUM_ID),cursor.getInt(NUM_ARRET),
                new Date(cursor.getString(NUM_HEURE_ARRIVEE)), cursor.getString(NUM_LIEU_STATIONNEMENT),
                cursor.getInt(NUM_NUM_CLIENT),cursor.getString(NUM_ADRESSE), cursor.getString(NUM_NATURE_LIEU),
                cursor.getString(NUM_NATURE_ACTION), cursor.getString(NUM_LIEU_ACTION),
                cursor.getString(NUM_MOYEN_MANUTENTION), cursor.getString(NUM_NATURE_CONDITIONNEMENT),
                cursor.getInt(NUM_NOMBRE_UNITE), cursor.getString(NUM_NATURE_MARCHANDISE),
                cursor.getFloat(NUM_POIDS), cursor.getFloat(NUM_VOLUME),
                cursor.getString(NUM_OPERATION_EFFECTUEE), new Date(cursor.getString(NUM_HEURE_DEPART)),
                cursor.getString(NUM_COMMENTAIRE),cursor.getString(NUM_PHOTO),cursor.getInt(NUM_ENQUETE_ID),
                cursor.getFloat(NUM_LONGITUDE),cursor.getFloat(NUM_LATITUDE));
    }

    private Date parse(String s) {
        try { return SimpleDateFormat.getDateTimeInstance().parse(s); }
        catch (ParseException e) { return new Date(); }
    }
}
