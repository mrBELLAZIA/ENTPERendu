package com.example.entpe.storage.database_storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.entpe.model.Position;
import com.example.entpe.storage.utility.DataBaseStorage;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PositionDataBaseStorage extends DataBaseStorage<Position> {
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
        private static final String DATABASE_NAME = "Position.db";

        private static final String SQL_CREATE_POSITION_ENTRIES = "CREATE TABLE " + TABLE_NAME + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY,"+ NAME_POSITION+" INTEGER,"
                + NAME_DATE + " TEXT," + NAME_LATITUDE + " REAL," + NAME_LONGITUDE + " REAL,"
                + NAME_VITESSE + " REAL," + NAME_ALTITUDE + " REAL," + NAME_DISTANCE + " REAL,"
                + NAME_COURSE + " REAL," + NAME_ENQUETE_ID + " INTEGER NOT NULL,"
                + "FOREIGN KEY (" + NAME_ENQUETE_ID + ")"
                + "REFERENCES " + EnqueteDataBaseStorage.TABLE_NAME + " (" + BaseColumns._ID + "))";

        //////////////////////////////////////////////////////////////////
        //// Constructor /////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        public DataBaseHelper(Context context) { super(context, DATABASE_NAME, null, DATABASE_VERSION); }

        //////////////////////////////////////////////////////////////////
        //// Methods /////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        @Override
        public void onCreate(SQLiteDatabase db) { db.execSQL(SQL_CREATE_POSITION_ENTRIES); }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constants //////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private static final String TABLE_NAME = "position";
    private static final int NUM_ID = 0;
    private static final String NAME_POSITION = "num_position";
    private static final int NUM_POSITION = 1;
    private static final String NAME_DATE = "date";
    private static final int NUM_DATE = 2;
    private static final String NAME_LATITUDE = "latitude";
    private static final int NUM_LATITUDE = 3;
    private static final String NAME_LONGITUDE = "longitude";
    private static final int NUM_LONGITUDE = 4;
    private static final String NAME_VITESSE = "vitesse";
    private static final int NUM_VITESSE = 5;
    private static final String NAME_ALTITUDE = "altitude";
    private static final int NUM_ALTITUDE = 6;
    private static final String NAME_DISTANCE = "distance";
    private static final int NUM_DISTANCE = 7;
    private static final String NAME_COURSE = "course";
    private static final int NUM_COURSE = 8;
    private static final String NAME_ENQUETE_ID = "enquete_id";
    private static final int NUM_ENQUETE_ID = 9;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private static PositionDataBaseStorage STORAGE;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor ////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Constructeur privé
     * @param context - Contexte de l'application
     * @throws IOException - En cas de problème lors de la création de la config
     */
    private PositionDataBaseStorage(Context context) throws IOException { super(new DataBaseHelper(context), TABLE_NAME, context); }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Getters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public static PositionDataBaseStorage get(Context context) throws IOException {
        if(STORAGE == null)
            STORAGE = new PositionDataBaseStorage(context);

        return STORAGE;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    @NonNull
    @Override
    protected ContentValues objectToContentValues(int id, Position object) {
        ContentValues values = new ContentValues();
            values.put(NAME_POSITION, object.getNum_position());
            values.put(NAME_DATE, object.getDate().toString());
            values.put(NAME_LATITUDE, object.getLatitude());
            values.put(NAME_LONGITUDE, object.getLongitude());
            values.put(NAME_VITESSE, object.getVitesse());
            values.put(NAME_ALTITUDE, object.getAltitude());
            values.put(NAME_DISTANCE, object.getDistance());
            values.put(NAME_COURSE, object.getCourse());
            values.put(NAME_ENQUETE_ID, object.getEnqueteId());
        return values;
    }

    @Nullable
    @Override
    protected Position cursorToObject(Cursor cursor) {
        return new Position(cursor.getInt(NUM_ID),cursor.getInt(NUM_POSITION),
                parse(cursor.getString(NUM_DATE)), cursor.getFloat(NUM_LATITUDE),
                cursor.getFloat(NUM_LONGITUDE), cursor.getFloat(NUM_VITESSE),
                cursor.getFloat(NUM_ALTITUDE), cursor.getFloat(NUM_DISTANCE),
                cursor.getFloat(NUM_COURSE), cursor.getInt(NUM_ENQUETE_ID));
    }

    private Date parse(String s) {
        try { return SimpleDateFormat.getDateTimeInstance().parse(s); }
        catch(ParseException e) { return new Date(); }
    }
}
