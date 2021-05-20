package com.example.entpe.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.entpe.R;
import com.example.entpe.dialog.EtablissementAPIDialog;
import com.example.entpe.dialog.EtablissementDialogFragment;
import com.example.entpe.model.ApiManager;
import com.example.entpe.storage.DataBaseManager;

public class MainActivity extends AppCompatActivity {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ApiManager apiManager = new ApiManager();
        DataBaseManager dbm = new DataBaseManager(getApplicationContext());
        dbm.deleteAllEtablissement();
        dbm.deleteAllVehicule();
        apiManager.recupEtablissements();
        apiManager.recupVehicule();
        // Demande d'accès au stockage
        ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION, Manifest.permission.FOREGROUND_SERVICE,Manifest.permission.CAMERA}, 5);
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        int permissionCheckWrite = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissionCheckWrite != PackageManager.PERMISSION_GRANTED) {
            // ask permissions here using below code
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
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

        // ============Renvoi vers la liste d'entreprise
        Button gestionEtablissements = findViewById(R.id.GestionFlotteButton);
        gestionEtablissements.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), ListEntrepriseActivity.class);
                startActivityForResult(myIntent, 0);
                //(new EtablissementAPIDialog()).show(getSupportFragmentManager(),"");
            }
        });

        // ============Renvoi vers l'initialisation======================================
        Button Init = findViewById(R.id.InitialisationButton);
        Init.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Initialisation.class);
                startActivityForResult(myIntent, 0);
            }
        });

        ImageButton set = findViewById(R.id.parametres);
        set.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Parametres.class);
                startActivityForResult(myIntent, 0);
            }
        });
    }
}




