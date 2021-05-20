package com.example.entpe.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;

public class Position implements Parcelable, JSONable {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constants //////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private static final String ID = "id";
    private static final String NUM_POSITION = "num_position";
    private static final String DATE = "datepos";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String VITESSE = "vitesse";
    private static final String ALTITUDE = "altitude";
    private static final String DISTANCE = "distance";
    private static final String COURSE = "course";

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private int id;
    private int num_position;
    private Date date;
    private float latitude;
    private float longitude;
    private float vitesse;
    private float altitude;
    private float distance;
    private float course;
    private int enqueteId;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Constructeur à partir d'un Parcel
     * @param in est l'objet contenant toutes les informations nécessaires pour instancier la classe
     */
    protected Position(Parcel in) { this(in.readInt(),in.readInt(),new Date(in.readLong()),in.readFloat(), in.readFloat(), in.readFloat(), in.readFloat(), in.readFloat(), in.readFloat(), in.readInt()); }

    /**
     * Constructeur partiel
     * @param id est l'id dans la BDD
     * @param latitude est la latitude
     * @param longitude est la longitude
     * @param altitude est l'altitude
     * @param vitesse est la vitesse
     * @param distance est la distance parcourue depuis la position précédente
     * @param course est ???
     */
    public Position(int id, int num_position, float latitude, float longitude, float altitude, float vitesse,
                    float distance, float course, int enqueteId) { this(id, num_position, new Date(), latitude, longitude, vitesse, altitude, distance, course,enqueteId); }

    /**
     * Constructeur principal
     * @param id est l'id dans la BDD
     * @param date est le marqueur temporel
     * @param latitude est la latitude
     * @param longitude est la longitude
     * @param vitesse est la vitesse
     * @param altitude est l'altitude
     * @param distance est la distance parcourue depuis la position précédente
     * @param course est ???
     */
    public Position(int id, int num_position, Date date, float latitude, float longitude, float vitesse,
                    float altitude, float distance, float course, int enqueteId) {
        setId(id);
        setNum_position(num_position);
        setDate(date);
        setLatitude(latitude);
        setLongitude(longitude);
        setVitesse(vitesse);
        setAltitude(altitude);
        setDistance(distance);
        setCourse(course);
        setEnqueteId(enqueteId);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Setters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public void setId(int id) { this.id = id; }

    public void setNum_position(int num_position) { this.num_position = num_position; }

    public void setDate(Date date) { this.date = date; }

    public void setLatitude(float latitude) { this.latitude = latitude; }

    public void setLongitude(float longitude) { this.longitude = longitude; }

    public void setVitesse(float vitesse) { this.vitesse = vitesse; }

    public void setAltitude(float altitude) { this.altitude = altitude; }

    public void setDistance(float distance) { this.distance = distance; }

    public void setCourse(float course) { this.course = course; }

    public void setEnqueteId(int enqueteId) { this.enqueteId = enqueteId; }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Getters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public int getId() { return id; }

    public int getNum_position() { return num_position; }

    public Date getDate() { return date; }

    public float getLatitude() { return latitude; }

    public float getLongitude() { return longitude; }

    public float getVitesse() { return vitesse; }

    public float getAltitude() { return altitude; }

    public float getDistance() { return distance; }

    public float getCourse() { return course; }

    public int getEnqueteId() { return enqueteId; }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeLong(date.getTime());
        dest.writeFloat(latitude);
        dest.writeFloat(longitude);
        dest.writeFloat(altitude);
        dest.writeFloat(vitesse);
        dest.writeFloat(distance);
        dest.writeFloat(course);
        dest.writeInt(enqueteId);
    }

    @Override
    public int describeContents() { return 0; }

    public static final Creator<Position> CREATOR = new Creator<Position>() {
        @Override
        public Position createFromParcel(Parcel in) { return new Position(in); }

        @Override
        public Position[] newArray(int size) { return new Position[size]; }
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
                object.put(NUM_POSITION, getId());
                object.put(DATE, DateFormat.getDateTimeInstance().format(getDate()));
                object.put(LATITUDE, getLatitude());
                object.put(LONGITUDE, getLongitude());
                object.put(ALTITUDE, getAltitude());
                object.put(VITESSE, getVitesse());
                object.put(DISTANCE, getDistance());
                object.put(COURSE, getCourse());

            return object;
        } catch(JSONException e) { e.printStackTrace(); }
        return null;
    }
}
