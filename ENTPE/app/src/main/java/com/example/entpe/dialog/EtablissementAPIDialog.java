package com.example.entpe.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entpe.R;
import com.example.entpe.activity.Parametres;
import com.example.entpe.adapter.EtablissementAPIAdapter;
import com.example.entpe.adapter.SettingsAdapter;
import com.example.entpe.application.MyApplication;
import com.example.entpe.model.ApiManager;
import com.example.entpe.model.EtablissementAPI;
import com.example.entpe.model.Settings;
import com.example.entpe.storage.DataBaseManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EtablissementAPIDialog extends DialogFragment {
        ///////////////////////////////////////////////////////////////////////////////////////////////
        // Attributes /////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////
        private EtablissementAPIAdapter adapter;
        private View view;
        private Api updatable;
        private RecyclerView list;
        private List<EtablissementAPI> etablissements;

        ///////////////////////////////////////////////////////////////////////////////////////////////
        // Constructors ///////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////
        public EtablissementAPIDialog(Api updatable) {
            this.updatable = updatable;
            etablissements = new ArrayList<>();
        }



        ///////////////////////////////////////////////////////////////////////////////////////////////
        // Methods ////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////
        public void start(){
            list = view.findViewById(R.id.RecyclerEtablissementChoix);
            list.setLayoutManager(new LinearLayoutManager(this.getContext()));

            adapter = new EtablissementAPIAdapter(etablissements) {

                @Override
                public void onItemClick(View v) {
                    EtablissementAPI etablissementAPI = etablissements.get(list.getChildViewHolder(v).getAdapterPosition());
                    MyApplication.setEtablissementAPI(etablissementAPI);
                    updatable.choix();
                    dismiss();

                }

                @Override
                public boolean onItemLongClick(View v) {
                    return true;}
            };

            list.setAdapter(adapter);

        }
        @SuppressLint("InflateParams")
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstance) {
            view = requireActivity().getLayoutInflater().inflate(R.layout.recherchebysirene,null);
            start();

            //Bouton Aucun
            ImageButton recherche = view.findViewById(R.id.rechercheSirene);
            recherche.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String texte = ((EditText)view.findViewById(R.id.champRecherche)).getText().toString();
                    ApiManager apiManager = new ApiManager();
                    apiManager.getEtablissementByTexte(texte,EtablissementAPIDialog.this);
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
            if(etablissements.size()==0){
                Toast.makeText(getActivity(),"Aucun résultat !", Toast.LENGTH_LONG).show();
            }
        }

        public void setListe(ArrayList<EtablissementAPI> liste){
            this.etablissements = liste;
        }
}

