package com.example.entpe.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Vehicule implements JSONable {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constants //////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private static final String ID = "id";

    private static final String PLAQUE = "plaque";
    private static final String MARQUE = "marque";
    private static final String MODELE = "modele";
    private static final String TYPE = "type";
    private static final String CATEGORIE = "categorie";
    private static final String MOTORISATION = "motorisation";

    private static final String PTAC = "ptac";
    private static final String PTRA = "ptra";
    private static final String POIDS_VIDE = "poidsvide";
    private static final String CHARGE_UTILE = "chargeutile";

    private static final String AGE = "age";
    private static final String SURFACE_SOL = "surfacesol";
    private static final String LARGEUR = "largeur";
    private static final String LONGUEUR = "longueur";
    private static final String HAUTEUR = "hauteur";

    private static final String CRITAIR = "critair";
    private static final String PHOTO = "photo";

    private static final String ETABLISSEMENT_ID = "etablissement_id";

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private int id;

    private String plaque;
    private String marque;
    private String modele;
    private int type;
    private int categorie;
    private int motorisation;

    private long ptac;
    private long ptra;
    private long poidsVide;
    private long chargeUtile;

    private int age;
    private long surfaceSol;
    private long largeur;
    private long longueur;
    private long hauteur;

    private int etablissementId;
    private int critAir;

    private String photo;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public Vehicule(JSONObject json) {
        setId(-1);
        fromJSONObject(json);
    }

    public Vehicule(int id, String plaque, String marque, String modele, int type, int categorie,
                    int motorisation, long ptac, long ptra, long poidsVide, long chargeUtile,
                    int age, long surfaceSol, long largeur, long longueur, long hauteur,
                    int etablissementId, int critAir, String photo) {
        setId(id);
        setPlaque(plaque);
        setMarque(marque);
        setModele(modele);
        setType(type);
        setCategorie(categorie);
        setMotorisation(motorisation);
        setPtac(ptac);
        setPtra(ptra);
        setPoidsVide(poidsVide);
        setChargeUtile(chargeUtile);
        setAge(age);
        setSurfaceSol(surfaceSol);
        setLargeur(largeur);
        setLongueur(longueur);
        setHauteur(hauteur);
        setEtablissementId(etablissementId);
        setCritAir(critAir);
        setPhoto(photo);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Setters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private void setId(int id) { this.id = id; }

    public void setPlaque(String plaque) { this.plaque = plaque; }

    public void setMarque(String marque) { this.marque = marque; }

    public void setModele(String modele) { this.modele = modele; }

    public void setType(int type) { this.type = type; }

    public void setCategorie(int categorie) { this.categorie = categorie; }

    public void setMotorisation(int motorisation) { this.motorisation = motorisation; }

    public void setPtac(long ptac) { this.ptac = ptac; }

    public void setPtra(long ptra) { this.ptra = ptra; }

    public void setPoidsVide(long poidsVide) { this.poidsVide = poidsVide; }

    public void setChargeUtile(long chargeUtile) { this.chargeUtile = chargeUtile; }

    public void setAge(int age) { this.age = age; }

    public void setSurfaceSol(long surfaceSol) { this.surfaceSol = surfaceSol; }

    public void setLargeur(long largeur) { this.largeur = largeur; }

    public void setLongueur(long longueur) { this.longueur = longueur; }

    public void setHauteur(long hauteur) { this.hauteur = hauteur; }

    public void setEtablissementId(int etablissementId) { this.etablissementId = etablissementId; }

    public void setCritAir(int critAir) { this.critAir = critAir; }

    public void setPhoto(String photo) { this.photo = photo; }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Getters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public int getId() { return id; }

    public String getPlaque() { return plaque; }

    public String getMarque() { return marque; }

    public String getModele() { return modele; }

    public int getType() { return type; }

    public int getCategorie() { return categorie; }

    public int getMotorisation() { return motorisation; }

    public long getPtac() { return ptac; }

    public long getPtra() { return ptra; }

    public long getPoidsVide() { return poidsVide; }

    public long getChargeUtile() { return chargeUtile; }

    public int getAge() { return age; }

    public long getSurfaceSol() { return surfaceSol; }

    public long getLargeur() { return largeur; }

    public long getLongueur() { return longueur; }

    public long getHauteur() { return hauteur; }

    public int getEtablissementId() { return etablissementId; }

    public int getCritAir() { return critAir; }

    public String getPhoto() { return photo; }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void fromJSONObject(JSONObject object) {
        try {
            setPlaque(object.getString(PLAQUE));
            setMarque(object.getString(MARQUE));
            setModele(object.getString(MODELE));
            setType(object.getInt(TYPE));
            setCategorie(object.getInt(CATEGORIE));
            setMotorisation(object.getInt(MOTORISATION));
            setPtac(object.getLong(PTAC));
            setPtra(object.getLong(PTRA));
            setPoidsVide(object.getLong(POIDS_VIDE));
            setChargeUtile(object.getLong(CHARGE_UTILE));
            setAge(object.getInt(AGE));
            setSurfaceSol(object.getLong(SURFACE_SOL));
            setLargeur(object.getLong(LARGEUR));
            setLongueur(object.getLong(LONGUEUR));
            setHauteur(object.getLong(HAUTEUR));
            setEtablissementId(object.getInt(ETABLISSEMENT_ID));
            setCritAir(object.getInt(CRITAIR));
            setPhoto(object.getString(PHOTO));
        } catch(JSONException e) { e.printStackTrace(); }
    }

    @Override
    public JSONObject toJSONObject() {
        try {
            JSONObject object = new JSONObject();
                object.put(PLAQUE, getPlaque());
                object.put(MARQUE, getMarque());
                object.put(MODELE, getModele());
                object.put(TYPE, getType());
                object.put(CATEGORIE, getCategorie());
                object.put(MOTORISATION, getMotorisation());
                object.put(PTAC, getPtac());
                object.put(PTRA, getPtra());
                object.put(POIDS_VIDE, getPoidsVide());
                object.put(CHARGE_UTILE, getChargeUtile());
                object.put(AGE, getAge());
                object.put(SURFACE_SOL, getSurfaceSol());
                object.put(LARGEUR, getLargeur());
                object.put(LONGUEUR, getLongueur());
                object.put(HAUTEUR, getHauteur());
                object.put(ETABLISSEMENT_ID, getEtablissementId());
                object.put(CRITAIR, getCritAir());
                object.put(PHOTO, getPhoto() );

            return object;
        } catch(JSONException e) { e.printStackTrace(); }
        return null;
    }

    public JSONObject toJSONObjectID() {
        try {
            JSONObject object = new JSONObject();
            object.put(ID, getId());
            object.put(PLAQUE, getPlaque());
            object.put(MARQUE, getMarque());
            object.put(MODELE, getModele());
            object.put(TYPE, getType());
            object.put(CATEGORIE, getCategorie());
            object.put(MOTORISATION, getMotorisation());
            object.put(PTAC, getPtac());
            object.put(PTRA, getPtra());
            object.put(POIDS_VIDE, getPoidsVide());
            object.put(CHARGE_UTILE, getChargeUtile());
            object.put(AGE, getAge());
            object.put(SURFACE_SOL, getSurfaceSol());
            object.put(LARGEUR, getLargeur());
            object.put(LONGUEUR, getLongueur());
            object.put(HAUTEUR, getHauteur());
            object.put(ETABLISSEMENT_ID, getEtablissementId());
            object.put(CRITAIR, getCritAir());
            object.put(PHOTO, getPhoto() );

            return object;
        } catch(JSONException e) { e.printStackTrace(); }
        return null;
    }
}