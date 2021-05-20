package com.example.entpe.activity;
import android.location.Criteria;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.entpe.R;
import com.example.entpe.application.MyApplication;
import com.example.entpe.dialog.ChoixSettings;
import com.example.entpe.dialog.Updatable;
import com.example.entpe.model.Settings;
import com.example.entpe.storage.DataBaseManager;

public class Parametres extends AppCompatActivity implements Updatable {
    Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        settings = null;

        final Button choixSettings = findViewById(R.id.button);
        choixSettings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                (new ChoixSettings(Parametres.this)).show(getSupportFragmentManager(), "");
            }
        });

        final Button modif = findViewById(R.id.button2);
        modif.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DataBaseManager dbm = new DataBaseManager(getApplicationContext());
                String nom = ((EditText)findViewById(R.id.nomSettings)).getText().toString();
                int periode = 2;
                int precision = Criteria.ACCURACY_HIGH;
                int conso = Criteria.POWER_HIGH;

                if(((EditText)findViewById(R.id.periode)).getText().toString()!=""){
                    periode = Integer.parseInt(((EditText)findViewById(R.id.periode)).getText().toString());
                }

                RadioGroup rb = findViewById(R.id.groupePrecision);
                RadioButton choixPrecision = rb.findViewById(rb.getCheckedRadioButtonId());
                if(choixPrecision == null) {
                    precision = Criteria.ACCURACY_HIGH;
                } else {

                    String precisionTexte = choixPrecision.getText().toString();

                    switch(precisionTexte) {
                        case "Faible":
                            // code block
                            precision = Criteria.ACCURACY_LOW;
                            break;
                        case "Moyenne":
                            // code block
                            precision = Criteria.ACCURACY_MEDIUM;
                            break;
                        case "Haute":
                            // code block
                            precision = Criteria.ACCURACY_HIGH;
                            break;
                        }
                    }

                    RadioGroup rc = findViewById(R.id.groupeConso);
                    RadioButton choixConso = rc.findViewById(rc.getCheckedRadioButtonId());
                    if(choixConso == null) {
                        conso = Criteria.POWER_HIGH;
                    } else {

                        String consoTexte = choixConso.getText().toString();

                        switch(consoTexte) {
                            case "Faible":
                                // code block
                                conso = Criteria.POWER_LOW;
                                break;
                            case "Moyenne":
                                // code block
                                conso = Criteria.POWER_MEDIUM;
                                break;
                            case "Haute":
                                // code block
                                conso = Criteria.POWER_HIGH;
                                break;
                        }
                    }

                    if(settings == null){
                        dbm.insert(new Settings(-1,nom,conso,precision,periode));
                        Toast.makeText(getApplicationContext(),"Paramètres ajoutés à la liste", Toast.LENGTH_LONG).show();
                    }

                    if(settings!=null){
                        settings.setPeriode(periode);
                        settings.setPrecision(precision);
                        settings.setNomSettings(nom);
                        settings.setConsommation(conso);
                        dbm.update(settings.getId(),settings);
                        Toast.makeText(getApplicationContext(),"Paramètres modifiés", Toast.LENGTH_LONG).show();
                    }

                    update();
            }
        });

        final Button confirmeSettings = findViewById(R.id.editConfirme);
        confirmeSettings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(settings==null){
                    Toast.makeText(getApplicationContext(),"Aucun paramètres selectionnés", Toast.LENGTH_LONG).show();
                }else{
                    MyApplication.setSettings(settings);
                    Toast.makeText(getApplicationContext(),"Paramètres mis à jour pour les prochaines tournées", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    public void update() {
        if(settings == null){
            ((Button)findViewById(R.id.button2)).setText("Ajouter");
            ((EditText)findViewById(R.id.nomSettings)).setText("");
            ((EditText)findViewById(R.id.periode)).setText("");
            ((RadioButton)findViewById(R.id.setLow)).setChecked(false);
            ((RadioButton)findViewById(R.id.setMedium)).setChecked(false);
            ((RadioButton)findViewById(R.id.setHigh)).setChecked(false);
            ((RadioButton)findViewById(R.id.powLow)).setChecked(false);
            ((RadioButton)findViewById(R.id.powMedium)).setChecked(false);
            ((RadioButton)findViewById(R.id.powHigh)).setChecked(false);
        }else{
            ((Button)findViewById(R.id.button2)).setText("Modifier");
            ((EditText)findViewById(R.id.nomSettings)).setText(settings.getNomSettings());
            ((EditText)findViewById(R.id.periode)).setText(Integer.toString(settings.getPeriode()));
            switch (settings.getPrecision()){
                case 1:
                    ((RadioButton)findViewById(R.id.setLow)).setChecked(true);
                    break;
                case 2:
                    ((RadioButton)findViewById(R.id.setMedium)).setChecked(true);
                    break;
                case 3:
                    ((RadioButton)findViewById(R.id.setHigh)).setChecked(true);
                    break;
            }

            switch (settings.getConsommation()){
                case 1:
                    ((RadioButton)findViewById(R.id.powLow)).setChecked(true);
                    break;
                case 2:
                    ((RadioButton)findViewById(R.id.powMedium)).setChecked(true);
                    break;
                case 3:
                    ((RadioButton)findViewById(R.id.powHigh)).setChecked(true);
                    break;
            }
        }

    }


    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}
