package com.example.entpe.storage.database_storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.entpe.model.DetailEnquete;
import com.example.entpe.model.Enquete;
import com.example.entpe.storage.utility.DataBaseStorage;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EnqueteDataBaseStorage extends DataBaseStorage<Enquete> {
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
        private static final String DATABASE_NAME = "Enquete.db";

        private static final String SQL_CREATE_ENQUETE_ENTRIES = "CREATE TABLE " + TABLE_NAME + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY,"
                + NAME_NOM_CHAUFFEUR + " TEXT," + NAME_DATE_D + " TEXT," + NAME_PCP_D + " TEXT,"
                + NAME_COMMUNE_D + " TEXT," + NAME_NATURE_LIEU_D + " TEXT," + NAME_EST_CHARGE_D + " INTEGER,"
                + NAME_POIDS_D + " FLOAT," + NAME_VOLUME_D + " FLOAT," + NAME_CONDITIONNEMENT_D + " TEXT,"
                + NAME_NATURE_D + " TEXT,"+NAME_DATE_A+ " TEXT," + NAME_PCP_A + " TEXT," + NAME_COMMUNE_A + " TEXT,"
                + NAME_NATURE_LIEU_A + " TEXT," + NAME_EST_CHARGE_A + " INTEGER," + NAME_POIDS_A + " FLOAT,"
                + NAME_VOLUME_A + " FLOAT," + NAME_CONDITIONNEMENT_A + " TEXT," + NAME_NATURE_A + " TEXT,"
                + NAME_ID_VEHICULE + " INTEGER,"
                + "FOREIGN KEY (" + NAME_ID_VEHICULE + ") REFERENCES "
                + VehiculeDataBaseStorage.TABLE_NAME + " (" + BaseColumns._ID + "))";




        //////////////////////////////////////////////////////////////////
        //// Constructor /////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        public DataBaseHelper(Context context) { super(context, DATABASE_NAME, null, DATABASE_VERSION); }

        //////////////////////////////////////////////////////////////////
        //// Methods /////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        @Override
        public void onCreate(SQLiteDatabase db) { db.execSQL(SQL_CREATE_ENQUETE_ENTRIES); }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constants //////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public static final String TABLE_NAME = "enquete";
    private static final int NUM_ID = 0;
    private static final String NAME_NOM_CHAUFFEUR = "nom_chauffeur";
    private static final int NUM_NOM_CHAUFFEUR = 1;
    private static final String NAME_DATE_D = "date_depart";
    private static final int NUM_DATE_D = 2;
    private static final String NAME_PCP_D = "pays_code_postal_depart";
    private static final int NUM_PCP_D = 3;
    private static final String NAME_COMMUNE_D = "commune_depart";
    private static final int NUM_COMMUNE_D = 4;
    private static final String NAME_NATURE_LIEU_D = "nature_lieu_depart";
    private static final int NUM_NATURE_LIEU_D = 5;
    private static final String NAME_EST_CHARGE_D = "est_charge_depart";
    private static final int NUM_EST_CHARGE_D = 6;
    private static final String NAME_POIDS_D = "poids_depart";
    private static final int NUM_POIDS_D = 7;
    private static final String NAME_VOLUME_D = "volume_depart";
    private static final int NUM_VOLUME_D = 8;
    private static final String NAME_CONDITIONNEMENT_D = "conditionnement_depart";
    private static final int NUM_CONDITIONNEMENT_D = 9;
    private static final String NAME_NATURE_D = "nature_depart";
    private static final int NUM_NATURE_D = 10;
    private static final String NAME_DATE_A = "date_arrive";
    private static final int NUM_DATE_A = 11;
    private static final String NAME_PCP_A = "pays_code_postal_arrive";
    private static final int NUM_PCP_A = 12;
    private static final String NAME_COMMUNE_A = "commune_arrive";
    private static final int NUM_COMMUNE_A = 13;
    private static final String NAME_NATURE_LIEU_A = "nature_lieu_arrive";
    private static final int NUM_NATURE_LIEU_A = 14;
    private static final String NAME_EST_CHARGE_A = "est_charge_arrive";
    private static final int NUM_EST_CHARGE_A = 15;
    private static final String NAME_POIDS_A = "poids_arrive";
    private static final int NUM_POIDS_A = 16;
    private static final String NAME_VOLUME_A = "volume_arrive";
    private static final int NUM_VOLUME_A = 17;
    private static final String NAME_CONDITIONNEMENT_A = "conditionnement_arrive";
    private static final int NUM_CONDITIONNEMENT_A = 18;
    private static final String NAME_NATURE_A = "nature_arrive";
    private static final int NUM_NATURE_A = 19;
    private static final String NAME_ID_VEHICULE = "vehicule_id";
    private static final int NUM_ID_VEHICULE = 20;




    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private static EnqueteDataBaseStorage STORAGE;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor ////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Constructeur privé
     * @param context - Contexte de l'application
     * @throws IOException - En cas de problème lors de la création de la config
     */
    private EnqueteDataBaseStorage(Context context) throws IOException { super(new EnqueteDataBaseStorage.DataBaseHelper(context), TABLE_NAME, context); }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Getters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public static EnqueteDataBaseStorage get(Context context) throws IOException {
        if(STORAGE == null)
            STORAGE = new EnqueteDataBaseStorage(context);

        return STORAGE;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    @NonNull
    @Override
    protected ContentValues objectToContentValues(int id, Enquete object) {
        ContentValues values = new ContentValues();
            values.put(NAME_NOM_CHAUFFEUR, object.getNomChauffeur());
            values.put(NAME_DATE_D, object.getDateDepart().toString());
            values.put(NAME_PCP_D, object.getCodePostalDepart());
            values.put(NAME_COMMUNE_D, object.getCommuneDepart());
            values.put(NAME_NATURE_LIEU_D, object.getNatureLieuDepart());
            values.put(NAME_EST_CHARGE_D, object.getEstChargeDepart());
            values.put(NAME_POIDS_D, object.getPoidsDepart());
            values.put(NAME_VOLUME_D, object.getVolumeDepart());
            values.put(NAME_CONDITIONNEMENT_D, object.getConditionnementDepart());
            values.put(NAME_NATURE_D, object.getNatureMarchandiseDepart());
            values.put(NAME_DATE_A, object.getDateArrive().toString());
            values.put(NAME_PCP_A, object.getCodePostalArrive());
            values.put(NAME_COMMUNE_A, object.getCommuneArrive());
            values.put(NAME_NATURE_LIEU_A, object.getNatureLieuArrive());
            values.put(NAME_EST_CHARGE_A, object.getEstChargeArrive());
            values.put(NAME_POIDS_A, object.getPoidsArrive());
            values.put(NAME_VOLUME_A, object.getVolumeArrive());
            values.put(NAME_CONDITIONNEMENT_A, object.getConditionnementArrive());
            values.put(NAME_NATURE_A, object.getNatureMarchandiseArrive());
            values.put(NAME_ID_VEHICULE, object.getIdVehicule());
        return values;
    }

    @Nullable
    @Override
    protected Enquete cursorToObject(Cursor cursor) {
        return new Enquete(
                cursor.getInt(NUM_ID),
                cursor.getString(NUM_NOM_CHAUFFEUR),
                parse(cursor.getString(NUM_DATE_D)),
                cursor.getString(NUM_PCP_D),
                cursor.getString(NUM_COMMUNE_D),
                cursor.getString(NUM_NATURE_LIEU_D),
                cursor.getInt(NUM_EST_CHARGE_D) ==1,
                cursor.getFloat(NUM_POIDS_D),
                cursor.getFloat(NUM_VOLUME_D),
                cursor.getString(NUM_CONDITIONNEMENT_D),
                cursor.getString(NUM_NATURE_D),
                parse(cursor.getString(NUM_DATE_A)),
                cursor.getString(NUM_PCP_A),
                cursor.getString(NUM_COMMUNE_A),
                cursor.getString(NUM_NATURE_LIEU_A),
                cursor.getInt(NUM_EST_CHARGE_A) ==1,
                cursor.getFloat(NUM_POIDS_A),
                cursor.getFloat(NUM_VOLUME_A),
                cursor.getString(NUM_CONDITIONNEMENT_A),
                cursor.getString(NUM_NATURE_A),
                cursor.getInt(NUM_ID_VEHICULE));
    }

    private Date parse(String s) {
        try { return SimpleDateFormat.getDateTimeInstance().parse(s); }
        catch(ParseException e) { return new Date(); }
    }
}
