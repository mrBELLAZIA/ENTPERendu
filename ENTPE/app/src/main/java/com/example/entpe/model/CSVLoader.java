package com.example.entpe.model;

import android.util.Pair;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.opencsv.CSVReader;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CSVLoader {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constants //////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * À MODIFIER QUAND LA LIGNE EN TROP N'EST PLUS
     */
    private static final int LINES_SKIPPED = 2;

    private static final String IN_CAR = "En tournée";

    //////////////////////////////////////////////////////////////////////
    //// Table index variables ///////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////
    ////// Path loading table ///////////////////
    /////////////////////////////////////////////
    private static final int LATITUDE_PATH = 2;
    private static final int LONGITUDE_PATH = 3;
    private static final int ETAT = 9;

    /////////////////////////////////////////////
    ////// Client loading table /////////////////
    /////////////////////////////////////////////
    private static final int LONGITUDE_CLIENT = 17;
    private static final int LATITUDE_CLIENT = 18;
    private static final int ADRESSE = 4;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Getters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private static float getFloat(String[] sub, int idx) { return Float.parseFloat(sub[idx]); }

    //////////////////////////////////////////////////////////////////////
    //// Record-reading getters //////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////
    ////// Path loading getters /////////////////
    /////////////////////////////////////////////
    private static LatLng getLatLng(String[] sub) { return new LatLng(getLatitudePath(sub), getLongitudePath(sub)); }

    private static float getLatitudePath(String[] sub) { return getFloat(sub, LATITUDE_PATH); }

    private static float getLongitudePath(String[] sub) { return getFloat(sub, LONGITUDE_PATH); }

    private static boolean getEtat(String[] sub) { return sub[ETAT].equals(IN_CAR); }

    /////////////////////////////////////////////
    ////// Client loading getters ///////////////
    /////////////////////////////////////////////
    private static float getLatitudeClient(String[] sub) { return getFloat(sub, LATITUDE_CLIENT); }

    private static float getLongitudeClient(String[] sub) { return getFloat(sub, LONGITUDE_CLIENT); }

    private static String getAdresse(String[] sub) { return sub[ADRESSE]; }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Charge les positions et états lors du trajet
     * @param fileName est le nom du fichier
     * @return une liste associant les positions à l'état
     */
    public static @NonNull List<Pair<LatLng, Boolean>> loadPath(@NonNull String fileName) {
        List<Pair<LatLng, Boolean>> positions = new ArrayList<>();

        try {
            CSVReader csvReader = new CSVReader(Files.newBufferedReader(Paths.get(fileName)));
            csvReader.skip(LINES_SKIPPED);

            String[] nextRecord;

            while((nextRecord = csvReader.readNext()) != null) { positions.add(new Pair<>(getLatLng(nextRecord), getEtat(nextRecord))); }

            csvReader.close();
        } catch(Exception e) { e.printStackTrace(); }

        return positions;
    }

    public static @NonNull List<LatLng> loadClients(@NonNull String fileName) {
        List<LatLng> clients = new ArrayList<>();

        try {
            CSVReader csvReader = new CSVReader(Files.newBufferedReader(Paths.get(fileName)));
            csvReader.skip(1);

            String[] nextRecord;

            while((nextRecord = csvReader.readNext()) != null) { clients.add(new LatLng(getLatitudeClient(nextRecord), getLongitudeClient(nextRecord))); }

            csvReader.close();
        } catch(Exception e) { e.printStackTrace(); }

        return clients;
    }
}
