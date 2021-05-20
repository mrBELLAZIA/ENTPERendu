package com.example.entpe.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.entpe.R;
import com.example.entpe.application.MyApplication;
import com.example.entpe.dialog.Api;
import com.example.entpe.dialog.EtablissementAPIDialog;
import com.example.entpe.dialog.EtablissementDialogFragment;
import com.example.entpe.dialog.Updatable;
import com.example.entpe.dialog.radioButton.ActionDialog;
import com.example.entpe.dialog.radioButton.ConditionnementsDialog;
import com.example.entpe.dialog.radioButton.ManutentionDialog;
import com.example.entpe.dialog.radioButton.NatureClientDialog;
import com.example.entpe.dialog.radioButton.OperationDialog;
import com.example.entpe.dialog.radioButton.StationementDialog;
import com.example.entpe.dialog.radioButton.TypeArretDialog;
import com.example.entpe.model.Arret;
import com.example.entpe.model.TrackerGPS;
import com.example.entpe.model.UploadApis;
import com.example.entpe.model.UploadToServ;
import com.example.entpe.storage.DataBaseManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

public class Questionnaire extends AppCompatActivity implements Updatable, Api {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constants /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 12;
    private static final int ID_NONE = -1;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    //private EnqueteCSV enquete;
    private DataBaseManager dbm;

    private Arret arret;
    private Date arrivee;
    private String lieuStationnement;
    private String adresseClient;
    private String natureLieu;

    private String action;
    private String lieuAction;
    private String moyenManutention;
    private String natureCondionnement;

    private int nombreUnite;
    private String natureMarchandise;
    private float poids;
    private float volume;

    private String operationEffectue;
    private String commentaire;

    private float longitude;
    private float latitude;


    //private int enqueteId;


    private Location derniereLoca;

    //private TrackerGPS gps;

    private int id;


    //pour la photo
    private ImageView imageAffiche;
    private String photoPath = "";
    private final String aRemplir = "à remplir";
    private String photoUrl ="";


    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Setters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public void setLieuStationnement(String lieuStationnement) { this.lieuStationnement = lieuStationnement; }

    public void setNatureLieu(String natureLieu) { this.natureLieu = natureLieu; }

    public void setAction(String action) { this.action = action; }

    public void setLieuAction(String lieuAction) { this.lieuAction = lieuAction; }

    public void setMoyenManutention(String moyenManutention) { this.moyenManutention = moyenManutention; }

    public void setNatureCondionnement(String natureCondionnement) { this.natureCondionnement = natureCondionnement; }

    public void setOperationEffectue(String operationEffectue) { this.operationEffectue = operationEffectue; }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Getters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public String getLieuStationnement() { return lieuStationnement; }

    public String getNatureLieu() { return natureLieu; }

    public String getAction() { return action; }

    public String getLieuAction() { return lieuAction; }

    public String getMoyenManutention() { return moyenManutention; }

    public String getNatureCondionnement() { return natureCondionnement; }

    public String getOperationEffectue() { return operationEffectue; }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionairesarrets);
        dbm = new DataBaseManager(getApplicationContext());
        MyApplication.setEtatEnquete(TrackerGPS.ETAT_WORKING);
        final LinearLayout photo = findViewById(R.id.photo_layout);
        photo.setVisibility(View.GONE);
        final LinearLayout questionaireMerch = findViewById(R.id.merchandise_layout);
        final Switch photoOuPas = findViewById(R.id.switchPhoto);
        photoOuPas.setChecked(false);
        photoOuPas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //Si l'on commence avec un chargement
                    questionaireMerch.setVisibility(View.GONE);
                    photo.setVisibility(View.VISIBLE);
                } else {
                    //Si l'on commence la tournée à vide
                    questionaireMerch.setVisibility(View.VISIBLE);
                    photo.setVisibility(View.GONE);
                }
            }
        });

        //===Dialogue de recherche
        ImageButton recherche = findViewById(R.id.rechercheBouton);
        recherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new EtablissementAPIDialog(Questionnaire.this)).show(getSupportFragmentManager(),"");
            }
        });

        arret = new Arret();
        id = getIntent().getExtras().getInt("id");
        if (id!=-1) {
            getArretToView();
            ((Button) findViewById(R.id.ArretSuivant)).setVisibility(View.GONE);
            ((Button) findViewById(R.id.clientSuivant)).setText("Modifier");
        }
        if(id==-1){
            if(MyApplication.getEnquete().getPositionsListe().size()==0){
                longitude = (float)0;
                latitude = (float)0;
            }else {
                longitude = MyApplication.getEnquete().getPositionsListe().get(MyApplication.getEnquete().getPositionsListe().size() - 1).getLongitude();
                latitude = MyApplication.getEnquete().getPositionsListe().get(MyApplication.getEnquete().getPositionsListe().size() - 1).getLatitude();
            }
        }else{
            Arret inter = dbm.findArret(id);
            longitude = inter.getLongitude();
            latitude = inter.getLatitude();
        }
        derniereLoca = null;

        imageAffiche = findViewById(R.id.imageMerch);
        imageAffiche.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent data = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File photoDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                Date date = new Date();
                photoUrl = ("photo_merch_"+date+".jpg").replaceAll("\\s+","");
                File photoFile = new File(photoDir+photoUrl);
                photoPath = photoFile.getAbsolutePath();
                //creation URI
                Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),getApplicationContext().getPackageName()+".provider",photoFile);
                data.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                data.addFlags(data.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(data,REQUEST_IMAGE_CAPTURE);
            }});

        if(id == -1){
            TextView numArret = findViewById(R.id.numArret);
            numArret.setText(Integer.toString(MyApplication.getEnquete().getNumArretCourant()));

            TextView numClient = findViewById(R.id.numClient);
            numClient.setText(Integer.toString(MyApplication.getEnquete().getNumClientCourant()));
        }else{
            Arret arret = dbm.findArret(id);
            TextView numArret = findViewById(R.id.numArret);
            numArret.setText(Integer.toString(arret.getNum_arret()));

            TextView numClient = findViewById(R.id.numClient);
            numClient.setText(Integer.toString(arret.getNumClient()));
        }


        //////////////////////////////////////////////////////////////////
        //// Initialisation de l'arrêt ///////////////////////////////////
        //////////////////////////////////////////////////////////////////
        if (id == -1) {
            initVal();
        }
        if(MyApplication.getEnquete().getNumClientCourant() > 1) {
            Arret der = MyApplication.getEnquete().dernierArret();
            lieuStationnement = der.getLieuStationnement();
            arrivee = der.getHeureArrivee();
            getArretToViewBasique();
        } else if (id==-1){ arrivee = new Date();
        }

        //////////////////////////////////////////////////////////////////
        //// Gestion des boutons /////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        // Bouton client suivant
        Button clientSuiv = findViewById(R.id.clientSuivant);

        clientSuiv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                getArretFromView();
                if(arret != null) {
                    Intent activite = new Intent(view.getContext(), Questionnaire.class);
                    if(id!=-1){
                        dbm.update(id,arret);
                    }else{
                        MyApplication.incrementeClient();
                        MyApplication.ajouterArret(arret);
                        dbm.insert(arret);
                    }
                    try {
                        MyApplication.ajoutArret(new String[] {
                                String.valueOf(arret.getNum_arret()),
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
                        });
                    } catch(IOException e) { e.printStackTrace(); }

                    if (id!=-1){
                        activite = new Intent(view.getContext(),ListArretActivity.class);
                    }
                    activite.putExtra("id", -1);
                    //activite.putExtra("EnqueteId", enqueteId);

                    startActivity(activite);
                } else {
                    resetView();
                    afficheErreur();
                    Toast.makeText(getApplicationContext(),"Completez les champs annotés d'un *", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Bouton arret suivant
        Button arretSuiv = findViewById(R.id.ArretSuivant);

        arretSuiv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                getArretFromView();

                if(arret != null) {
                    Intent activite = new Intent(view.getContext(), EntreArret.class);
                    MyApplication.ajouterArret(arret);
                    MyApplication.incrementeArret();
                    dbm.insert(arret);
                    try {
                        MyApplication.ajoutArret(new String[] {
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

                        });
                    } catch(IOException e) { e.printStackTrace(); }
                    //activite.putExtra("EnqueteId", enqueteId);
                    activite.putExtra("id", -1);
                    startActivity(activite);
                } else {
                    resetView();
                    afficheErreur();
                    Toast.makeText(getApplicationContext(),"Completez les champs annotés d'un *", Toast.LENGTH_LONG).show();
                }
            }
        });

        //////////////////////////////////////////////////////////////////
        //// Gestion du questionnaire ////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        // Bouton stationnement
        Button stationnementButton = findViewById(R.id.buttonStationement);
        stationnementButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) { (new StationementDialog(Questionnaire.this)).show(getSupportFragmentManager(),""); }
        });

        // Bouton nature client
        Button natureClientButton = findViewById(R.id.buttonNatureClient);
        natureClientButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) { (new NatureClientDialog(Questionnaire.this)).show(getSupportFragmentManager(),""); }
        });

        // Bouton action
        Button actionButton = findViewById(R.id.actionfaite);
        actionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) { (new ActionDialog(Questionnaire.this)).show(getSupportFragmentManager(),""); }
        });

        // Bouton nature lieu dépot
        Button natureLieutButton = findViewById(R.id.buttonLieuDepose);
        natureLieutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) { (new TypeArretDialog(Questionnaire.this)).show(getSupportFragmentManager(),""); }
        });

        // Bouton nature manutention
        Button manutentionButton = findViewById(R.id.buttonManutention);
        manutentionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) { (new ManutentionDialog(Questionnaire.this)).show(getSupportFragmentManager(),""); }
        });

        // Bouton nature conditionnement
        Button conditionnementButton = findViewById(R.id.buttonConditionement);
        conditionnementButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) { (new ConditionnementsDialog(Questionnaire.this)).show(getSupportFragmentManager(),""); }
        });

        // Bouton operation
        Button operationButton = findViewById(R.id.buttonOperation);
        operationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) { (new OperationDialog(Questionnaire.this)).show(getSupportFragmentManager(),""); }
        });
    }

    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    @Override
    public void update() {
        if(!lieuStationnement.equals(aRemplir)) {((TextView)findViewById(R.id.stationnement)).setText(limiteText(getText(R.string.stationnement) + lieuStationnement)); }
        if(!natureLieu.equals(aRemplir)) { ((TextView)findViewById(R.id.natureclient)).setText(limiteText(getText(R.string.nature) + natureLieu)); }
        if(!action.equals(aRemplir)) { ((TextView)findViewById(R.id.typearrets)).setText(limiteText(getText(R.string.actionspossible) + action)); }
        if(!lieuAction.equals(aRemplir)) { ((TextView)findViewById(R.id.lieudepose)).setText(limiteText(getText(R.string.lieuarrets) + lieuAction)); }
        if(!moyenManutention.equals(aRemplir)) { ((TextView)findViewById(R.id.manutention)).setText(limiteText(getText(R.string.manutention) + moyenManutention)); }
        if(!natureCondionnement.equals(aRemplir)) { ((TextView)findViewById(R.id.conditionement)).setText(limiteText(getText(R.string.conditionementarretes) + natureCondionnement)); }
        if(!operationEffectue.equals(aRemplir)) { ((TextView)findViewById(R.id.operation)).setText(limiteText(getText(R.string.operation) + operationEffectue)); }
    }

    @SuppressLint("SetTextI18n")
    public void getArretToViewBasique() { ((TextView)findViewById(R.id.stationnement)).setText(getText(R.string.stationnement) + " " + MyApplication.getEnquete().dernierArret().getLieuStationnement()); }

    public void getArretToView() {
        DataBaseManager dbm = new DataBaseManager(getApplicationContext());
        Arret arretbis = dbm.findArret(id);
        final Switch photoOuPas = findViewById(R.id.switchPhoto);
        photoOuPas.setVisibility(View.GONE);
        final LinearLayout photo = findViewById(R.id.photo_layout);
        final LinearLayout questionaireMerch = findViewById(R.id.merchandise_layout);


        arrivee = arretbis.getHeureArrivee();
        lieuStationnement = arretbis.getLieuStationnement();
        adresseClient = arretbis.getAdresseClient();
        natureLieu = arretbis.getNatureLieu();
        action = arretbis.getAction();
        lieuAction = arretbis.getLieuAction();
        moyenManutention = arretbis.getMoyenManutention();
        natureCondionnement = arretbis.getNatureConditionnement();
        nombreUnite = arretbis.getNombreUnite();
        natureMarchandise = arretbis.getNatureMarchandise();
        poids = arretbis.getPoids();
        volume = arretbis.getVolume();
        operationEffectue = arretbis.getOperationEffectuee();
        commentaire = arretbis.getCommentaire();
        photoPath = arretbis.getPhoto();

        if(photoPath.equals("")) {
            questionaireMerch.setVisibility(View.VISIBLE);
            photo.setVisibility(View.GONE);
        }
        else{
            questionaireMerch.setVisibility(View.GONE);
            photo.setVisibility(View.VISIBLE);
            imageAffiche = findViewById(R.id.imageMerch);
            Bitmap image = BitmapFactory.decodeFile(photoPath);
            imageAffiche.setImageBitmap(image);
        }
        update();

        ((EditText)findViewById(R.id.editAdresseClient)).setText(adresseClient);
        ((EditText)findViewById(R.id.nbunitesedit)).setText(nombreUnite+"");
        ((EditText)findViewById(R.id.naturemerchedit)).setText(natureMarchandise);
        ((EditText)findViewById(R.id.poidsmerchedit)).setText(poids+"");
        ((EditText)findViewById(R.id.volumemerchedit)).setText(volume+"");
        ((EditText)findViewById(R.id.editCom)).setText(commentaire);

        TextView numArret = findViewById(R.id.numArret);
        numArret.setText(Integer.toString(arret.getId()));

        TextView numClient = findViewById(R.id.numClient);
        numClient.setText(Integer.toString(arret.getNumClient()));




    }
    public void updateFromView() {
        if(isNotNull(findViewById(R.id.editAdresseClient))) { adresseClient = getContent(findViewById(R.id.editAdresseClient)); }
        if(isNotNull(findViewById(R.id.nbunitesedit))) { nombreUnite = Integer.parseInt(getContent(findViewById(R.id.nbunitesedit))); }
        if(isNotNull(findViewById(R.id.naturemerchedit))) { natureMarchandise = getContent(findViewById(R.id.naturemerchedit)); }
        if(isNotNull(findViewById(R.id.poidsmerchedit))) { poids = Float.parseFloat(getContent(findViewById(R.id.poidsmerchedit))); }
        if(isNotNull(findViewById(R.id.volumemerchedit))) { volume = Float.parseFloat(getContent(findViewById(R.id.volumemerchedit))); }
        if(isNotNull(findViewById(R.id.editCom))) { commentaire = getContent(findViewById(R.id.editCom)); }
    }


    public void getArretFromView() {
        arret = new Arret();
        updateFromView();
        if(id==-1) {
            if (estComplet()) {
                arret.setArret(-1, MyApplication.getEnquete().getNumArretCourant(), arrivee, lieuStationnement,
                        MyApplication.getEnquete().getNumClientCourant(), adresseClient, natureLieu,
                        action, lieuAction, moyenManutention, natureCondionnement,
                        nombreUnite, natureMarchandise, poids, volume,
                        operationEffectue, new Date(), commentaire, photoPath, MyApplication.getEnqueteId(),longitude,latitude);
            } else {
                arret = null;
            }
        }else{
            Arret arretInter = dbm.findArret(id);
            if (estComplet()) {
                arret.setArret(-1, arretInter.getNum_arret(), arretInter.getHeureArrivee(), lieuStationnement,
                        arretInter.getNumClient(), adresseClient, natureLieu,
                        action, lieuAction, moyenManutention, natureCondionnement,
                        nombreUnite, natureMarchandise, poids, volume,
                        operationEffectue, arretInter.getHeureDepart(), commentaire, photoPath, MyApplication.getEnqueteId(),longitude,latitude);
            } else {
                arret = null;
            }
        }
    }

    public void resetView() {
        ((TextView)findViewById(R.id.stationnement)).setTextColor(Color.parseColor("#5b39c6"));
        ((TextView)findViewById(R.id.adresseClient)).setTextColor(Color.parseColor("#5b39c6"));
        ((TextView)findViewById(R.id.natureclient)).setTextColor(Color.parseColor("#5b39c6"));
        ((TextView)findViewById(R.id.typearrets)).setTextColor(Color.parseColor("#5b39c6"));
        ((TextView)findViewById(R.id.lieudepose)).setTextColor(Color.parseColor("#5b39c6"));
        ((TextView)findViewById(R.id.manutention)).setTextColor(Color.parseColor("#5b39c6"));
        ((TextView)findViewById(R.id.conditionement)).setTextColor(Color.parseColor("#5b39c6"));
        ((TextView)findViewById(R.id.nbunites)).setTextColor(Color.parseColor("#5b39c6"));
        ((TextView)findViewById(R.id.naturemerch)).setTextColor(Color.parseColor("#5b39c6"));
        ((TextView)findViewById(R.id.poidsmerch)).setTextColor(Color.parseColor("#5b39c6"));
        ((TextView)findViewById(R.id.volumemerch)).setTextColor(Color.parseColor("#5b39c6"));
        ((TextView)findViewById(R.id.operation)).setTextColor(Color.parseColor("#5b39c6"));
    }

    public void afficheErreur() {
        if(lieuStationnement.equals(aRemplir)) { ((TextView)findViewById(R.id.stationnement)).setTextColor(Color.RED); }
        if(adresseClient.equals(aRemplir)) { ((TextView)findViewById(R.id.adresseClient)).setTextColor(Color.RED); }
        if(natureLieu.equals(aRemplir)) { ((TextView)findViewById(R.id.natureclient)).setTextColor(Color.RED); }
        if(action.equals(aRemplir)) { ((TextView)findViewById(R.id.typearrets)).setTextColor(Color.RED); }
        if(lieuAction.equals(aRemplir)) { ((TextView)findViewById(R.id.lieudepose)).setTextColor(Color.RED); }
        if(moyenManutention.equals(aRemplir)) { ((TextView)findViewById(R.id.manutention)).setTextColor(Color.RED); }
        if(natureCondionnement.equals(aRemplir)) { ((TextView)findViewById(R.id.conditionement)).setTextColor(Color.RED); }
        if(nombreUnite == -1) { ((TextView)findViewById(R.id.nbunites)).setTextColor(Color.RED); }
        if(natureMarchandise.equals(aRemplir)) { ((TextView)findViewById(R.id.naturemerch)).setTextColor(Color.RED); }
        if(poids == -1) { ((TextView)findViewById(R.id.poidsmerch)).setTextColor(Color.RED); }
        if(volume == -1) { ((TextView)findViewById(R.id.volumemerch)).setTextColor(Color.RED); }
        if(operationEffectue.equals(aRemplir)) { ((TextView)findViewById(R.id.operation)).setTextColor(Color.RED); }
    }

    public Boolean estComplet() { return !lieuStationnement.equals(aRemplir) && !adresseClient.equals(aRemplir) && !natureLieu.equals(aRemplir) && !action.equals(aRemplir) && !lieuAction.equals(aRemplir)
            && !moyenManutention.equals(aRemplir) && ((!natureMarchandise.equals(aRemplir) && nombreUnite != -1 && poids != -1 && volume != -1)||photoPath != null); }

    public void initVal() {
        lieuStationnement = aRemplir;
        adresseClient = aRemplir;
        natureLieu = aRemplir;
        action = aRemplir;
        lieuAction = aRemplir;
        moyenManutention = aRemplir;
        natureCondionnement = aRemplir;
        nombreUnite = -1;
        natureMarchandise = aRemplir;
        poids = -1;
        volume = -1;
        operationEffectue = aRemplir;
        commentaire = aRemplir;
        photoPath = "";
    }

    private String getContent(View view) { return ((EditText) view).getText().toString(); }

    private boolean isNotNull(View view) { return !getContent(view).equals(""); }


    @Override
    public void onDestroy() {
        //stopService(gpsService);
        MyApplication.setEtatGps(MyApplication.ETAT_STOPPED);
        super.onDestroy();

    }
    @Override
    public void onActivityResult(int requestcode, int resultcode, @Nullable Intent intent){
        super.onActivityResult(requestcode,requestcode,intent);
        if( requestcode == REQUEST_IMAGE_CAPTURE && resultcode==-1){
            Bitmap image = BitmapFactory.decodeFile(photoPath);
            imageAffiche.setImageBitmap(image);
        }
    }

    private String limiteText(String text){
        String res = text;
         if (text.length()>38){
             res = text.substring(0,38)+"...";
         }
        return res;
    }

    @Override
    public void choix() {
        longitude = MyApplication.getEtablissementAPI().getLongitude();
        latitude = MyApplication.getEtablissementAPI().getLatitude();
        natureLieu = MyApplication.getEtablissementAPI().getNature();
        ((EditText)findViewById(R.id.editAdresseClient)).setText(MyApplication.getEtablissementAPI().getAdresse());
        Toast.makeText(this,"résultat trouvé", Toast.LENGTH_LONG).show();
        update();
    }
}
