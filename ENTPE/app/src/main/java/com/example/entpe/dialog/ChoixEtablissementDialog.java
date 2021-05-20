package com.example.entpe.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entpe.R;
import com.example.entpe.activity.Initialisation;
import com.example.entpe.adapter.EntrepriseAdapter;
import com.example.entpe.model.Etablissement;
import com.example.entpe.storage.DataBaseManager;

import java.util.ArrayList;
import java.util.List;

public class ChoixEtablissementDialog extends DialogFragment {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private Initialisation init;
    private EntrepriseAdapter adapter;
    private View view;

    private RecyclerView list;
    private List<Etablissement> etablissements;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public ChoixEtablissementDialog(Initialisation init) { setInit(init); }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Setters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private void setInit(Initialisation init) { this.init = init; }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstance){
        view = requireActivity().getLayoutInflater().inflate(R.layout.choix_entreprise,null);

        // Récuperation de la liste à partir du json
        etablissements = new ArrayList<>();
        DataBaseManager manager = new DataBaseManager(getContext());
        //EtablissementJSONFileStorage etablissementJSONFileStorage= EtablissementJSONFileStorage.get(view.getContext());
        // etablissements = etablissementJSONFileStorage.findAll();
        etablissements = manager.findAllEtablissement();
        list = view.findViewById(R.id.RecyclerEntreprisChoix);
        list.setLayoutManager(new LinearLayoutManager(this.getContext()));

        adapter = new EntrepriseAdapter(etablissements) {
            // Un appui court mène vers la modification
            @Override
            public void onItemClick(View v) {
                Etablissement etablissement = etablissements.get(list.getChildViewHolder(v).getAdapterPosition()) ;
                init.setEtablissementCourant(etablissement);
                init.update();
                dismiss();
            }

            @Override
            public boolean onItemLongClick(View v) { return false; }
        };

        list.setAdapter(adapter);

        // Bouton aucun
        Button aucun = view.findViewById(R.id.aucunEtablissement);
        aucun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init.setEtablissementCourant(null);
                init.update();
                dismiss();
            }
        });

        SearchView search = view.findViewById(R.id.searchEtablissement);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();
    }
}
