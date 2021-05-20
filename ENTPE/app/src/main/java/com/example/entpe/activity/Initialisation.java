package com.example.entpe.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.entpe.R;
import com.example.entpe.application.MyApplication;
import com.example.entpe.dialog.ChoixEtablissementDialog;
import com.example.entpe.dialog.ChoixVehiculeDialog;
import com.example.entpe.dialog.Updatable;
import com.example.entpe.dialog.radioButton.InitConditionnements;
import com.example.entpe.dialog.radioButton.NatureMarchandiseDialog;
import com.example.entpe.model.Enquete;
import com.example.entpe.model.EnqueteCSV;
import com.example.entpe.model.Etablissement;
import com.example.entpe.model.Vehicule;
import com.example.entpe.service.GpsService;
import com.example.entpe.storage.DataBaseManager;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;


public class Initialisation extends AppCompatActivity implements Updatable {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constants /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private static final int ID = -1;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private Etablissement etablissementCourant = null;
    private Vehicule vehiculeCourant = null;
    private String natureMerch;
    private String conditionnements;
    private DataBaseManager dbm;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Setters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public void setEtablissementCourant(Etablissement etablissementCourant) { this.etablissementCourant = etablissementCourant; }

    public void setVehiculeCourant(Vehicule vehiculeCourant) { this.vehiculeCourant = vehiculeCourant; }

    public void setNatureMerch(String natureMerch) { this.natureMerch = natureMerch; }

    public void setConditionnements(String conditionnements) { this.conditionnements = conditionnements; }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Getters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public Etablissement getEtablissementCourant() { return etablissementCourant; }

    public String getNatureMerch() { return natureMerch;}

    public String getConditionnements() { return natureMerch;}

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Choix du layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initialisation);
        //////////////////////////////////////////////////////////////////
        //// Demande d'accès aux diffèrents périphériques/////////////////
        //////////////////////////////////////////////////////////////////
        ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION, Manifest.permission.FOREGROUND_SERVICE,Manifest.permission.CAMERA}, 5);
        int permissionCheckWrite = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissionCheckWrite != PackageManager.PERMISSION_GRANTED) {
            // ask permissions here using below code
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
            // ask permissions here using below code
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }

        int permissionCheckBack = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION);
        if(permissionCheckBack != PackageManager.PERMISSION_GRANTED) {
            // ask permissions here using below code
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                    1);
        }

        int permissionCheckFore = ContextCompat.checkSelfPermission(this,
                Manifest.permission.FOREGROUND_SERVICE);
        if(permissionCheckFore != PackageManager.PERMISSION_GRANTED) {
            // ask permissions here using below code
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.FOREGROUND_SERVICE},
                    1);
        }

        int permissionCheckCamera = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA);
        if(permissionCheckCamera!= PackageManager.PERMISSION_GRANTED){
            // ask permissions here using below code
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    1);
        }

        //////////////////////////////////////////////////////////////////
        //// Initialisation des attributs ////////////////////////////////
        //////////////////////////////////////////////////////////////////
        dbm = new DataBaseManager(getApplicationContext());

        //////////////////////////////////////////////////////////////////
        //// Lancement activité //////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        // À la création ,le champ de saisie du chargement n'est pas visible
        final LinearLayout Chargement = findViewById(R.id.editChargement);

        Chargement.setVisibility(View.GONE);
        ((RadioButton)findViewById(R.id.initDomicile)).setChecked(true);
        findViewById(R.id.choixVehicule).setVisibility(View.INVISIBLE);
        findViewById(R.id.autresdepartext).setVisibility(View.GONE);

        //////////////////////////////////////////////////////////////////
        //// Chargement //////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        // Switch permettant d'afficher ou non le champ de choix du chargement
        final Switch aVide = findViewById(R.id.estVide);

        aVide.setChecked(false);
        aVide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //Si l'on commence avec un chargement
                    Chargement.setVisibility(View.VISIBLE);
                } else {
                    //Si l'on commence la tournée à vide
                    Chargement.setVisibility(View.GONE);
                }
            }
        });

        //////////////////////////////////////////////////////////////////
        //// Choix de départ /////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        RadioGroup depart = findViewById(R.id.choixLieu);
        depart.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String choix = ((RadioButton)findViewById(checkedId)).getText().toString();
                if("Autres".equals(choix)) { findViewById(R.id.autresdepartext).setVisibility(View.VISIBLE); }
                else { findViewById(R.id.autresdepartext).setVisibility(View.GONE); }
            }
        });

        //////////////////////////////////////////////////////////////////
        //// Choix de l'établissement ////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        final Button choixEta = findViewById(R.id.choixEtablissement);
        choixEta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) { (new ChoixEtablissementDialog(Initialisation.this)).show(getSupportFragmentManager(),""); }
        });

        //////////////////////////////////////////////////////////////////
        //// Choix du véhicule ///////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        Button choixVehicule = findViewById(R.id.choixVehicule);
        choixVehicule.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) { (new ChoixVehiculeDialog(Initialisation.this)).show(getSupportFragmentManager(), ""); }
        });

        //////////////////////////////////////////////////////////////////
        //// Choix du NatureMerch ////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        Button choixNatureMerch = findViewById(R.id.choixNatureMerch);
        choixNatureMerch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) { (new NatureMarchandiseDialog(Initialisation.this)).show(getSupportFragmentManager(), ""); }
        });

        //////////////////////////////////////////////////////////////////
        //// Choix du conditionnement ////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        Button choixConditionnement = findViewById(R.id.choixConditionnements);
        choixConditionnement.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) { (new InitConditionnements(Initialisation.this)).show(getSupportFragmentManager(), ""); }
        });

        //////////////////////////////////////////////////////////////////
        //// Initialisation //////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        Button Init = findViewById(R.id.BoutonInit);
        Init.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CutPasteId")
            public void onClick(View view) {
                // Récupération et vérification des valeurs
                EnqueteCSV enq;
                boolean correct = true;
                String nom = "";
                Date dateFormat = new Date();
                String date = dateFormat.toString();

                if(!((EditText) findViewById(R.id.editNomChauffeur)).getText().toString().equals("")) { nom = ((EditText) findViewById(R.id.editNomChauffeur)).getText().toString(); }
                else { correct = false; }

                String postal;

                if(!((EditText)findViewById(R.id.editPaysOuPostal)).getText().toString().equals("")) { postal = ((EditText) findViewById(R.id.editPaysOuPostal)).getText().toString(); }
                else { postal = "A remplir"; }

                String commune;

                if(!((EditText)findViewById(R.id.editNomCommune)).getText().toString().equals("")) { commune = ((EditText)findViewById(R.id.editPaysOuPostal)).getText().toString(); }
                else { commune = "A remplir"; }

                String lieuDepart;
                RadioGroup choixNatureLieu =  findViewById(R.id.choixLieu);
                RadioButton choixLieu = choixNatureLieu.findViewById(choixNatureLieu.getCheckedRadioButtonId());

                if(choixLieu != null) {
                    lieuDepart = choixLieu.getText().toString();
                    if(choixLieu.getText().toString().equals("Autres")) { lieuDepart = ((EditText)findViewById(R.id.autresdepartext)).getText().toString(); }
                } else { lieuDepart = "A remplir"; }

                String etablissement = "";

                if(etablissementCourant != null) { etablissement = String.valueOf(etablissementCourant.getId()); }
                else { correct = false; }

                String vehicule = "";

                if(vehiculeCourant != null) { vehicule = String.valueOf(vehiculeCourant.getId()); }
                else { correct = false; }

                String estCharge;

                if(aVide.isChecked()) { estCharge="oui"; }
                else { estCharge="non"; }

                String poids;
                float poidsDbm;

                if(!((EditText)findViewById(R.id.editChargementPoids)).getText().toString().equals("")) {
                    poids = ((EditText)findViewById(R.id.editChargementPoids)).getText().toString();
                    poidsDbm = Float.parseFloat(((EditText)findViewById(R.id.editChargementPoids)).getText().toString());
                }
                else {
                    poids = "A remplir";
                    poidsDbm = 99f;
                }

                String volume;
                float volumeDbm;

                if(!((EditText)findViewById(R.id.editChargementVolume)).getText().toString().equals("")) {
                    volume = ((EditText)findViewById(R.id.editChargementVolume)).getText().toString();
                    volumeDbm = Float.parseFloat(((EditText)findViewById(R.id.editChargementVolume)).getText().toString());
                }
                else {
                    volume = "A remplir";
                    volumeDbm = 99f;
                }
                if(conditionnements == null){
                    conditionnements = "incomplet";
                }
                if(natureMerch == null){
                    natureMerch = "incomplet";
                }

                if(correct) {
                    enq = new EnqueteCSV(nom);

                    Toast.makeText(getApplicationContext(), enq.getRepertoire(), Toast.LENGTH_LONG).show();

                    //===================================AJOUT DANS LE CSV==============================================================================
                    try {
                        enq.ajoutInformation(new String[] {
                            DateFormat.getDateTimeInstance().format(dateFormat),
                            nom,
                            postal,
                            commune,
                            lieuDepart,
                            etablissement,
                            vehicule,
                            estCharge,
                            poids,
                            volume,
                            natureMerch,
                            conditionnements
                        });
                    } catch(IOException e) { e.printStackTrace(); }
                    //===============================AJOUT DANS LA BDD==================================================================================


                    Enquete enqueteDbm = new Enquete(ID,nom,dateFormat,postal,commune,lieuDepart,aVide.isChecked(),poidsDbm,volumeDbm,conditionnements,natureMerch,vehiculeCourant.getId());
                    dbm.insert(enqueteDbm);

                    //============================REDIRECTION VERS L'ACTIVITE ENTREARRET================================================================
                    int id = dbm.findIdEnqueteCourante();
                    Intent myIntent = new Intent(view.getContext(), EntreArret.class);

                    MyApplication.setEnquete(enq);
                    MyApplication.setEnqueteId(id);
                    //=====================ENVOI DES ATTRIBUTS DE L'ENQUETE===========================
                    //myIntent.putExtra("EnqueteId",id);
                    //================================================================================
                    ///LANCEMENT DU GPS
                    Intent gpsService =  new Intent(getApplicationContext(), GpsService.class);
                    MyApplication.setEtatGps(MyApplication.ETAT_RUNNING);
                    //gpsService.putExtra("EnqueteId",id);
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                        startForegroundService(gpsService);
                    }else{
                        startService(gpsService);
                    }
                    startActivity(myIntent);
                } else {
                    Toast.makeText(getApplicationContext(),"Completez les champs annotés d'un *", Toast.LENGTH_LONG).show();
                    if (etablissementCourant == null) {((TextView)findViewById(R.id.etablissementChoisi)).setTextColor(Color.RED);}
                    if (vehiculeCourant == null) {((TextView)findViewById(R.id.VehiculeChoisi)).setTextColor(Color.RED);}
                    if (((EditText) findViewById(R.id.editNomChauffeur)).getText().toString().equals("")) {((TextView)findViewById(R.id.NomChauffeur)).setTextColor(Color.RED);}
                }
            }
        });
    }

    @Override
    public void update() {
        // Mise à jour de l'établissement selectionné
        TextView texteEtablissement = findViewById(R.id.etablissementChoisi);
        String etablissement = "Etablissement: ";

        if(etablissementCourant != null) {
            etablissement += etablissementCourant.getNom();
            texteEtablissement.setText(etablissement);
            findViewById(R.id.choixVehicule).setVisibility(View.VISIBLE);
        } else {
            etablissement += "Aucun";
            texteEtablissement.setText(etablissement);
            findViewById(R.id.choixVehicule).setVisibility(View.INVISIBLE);
            vehiculeCourant = null;
        }

        // Mise à jour du véhicule selectionné
        TextView texteVehicule = findViewById(R.id.VehiculeChoisi);
        String vehicule = "Véhicule: ";

        if(vehiculeCourant != null) { vehicule += vehiculeCourant.getPlaque(); }
        else { vehicule += "Aucun"; }

        texteVehicule.setText(vehicule);
    }

    public String prendreNumero(@NonNull String val) {
        StringBuilder num = new StringBuilder();

        for(int i = 0 ; i < val.length() ; i++) {
            String sub = val.substring(i, i + 1);
            if(sub.equals("-")) { break; }
            else { num.append(sub); }
        }

        return num.toString();
    }
}
