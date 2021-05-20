package com.example.entpe.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Etablissement implements JSONable {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constants //////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private static final String ID = "id";
    private static final String SIRET = "siret";
    private static final String NOM = "nom";
    private static final String ADRESSE = "adresse";
    private static final String CODE_POSTAL = "code_postal";
    private static final String VILLE = "ville";
    private static final String NATURE = "nature";

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private int id;

    private String siret;
    private String nom;

    private String adresse;
    private String codePostal;
    private String ville;

    private String nature;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Constructeur à partir d'un JSONObject
     * @param json - L'objet JSON contenant les informations
     */
    public Etablissement(JSONObject json) {
        setId(-1);
        fromJSONObject(json);
    }

    /**
     * Constructeur principal
     * @param id est l'identifiant de l'établissement dans son espace de stockage
     * @param siret est son numéro de siret
     * @param nom est le nom de l'entreprise
     * @param adresse est l'adresse de l'entreprise
     * @param codePostal représente le code postal
     * @param ville correspond à la ville où est située l'entreprise
     * @param nature correspond à la nature de l'établissement
     */
    public Etablissement(int id, String siret, String nom, String adresse, String codePostal, String ville, String nature) {
        setId(id);
        setSiret(siret);
        setNom(nom);
        setAdresse(adresse);
        setCodePostal(codePostal);
        setVille(ville);
        setNature(nature);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Setters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private void setId(int id) { this.id = id; }

    public void setSiret(String siret) { this.siret = siret; }

    public void setNom(String nom) { this.nom = nom; }

    public void setAdresse(String adresse) { this.adresse = adresse; }

    public void setCodePostal(String codePostal) { this.codePostal = codePostal; }

    public void setVille(String ville) { this.ville = ville; }

    public void setNature(String nature) { this.nature = nature; }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Getters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public int getId() { return id; }

    public String getSiret() { return siret; }

    public String getNom() { return nom; }

    public String getAdresse() { return adresse; }

    public String getCodePostal() { return codePostal; }

    public String getVille() { return ville; }

    public String getNature() { return nature; }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void fromJSONObject(JSONObject object) {
        try {
            setSiret(object.getString(SIRET));
            setNom(object.getString(NOM));
            setAdresse(object.getString(ADRESSE));
            setCodePostal(object.getString(CODE_POSTAL));
            setVille(object.getString(VILLE));
            setNature(object.getString(NATURE));
        } catch(JSONException e) { e.printStackTrace(); }
    }

    @Override
    public JSONObject toJSONObject() {
        try {
            JSONObject object = new JSONObject();
                object.put(SIRET, getSiret());
                object.put(NOM, getNom());
                object.put(ADRESSE, getAdresse());
                object.put(CODE_POSTAL, getCodePostal());
                object.put(VILLE, getVille());
                object.put(NATURE, getNature());

            return object;
        } catch(JSONException e) { e.printStackTrace(); }
        return null;
    }

    public JSONObject toJSONObjectID() {
        try {
            JSONObject object = new JSONObject();
            object.put(ID, getId());
            object.put(SIRET, getSiret());
            object.put(NOM, getNom());
            object.put(ADRESSE, getAdresse());
            object.put(CODE_POSTAL, getCodePostal());
            object.put(VILLE, getVille());
            object.put(NATURE, getNature());

            return object;
        } catch(JSONException e) { e.printStackTrace(); }
        return null;
    }
}
