package com.example.entpe.model;

public class Settings {

    ////////////////////////////////
    ///////////Attributes///////////
    ////////////////////////////////
    private int id;
    private String nomSettings;
    private int consommation;
    private int precision;
    private int periode;


    ////////////////////////////////
    /////////Constructors///////////
    ////////////////////////////////
    public Settings (int id, String nomSettings, int consommation, int precision, int periode){
        this.id = id;
        setNomSettings(nomSettings);
        setConsommation(consommation);
        setPrecision(precision);
        setPeriode(periode);
    }


    ///////////////////////////
    /////////Getters///////////
    ///////////////////////////


    public int getId() {
        return id;
    }

    public String getNomSettings() {
        return nomSettings;
    }

    public int getConsommation() {
        return consommation;
    }

    public int getPrecision() {
        return precision;
    }

    public int getPeriode() {
        return periode;
    }

    ////////////////////////////
    /////////Setters////////////
    ////////////////////////////

    public void setNomSettings(String nomSettings) {
        this.nomSettings = nomSettings;
    }

    public void setConsommation(int consommation) {
        this.consommation = consommation;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public void setPeriode(int periode) {
        this.periode = periode;
    }
}
