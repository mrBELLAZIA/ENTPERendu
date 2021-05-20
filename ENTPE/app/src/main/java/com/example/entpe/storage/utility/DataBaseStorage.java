package com.example.entpe.storage.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bcstudio.androidsqlitetoolbox.Export.DBExporterJson;
import com.bcstudio.androidsqlitetoolbox.Export.ExportConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class DataBaseStorage<T> implements Storage<T> {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private final SQLiteOpenHelper helper;
    private final String table;

    private static DBExporterJson JSON_EXPORT;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor ////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Constructeur principal
     * @param helper - Assisant SQLite
     * @param table - Nom de la table
     * @param context - Contexte de l'application
     * @throws IOException en cas de problème lors de la création de la config
     */
    public DataBaseStorage(SQLiteOpenHelper helper, String table, Context context) throws IOException {
        this.helper = helper;
        this.table = table;
        JSON_EXPORT = new DBExporterJson(new ExportConfig(helper.getReadableDatabase(), table, ExportConfig.ExportType.JSON, context));
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Exporte la BDD sous forme de fichier JSON
     * @throws Exception est lancée en cas de problème
     */
    public void export() throws Exception { JSON_EXPORT.export(); }

    //////////////////////////////////////////////////////////////////////
    //// Overriden methods ///////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////
    @Override
    @Nullable
    public T find(int id) {
        T object = null;
        Cursor cursor = helper.getReadableDatabase().query(table, null, BaseColumns._ID + " = ?", new String[]{"" + id}, null, null, null);

        if(cursor.moveToNext()) { object = cursorToObject(cursor); }

        cursor.close();
        return object;
    }

    @Override
    @NonNull
    public List<T> findAll() {
        List<T> list = new ArrayList<>();
        Cursor cursor = helper.getReadableDatabase().query(table, null, null, null, null, null, null);

        while(cursor.moveToNext()) { list.add(cursorToObject(cursor)); }

        cursor.close();
        return list;
    }

    @Override
    public int size() {
        Cursor cursor = helper.getReadableDatabase().query(table, null, null, null, null, null, null);
        int size = cursor.getCount();
        cursor.close();
        return size;
    }

    @Override
    public void insert(@NonNull T object) { helper.getWritableDatabase().insert(table, null, objectToContentValues(-1, object)); }

    @Override
    public void update(int id, @NonNull T object) { helper.getWritableDatabase().update(table, objectToContentValues(id, object), BaseColumns._ID + " = ?", new String[]{"" + id}); }

    @Override
    public void delete(int id) { helper.getWritableDatabase().delete(table, BaseColumns._ID + " = ?", new String[]{"" + id}); }

    //////////////////////////////////////////////////////////////////////
    //// Abstract methods ////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////
    @NonNull
    protected abstract ContentValues objectToContentValues(int id, T object);

    @Nullable
    protected abstract T cursorToObject(Cursor cursor);
}
