package com.example.entpe.model;

public class EtablissementAPI {
    private String nom;
    private String siret;
    private String codePostal;
    private String adresse;
    private float longitude;
    private float latitude;
    private String ville;
    private String nature;


    public EtablissementAPI(String siret ,String nom, String codePostal, String adresse, float longitude, float latitude, String ville,String nature){
        setNom(nom);
        setSiret(siret);
        setCodePostal(codePostal);
        setAdresse(adresse);
        setLongitude(longitude);
        setLatitude(latitude);
        setVille(ville);
        setNature(nature);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Setters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////


    public void setSiret(String siret) {
        this.siret = siret;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void setNom(String nom) { this.nom = nom; }

    public void setNature(String nature) { this.nature = nature; }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Getters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public String getSiret() {
        return siret;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public String getAdresse() {
        return adresse;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public String getVille() {
        return ville;
    }

    public String getNom() { return nom; }

    public String getNature() { return nature; }

    @Override
    public String toString(){
        return ("Siret: "+getSiret() +" code postal: " + getCodePostal() + " ville: "+ getVille() + " adresse: "+ getAdresse()+ " longitude "+ getLongitude() +" latitude: "+ getLatitude());
    }
}
