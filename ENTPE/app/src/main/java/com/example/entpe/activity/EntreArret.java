package com.example.entpe.activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.entpe.R;
import com.example.entpe.application.MyApplication;
import com.example.entpe.dialog.ValidationFinTourneDialog;
import com.example.entpe.model.EnqueteCSV;
import com.example.entpe.model.TrackerGPS;
import com.example.entpe.service.GpsService;

import java.util.Objects;


public class EntreArret extends AppCompatActivity {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constants //////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public int ID_NOTIFICATION = 0;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    //private EnqueteCSV enquete;
    //private int enqueteId;
    //private Intent gpsService;







    private TrackerGPS gps;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_transition);
        MyApplication.setEtatEnquete(TrackerGPS.ETAT_IN_PROGRESS);



        //======================RECUPERATION DES ATTRIBUTS=====================================
        //enqueteId = getIntent().getExtras().getInt("EnqueteId");


        //Bouton de fin
        Button fin = findViewById(R.id.buttonFinTourne);
        fin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                (new ValidationFinTourneDialog(MyApplication.getEnqueteId())).show(getSupportFragmentManager(),"");

            }
        });

        // Bouton arret suivant
        Button arretSuiv = findViewById(R.id.buttonArrets);
        arretSuiv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent activite = new Intent(view.getContext(), Questionnaire.class);

                //activite.putExtra("EnqueteId", enqueteId);
                activite.putExtra("id", -1);

                startActivity(activite);
            }
        });

    }

    @Override
    public void onDestroy(){
        MyApplication.setEtatGps(MyApplication.ETAT_STOPPED);
        super.onDestroy();
    }
}
