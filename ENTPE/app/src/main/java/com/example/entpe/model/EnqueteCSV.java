package com.example.entpe.model;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.entpe.application.MyApplication;
import com.example.entpe.storage.DataBaseManager;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

// TODO : REVOIR AVEC THEO
public class EnqueteCSV implements Parcelable {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constants //////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private static final String[] INFORMATION_COLUMNS = {
            "Date",
            "NomChauffeur",
            "Pays ou Code Postal",
            "Commune","Nature du lieu",
            "idEtablissement",
            "idVéhicule",
            "Chargé au départ",
            "Poids",
            "Volume",
            "Nature Marchandise",
            "Conditionnements"
    };

    private static final String[] POSITIONS_COLUMNS = {
            "Date",
            "id",
            "Latitude",
            "Longitude",
            "Altitude",
            "Speed",
            "Orientation",
            "Distance",
            "Etat"
    };

    private static final String[] ARRETS_COLUMNS = {
            "numArret",
            "numClient",
            "Heure arrivé",
            "Lieu stationnement",
            "Adresse Client",
            "Nature du lieu",
            "Action menée",
            "Lieu de l'action",
            "Moyen de manutention",
            "Condidionnement",
            "Nombre d'unité",
            "Nature marchandise",
            "Poids",
            "Volume",
            "Operation effectuée",
            "Commentaire",
            "Heure départ",
            "longitude",
            "latitude"
    };

    private static final String[] FIN_COLUMNS = {
            "Date",
            "Pays ou Code Postal",
            "Commune",
            "Nature du lieu",
            "Arrivé chargé ?",
            "Nature marchandise",
            "Conditionnement"
    };

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private int numArretCourant;
    private int numClientCourant;

    private String repertoire;
    private String position;
    private String information;
    private String arret;
    private String finTournee;

    private ArrayList<Arret> arretsListe;
    private ArrayList<Position> positionsListe;

    private int compteurPosition;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor ////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Constructeur basé sur un parcel
     * @param in est un objet contenant toutes les informations nécessaires pour instancier l'objet
     */
    protected EnqueteCSV(Parcel in) { readFromParcel(in); }

    public EnqueteCSV(String nom) {
        setNumArretCourant(1);
        setNumClientCourant(1);
        setCompteurPosition(1);

        this.arretsListe = new ArrayList<>();
        this.positionsListe =  new ArrayList<>();

        // Récuperation de la date
        String date = SimpleDateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        
        // Création du répertoire
        setRepertoire(nom, date);

        // Création du fichier contenant les données liées aux infos de début d'enquêtes
        setInformation(nom);
        writeInit(getInformation(), INFORMATION_COLUMNS);

        // Création du fichier contenant les données liées aux infos de position
        setPosition(nom);
        writeInit(getPosition(), POSITIONS_COLUMNS);

        // Création du fichier contenant les données liées aux infos des arrêts
        setArret(nom);
        writeInit(getArret(), ARRETS_COLUMNS);

        // Création du fichier contenant les données liées aux infos de fin de tournée
        setFinTournee(nom);
        writeInit(getFinTournee(), FIN_COLUMNS);
    }

    public EnqueteCSV(int idEnquete, int numArretCourant, int numClientCourant, String repertoire, String position,String arret, String finTournee)
    {
        //Affectation des champs
        setNumArretCourant(numArretCourant);
        setNumClientCourant(numClientCourant);
        this.repertoire = repertoire;
        this.position = position;
        this.arret = arret;
        this.finTournee = finTournee;



        //Récupération des listes
        DataBaseManager manager = new DataBaseManager(MyApplication.getAppContext());
        arretsListe = (ArrayList<Arret>) manager.findAllArretByID(idEnquete);
        positionsListe = (ArrayList<Position>) manager.findAllPostionByID(idEnquete);

        //Récupération du compteur d'arrêt
        if(positionsListe.size()!=0){
            setCompteurPosition(positionsListe.get(positionsListe.size()-1).getNum_position() +1);

        }else{
            setCompteurPosition(1);
        }


    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Setters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private void setNumArretCourant(int numArretCourant) { this.numArretCourant = numArretCourant; }

    private void setNumClientCourant(int numClientCourant) { this.numClientCourant = numClientCourant; }

    private void setCompteurPosition(int compteurPosition) { this.compteurPosition = compteurPosition; }

    private void setRepertoire(String repertoire) { this.repertoire = repertoire; }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("SdCardPath")
    private void setRepertoire(String nom, String date) {
        File parent = new File("/sdcard","/entpe");
        if(!parent.exists()){
            parent.mkdir();
        }
        File repertoire = new File("/sdcard/entpe", "/" + nom + date);
        setRepertoire(repertoire.getAbsolutePath());
        repertoire.mkdir();
    }

    private void setInformation(String nom) {
        File information = new File(getRepertoire(), nom + "Informations.csv");
        this.information = information.getAbsolutePath();
    }

    private void setPosition(String nom) {
        File position = new File(getRepertoire(), nom + "Positions.csv");
        this.position = position.getAbsolutePath();
    }

    private void setArret(String nom) {
        File arret = new File(getRepertoire(), nom + "Arrets.csv");
        this.arret = arret.getAbsolutePath();
    }

    private void setFinTournee(String nom) {
        File finTournee = new File(getRepertoire(), nom + "FinTournee.csv");
        this.finTournee = finTournee.getAbsolutePath();
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Getters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public String getRepertoire() { return repertoire; }

    public String getInformation() { return information; }

    public String getPosition() { return position; }

    public String getArret() { return arret; }

    public String getFinTournee() { return finTournee; }

    public int getNumClientCourant() {
        return numClientCourant;
    }

    public int getNumArretCourant() {
        return numArretCourant;
    }

    public ArrayList<Arret> getArretsListe() { return arretsListe; }

    public int getCompteurPosition() { return compteurPosition; }

    public ArrayList<Position> getPositionsListe() {
        return positionsListe;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    //// Overridden methods //////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(repertoire);
        dest.writeString(position);
        dest.writeString(information);
        dest.writeString(arret);
        dest.writeString(finTournee);
        dest.writeTypedList(arretsListe);
        dest.writeInt(numArretCourant);
        dest.writeInt(numClientCourant);
        dest.writeTypedList(positionsListe);
        dest.writeInt(compteurPosition);
    }

    public static final Creator<EnqueteCSV> CREATOR = new Creator<EnqueteCSV>() {
        @Override
        public EnqueteCSV createFromParcel(Parcel in) { return new EnqueteCSV(in); }

        @Override
        public EnqueteCSV[] newArray(int size) { return new EnqueteCSV[size]; }
    };

    private void readFromParcel(Parcel in){
        setRepertoire(in.readString());
        position = in.readString();
        information = in.readString();
        arret = in.readString();
        finTournee = in.readString();
        arretsListe = in.createTypedArrayList(Arret.CREATOR);
        setNumArretCourant(in.readInt());
        setNumClientCourant(in.readInt());
        positionsListe = in.createTypedArrayList(Position.CREATOR);
        setCompteurPosition(in.readInt());
    }

    //////////////////////////////////////////////////////////////////////
    //// CSV writing methods /////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////
    /**
     * Initialise un fichier CSV
     * @param path est le chemin vers le fichier CSV
     * @param data représente les noms des colonnes
     */
    private void writeInit(String path, String[] data) {
        List<String[]> dataList = new ArrayList<>();
        dataList.add(data);

        try {
            CSVWriter writer = new CSVWriter(new FileWriter(path));
            writer.writeAll(dataList);
            writer.close();
        } catch(IOException e) { e.printStackTrace(); }
    }

    /**
     * Écrit dans un fichier CSV
     * @param path est le chemin vers le fichier dans lequel écrire
     * @param data représente les informations à écrire
     * @throws IOException est lancée en cas de problème à l'écriture
     */
    private void write(String path, String[] data) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(path, true));
        writer.writeNext(data);
        writer.close();
    }

    public void ajoutInformation(String[] data) throws IOException { write(getInformation(), data); }

    public void ajoutPosition(String[] data) throws IOException { write(getPosition(), data); }

    public void ajoutArret(String[] data) throws IOException { write(getArret(), data); }

    public void ajoutFinTournee(String[] data) throws IOException { write(getFinTournee(), data); }

    //////////////////////////////////////////////////////////////////////
    //// Counter methods /////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////
    public void resetCompteur() { setNumClientCourant(1); }

    public void incrementeClient() { setNumClientCourant(getNumClientCourant() + 1); }

    public void incrementeArret(){
        setNumArretCourant(getNumArretCourant() + 1);
        resetCompteur();
    }

    /*********************************************************************************************/
    public void ajouterArretSup(Arret arret) {
        arretsListe.add(arret);
        incrementeClient();
    }

    public void ajouterArret(Arret arret) { arretsListe.add(arret); }
    /*********************************************************************************************/

    public Arret dernierArret() { return arretsListe.get(arretsListe.size() - 1); }

    public Position dernierePosition() {return positionsListe.get(positionsListe.size()-1);}

    public void ajouterPosition(Position position) {
        positionsListe.add(position);
        incrementPosition();
    }

    public void incrementPosition() { setCompteurPosition(getCompteurPosition() + 1); }

    public void majCSV(int id) throws IOException {
        DataBaseManager dbm = new DataBaseManager(MyApplication.getAppContext());
        ArrayList<Arret> liste = (ArrayList<Arret>)dbm.findAllArretByID(id);
        CSVWriter writer = new CSVWriter(new FileWriter(getArret(),false));
        writer.writeAll(Collections.singleton(ARRETS_COLUMNS));
        writer.close();



        //Boucle pour les arrêts
        for(int i=0;i<liste.size();i++) {
            Arret arret = liste.get(i);
            String[] chaine = new String[]{
                    String.valueOf(arret.getId()),
                    Integer.toString(arret.getNumClient()),
                    DateFormat.getDateTimeInstance().format(arret.getHeureArrivee()),
                    arret.getLieuStationnement(),
                    arret.getAdresseClient(),
                    arret.getNatureLieu(),
                    arret.getAction(),
                    arret.getLieuAction(),
                    arret.getMoyenManutention(),
                    arret.getNatureConditionnement(),
                    Integer.toString(arret.getNombreUnite()),
                    arret.getNatureMarchandise(),
                    Float.toString(arret.getPoids()),
                    Float.toString(arret.getVolume()),
                    arret.getOperationEffectuee(),
                    arret.getCommentaire(),
                    DateFormat.getDateTimeInstance().format(arret.getHeureDepart()),
                    Float.toString(arret.getLongitude()),
                    Float.toString(arret.getLatitude())
            };
            ajoutArret(chaine);
        }


    }

}
