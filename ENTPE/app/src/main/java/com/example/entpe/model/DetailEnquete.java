package com.example.entpe.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class DetailEnquete implements JSONable {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constants //////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private static final String DATE = "date";
    private static final String PAYS_CODE_POSTAL = "paysCodePostal";
    private static final String COMMUNE = "commune";
    private static final String NATURE_LIEU = "natureLieu";
    private static final String EST_CHARGE = "estCharge";

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private Date date;
    private String paysCodePostal;
    private String commune;
    private String natureLieu;
    private boolean estCharge;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Constructeur principal
     * @param date est la date de référence (date et heure)
     * @param paysCodePostal est le pays / code postal
     * @param commune est la commune de référence
     * @param natureLieu est la nature du lieu de référence
     * @param estCharge est à vrai s'il y a de la charge
     */
    public DetailEnquete(Date date, String paysCodePostal, String commune, String natureLieu, boolean estCharge) {
        setDate(date);
        setPaysCodePostal(paysCodePostal);
        setCommune(commune);
        setNatureLieu(natureLieu);
        setCharge(estCharge);
    }

    //Constructeur à vide(utile pour initialiser la fin de tournée)
    public DetailEnquete(){
        setDate(new Date());
        setPaysCodePostal("à compléter");
        setCommune("à compléter");
        setNatureLieu("à compléter");
        setCharge(false);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Setters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private void setDate(Date date) { this.date = date; }

    private void setPaysCodePostal(String paysCodePostal) { this.paysCodePostal = paysCodePostal; }

    private void setCommune(String commune) { this.commune = commune; }

    private void setNatureLieu(String natureLieu) { this.natureLieu = natureLieu; }

    private void setCharge(boolean estCharge) { this.estCharge = estCharge; }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Getters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public Date getDate() { return date; }

    public String getPaysCodePostal() { return paysCodePostal; }

    public String getCommune() { return commune; }

    public String getNatureLieu() { return natureLieu; }

    public boolean estCharge() { return estCharge; }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void fromJSONObject(JSONObject object) { }

    @Override
    public JSONObject toJSONObject() {
        try {
            JSONObject object = new JSONObject();
                object.put(DATE, getDate().toString());
                object.put(PAYS_CODE_POSTAL, getPaysCodePostal());
                object.put(COMMUNE, getCommune());
                object.put(NATURE_LIEU, getNatureLieu());
                object.put(EST_CHARGE, estCharge() ? 1 : 0);

            return object;
        } catch(JSONException e) { e.printStackTrace(); }
        return null;
    }
}
