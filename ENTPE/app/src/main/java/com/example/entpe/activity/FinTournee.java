package com.example.entpe.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import androidx.appcompat.app.AppCompatActivity;


import com.example.entpe.R;
import com.example.entpe.application.MyApplication;
import com.example.entpe.model.ApiManager;
import com.example.entpe.model.Enquete;
import com.example.entpe.storage.DataBaseManager;

import org.json.JSONException;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

public class FinTournee extends AppCompatActivity {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Choix du layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fin_de_tournee);

        findViewById(R.id.editAutreChoix).setVisibility(View.GONE);
        findViewById(R.id.layoutFin).setVisibility(View.GONE);
        findViewById(R.id.editAutreCondi).setVisibility(View.GONE);
        findViewById(R.id.autreNature).setVisibility(View.GONE);
        findViewById(R.id.layoutFin).setVisibility(View.GONE);

        //////////////////////////////////////////////////////////////////
        //// Choix autres lieux fin de tournée ///////////////////////////
        //////////////////////////////////////////////////////////////////
        RadioGroup fin = findViewById(R.id.GroupeChoixNature);
        fin.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String choix = ((RadioButton)findViewById(checkedId)).getText().toString();

                if ("Autres".equals(choix)) { findViewById(R.id.editAutreChoix).setVisibility(View.VISIBLE); }
                else { findViewById(R.id.editAutreChoix).setVisibility(View.GONE); }
            }
        });

        //////////////////////////////////////////////////////////////////
        //// Choix chargement ////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        Switch chargement = findViewById(R.id.estVide);
        chargement.setChecked(false);
        chargement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LinearLayout chargement = findViewById(R.id.layoutFin);

                if(isChecked) {
                    //Si l'on commence avec un chargement
                    chargement.setVisibility(View.VISIBLE);
                } else {
                    //Si l'on commence la tournée à vide
                    chargement.setVisibility(View.GONE);
                }
            }
        });

        //////////////////////////////////////////////////////////////////
        //// Choix nature ////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        RadioGroup groupeNature = findViewById(R.id.choixNature);

        groupeNature.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String choix = ((RadioButton)findViewById(checkedId)).getText().toString();

                if(choix.equals("15-autre")) { findViewById(R.id.autreNature).setVisibility(View.VISIBLE); }
                else { findViewById(R.id.autreNature).setVisibility(View.GONE); }
            }
        });

        //////////////////////////////////////////////////////////////////
        //// Choix conditionnement ///////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        RadioGroup groupeCondi = findViewById(R.id.choixCondi);
        groupeCondi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String choix = ((RadioButton)findViewById(checkedId)).getText().toString();

                if(choix.equals("11-Autre, précisez")) { findViewById(R.id.editAutreCondi).setVisibility(View.VISIBLE); }
                else { findViewById(R.id.editAutreCondi).setVisibility(View.GONE); }
            }
        });

        //final EnqueteCSV enquete = getIntent().getExtras().getParcelable("Enquete");

        //////////////////////////////////////////////////////////////////
        //// Bouton "Fin de tournée" /////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        Button finTourne = findViewById(R.id.confirmerFIN);
        finTourne.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                boolean rempli = true;
                String codePostal= "";
                String date;
                String commune = "";
                Date now =new Date();
                String lieuArrivee = "";
                String conditionnement = "";
                String marchandise = "";
                date = now.toString();
                float volume = 0;
                float poids = 0;

                // Vérification des champs
                if(!((EditText) findViewById(R.id.codepostalfin)).getText().toString().equals("")) {
                    codePostal = ((EditText) findViewById(R.id.codepostalfin)).getText().toString();
                } else { rempli = false; }

                if(!((EditText)findViewById(R.id.Nomcommunefin)).getText().toString().equals("")) {
                    commune = ((EditText)findViewById(R.id.Nomcommunefin)).getText().toString();
                } else { rempli = false; }

                // Vérification nature du lieu d'arrivée
                RadioGroup rb = findViewById(R.id.GroupeChoixNature);
                RadioButton choixLieu = rb.findViewById(rb.getCheckedRadioButtonId());

                if(choixLieu != null) {
                    if(lieuArrivee.equals("Autres")) { lieuArrivee = ((EditText) findViewById(R.id.editAutreChoix)).getText().toString(); }
                    else { lieuArrivee = choixLieu.getText().toString(); }
                } else { rempli = false; }

                // Vérification est charge
                String estCharge;
                Switch charge = findViewById(R.id.estVide);

                if(charge.isChecked()) {
                    estCharge = "Oui";
                    // Vérification de la marchandise
                    RadioGroup groupeMarchandise = findViewById(R.id.choixNature);
                    RadioButton choixMarchandise = groupeMarchandise.findViewById(groupeMarchandise.getCheckedRadioButtonId());

                    if(choixMarchandise != null) {
                        marchandise = choixMarchandise.getText().toString();

                        if(marchandise.equals("15-autre")) {
                            marchandise = ((EditText) findViewById(R.id.autreNature)).getText().toString();
                        }
                    } else { rempli = false; }

                    // Vérification du conditionnement
                    RadioGroup groupeCondi = findViewById(R.id.choixCondi);
                    RadioButton choixCondi = groupeCondi.findViewById(groupeCondi.getCheckedRadioButtonId());

                    if(choixCondi != null) {
                        conditionnement = choixCondi.getText().toString();

                        if(lieuArrivee.equals("11-Autre, précisez")) { conditionnement =((EditText) findViewById(R.id.editAutreCondi)).getText().toString(); }
                    } else { rempli = false; }
                } else {
                    estCharge = "Non";
                    conditionnement ="rien";
                    marchandise ="rien";
                }

                if(rempli) {
                    try { MyApplication.ajoutFinTournee(new String[]{DateFormat.getDateTimeInstance().format(now), codePostal,commune ,lieuArrivee,estCharge,marchandise,conditionnement}); }
                    catch (IOException e) { e.printStackTrace(); }
                    DataBaseManager dbm = new DataBaseManager(getApplicationContext());
                    Enquete enquete = dbm.findEnquete(MyApplication.getEnqueteId());
                    enquete.setDateArrive(now);
                    enquete.setCodePostalArrive(codePostal);
                    enquete.setCommuneArrive(commune);
                    enquete.setNatureLieuArrive(lieuArrivee);
                    enquete.setEstChargeArrive(charge.isChecked());
                    enquete.setNatureMarchandiseArrive(marchandise);
                    enquete.setConditionnementArrive(conditionnement);
                    enquete.setVolumeArrive(volume);
                    enquete.setPoidsArrive(poids);
                    dbm.update(MyApplication.getEnqueteId(),enquete);
                    ApiManager apiManager = new ApiManager();
                    try {
                        apiManager.insertEnquete(enquete);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(),"Enquete terminé !", Toast.LENGTH_LONG).show();

                    Intent activite = new Intent(view.getContext(), MainActivity.class);
                    startActivity(activite);
                } else {
                    Toast.makeText(getApplicationContext(),"Remplissez les champs annotés d'un *", Toast.LENGTH_LONG).show();
                    if (choixLieu == null) {((TextView)findViewById(R.id.choixlieu)).setTextColor(Color.RED);}
                    if (((EditText)findViewById(R.id.Nomcommunefin)).getText().toString().equals("")) {((TextView)findViewById(R.id.nomcomunefintext)).setTextColor(Color.RED);}
                    if (((EditText) findViewById(R.id.codepostalfin)).getText().toString().equals("")) {((TextView)findViewById(R.id.codepostalfintext)).setTextColor(Color.RED);}
                }
            }
        });
    }
}
