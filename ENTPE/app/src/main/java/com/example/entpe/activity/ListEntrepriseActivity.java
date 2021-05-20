package com.example.entpe.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entpe.R;
import com.example.entpe.adapter.EntrepriseAdapter;
import com.example.entpe.dialog.AfficheEtablissementDialog;
import com.example.entpe.dialog.DeleteEtablissementDialogFragment;
import com.example.entpe.dialog.EtablissementDialogFragment;
import com.example.entpe.dialog.Updatable;
import com.example.entpe.model.ApiManager;
import com.example.entpe.model.Etablissement;

import com.example.entpe.storage.DataBaseManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;

public class ListEntrepriseActivity extends AppCompatActivity implements Updatable {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private RecyclerView list;
    private List<Etablissement> etablissements;
    private EntrepriseAdapter adapter;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private void start() {
        // Récuperation de la liste à partir du json
        etablissements = new ArrayList<>();
        DataBaseManager dataBaseManager =  new DataBaseManager(getApplicationContext());
        etablissements = dataBaseManager.findAllEtablissement();

        list = findViewById(R.id.entreprises_list);
        list.setLayoutManager(new LinearLayoutManager(this));

        adapter = new EntrepriseAdapter(etablissements) {
            // Un appui court mène vers la modification
            @Override
            public void onItemClick(View v) {
                Etablissement etablissement = etablissements.get(list.getChildViewHolder(v).getAdapterPosition()) ;
                (new AfficheEtablissementDialog(ListEntrepriseActivity.this,etablissement.getId())).show(getSupportFragmentManager(),"");
            }

            // Un appui long mène vers la suppression
            @Override
            public boolean onItemLongClick(View v) {
                Etablissement etablissement = etablissements.get(list.getChildViewHolder(v).getAdapterPosition());
                //(new DeleteEtablissementDialogFragment(ListEntrepriseActivity.this,etablissement.getId())).show(getSupportFragmentManager(),"");
                return true;
            }
        };

        list.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.affichage_entreprises);
        start();
        Button ajout = findViewById(R.id.ajoutEntreprise);

        ajout.setOnClickListener(new View.OnClickListener() {
            // Une pression sur le bouton mène vers le com.example.entpe.dialog d'ajout de tache
            public void onClick(View view) {
                (new EtablissementDialogFragment(ListEntrepriseActivity.this)).show(getSupportFragmentManager(),""); }
        });

        ImageButton refresh = findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            // Une pression sur le bouton mène vers le com.example.entpe.dialog d'ajout de tache
            public void onClick(View view) {
                update();
            }
        });

        SearchView search = findViewById(R.id.searchEtablissement);

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
        dataBaseManager.deleteAllEtablissement();
        ApiManager apiManager = new ApiManager();
        apiManager.recupEtablissements(this);
    }
}
