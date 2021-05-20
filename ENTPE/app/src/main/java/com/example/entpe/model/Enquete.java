package com.example.entpe.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;

public class Enquete implements JSONable {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constants //////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private static final String ID = "id";
    private static final String NOM_CHAUFFEUR = "nom_chauffeur";
    private static final String DATE_D = "date_depart";
    private static final String PCP_D = "pays_code_postal_depart";
    private static final String COMMUNE_D = "commune_depart";
    private static final String NATURE_LIEU_D = "nature_lieu_depart";
    private static final String EST_CHARGE_D = "est_charge_depart";
    private static final String POIDS_D = "poids_depart";
    private static final String VOLUME_D = "volume_depart";
    private static final String CONDITIONNEMENT_D = "conditionnement_depart";
    private static final String NATURE_D = "nature_depart";
    private static final String DATE_A = "date_arrive";
    private static final String PCP_A = "pays_code_postal_arrive";
    private static final String COMMUNE_A = "commune_arrive";
    private static final String NATURE_LIEU_A = "nature_lieu_arrive";
    private static final String EST_CHARGE_A = "est_charge_arrive";
    private static final String POIDS_A = "poids_arrive";
    private static final String VOLUME_A = "volume_arrive";
    private static final String CONDITIONNEMENT_A = "conditionnement_arrive";
    private static final String NATURE_A = "nature_arrive";
    private static final String ID_VEHICULE = "id_vehicule";

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private int id;
    private String nomChauffeur;

    //Depart
    private Date dateDepart;
    private String codePostalDepart;
    private String communeDepart;
    private String natureLieuDepart;
    private Boolean estChargeDepart;
    private float poidsDepart;
    private float volumeDepart;
    private String conditionnementDepart;
    private String natureMarchandiseDepart;
    //Arrivé
    private Date dateArrive;
    private String codePostalArrive;
    private String communeArrive;
    private String natureLieuArrive;
    private Boolean estChargeArrive;
    private float poidsArrive;
    private float volumeArrive;
    private String conditionnementArrive;
    private String natureMarchandiseArrive;

    private int idVehicule;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Constructeur principal
     * @param id est l'id de l'enquête dans la BDD
     * @param dateDepart est la date de début de l'enquête
     * @param nomChauffeur est le nom du chauffeur rattaché à l'enquête
     */
    public Enquete(int id,String nomChauffeur,Date dateDepart,String codePostalDepart,String communeDepart,String natureLieuDepart
    ,Boolean estChargeDepart,float poidsDepart, float volumeDepart,String conditionnementDepart, String natureMarchandiseDepart,
                   int idVehicule ) {
        setId(id);
        setNomChauffeur(nomChauffeur);
        setDateDepart(dateDepart);
        setCodePostalDepart(codePostalDepart);
        setCommuneDepart(communeDepart);
        setNatureLieuDepart(natureLieuDepart);
        setEstChargeDepart(estChargeDepart);
        setPoidsDepart(poidsDepart);
        setVolumeDepart(volumeDepart);
        setConditionnementDepart(conditionnementDepart);
        setNatureMarchandiseDepart(natureMarchandiseDepart);
        setIdVehicule(idVehicule);
        setDateArrive(new Date());
        setCodePostalArrive("incomplet");
        setCommuneArrive("incomplet");
        setNatureLieuArrive("incomplet");
        setEstChargeArrive(false);
        setPoidsArrive(0);
        setVolumeArrive(0);
        setConditionnementArrive("incomplet");
        setNatureMarchandiseArrive("incomplet");
    }

    public Enquete(int id,String nomChauffeur,Date dateDepart,String codePostalDepart,String communeDepart,String natureLieuDepart
            ,Boolean estChargeDepart,float poidsDepart, float volumeDepart,String conditionnementDepart, String natureMarchandiseDepart,
                   Date dateArrive,String codePostalArrive,String communeArrive, String natureLieuArrive,Boolean estChargeArrive,
                   float poidsArrive, float volumeArrive, String conditionnementArrive, String natureMarchandiseArrive,
                   int idVehicule ) {
        setId(id);
        setNomChauffeur(nomChauffeur);
        setDateDepart(dateDepart);
        setCodePostalDepart(codePostalDepart);
        setCommuneDepart(communeDepart);
        setNatureLieuDepart(natureLieuDepart);
        setEstChargeDepart(estChargeDepart);
        setPoidsDepart(poidsDepart);
        setVolumeDepart(volumeDepart);
        setConditionnementDepart(conditionnementDepart);
        setNatureMarchandiseDepart(natureMarchandiseDepart);
        setIdVehicule(idVehicule);
        setDateArrive(dateArrive);
        setCodePostalArrive(codePostalArrive);
        setCommuneArrive(communeArrive);
        setNatureLieuArrive(natureLieuArrive);
        setEstChargeArrive(estChargeArrive);
        setPoidsArrive(poidsArrive);
        setVolumeArrive(volumeArrive);
        setConditionnementArrive(conditionnementArrive);
        setNatureMarchandiseArrive(natureMarchandiseArrive);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Setters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    public void setId(int id) {
        this.id = id;
    }

    public void setNomChauffeur(String nomChauffeur) {
        this.nomChauffeur = nomChauffeur;
    }

    public void setDateDepart(Date dateDepart) {
        this.dateDepart = dateDepart;
    }

    public void setCodePostalDepart(String codePostalDepart) {
        this.codePostalDepart = codePostalDepart;
    }

    public void setCommuneDepart(String communeDepart) {
        this.communeDepart = communeDepart;
    }

    public void setNatureLieuDepart(String natureLieuDepart) {
        this.natureLieuDepart = natureLieuDepart;
    }

    public void setEstChargeDepart(Boolean estChargeDepart) {
        this.estChargeDepart = estChargeDepart;
    }

    public void setPoidsDepart(float poidsDepart) {
        this.poidsDepart = poidsDepart;
    }

    public void setVolumeDepart(float volumeDepart) {
        this.volumeDepart = volumeDepart;
    }

    public void setConditionnementDepart(String conditionnementDepart) {
        this.conditionnementDepart = conditionnementDepart;
    }

    public void setNatureMarchandiseDepart(String natureMarchandiseDepart) {
        this.natureMarchandiseDepart = natureMarchandiseDepart;
    }

    public void setDateArrive(Date dateArrive) {
        this.dateArrive = dateArrive;
    }

    public void setCodePostalArrive(String codePostaArrive) {
        this.codePostalArrive = codePostaArrive;
    }

    public void setCommuneArrive(String communeArrive) {
        this.communeArrive = communeArrive;
    }

    public void setNatureLieuArrive(String natureLieuArrive) {
        this.natureLieuArrive = natureLieuArrive;
    }

    public void setEstChargeArrive(Boolean estChargeArrive) {
        this.estChargeArrive = estChargeArrive;
    }

    public void setPoidsArrive(float poidsArrive) {
        this.poidsArrive = poidsArrive;
    }

    public void setVolumeArrive(float volumeArrive) {
        this.volumeArrive = volumeArrive;
    }

    public void setConditionnementArrive(String conditionnementArrive) {
        this.conditionnementArrive = conditionnementArrive;
    }

    public void setNatureMarchandiseArrive(String natureMarchandiseArrive) {
        this.natureMarchandiseArrive = natureMarchandiseArrive;
    }

    public void setIdVehicule(int idVehicule) {
        this.idVehicule = idVehicule;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Getters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////


    public int getId() {
        return id;
    }

    public String getNomChauffeur() {
        return nomChauffeur;
    }

    public Date getDateDepart() {
        return dateDepart;
    }

    public String getCodePostalDepart() {
        return codePostalDepart;
    }

    public String getCommuneDepart() {
        return communeDepart;
    }

    public String getNatureLieuDepart() {
        return natureLieuDepart;
    }

    public Boolean getEstChargeDepart() {
        return estChargeDepart;
    }

    public float getPoidsDepart() {
        return poidsDepart;
    }

    public float getVolumeDepart() {
        return volumeDepart;
    }

    public String getConditionnementDepart() {
        return conditionnementDepart;
    }

    public String getNatureMarchandiseDepart() {
        return natureMarchandiseDepart;
    }

    public Date getDateArrive() {
        return dateArrive;
    }

    public String getCodePostalArrive() {
        return codePostalArrive;
    }

    public String getCommuneArrive() {
        return communeArrive;
    }

    public String getNatureLieuArrive() {
        return natureLieuArrive;
    }

    public Boolean getEstChargeArrive() {
        return estChargeArrive;
    }

    public float getPoidsArrive() {
        return poidsArrive;
    }

    public float getVolumeArrive() {
        return volumeArrive;
    }

    public String getConditionnementArrive() {
        return conditionnementArrive;
    }

    public String getNatureMarchandiseArrive() {
        return natureMarchandiseArrive;
    }

    public int getIdVehicule() {
        return idVehicule;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void fromJSONObject(JSONObject object) { }


    @Override
    public JSONObject toJSONObject() {
        try {
            JSONObject object = new JSONObject();
            object.put(NOM_CHAUFFEUR,nomChauffeur);
            object.put(DATE_D,dateDepart.toString());
            object.put(PCP_D,codePostalDepart);
            object.put(COMMUNE_D,communeDepart);
            object.put(NATURE_LIEU_D,natureLieuDepart);
            object.put(EST_CHARGE_D,estChargeDepart? 1:0);
            object.put(POIDS_D,poidsDepart);
            object.put(VOLUME_D,volumeDepart);
            object.put(CONDITIONNEMENT_D,conditionnementDepart);
            object.put(NATURE_D,natureMarchandiseDepart);
            object.put(DATE_A,dateArrive.toString());
            object.put(PCP_A,codePostalArrive);
            object.put(COMMUNE_A,communeArrive);
            object.put(NATURE_LIEU_A,natureLieuArrive);
            object.put(EST_CHARGE_A,estChargeArrive? 1:0);
            object.put(POIDS_A,poidsArrive);
            object.put(VOLUME_A,volumeArrive);
            object.put(CONDITIONNEMENT_A,conditionnementArrive);
            object.put(NATURE_A,natureMarchandiseArrive);
            object.put(ID_VEHICULE,idVehicule);
            return object;
        } catch(JSONException e) { e.printStackTrace(); }
        return null;
    }



    public JSONObject toJSONObjectID() {
        try {
            JSONObject object = new JSONObject();
            object.put(ID,id);
            object.put(NOM_CHAUFFEUR,nomChauffeur);
            object.put(DATE_D, DateFormat.getDateTimeInstance().format(dateDepart) );
            object.put(PCP_D,codePostalDepart);
            object.put(COMMUNE_D,communeDepart);
            object.put(NATURE_LIEU_D,natureLieuDepart);
            object.put(EST_CHARGE_D,estChargeDepart? 1:0);
            object.put(POIDS_D,poidsDepart);
            object.put(VOLUME_D,volumeDepart);
            object.put(CONDITIONNEMENT_D,conditionnementDepart);
            object.put(NATURE_D,natureMarchandiseDepart);
            object.put(DATE_A, DateFormat.getDateTimeInstance().format(dateArrive) );
            object.put(PCP_A,codePostalArrive);
            object.put(COMMUNE_A,communeArrive);
            object.put(NATURE_LIEU_A,natureLieuArrive);
            object.put(EST_CHARGE_A,estChargeArrive? 1:0);
            object.put(POIDS_A,poidsArrive);
            object.put(VOLUME_A,volumeArrive);
            object.put(CONDITIONNEMENT_A,conditionnementArrive);
            object.put(NATURE_A,natureMarchandiseArrive);
            object.put(ID_VEHICULE,idVehicule);
            return object;
        } catch(JSONException e) { e.printStackTrace(); }
        return null;
    }
}
