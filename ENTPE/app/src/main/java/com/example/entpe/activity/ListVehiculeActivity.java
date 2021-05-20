package com.example.entpe.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entpe.R;
import com.example.entpe.adapter.VehiculeAdapter;
import com.example.entpe.dialog.DeleteVehiculeDialogFragment;
import com.example.entpe.dialog.Updatable;
import com.example.entpe.dialog.VehiculeDialogFragment;
import com.example.entpe.model.ApiManager;
import com.example.entpe.model.Etablissement;
import com.example.entpe.model.Vehicule;
import com.example.entpe.storage.DataBaseManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListVehiculeActivity extends AppCompatActivity implements Updatable {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private RecyclerView list;
    private List<Vehicule> vehicules;
    private Etablissement etablissement;
    private VehiculeAdapter adapter;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private void start() {
        // Récupération de l'entreprise concernée
        int id = getIntent().getIntExtra("EntrepriseId", -1);
        DataBaseManager manager = new DataBaseManager(getApplicationContext());
        etablissement = manager.findEtablissement(id);

        // Changement du titre et des infos de haut de page
        ((TextView)findViewById(R.id.NomEntrepriseAffiche)).setText(etablissement.getNom());
        String adresse = etablissement.getAdresse() + ", " + etablissement.getVille();
        ((TextView)findViewById(R.id.AdresseEntrepriseAffiche)).setText(adresse);

        // Récuperation de la liste à partir du json
        vehicules = new ArrayList<>();
        DataBaseManager dataBaseManager =  new DataBaseManager(getApplicationContext());
        vehicules = dataBaseManager.findAllBySiret(id);
        // Mise en place de la RecyclerView
        list = findViewById(R.id.listFlotte);
        list.setLayoutManager(new LinearLayoutManager(this));

        adapter = new VehiculeAdapter(vehicules) {
            // Un appui court mène vers la modification
            @Override
            public void onItemClick(View v) {
                Vehicule vehicule = vehicules.get(list.getChildViewHolder(v).getAdapterPosition()) ;
                Log.e("Id du véhicule choisit : ", " "+vehicule.getId());
                (new VehiculeDialogFragment(ListVehiculeActivity.this,vehicule.getId(),etablissement.getId())).show(getSupportFragmentManager(),"");
            }

            // Un appuie long mène vers la suppression
            @Override
            public boolean onItemLongClick(View v) {
                Vehicule vehicule = vehicules.get(list.getChildViewHolder(v).getAdapterPosition());
                //(new DeleteVehiculeDialogFragment(ListVehiculeActivity.this,vehicule.getId())).show(getSupportFragmentManager(),"");
                return true;
            }
        };

        list.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.affichageflotte);
        start();
        Button ajout = findViewById(R.id.ajoutVehicule);
        ajout.setOnClickListener(new View.OnClickListener() {
            // Une pression sur le bouton mène vers le com.example.entpe.dialog d'ajout d'un Véhicule
            public void onClick(View view) { (new VehiculeDialogFragment(ListVehiculeActivity.this,etablissement.getId())).show(getSupportFragmentManager(),""); }
        });

        ImageButton refresh = findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            // Une pression sur le bouton mène vers le com.example.entpe.dialog d'ajout de tache
            public void onClick(View view) {
                update();
            }
        });

        SearchView search = findViewById(R.id.searchVehicule);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        start();
    }

    @Override
    public void update() {
        Objects.requireNonNull(list.getAdapter()).notifyDataSetChanged();
        DataBaseManager dataBaseManager = new DataBaseManager(getApplicationContext());
        dataBaseManager.deleteAllVehicule();
        ApiManager apiManager = new ApiManager();
        apiManager.recupVehicule(this);
    }
}
