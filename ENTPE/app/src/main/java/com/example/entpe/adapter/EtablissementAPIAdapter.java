package com.example.entpe.adapter;

import android.location.Criteria;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entpe.R;
import com.example.entpe.model.EtablissementAPI;
import com.example.entpe.model.Settings;

import java.util.List;

public abstract class EtablissementAPIAdapter extends RecyclerView.Adapter<EtablissementAPIAdapter.EtablissementAPIHolder> {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Private class //////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    static class EtablissementAPIHolder extends RecyclerView.ViewHolder {
        //////////////////////////////////////////////////////////////////
        //// Attributes //////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        public TextView nom;
        public TextView adresse;
        public TextView ville;
        public TextView siret;

        //////////////////////////////////////////////////////////////////
        //// Constructors ////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        public EtablissementAPIHolder(@NonNull View itemView) {
            super(itemView);
            nom = itemView.findViewById(R.id.afficheNom);
            adresse = itemView.findViewById(R.id.afficheAdresse);
            ville = itemView.findViewById(R.id.afficheVille);
            siret = itemView.findViewById(R.id.afficheSiret);
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    List<EtablissementAPI> etablissementAPIList;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public EtablissementAPIAdapter(List<EtablissementAPI> etablissementAPIS) {
        this.etablissementAPIList = etablissementAPIS;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    @NonNull
    @Override
    public EtablissementAPIAdapter.EtablissementAPIHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.etablissementapi_element,parent,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { EtablissementAPIAdapter.this.onItemClick(v); }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                EtablissementAPIAdapter.this.onItemLongClick(v);
                return true;
            }
        });

        return new EtablissementAPIAdapter.EtablissementAPIHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EtablissementAPIAdapter.EtablissementAPIHolder holder, int position) {
        EtablissementAPI s = etablissementAPIList.get(position);
        holder.nom.setText(limiteText(s.getNom(),30));
        holder.adresse.setText(limiteText(s.getAdresse(),40));
        holder.ville.setText(limiteText(s.getVille(),10));
        holder.siret.setText(limiteText(s.getSiret(),15));

    }


    @Override
    public int getItemCount() { return etablissementAPIList.size(); }

    public abstract  void onItemClick(View v);

    public abstract boolean onItemLongClick(View v);

    private String limiteText(String text,int taille){
        String res = text;
        if (text.length()>taille){
            res = text.substring(0,taille)+"...";
        }
        return res;
    }
}
