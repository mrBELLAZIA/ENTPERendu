package com.example.entpe.application;

import android.app.Application;
import android.content.Context;
import android.location.Criteria;

import com.example.entpe.model.Arret;
import com.example.entpe.model.EnqueteCSV;
import com.example.entpe.model.EtablissementAPI;
import com.example.entpe.model.Position;
import com.example.entpe.model.Settings;
import com.example.entpe.model.TrackerGPS;

import java.io.IOException;

public class MyApplication extends Application {

        ///////////////////////////////////////
        //////////Constantes //////////////////
        ///////////////////////////////////////
        public static final int ETAT_RUNNING = 0;
        public static final int ETAT_STOPPED = 1;


        ///////////////////////////////////////
        //////////Attributes //////////////////
        ///////////////////////////////////////
        private static Context context;
        private static int nbService;
        private static EnqueteCSV enqueteCSV;
        private static int etatEnquete;
        private static int etatGps;
        private static int enqueteId;
        private static Settings settings;
        private static EtablissementAPI etablissementAPI;

        public void onCreate() {
            super.onCreate();
            MyApplication.context = getApplicationContext();
            setNbService(0);
            setEnquete(null);
            setEtatEnquete(TrackerGPS.ETAT_WORKING);
            setEtatGps(ETAT_STOPPED);
        }

        ///////////////////////////////////////
        //////////Methods /////////////////////
        ///////////////////////////////////////

        ////////////////GETTERS////////////////
        public static Context getAppContext() {
            return MyApplication.context;
        }
        public static int getNbService(){ return MyApplication.nbService;}
        public static EnqueteCSV getEnquete(){return enqueteCSV;}

    public static int getEtatEnquete() {
        return etatEnquete;
    }

    public static int getEtatGps() {
        return etatGps;
    }

    public static int getEnqueteId() {
        return enqueteId;
    }

    public static Settings getSettings(){ return settings;}

    public static EtablissementAPI getEtablissementAPI() {
        return etablissementAPI;
    }

    ////////////////SETTERS////////////////
        public static void setNbService(int nb) { nbService =nb ;}
        public static  void setEnquete(EnqueteCSV enquete){ enqueteCSV = enquete;}
        public static void setEtatEnquete(int etat){ etatEnquete = etat; }
        public static void setEtatGps(int etatGps) { MyApplication.etatGps = etatGps; }
        public static void setSettings(Settings setting){ settings = setting;}

    public static void setEtablissementAPI(EtablissementAPI etablissementAPI) {
        MyApplication.etablissementAPI = etablissementAPI;
    }

    public static void setEnqueteId(int enqueteId) {
        MyApplication.enqueteId = enqueteId;
    }

    ///////////////AJOUTS CSV///////////////
    public static void ajoutPosition(String[] data) throws IOException {
            enqueteCSV.ajoutPosition(data);
    }

    public static void ajoutArret(String[] data) throws  IOException {
            enqueteCSV.ajoutArret(data);
    }

    public static void incrementeClient(){
            enqueteCSV.incrementeClient();
    }

    public static void ajouterArret(Arret arret){
            enqueteCSV.ajouterArret(arret);
    }

    public static void ajouterPosition(Position position){
            enqueteCSV.ajouterPosition(position);
    }

    public static void ajoutFinTournee(String[] data) throws IOException{
            enqueteCSV.ajoutFinTournee(data);
    }

    public static void incrementeArret(){
            enqueteCSV.incrementeArret();
    }
}
