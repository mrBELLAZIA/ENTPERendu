package com.example.entpe.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;

public class Arret implements Parcelable, JSONable {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constants //////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private static final String ID = "id";
    private static final String NUM_ARRET = "num_arret";
    private static final String HEURE_ARRIVEE = "heure_arrivee";
    private static final String LIEU_STATIONNEMENT = "lieu_stationnement";
    private static final String NUM_CLIENT = "num_client";
    private static final String ADRESSE_CLIENT = "adresse";
    
    private static final String NATURE_LIEU = "nature_lieu";
    private static final String ACTION = "nature_action";
    private static final String LIEU_ACTION = "lieu_action";
    private static final String MOYEN_MANUTENTION = "moyen_manutention";
    private static final String NATURE_CONDITIONNEMENT = "nature_conditionnement";
    private static final String NOMBRE_UNITE = "nombre_unite";
    private static final String NATURE_MARCHANDISE = "nature_marchandise";
    private static final String POIDS = "poids";
    private static final String VOLUME = "volume";
    private static final String OPERATION_EFFECTUEE = "operation_effectuee";
    private static final String HEURE_DEPART = "heure_depart";
    private static final String COMMENTAIRE = "commentaire";
    private static final String PHOTO = "photo";
    private static final String LONGITUDE = "longitude";
    private static final String LATITUDE = "latitude";

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private int id;
    private int num_arret;
    private Date heureArrive;
    private String lieuStationnement;
    private int numClient;
    private String adresseClient;

    private String natureLieu;
    private String action;
    private String lieuAction;
    private String moyenManutention;
    private String natureConditionnement;
    private int nombreUnite;
    private String natureMarchandise;
    private float poids;
    private float volume;
    private String operationEffectue;
    private Date heureDepart;
    private String commentaire;
    private String photo;
    private  int enqueteId;
    private float longitude;
    private float latitude;


    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Constructeur vide
     */
    public Arret() { this(new Date(), -1); }

    /**
     * Constructeur partiel
     * @param arrivee est la date d'arrivée à l'arrêt
     */
    public Arret(Date arrivee, int enqueteId) {
        this(-1, -1,arrivee, "", -1, "", "",
                "", "", "", "",
                -1, "", -1.0f, -1.0f,
                "", new Date(), "","",enqueteId,0,0);
    }

    /**
     * Constructeur à partir d'un Parcel
     * @param in est l'objet contenant les informations de construction de l'objet
     */
    protected Arret(Parcel in) {
        this(in.readInt(), in.readInt(),new Date(in.readLong()), in.readString(), in.readInt(), in.readString(),
                in.readString(), in.readString(), in.readString(), in.readString(), in.readString(),
                in.readInt(), in.readString(), in.readFloat(), in.readFloat(), in.readString(),
                new Date(in.readLong()), in.readString(),in.readString(), in.readInt(), in.readFloat(),in.readFloat());
    }

    /**
     * Constructeur complet
     * @param id est l'id de l'arrêt dans la BDD
     * @param heureArrivee est l'heure d'arrivée
     * @param lieuStationnement est le lieu de stationnement
     * @param numClient est le numéro du client
     * @param natureLieu est la nature du lieu
     * @param action représente l'action effectuée
     * @param lieuAction est le lieu où l'action est effectuée
     * @param moyenManutention représente le moyen de manutention utilisé
     * @param natureConditionnement représente la nature du conditionnement
     * @param nombreUnite représente le nombre d'unités transvasées
     * @param natureMarchandise représente la nature des marchandises
     * @param poids correspond au poids transvasé
     * @param volume correspond au volume transvasé
     * @param operationEffectuee correspond à l'opération effectuée
     * @param heureDepart est l'heure de départ
     * @param commentaire est un commentaire additionnel
     */
    public Arret(int id,int num_arret, Date heureArrivee, String lieuStationnement,
                 int numClient, String adresseClient, String natureLieu, String action,
                 String lieuAction, String moyenManutention, String natureConditionnement,
                 int nombreUnite, String natureMarchandise, float poids,
                 float volume, String operationEffectuee, Date heureDepart,
                 String commentaire,String photo, int enqueteId, float longitude, float latitude) {
        setId(id);
        setNum_arret(num_arret);
        setHeureArrivee(heureArrivee);
        setLieuStationnement(lieuStationnement);
        setNumClient(numClient);
        setAdresseClient(adresseClient);
        setNatureLieu(natureLieu);
        setAction(action);
        setLieuAction(lieuAction);
        setMoyenManutention(moyenManutention);
        setNatureConditionnement(natureConditionnement);
        setNombreUnite(nombreUnite);
        setNatureMarchandise(natureMarchandise);
        setPoids(poids);
        setVolume(volume);
        setOperationEffectuee(operationEffectuee);
        setHeureDepart(heureDepart);
        setCommentaire(commentaire);
        setPhoto(photo);
        setEnqueteId(enqueteId);
        setLongitude(longitude);
        setLatitude(latitude);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Setters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public void setId(int id) { this.id = id; }

    public void setNum_arret(int num_arret) { this.num_arret = num_arret; }

    public void setHeureArrivee(Date heureArrivee) { this.heureArrive = heureArrivee; }

    public void setLieuStationnement(String lieuStationnement) { this.lieuStationnement = lieuStationnement; }

    public void setNumClient(int numClient) { this.numClient = numClient; }

    public void setAdresseClient(String adresseClient) { this.adresseClient = adresseClient; }

    public void setNatureLieu(String natureLieu) { this.natureLieu = natureLieu; }

    public void setAction(String action) { this.action = action; }

    public void setLieuAction(String lieuAction) { this.lieuAction = lieuAction; }

    public void setMoyenManutention(String moyenManutention) { this.moyenManutention = moyenManutention; }

    public void setNatureConditionnement(String natureConditionnement) { this.natureConditionnement = natureConditionnement; }

    public void setNombreUnite(int nombreUnite) { this.nombreUnite = nombreUnite; }

    public void setNatureMarchandise(String natureMarchandise) { this.natureMarchandise = natureMarchandise; }

    public void setPoids(float poids) { this.poids = poids; }

    public void setVolume(float volume) { this.volume = volume; }

    public void setOperationEffectuee(String operationEffectuee) { this.operationEffectue = operationEffectuee; }

    public void setHeureDepart(Date heureDepart) { this.heureDepart = heureDepart; }

    public void setCommentaire(String commentaire) { this.commentaire = commentaire; }

    public void setPhoto(String photo) { this.photo = photo; }

    public void setEnqueteId(int enqueteId) { this.enqueteId = enqueteId; }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setArret(int id, int num_arret, Date arrivee, String lieuStationnement, int numClient,
                         String adresseClient, String natureLieu, String action, String lieuAction,
                         String moyenManutention, String natureConditionnement, int nombreUnite,
                         String natureMarchandise, float poids, float volume,
                         String operationEffectue, Date depart, String commentaire, String photo, int enqueteId,float longitude, float latitude) {
        setId(id);
        setNum_arret(num_arret);
        setHeureArrivee(arrivee);
        setLieuStationnement(lieuStationnement);
        setNumClient(numClient);
        setAdresseClient(adresseClient);
        setNatureLieu(natureLieu);
        setAction(action);
        setLieuAction(lieuAction);
        setMoyenManutention(moyenManutention);
        setNatureConditionnement(natureConditionnement);
        setNombreUnite(nombreUnite);
        setNatureMarchandise(natureMarchandise);
        setPoids(poids);
        setVolume(volume);
        setOperationEffectuee(operationEffectue);
        setCommentaire(commentaire);
        setHeureDepart(depart);
        setPhoto(photo);
        setEnqueteId(enqueteId);
        setLongitude(longitude);
        setLatitude(latitude);

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Getters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public int getId() { return id; }

    public int getNum_arret() { return num_arret; }

    public Date getHeureArrivee() { return heureArrive; }

    public String getLieuStationnement() { return lieuStationnement; }

    public int getNumClient() { return numClient; }

    public String getAdresseClient() { return adresseClient; }

    public String getNatureLieu() { return natureLieu; }

    public String getAction() { return action; }

    public String getLieuAction() { return lieuAction; }

    public String getMoyenManutention() { return moyenManutention; }

    public String getNatureConditionnement() { return natureConditionnement; }

    public int getNombreUnite() { return nombreUnite; }

    public String getNatureMarchandise() { return natureMarchandise; }

    public float getPoids() { return poids; }

    public float getVolume() { return volume; }

    public String getOperationEffectuee() { return operationEffectue; }

    public Date getHeureDepart() { return heureDepart; }

    public String getCommentaire() { return commentaire; }

    public String getPhoto() { return photo; }

    public int getEnqueteId() { return enqueteId; }

    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(num_arret);
        dest.writeLong(heureArrive.getTime());
        dest.writeString(lieuStationnement);
        dest.writeInt(numClient);
        dest.writeString(adresseClient);
        dest.writeString(natureLieu);
        dest.writeString(action);
        dest.writeString(lieuAction);
        dest.writeString(moyenManutention);
        dest.writeString(natureConditionnement);
        dest.writeInt(nombreUnite);
        dest.writeString(natureMarchandise);
        dest.writeFloat(poids);
        dest.writeFloat(volume);
        dest.writeString(operationEffectue);
        dest.writeLong(heureDepart.getTime());
        dest.writeString(commentaire);
        dest.writeString(photo);
        dest.writeInt(enqueteId);
        dest.writeFloat(longitude);
        dest.writeFloat(latitude);
    }

    @Override
    public int describeContents() { return 0; }

    public static final Creator<Arret> CREATOR = new Creator<Arret>() {
        @Override
        public Arret createFromParcel(Parcel in) { return new Arret(in); }

        @Override
        public Arret[] newArray(int size) { return new Arret[size]; }
    };

    //////////////////////////////////////////////////////////////////////
    //// JSONable methods ////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////
    @Override
    public void fromJSONObject(JSONObject object) { }

    @Override
    public JSONObject toJSONObject() {
        try {
            JSONObject object = new JSONObject();
                object.put(NUM_ARRET, getNum_arret());
                object.put(HEURE_ARRIVEE,  DateFormat.getDateTimeInstance().format(getHeureArrivee()) );
                object.put(LIEU_STATIONNEMENT, getLieuStationnement());
                object.put(NUM_CLIENT, getNumClient());
                object.put(ADRESSE_CLIENT, getAdresseClient());

                object.put(NATURE_LIEU, getNatureLieu());
                object.put(ACTION, getAction());
                object.put(LIEU_ACTION, getLieuAction());
                object.put(MOYEN_MANUTENTION, getMoyenManutention());
                object.put(NATURE_CONDITIONNEMENT, getNatureConditionnement());
                object.put(NOMBRE_UNITE, getNombreUnite());
                object.put(NATURE_MARCHANDISE, getNatureMarchandise());
                object.put(POIDS, getPoids());
                object.put(VOLUME, getVolume());
                object.put(OPERATION_EFFECTUEE, getOperationEffectuee());
                object.put(HEURE_DEPART, DateFormat.getDateTimeInstance().format(getHeureDepart()));
                object.put(COMMENTAIRE, getCommentaire());
                object.put(PHOTO, getPhoto());
                object.put(LONGITUDE, getLongitude());
                object.put(LATITUDE, getLatitude());

            
            return object;
        } catch(JSONException e) { e.printStackTrace(); }
        return null;
    }
}
