package com.example.entpe.model;

import android.Manifest;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;

import com.example.entpe.application.MyApplication;
import com.example.entpe.service.GpsService;
import com.example.entpe.storage.DataBaseManager;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TrackerGPS {
    //////////////////////////////////////////////////////////////////
    //// constantes //////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    public static final int DEFAULT_PERIODE = 2000;
    public static final int ETAT_IN_PROGRESS = 0;
    public static final int ETAT_WORKING = 1;
    //////////////////////////////////////////////////////////////////
    //// attributes //////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    //Liée à l'activité mère
    private int enqueteId;
    private DataBaseManager dbm;
    //private EnqueteCSV enquete;

    //Attributs propre au GPS
    private String provider;
    private int periode;
    private LocationListener locationListener;
    private Criteria critere;
    private LocationManager locationManager;
    private Location derniereLoca;
    private Position dernierePosition;
    private GpsService service;
    private int numService;

    //////////////////////////////////////////////////////////////////
    //// constructors ////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    public TrackerGPS(int enqueteId, EnqueteCSV enquete, final int etat) {

        //Liée à l'activité mère
        this.enqueteId = enqueteId;
        this.dbm = new DataBaseManager(MyApplication.getAppContext());
        //this.enquete = enquete;

        //Attributs propre au GPS
        this.periode = DEFAULT_PERIODE;
        this.critere = new Criteria();
        this.locationManager = (LocationManager) MyApplication.getAppContext().getSystemService(Context.LOCATION_SERVICE);
        setDefaultCritere();
        setDefaultProvider();
        setDefaultListener(etat);
        locaInit();

        //Liée au service runnant le gps
        this.service = null;

    }

    public TrackerGPS(int enqueteId, GpsService service, int numService) {

        //Liée à l'activité mère
        this.numService = numService;
        this.enqueteId = enqueteId;
        this.dbm = new DataBaseManager(MyApplication.getAppContext());
        //this.enquete = enquete;

        //Attributs propre au GPS
        this.periode = DEFAULT_PERIODE;
        this.critere = new Criteria();
        this.locationManager = (LocationManager) MyApplication.getAppContext().getSystemService(Context.LOCATION_SERVICE);
        if(MyApplication.getSettings()==null){
            setDefaultCritere();
        }else{
            setApplicationCritere();
        }
        setDefaultProvider();
        setServiceListener();


        //Liée au service runnant le gps
        this.service = service;

    }


    //////////////////////////////////////////////////////////////////
    //// methods /////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////

    //Lance le gps
    public void launch() {
        ActivityCompat.checkSelfPermission(MyApplication.getAppContext(), Manifest.permission.ACCESS_FINE_LOCATION);//ActivityCompat.requestPermissions(activity, new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, 1);
        locationManager.requestLocationUpdates(provider, periode, 0, locationListener);
    }


    //Initialise la dernière localisation
    public void locaInit(){
        ActivityCompat.checkSelfPermission(MyApplication.getAppContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        derniereLoca = locationManager.getLastKnownLocation(provider);
        if(MyApplication.getEnquete().getCompteurPosition()==1){
            Position position = new Position(-1,MyApplication.getEnquete().getCompteurPosition(),
                    (float) derniereLoca.getLatitude(),
                    (float) derniereLoca.getLongitude(),
                    (float) derniereLoca.getAltitude(),
                    derniereLoca.getSpeed(),
                    0,
                    derniereLoca.getBearing(),enqueteId);
            dernierePosition = position;
            try {
                MyApplication.ajoutPosition(new String[]{
                        (new Date(derniereLoca.getTime())).toString(),
                        Integer.toString(MyApplication.getEnquete().getCompteurPosition()),
                        Double.toString(position.getLatitude()),
                        Double.toString(position.getLongitude()),
                        Double.toString(position.getAltitude()),
                        Double.toString(position.getVitesse()),
                        Double.toString(0),
                        Float.toString((float)position.getCourse()),
                        Integer.toString(enqueteId),
                        (MyApplication.getEtatEnquete() == ETAT_IN_PROGRESS) ? "En tournée" : "à pied"
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            MyApplication.ajouterPosition(position);
            dbm.insert(position);
        }
    }


    //Arrete la capture de données
    public void stop(){
        locationManager.removeUpdates(locationListener);
    }

    public void setDefaultCritere(){
        setPeriode(2);
        // On peut mettre ACCURACY_FINE pour une haute précision ou ACCURACY_COARSE pour une moins bonne précision
        critere.setAccuracy(Criteria.ACCURACY_FINE);

        // Est-ce que le fournisseur doit être capable de donner une altitude ?
        critere.setAltitudeRequired(true);

        // Est-ce que le fournisseur doit être capable de donner une direction ?
        critere.setBearingRequired(true);

        // Est-ce que le fournisseur peut être payant ?
        critere.setCostAllowed(false);

        // Pour indiquer la consommation d'énergie demandée
        // Criteria.POWER_HIGH pour une haute consommation, Criteria.POWER_MEDIUM pour une consommation moyenne et Criteria.POWER_LOW pour une basse consommation
        critere.setPowerRequirement(Criteria.POWER_MEDIUM);

        // Est-ce que le fournisseur doit être capable de donner une vitesse ?
        critere.setSpeedRequired(true);
    }

    public void setApplicationCritere(){
        setPeriode(MyApplication.getSettings().getPeriode());
        // On peut mettre ACCURACY_FINE pour une haute précision ou ACCURACY_COARSE pour une moins bonne précision
        critere.setAccuracy(MyApplication.getSettings().getPrecision());

        // Est-ce que le fournisseur doit être capable de donner une altitude ?
        critere.setAltitudeRequired(true);

        // Est-ce que le fournisseur doit être capable de donner une direction ?
        critere.setBearingRequired(true);

        // Est-ce que le fournisseur peut être payant ?
        critere.setCostAllowed(false);

        // Pour indiquer la consommation d'énergie demandée
        // Criteria.POWER_HIGH pour une haute consommation, Criteria.POWER_MEDIUM pour une consommation moyenne et Criteria.POWER_LOW pour une basse consommation
        critere.setPowerRequirement(MyApplication.getSettings().getConsommation());

        // Est-ce que le fournisseur doit être capable de donner une vitesse ?
        critere.setSpeedRequired(true);
    }

    public boolean estArret(){
        int compteur = MyApplication.getEnquete().getCompteurPosition();
        ArrayList<Position> listePositions = (ArrayList<Position>)dbm.findAllPostionByID(enqueteId);
        for (int i = compteur-45;i<compteur-2;i++){
            int longSup = Float.compare(listePositions.get(i).getLongitude()-30,listePositions.get(i+1).getLongitude()); //Doit retourner -1
            int longInf = Float.compare(listePositions.get(i).getLongitude()+30,listePositions.get(i+1).getLongitude()); //Doit retourner 1
            int latSup = Float.compare(listePositions.get(i).getLatitude()-30,listePositions.get(i+1).getLatitude()); //Doit retourner -1
            int latInf = Float.compare(listePositions.get(i).getLatitude()+30,listePositions.get(i+1).getLatitude()); //Doit retourner 1
            if(longSup==1&&longInf==-1&&latSup==1&&latInf==-1)
                return false;
        }
        return true;
    }



    //////////////////////////////////////////////////////////////////
    //// setters /////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
    public void setPeriode(int periode) {
        this.periode = periode*1000;
    }

    public void setDefaultListener(final int etat){
        locationListener = new LocationListener() {
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) { }

            @Override
            public void onProviderEnabled(String provider) { }

            @Override
            public void onProviderDisabled(String provider) { }

            @Override
            public void onLocationChanged(Location location) {
                float distance;
                Position position;
                float[] distances = new float[20];
                Location.distanceBetween(derniereLoca.getLatitude(), derniereLoca.getLongitude(),location.getLatitude(),location.getLongitude(),distances);
                distance = distances[0] + dernierePosition.getDistance();

                // Ajout dans l'arraylist
                position = new Position(-1,MyApplication.getEnquete().getCompteurPosition(),new Date(location.getTime()),
                        (float) location.getLatitude(),
                        (float) location.getLongitude(),
                        (float) location.getAltitude(),
                        location.getSpeed(),
                        distance,
                        location.getBearing(),enqueteId);

                MyApplication.ajouterPosition(position);
                dbm.insert(position);

                // Ajout dans CSV
                try {
                    MyApplication.ajoutPosition(new String[] {
                            DateFormat.getDateTimeInstance().format((new Date(location.getTime()))),
                            Integer.toString(MyApplication.getEnquete().getCompteurPosition()),
                            Double.toString(location.getLatitude()),
                            Double.toString(location.getLongitude()),
                            Double.toString(location.getAltitude()),
                            Double.toString(location.getSpeed()),
                            Double.toString(location.getBearing()),
                            Float.toString(distance),
                            Integer.toString(enqueteId),
                            (etat == ETAT_IN_PROGRESS) ? "En tournée" : "à pied"
                    });
                } catch (IOException e) { e.printStackTrace(); }
                derniereLoca = location;
            }
        };
    }

    public void setServiceListener(){
        locationListener = new LocationListener() {
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) { }

            @Override
            public void onProviderEnabled(String provider) { }

            @Override
            public void onProviderDisabled(String provider) { }

            @Override
            public void onLocationChanged(Location location) {
                if (MyApplication.getEnquete().getCompteurPosition() == 1) {
                    derniereLoca = location;
                    Position position = new Position(-1, MyApplication.getEnquete().getCompteurPosition(),
                            (float) derniereLoca.getLatitude(),
                            (float) derniereLoca.getLongitude(),
                            (float) derniereLoca.getAltitude(),
                            derniereLoca.getSpeed(),
                            0,
                            derniereLoca.getBearing(), enqueteId);

                    dernierePosition = position;
                    try {
                        MyApplication.ajoutPosition(new String[]{
                                DateFormat.getDateTimeInstance().format((new Date(derniereLoca.getTime()))),
                                Integer.toString(MyApplication.getEnquete().getCompteurPosition()),
                                Double.toString(position.getLatitude()),
                                Double.toString(position.getLongitude()),
                                Double.toString(position.getAltitude()),
                                Double.toString(position.getVitesse()),
                                Double.toString(0),
                                Float.toString((float) position.getCourse()),
                                (MyApplication.getEtatEnquete() == ETAT_IN_PROGRESS) ? "En tournée" : "à pied"
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    MyApplication.ajouterPosition(position);
                    dbm.insert(position);
                } else {
                    if (MyApplication.getEtatGps() == MyApplication.ETAT_STOPPED) {
                        service.onDestroy();
                    } else {
                        assert (service != null);
                        ///////////////////////////AJOUT DANS LE CSV ET LA BASE//////////////////////////////////////////////////////////////////////////////////
                        float distance;
                        Position position;
                        float[] distances = new float[20];
                        Location.distanceBetween(derniereLoca.getLatitude(), derniereLoca.getLongitude(), location.getLatitude(), location.getLongitude(), distances);
                        distance = distances[0] + dernierePosition.getDistance();
                        // Ajout dans l'arraylist
                        position = new Position(-1, MyApplication.getEnquete().getCompteurPosition(),
                                (float) location.getLatitude(),
                                (float) location.getLongitude(),
                                (float) location.getAltitude(),
                                location.getSpeed(),
                                distance,
                                location.getBearing(), enqueteId);

                        MyApplication.ajouterPosition(position);
                        dbm.insert(position);

                        // Ajout dans CSV
                        try {
                            MyApplication.ajoutPosition(new String[]{
                                    DateFormat.getDateTimeInstance().format((new Date(location.getTime()))),
                                    Integer.toString(position.getNum_position()),
                                    Double.toString(location.getLatitude()),
                                    Double.toString(location.getLongitude()),
                                    Double.toString(location.getAltitude()),
                                    Double.toString(location.getSpeed()),
                                    Double.toString(location.getBearing()),
                                    Float.toString(distance),
                                    Integer.toString(enqueteId),
                                    (MyApplication.getEtatEnquete() == ETAT_IN_PROGRESS) ? "En tournée" : "à pied"
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        derniereLoca = location;


                        ////////////////////////////VERIFICATION DE SI EST A L'ARRET///////////////////////////////////////////////////////
                        if (MyApplication.getEnquete().getCompteurPosition() % 45 == 0) {
                            if (estArret()) {
                                service.envoiNotifArret();
                            }
                        }
                    }
                }
            }
        };
    }

    public void setDefaultProvider(){
        provider = locationManager.getBestProvider(critere, true);

    }
    //////////////////////////////////////////////////////////////////
    //// getters /////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////


    public int getEnqueteId() {
        return enqueteId;
    }

    public EnqueteCSV getEnquete() {
        return MyApplication.getEnquete();
    }
}
