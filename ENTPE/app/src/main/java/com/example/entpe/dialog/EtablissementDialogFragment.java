package com.example.entpe.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.example.entpe.R;
import com.example.entpe.application.MyApplication;
import com.example.entpe.model.ApiManager;
import com.example.entpe.model.Etablissement;
import com.example.entpe.model.EtablissementAPI;
import com.example.entpe.storage.DataBaseManager;

import java.util.Objects;


public class EtablissementDialogFragment extends DialogFragment implements Api {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constants //////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private static final int ID_NONE = -1;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private Updatable updatable;
    private int id;
    private View view;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public EtablissementDialogFragment(Updatable updatable, int id) {
        setUpdatable(updatable);
        setId(id);
    }

    public EtablissementDialogFragment(Updatable updatable) { this(updatable, ID_NONE); }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Setters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private void setUpdatable(Updatable updatable) { this.updatable = updatable; }

    private void setId(int id) { this.id = id; }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstance) {
        view = requireActivity().getLayoutInflater().inflate(R.layout.ajout_modif_entreprise,null);
        setEntrepriseToView();

        // Changement du titre selon ajout ou modification
        String type = "MODIFICATION ETABLISSEMENT";
        if(id != -1) { type = "ETABLISSEMENT"; }

        ((TextView)view.findViewById(R.id.titre_gestion)).setText(type);

        // Boutons d'ajout et d'annulation
        Button confirme = view.findViewById(R.id.editConfirme);
        confirme.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ApiManager apiManager = new ApiManager();
                Etablissement etablissement = getEntrepriseFromView();
                DataBaseManager manager = new DataBaseManager(getContext());
                if(EtablissementDialogFragment.this.id == ID_NONE) {
                    if(etablissement != null) {
                        apiManager.insertEtablissement(etablissement);
                        Toast.makeText(getActivity(),"Etablissement ajouté !", Toast.LENGTH_LONG).show();
                        dismiss();
                    } else { Toast.makeText(getActivity(),"Saisie incomplète !", Toast.LENGTH_LONG).show(); }
                } else {
                    if(etablissement != null) {
                        apiManager.updateEtablissement(etablissement);
                        Toast.makeText(getActivity(),"Etablissement modifié !", Toast.LENGTH_LONG).show();
                        dismiss();
                    } else { Toast.makeText(getActivity(),"Saisie incomplète !", Toast.LENGTH_LONG).show(); }
                }

                updatable.update();
            }
        });

        Button recherche = view.findViewById(R.id.recherche);
        recherche.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ApiManager apiManager = new ApiManager();
                String siret = ((EditText)getViewFind().findViewById(R.id.editSiret)).getText().toString();
                apiManager.getEtablissementBySiret(siret,EtablissementDialogFragment.this);
            }
        });

        ImageButton search = view.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                (new EtablissementAPIDialog(EtablissementDialogFragment.this)).show(getActivity().getSupportFragmentManager(),"");
            }
        });

        Button annule = view.findViewById(R.id.editAnnule);
        annule.setOnClickListener(new View.OnClickListener() { public void onClick(View view) { dismiss(); } });

        // Gestion du bouton autre
        RadioButton autre = view.findViewById(R.id.editAutre);
        final EditText autreText = view.findViewById(R.id.editAutreChoix);

        autreText.setVisibility(View.INVISIBLE);
        autre.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    //Si l'on commence avec un chargement
                    autreText.setVisibility(View.VISIBLE);
                } else {
                    //Si l'on commence la tournée à vide
                    autreText.setVisibility(View.INVISIBLE);
                }
            }
        });

        return new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                .setView(view)
                .create();
    }

    // Récupère l'établissement à partir de la vue pour ajout
    private Etablissement getEntrepriseFromView() {
        Etablissement etablissement = null;
        String nature = "";

        RadioGroup rb = view.findViewById(R.id.GroupeChoixNature);
        RadioButton choixNature = rb.findViewById(rb.getCheckedRadioButtonId());
        String codePostal = "";

        if(choixNature == null) { return null; }
        else if(match(getContent(choixNature), "Autre")) { nature = ((EditText)view.findViewById(R.id.editAutreChoix)).getText().toString();
        } else {
            nature = getContent(choixNature);

            switch(nature) {
                case "Exploitation agricole":
                    // code block
                    nature = "Exploitation agricole";
                    break;
                case "Artisanat (interventions d’installation et réparations diverses)":
                    // code block
                    nature = "Artisanat";
                    break;
                case "Services (services à la personne ou aux entreprises)":
                    // code block
                    nature = "Services";
                    break;
                case "Industrie ou atelier":
                    // code block
                    nature = "Industrie ou atelier";
                    break;
                case "Commerce de gros":
                    // code block
                    nature = "Commerce de gros";
                    break;
                case "Commerce de détail (petit magasin ou hôtellerie-restauration)":
                    // code block
                    nature = "Commerce de détail";
                    break;
                case "Grande distribution":
                    // code
                    nature = "Grande distribution";
                    break;
                case "Activités de bureau":
                    // code block
                    nature = "Activités de bureau";
                    break;
                case "Etablissements scolaires ou d'hébergement":
                    // code block
                    nature = "Etablissements";
                    break;
                case "Transporteur ou plateforme logistique":
                    // code block
                    nature = "Transporteur/plateforme";
                    break;
            }
        }

        // Récupération des champs
        String siret = ((TextView)view.findViewById(R.id.editSiret)).getText().toString();
        String nom = ((TextView)view.findViewById(R.id.editNom)).getText().toString();
        String adresse = ((TextView)view.findViewById(R.id.editadresse)).getText().toString();

        if(isNotEmpty(view.findViewById(R.id.editTextTextPostalAddress))){ codePostal = ((TextView)view.findViewById(R.id.editTextTextPostalAddress)).getText().toString(); }

        String ville = ((TextView)view.findViewById(R.id.editVille)).getText().toString();

        //Vérification des champs
        if(match(nom, "")) {
            ((TextView)view.findViewById(R.id.textView4)).setTextColor(Color.RED);
            //((TextView)view.findViewById(R.id.textView4)).setTextColor(Color.parseColor("#5b39c6"));
            return null;
        }

        if (match(adresse, "")) { return null; }

        if (match(ville, "")) { return null; }

        if(!isNotEmpty(view.findViewById(R.id.editSiret))) { return null; }

        // Ajout pour Siret Incomplet
        int tailleChamp = siret.length();
        if(tailleChamp < 9) {
            ((EditText)view.findViewById(R.id.editSiret)).setTextColor(Color.RED);
            return null;
        } else if(tailleChamp < 14) {
            String ajout = "";

            for(int i = 0 ; i < 14 - tailleChamp ; i++) { ajout +="0"; }

            siret = ajout + siret;
        } else if(tailleChamp > 14) {
            ((EditText)view.findViewById(R.id.editSiret)).setTextColor(Color.RED);
            return null;
        }

        return new Etablissement(id, siret, nom, adresse, codePostal, ville, nature);
    }

    // Lors de la modification, on affiche les infos de la tache existante
    private void setEntrepriseToView() {
        DataBaseManager manager = new DataBaseManager(getContext());
        //Etablissement etablissement = EtablissementJSONFileStorage.get(view.getContext()).find(id);
        Etablissement etablissement = manager.findEtablissement(id);

        if(etablissement != null) {
            ((TextView)view.findViewById(R.id.editSiret)).setText(etablissement.getSiret());
            ((EditText)view.findViewById(R.id.editNom)).setText(etablissement.getNom());
            ((EditText)view.findViewById(R.id.editadresse)).setText(etablissement.getAdresse());
            ((EditText)view.findViewById(R.id.editTextTextPostalAddress)).setText(etablissement.getCodePostal());
            ((EditText)view.findViewById(R.id.editVille)).setText(etablissement.getVille());

            // Affichage de la nature
            String nature = etablissement.getNature();

            switch(nature) {
                case "Exploitation agricole":
                    // code block
                    ((RadioButton) view.findViewById(R.id.editExploitationAgricole)).setChecked(true);
                    break;
                case "Artisanat":
                    // code block
                    ((RadioButton) view.findViewById(R.id.editArtisanat)).setChecked(true);
                    break;
                case "Services":
                    // code block
                    ((RadioButton) view.findViewById(R.id.editServices)).setChecked(true);
                    break;
                case "Industrie ou atelier":
                    // code block
                    ((RadioButton) view.findViewById(R.id.editIndustrie)).setChecked(true);
                    break;
                case "Commerce de gros":
                    // code block
                    ((RadioButton) view.findViewById(R.id.editCommerceDeGros)).setChecked(true);
                    break;
                case "Commerce de détail":
                    // code block
                    ((RadioButton) view.findViewById(R.id.editCommerceDeDetail)).setChecked(true);
                    break;
                case "Grande distribution":
                    // code
                    ((RadioButton) view.findViewById(R.id.editGrandeDistribution)).setChecked(true);
                    break;
                case "Activités de bureau":
                    // code block
                    ((RadioButton) view.findViewById(R.id.editBureau)).setChecked(true);
                    break;
                case "Etablissements":
                    // code block
                    ((RadioButton) view.findViewById(R.id.editScolairesOuHebergement)).setChecked(true);
                    break;
                case "Transporteur/plateforme":
                    // code block
                    ((RadioButton) view.findViewById(R.id.editTransporteurOuLogistique)).setChecked(true);
                    break;
                default:
                    // code block
                    ((RadioButton) view.findViewById(R.id.editAutre)).setChecked(true);
                    ((EditText) view.findViewById(R.id.editAutreChoix)).setText(nature);
            }
        }
    }

    private String getContent(View view) { return ((EditText) view).getText().toString(); }

    private String getContent(RadioButton rb) { return rb.getText().toString(); }

    private boolean match(String s1, String s2) { return s1.equals(s2); }

    private boolean isNotEmpty(View view) { return !match(getContent(view), ""); }

    public void toView(EtablissementAPI etablissementAPI){
        if(etablissementAPI == null){
            Toast.makeText(getActivity(),"Aucun résultat trouvé", Toast.LENGTH_LONG).show();
        }else{
            ((EditText)view.findViewById(R.id.editadresse)).setText(etablissementAPI.getAdresse());
            ((EditText)view.findViewById(R.id.editVille)).setText(etablissementAPI.getVille());
            ((EditText)view.findViewById(R.id.editTextTextPostalAddress)).setText(etablissementAPI.getCodePostal());
        }
    }
    private View getViewFind(){
        return view;
    }

    private int toInt(String i) { return Integer.parseInt(i); }

    private long toLong(String l) { return Long.parseLong(l); }

    private String parse(long l) { return String.valueOf(l); }

    private String parse(int i) { return String.valueOf(i); }


    @Override
    public void choix() {
        if(MyApplication.getEtablissementAPI() == null){
            Toast.makeText(getActivity(),"Aucun résultat trouvé", Toast.LENGTH_LONG).show();
        }else{
            ((EditText)getViewFind().findViewById(R.id.editSiret)).setText(MyApplication.getEtablissementAPI().getSiret());
            ((EditText)getViewFind().findViewById(R.id.editadresse)).setText(MyApplication.getEtablissementAPI().getAdresse());
            ((EditText)getViewFind().findViewById(R.id.editNom)).setText(MyApplication.getEtablissementAPI().getNom());
            ((EditText)getViewFind().findViewById(R.id.editVille)).setText(MyApplication.getEtablissementAPI().getVille());
            ((RadioButton) view.findViewById(R.id.editAutre)).setChecked(true);
            ((EditText)getViewFind().findViewById(R.id.editAutreChoix)).setVisibility(View.VISIBLE);
            ((EditText)getViewFind().findViewById(R.id.editAutreChoix)).setText(MyApplication.getEtablissementAPI().getNature());
            ((EditText)getViewFind().findViewById(R.id.editTextTextPostalAddress)).setText(MyApplication.getEtablissementAPI().getCodePostal());

            Toast.makeText(getActivity(),"résultat trouvé", Toast.LENGTH_LONG).show();
        }
        MyApplication.setEtablissementAPI(null);
    }
}
