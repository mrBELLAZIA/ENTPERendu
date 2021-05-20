package com.example.entpe.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.entpe.R;
import com.example.entpe.activity.ListVehiculeActivity;
import com.example.entpe.model.Etablissement;
import com.example.entpe.storage.DataBaseManager;

public class AfficheEtablissementDialog extends DialogFragment {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private Updatable updatable;
    private int id;
    private View view;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TODO : JAVADOC
    public AfficheEtablissementDialog(Updatable updatable, int id) {
        setUpdatable(updatable);
        setId(id);
    }

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
        view = requireActivity().getLayoutInflater().inflate(R.layout.affiche_etablissement,null);
        setEntrepriseToView();

        //Bouton menant vers la gestion de flotte ou la modification de l'entreprise
        Button flotte = view.findViewById(R.id.affichageFlotte);
        Button modif = view.findViewById(R.id.versModifEtablissement);

        flotte.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), ListVehiculeActivity.class);
                myIntent.putExtra("EntrepriseId", id);
                startActivity(myIntent);
            }
        });

        modif.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                (new EtablissementDialogFragment(updatable,id)).show(getFragmentManager(),"");
                dismiss();
            }
        });

        //Groupe de radio buttons rendu incliquable
        RadioGroup rg = view.findViewById(R.id.GroupeChoixNature);
        rg.setFocusable(false);
        rg.setClickable(false);

        (view.findViewById(R.id.editExploitationAgricole)).setFocusable(false);
        (view.findViewById(R.id.editExploitationAgricole)).setClickable(false);

        (view.findViewById(R.id.editArtisanat)).setFocusable(false);
        (view.findViewById(R.id.editArtisanat)).setClickable(false);

        (view.findViewById(R.id.editServices)).setFocusable(false);
        (view.findViewById(R.id.editServices)).setClickable(false);

        (view.findViewById(R.id.editIndustrie)).setFocusable(false);
        (view.findViewById(R.id.editIndustrie)).setClickable(false);

        (view.findViewById(R.id.editCommerceDeGros)).setFocusable(false);
        (view.findViewById(R.id.editCommerceDeGros)).setClickable(false);

        (view.findViewById(R.id.editCommerceDeDetail)).setFocusable(false);
        (view.findViewById(R.id.editCommerceDeDetail)).setClickable(false);

        (view.findViewById(R.id.editGrandeDistribution)).setFocusable(false);
        (view.findViewById(R.id.editGrandeDistribution)).setClickable(false);

        (view.findViewById(R.id.editBureau)).setFocusable(false);
        (view.findViewById(R.id.editBureau)).setClickable(false);

        (view.findViewById(R.id.editScolairesOuHebergement)).setFocusable(false);
        (view.findViewById(R.id.editScolairesOuHebergement)).setClickable(false);

        (view.findViewById(R.id.editTransporteurOuLogistique)).setFocusable(false);
        (view.findViewById(R.id.editTransporteurOuLogistique)).setClickable(false);

        (view.findViewById(R.id.editAutre)).setFocusable(false);
        (view.findViewById(R.id.editAutre)).setClickable(false);

        (view.findViewById(R.id.editAutreChoix)).setFocusable(false);
        (view.findViewById(R.id.editAutreChoix)).setClickable(false);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();
    }

    // Lors de la modification, on affiche les infos de la tache existante
    private void setEntrepriseToView() {
        DataBaseManager manager = new DataBaseManager(getContext());
        Etablissement etablissement = manager.findEtablissement(id);

        if(etablissement != null) {
            ((TextView)view.findViewById(R.id.editSiret)).setText(etablissement.getSiret());
            ((TextView)view.findViewById(R.id.editNom)).setText(etablissement.getNom());
            ((TextView)view.findViewById(R.id.editadresse)).setText(etablissement.getAdresse());
            ((TextView)view.findViewById(R.id.editTextTextPostalAddress)).setText(etablissement.getCodePostal());
            ((TextView)view.findViewById(R.id.editVille)).setText(etablissement.getVille());

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
                    ((TextView) view.findViewById(R.id.editAutreChoix)).setText(nature);
            }
        }
    }
}
