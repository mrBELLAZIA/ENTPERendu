package com.example.entpe.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.example.entpe.R;
import com.example.entpe.model.ApiManager;
import com.example.entpe.model.UploadApis;
import com.example.entpe.model.UploadToServ;
import com.example.entpe.model.Vehicule;
import com.example.entpe.storage.DataBaseManager;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class VehiculeDialogFragment extends DialogFragment {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constants //////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private static final int ID_NONE = -1;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private Updatable updatable;
    private int id;
    private int idEtablissement;
    private View view;
    private ImageView imageAffiche;
    private String photoPath = null;
    private String nomPhoto ="";
    private Boolean estModifié = false;

    public VehiculeDialogFragment(Updatable updatable, int id, int idEta) {
        setUpdatable(updatable);
        setId(id);
        setIdEtablissement(idEta);
    }

    public VehiculeDialogFragment(Updatable updatable, int idEta) { this(updatable, ID_NONE, idEta); }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Setters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private void setUpdatable(Updatable updatable) { this.updatable = updatable; }

    private void setId(int id) { this.id = id; }

    private void setIdEtablissement(int idEtablissement) { this.idEtablissement = idEtablissement; }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstance) {
        view = requireActivity().getLayoutInflater().inflate(R.layout.ajout_modif_vehicule, null);
        setVehiculeToView();

        // Changement du titre selon ajout ou modification
        String type;
        if (id == -1) { type = "Ajout d'un véhicule"; }
        else { type = "Modification d'un véhicule";
        }

        ((TextView) view.findViewById(R.id.titre_gestion_vehicule)).setText(type);

        // Boutons d'ajout et d'annulation
        Button confirme = view.findViewById(R.id.editConfirme);

        confirme.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Vehicule vehicule = getVehiculeFromView();
                DataBaseManager manager = new DataBaseManager(getContext());

                if(vehicule != null) {
                    ApiManager apiManager = new ApiManager();
                    UploadToServ.initRetrofitClient();
                    String message = "Véhicule ";

                    if(VehiculeDialogFragment.this.id == ID_NONE) {
                        apiManager.insertVehicule(vehicule);
                        message += "ajouté";
                        if(nomPhoto!="") UploadToServ.multipartImageUpload(getContext(),photoPath);
                    } else {
                        apiManager.updateVehicule(vehicule);
                        message += "modifié";
                        if(nomPhoto!="") UploadToServ.multipartImageUpload(getContext(),photoPath);
                    }

                    Toast.makeText(getActivity(), message + " !", Toast.LENGTH_LONG).show();
                    dismiss();
                } else { Toast.makeText(getActivity(), "Complétez tous les champs annotés d'un *", Toast.LENGTH_LONG).show(); }

                updatable.update();
            }
        });

        imageAffiche = view.findViewById(R.id.imageNone);
        imageAffiche.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent data = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Date date = new Date();
                if(data.resolveActivity(getActivity().getPackageManager()) != null){
                    File photoDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                    nomPhoto = ("photo_vehicule_"+date+".jpg").replaceAll("\\s+","");
                    File photoFile = new File(photoDir+nomPhoto);
                    photoPath = photoFile.getAbsolutePath();
                    //creation URI
                    Uri photoURI = FileProvider.getUriForFile(getActivity(),getActivity().getApplicationContext().getPackageName()+".provider",photoFile);
                    data.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                    data.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivityForResult(data,REQUEST_IMAGE_CAPTURE);
                }
            }});

        //////////////////////////////////////////////////////////////////
        // Gestion type en fonction du gabarit ///////////////////////////
        //////////////////////////////////////////////////////////////////
        RadioGroup gabaritChoix = view.findViewById(R.id.groupeType);

        gabaritChoix.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int selectedId) {
                view.findViewById(R.id.layoutType).setVisibility(View.VISIBLE);
                view.findViewById(R.id.editSavoyarde).setVisibility(View.VISIBLE);
                view.findViewById(R.id.editTautliner).setVisibility(View.VISIBLE);
                view.findViewById(R.id.editPlateau).setVisibility(View.VISIBLE);
                view.findViewById(R.id.editCiterne).setVisibility(View.VISIBLE);
                view.findViewById(R.id.editBenne).setVisibility(View.VISIBLE);

                RadioButton gabarit = view.findViewById(selectedId);
                String gabaritChoisi = gabarit.getText().toString();

                switch (gabaritChoisi) {
                    case "9-Velo ou triporteur électrique":
                    case "3-Voiture":
                    case "8-2 roues motorisé":
                        view.findViewById(R.id.layoutType).setVisibility(View.GONE);
                        break;
                    case "4-Fourgonnette":
                    case "5-Camionnette":
                        view.findViewById(R.id.editSavoyarde).setVisibility(View.GONE);
                        view.findViewById(R.id.editTautliner).setVisibility(View.GONE);
                        view.findViewById(R.id.editPlateau).setVisibility(View.GONE);
                        view.findViewById(R.id.editCiterne).setVisibility(View.GONE);
                        view.findViewById(R.id.editBenne).setVisibility(View.GONE);
                        break;
                }
            }
        });

        RadioButton gabarit = view.findViewById(gabaritChoix.getCheckedRadioButtonId());

        Button annule = view.findViewById(R.id.editAnnule);

        annule.setOnClickListener(new View.OnClickListener() { public void onClick(View view) { dismiss(); } });

        return new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                .setView(view)
                .create();
    }


    // Récupère l'établissement à partir de la vue pour ajout
    private Vehicule getVehiculeFromView() {
        //////////////////////////////////////////////////////////////////
        // Remise à zéro des champs //////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        ((TextView)view.findViewById(R.id.textView2)).setTextColor(Color.parseColor("#5b39c6"));
        ((TextView)view.findViewById(R.id.textViewCat)).setTextColor(Color.parseColor("#5b39c6"));
        ((TextView)view.findViewById(R.id.textView)).setTextColor(Color.parseColor("#5b39c6"));
        ((TextView)view.findViewById(R.id.textView3)).setTextColor(Color.parseColor("#5b39c6"));
        ((TextView)view.findViewById(R.id.textView10)).setTextColor(Color.parseColor("#5b39c6"));
        ((TextView)view.findViewById(R.id.textView12)).setTextColor(Color.parseColor("#5b39c6"));

        Boolean estComplet = true;
        String categorie = null;
        String motorisation;
        String type;

        int typeVal;
        int categorieVal;
        int motorisationVal;
        int critAir = 0;



        // Récupération du type
        RadioGroup rbType = view.findViewById(R.id.groupeType);
        RadioButton choixType = rbType.findViewById(rbType.getCheckedRadioButtonId());

        if(choixType == null) { return null; }

        type = choixType.getText().toString();

        // Récupération de la catégorie

        RadioGroup rbCat = view.findViewById(R.id.groupeCategorie);
        RadioButton choixCat = rbCat.findViewById(rbCat.getCheckedRadioButtonId());

        if((choixCat == null)&&!((type.equals("3-Voiture"))||(type.equals("8-2 roues motorisé"))||(type.equals("9-Velo ou triporteur électrique")))) { return null; }
        else if(view.findViewById(R.id.layoutType).getVisibility() == View.GONE) { categorie = "non motorisé"; }
        else { categorie = choixCat.getText().toString(); }

        // Récupération de la motorisation
        RadioGroup rbMot = view.findViewById(R.id.groupeMotorisation);
        RadioButton choixMot = rbMot.findViewById(rbMot.getCheckedRadioButtonId());

        if(choixMot == null) { return null; }

        motorisation = choixMot.getText().toString();

        // Récupération de crit Air

        RadioGroup rbCritAir = view.findViewById(R.id.editLvLCritAir);
        RadioButton choixCritAir = rbCritAir.findViewById(rbCritAir.getCheckedRadioButtonId());

        if(choixCritAir != null) {
            // Choix critAir
            if(match(getContent(choixCritAir), "Electrique")) { critAir = 0; }
            else { critAir = toInt(choixCritAir.getText().toString()); }
        }

        // Choix de la Catégorie
        switch(categorie) {
            case "2-Savoyarde":
                // code block
                categorieVal = 2;
                break;
            case "3-Tautliner":
                // code block
                categorieVal = 3;
                break;
            case "4-Fourgon":
                // code block
                categorieVal = 4;
                break;
            case "5-Plateau":
                // code block
                categorieVal = 5;
                break;
            case "6-Citerne":
                // code block
                categorieVal = 6;
                break;
            case "7-Frigo":
                // code block
                categorieVal = 7;
                break;
            case "8-Benne":
                // code block
                categorieVal = 8;
                break;
            default:
                // code block
                categorieVal = 2;
        }

        // Choix type
        switch(type) {
            case "3-Voiture":
                // code block
                typeVal = 3;
                break;
            case "4-Fourgonnette":
                // code block
                typeVal = 4;
                break;
            case "5-Camionnette":
                // code block
                typeVal = 5;
                break;
            case "6-Camion porteur":
                // code block
                typeVal = 6;
                break;
            case "7-Ensemble routier articulé":
                // code block
                typeVal = 7;
                break;
            case "8-2 roues motorisé":
                // code block
                typeVal = 8;
                break;
            case "9-Velo ou triporteur électrique":
                typeVal = 9;
                break;
            default:
                // code block
                typeVal = 3;
        }

        // Choix motorisation
        switch(motorisation) {
            case "1-Diesel":
                // code block
                motorisationVal = 1;
                break;
            case "2-Essence":
                // code block
                motorisationVal = 2;
                break;
            case "3-Electrique":
                // code block
                motorisationVal = 3;
                break;
            case "4-Gnv, Gpl":
                // code block
                motorisationVal = 4;
                break;
            case "5-Hybride":
                // code block
                motorisationVal = 5;
                break;
            default:
                // code block
                motorisationVal = 1;
        }

        // Récupération du reste des valeurs
        String plaque = ((EditText)view.findViewById(R.id.editPlaque)).getText().toString();
        String marque = ((EditText)view.findViewById(R.id.editMarque)).getText().toString();
        String modele = ((EditText)view.findViewById(R.id.editModele)).getText().toString();

        long ptac = 0L;

        // Vérification des champs
        if(match(plaque, "")) { return null; }

        if(match(marque, "")){ marque = "incomplet"; }

        if(match(modele, "")){ modele = "incomplet"; }

        if(isNotEmpty(view.findViewById(R.id.editPTAC))) { ptac= toLong(((EditText)view.findViewById(R.id.editPTAC)).getText().toString()); }
        else { return null; }

        long ptra = 0L;

        if(isNotEmpty(view.findViewById(R.id.editPTRA))) { ptra = toLong(((EditText)view.findViewById(R.id.editPTRA)).getText().toString()); }
        else { ptra = 99L; }

        long poidsVide = 99L;

        if(isNotEmpty(view.findViewById(R.id.editPoidsVide))) { poidsVide = toLong(((EditText)view.findViewById(R.id.editPoidsVide)).getText().toString()); }
        else { return null; }

        long chargeUtile = 0L;

        if(isNotEmpty(view.findViewById(R.id.editChargeUtile))) { chargeUtile = toLong(((EditText)view.findViewById(R.id.editChargeUtile)).getText().toString()); }
        else { chargeUtile = 99L; }

        int age = 0;

        if(isNotEmpty(view.findViewById(R.id.editAge))) { age = toInt(((EditText)view.findViewById(R.id.editAge)).getText().toString()); }
        else { age = 99; }

        long surfaceUtile = 99;

        if(isNotEmpty(view.findViewById(R.id.editSurfaceSolUtile))) { surfaceUtile = toLong(((EditText)view.findViewById(R.id.editSurfaceSolUtile)).getText().toString()); }

        long largeur = 99;

        if(isNotEmpty(view.findViewById(R.id.editLargeur))) { largeur = toLong(((EditText)view.findViewById(R.id.editLargeur)).getText().toString()); }

        long longueur = 99;

        if(isNotEmpty(view.findViewById(R.id.editLargeur))) { longueur = toLong(((EditText)view.findViewById(R.id.editLongueur)).getText().toString()); }

        long hauteur = 99;

        if(isNotEmpty(view.findViewById(R.id.editHauteur))) { hauteur =  toLong(((EditText)view.findViewById(R.id.editHauteur)).getText().toString()); }

        return new Vehicule(id, plaque, marque, modele, typeVal, categorieVal, motorisationVal,
                ptac, ptra, poidsVide, chargeUtile, age, surfaceUtile, largeur, longueur, hauteur,
                idEtablissement, critAir, ("Pictures"+nomPhoto));
    }

    private void setVehiculeToView() {

        DataBaseManager manager =  new DataBaseManager(getContext());
        Vehicule vehicule = manager.findVehicule(id);
        if(vehicule != null) {
            ((EditText)view.findViewById(R.id.editPlaque)).setText(vehicule.getPlaque());
            ((EditText)view.findViewById(R.id.editMarque)).setText(vehicule.getMarque());
            ((EditText)view.findViewById(R.id.editModele)).setText(vehicule.getModele());


            //Affichage de la Type
            int categorie = vehicule.getCategorie();

            switch(categorie) {
                case 2:
                    // code block
                    ((RadioButton) view.findViewById(R.id.editSavoyarde)).setChecked(true);
                    break;
                case 3:
                    // code block
                    ((RadioButton) view.findViewById(R.id.editTautliner)).setChecked(true);
                    break;
                case 4:
                    // code block
                    ((RadioButton) view.findViewById(R.id.editFourgon)).setChecked(true);
                    break;
                case 5:
                    // code block
                    ((RadioButton) view.findViewById(R.id.editPlateau)).setChecked(true);
                    break;
                case 6:
                    // code block
                    ((RadioButton) view.findViewById(R.id.editCiterne)).setChecked(true);
                    break;
                case 7:
                    // code block
                    ((RadioButton) view.findViewById(R.id.editFrigo)).setChecked(true);
                    break;
                case 8:
                    // code block
                    ((RadioButton) view.findViewById(R.id.editBenne)).setChecked(true);
                    break;
                default:
                    // code block
                    ((RadioButton) view.findViewById(R.id.editSavoyarde)).setChecked(true);
            }

            //Affichage Gabarit
            int type = vehicule.getType();

            switch(type) {
                case 3:
                    // code block
                    ((RadioButton) view.findViewById(R.id.editVoiture)).setChecked(true);
                    break;
                case 4:
                    // code block
                    ((RadioButton) view.findViewById(R.id.editFourgonnette)).setChecked(true);
                    break;
                case 5:
                    // code block
                    ((RadioButton) view.findViewById(R.id.editCamionnette)).setChecked(true);
                    break;
                case 6:
                    // code block
                    ((RadioButton) view.findViewById(R.id.editCamionPorteur)).setChecked(true);
                    break;
                case 7:
                    // code block
                    ((RadioButton) view.findViewById(R.id.editEnsembleRoutier)).setChecked(true);
                    break;
                case 8:
                    // code block
                    ((RadioButton) view.findViewById(R.id.edit2RoueMotorisé)).setChecked(true);
                    break;
                case 9:
                    // code block
                    ((RadioButton) view.findViewById(R.id.editVelooutri)).setChecked(true);
                    break;
                default:
                    // code block
                    ((RadioButton) view.findViewById(R.id.editVoiture)).setChecked(true);
            }

            // Affichage motorisation
            int motorisation = vehicule.getMotorisation();

            switch(motorisation) {
                case 1:
                    // code block
                    ((RadioButton) view.findViewById(R.id.editDiesel)).setChecked(true);
                    break;
                case 2:
                    // code block
                    ((RadioButton) view.findViewById(R.id.editEssence)).setChecked(true);
                    break;
                case 3:
                    // code block
                    ((RadioButton) view.findViewById(R.id.editElectrique)).setChecked(true);
                    break;
                case 4:
                    // code block
                    ((RadioButton) view.findViewById(R.id.editGnvGpl)).setChecked(true);
                    break;
                case 5:
                    // code block
                    ((RadioButton) view.findViewById(R.id.editHybride)).setChecked(true);
                    break;
                default:
                    // code block
                    ((RadioButton) view.findViewById(R.id.editDiesel)).setChecked(true);
            }

            // Affichage critAir
            int critAir = vehicule.getCritAir();

            switch(critAir) {
                case 0:
                    // code block
                    ((RadioButton) view.findViewById(R.id.editLVL0)).setChecked(true);
                    break;
                case 1:
                    // code block
                    ((RadioButton) view.findViewById(R.id.editLVL1)).setChecked(true);
                    break;
                case 2:
                    // code block
                    ((RadioButton) view.findViewById(R.id.editLVL2)).setChecked(true);
                    break;
                case 3:
                    // code block
                    ((RadioButton) view.findViewById(R.id.editLVL3)).setChecked(true);
                    break;
                case 4:
                    // code block
                    ((RadioButton) view.findViewById(R.id.editLVL4)).setChecked(true);
                    break;
                case 5:
                    // code block
                    ((RadioButton) view.findViewById(R.id.editLVL5)).setChecked(true);
                    break;
                default:
                    // code block
                    ((RadioButton) view.findViewById(R.id.editLVL0)).setChecked(true);
            }

            ((EditText)view.findViewById(R.id.editPTAC)).setText(parse(vehicule.getPtac()));
            ((EditText)view.findViewById(R.id.editPTRA)).setText(parse(vehicule.getPtra()));
            ((EditText)view.findViewById(R.id.editPoidsVide)).setText(parse(vehicule.getPoidsVide()));
            ((EditText)view.findViewById(R.id.editChargeUtile)).setText(parse(vehicule.getChargeUtile()));
            ((EditText)view.findViewById(R.id.editChargeUtile)).setText(parse(vehicule.getChargeUtile()));
            ((EditText)view.findViewById(R.id.editChargeUtile)).setText(parse(vehicule.getChargeUtile()));
            ((EditText)view.findViewById(R.id.editAge)).setText(parse(vehicule.getAge()));
            ((EditText)view.findViewById(R.id.editSurfaceSolUtile)).setText(parse(vehicule.getSurfaceSol()));
            ((EditText)view.findViewById(R.id.editChargeUtile)).setText(parse(vehicule.getChargeUtile()));
            ((EditText)view.findViewById(R.id.editLargeur)).setText(parse(vehicule.getLargeur()));
            ((EditText)view.findViewById(R.id.editLongueur)).setText(parse(vehicule.getLongueur()));
            ((EditText)view.findViewById(R.id.editHauteur)).setText(parse(vehicule.getHauteur()));

            if (vehicule.getPhoto().equals("Pictures")){
                Drawable imageNone = ResourcesCompat.getDrawable(getResources(),R.drawable.emotecamion,null);
                imageAffiche = view.findViewById(R.id.imageNone);
                imageAffiche.setImageDrawable(imageNone);
            }
            else{
                //affichage image
                imageAffiche = view.findViewById(R.id.imageNone);
                Glide.with(getActivity()).load(ApiManager.adresse+"/uploads/"+vehicule.getPhoto()).into(imageAffiche);
            }
        }
    }

    private String getContent(View view) { return ((EditText) view).getText().toString(); }

    private String getContent(RadioButton rb) { return rb.getText().toString(); }

    private boolean match(String s1, String s2) { return s1.equals(s2); }

    private boolean isNotEmpty(View view) { return !match(getContent(view), ""); }

    private int toInt(String i) { return Integer.parseInt(i); }

    private long toLong(String l) { return Long.parseLong(l); }

    private String parse(long l) { return String.valueOf(l); }

    private String parse(int i) { return String.valueOf(i); }


    @Override
    public void onActivityResult(int requestcode, int resultcode, @Nullable Intent intent){
        super.onActivityResult(requestcode,requestcode,intent);
        if( requestcode == REQUEST_IMAGE_CAPTURE && resultcode==-1){
            imageAffiche = view.findViewById(R.id.imageNone);
            Bitmap image = BitmapFactory.decodeFile(photoPath);
            imageAffiche.setImageBitmap(image);
        }

    }
}