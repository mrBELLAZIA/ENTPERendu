package com.example.entpe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entpe.R;
import com.example.entpe.adapter.ArretAdapter;
import com.example.entpe.application.MyApplication;
import com.example.entpe.dialog.AfficheArretDialog;
import com.example.entpe.dialog.Updatable;
import com.example.entpe.model.Arret;
import com.example.entpe.model.EnqueteCSV;
import com.example.entpe.model.UploadToServ;
import com.example.entpe.storage.DataBaseManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListArretActivity extends AppCompatActivity implements Updatable {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private RecyclerView list;
    private List<Arret> arrets;
    private ArretAdapter adapter;


    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    private void start() {
        arrets = new ArrayList<>();
        DataBaseManager dataBaseManager = new DataBaseManager(getApplicationContext());
        arrets = dataBaseManager.findAllArretByID(MyApplication.getEnqueteId());
        list = findViewById(R.id.RecyclerListeArrets);
        list.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ArretAdapter(arrets) {

            @Override
            public void onItemClick(View v) {
                Arret arret = arrets.get(list.getChildViewHolder(v).getAdapterPosition());
                (new AfficheArretDialog(ListArretActivity.this,arret.getEnqueteId(),arret.getId())).show(getSupportFragmentManager(),"");
            }

            @Override
            public boolean onItemLongClick(View v) {
                return false;
            }
        };
        list.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_arrets);
        start();
        DataBaseManager dataBaseManager = new DataBaseManager(getApplicationContext());
        SearchView search = findViewById(R.id.searchArrets);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        Button fin = findViewById(R.id.finlistearret);
        fin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent activite = new Intent(view.getContext(), FinTournee.class);
                try {
                    MyApplication.getEnquete().majCSV(MyApplication.getEnqueteId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                UploadToServ.initRetrofitClient();
                for(int i =0;i<arrets.size();i++){
                    if (!arrets.get(i).getPhoto().equals("")){
                        UploadToServ.multipartImageUpload(ListArretActivity.this,arrets.get(i).getPhoto());
                        arrets.get(i).setPhoto(photoPathToPhotoName(arrets.get(i).getPhoto()));
                        arrets.get(i).getId();
                        dataBaseManager.update(arrets.get(i).getId(), arrets.get(i));
                    }
                }
                startActivity(activite);
            }
        });

        Button maps = findViewById(R.id.buttonMaps);
        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity = new Intent(v.getContext(), MapVisualisation.class);
                startActivity(activity);
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        start();
    }
    public void update() {
        Objects.requireNonNull(list.getAdapter()).notifyDataSetChanged();
        onResume();
    }
    private String photoPathToPhotoName(String photoPath){
        String res = "";
        for(int i = 0;i<photoPath.length();i++){
            if (photoPath.charAt(i)=='/') {
                res="";
            }
            else {
                res = res+photoPath.charAt(i);
            }
        }
        return res;
    }


}
