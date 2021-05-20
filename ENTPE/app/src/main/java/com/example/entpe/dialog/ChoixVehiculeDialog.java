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
import com.example.entpe.adapter.VehiculeAdapter;
import com.example.entpe.model.Etablissement;
import com.example.entpe.model.Vehicule;
import com.example.entpe.storage.DataBaseManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChoixVehiculeDialog extends DialogFragment implements Updatable{
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private Initialisation init;
    private VehiculeAdapter adapter;
    private View view;

    private RecyclerView list;
    private List<Vehicule> flottes;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TODO : JAVADOC
    public ChoixVehiculeDialog(Initialisation init) { setInit(init);}

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Setters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private void setInit(Initialisation init) { this.init = init; }

    ///////////////////////////////////////////////////////////////////////////////////////////////
// Methods ////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////
    public void start(){
        flottes = new ArrayList<>();
        DataBaseManager manager = new DataBaseManager(getContext());

        flottes = manager.findAllBySiret(init.getEtablissementCourant().getId());
        list = view.findViewById(R.id.RecyclerVehiculeChoix);
        list.setLayoutManager(new LinearLayoutManager(this.getContext()));

        adapter = new VehiculeAdapter(flottes) {
            //Un appuie court mÃ¨ne vers la modification
            @Override
            public void onItemClick(View v) {
                Vehicule vehicule = flottes.get(list.getChildViewHolder(v).getAdapterPosition()) ;
                init.setVehiculeCourant(vehicule);
                init.update();
                dismiss();
            }

            @Override
            public boolean onItemLongClick(View v) { return false; }
        };

        list.setAdapter(adapter);

    }
    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstance) {
        view = requireActivity().getLayoutInflater().inflate(R.layout.choix_vehicule,null);
        final Etablissement etablissementCourant = init.getEtablissementCourant();
        start();
        //Bouton newvehicule
        Button newVehicule = view.findViewById(R.id.newvehicule);
        newVehicule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new VehiculeDialogFragment( ChoixVehiculeDialog.this,etablissementCourant.getId())).show(init.getSupportFragmentManager(), "");
            }
        });

        SearchView search = view.findViewById(R.id.searchVehicule);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        //Bouton Aucun
        Button aucun = view.findViewById(R.id.aucunVehicule);
        aucun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init.setVehiculeCourant(null);
                init.update();
                dismiss();
            }
        });

        return new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                .setView(view)
                .create();

    }

    @Override
    public void onResume() {
        super.onResume();
        start();
    }
    @Override
    public void update() {
        Objects.requireNonNull(list.getAdapter()).notifyDataSetChanged();
        onResume();
        dismiss();
        (new ChoixVehiculeDialog(init)).show(init.getSupportFragmentManager(), "");
    }
}