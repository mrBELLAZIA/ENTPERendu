package com.example.entpe.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entpe.R;
import com.example.entpe.model.Etablissement;

import java.util.ArrayList;
import java.util.List;

public abstract class EntrepriseAdapter extends RecyclerView.Adapter<EntrepriseAdapter.EntrepriseHolder> implements Filterable {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Private class //////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    static class EntrepriseHolder extends RecyclerView.ViewHolder {
        //////////////////////////////////////////////////////////////////
        //// Attributes //////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        public int id;
        public TextView nom;
        public TextView adresse;
        public TextView ville;
        public TextView nature;

        //////////////////////////////////////////////////////////////////
        //// Constructors ////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        public EntrepriseHolder(@NonNull View itemView) {
            super(itemView);
            nom = itemView.findViewById(R.id.afficheNom);
            adresse = itemView.findViewById(R.id.afficheAdresse);
            ville = itemView.findViewById(R.id.afficheVille);
            nature = itemView.findViewById(R.id.afficheNature);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    List<Etablissement> etablissementsComplete;
    List<Etablissement> etablissementRecherche;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public EntrepriseAdapter(List<Etablissement> etablissements) {
        this.etablissementRecherche = etablissements;
        etablissementsComplete = new ArrayList<>(etablissementRecherche);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    @NonNull
    @Override
    public EntrepriseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entreprise_element,parent,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { EntrepriseAdapter.this.onItemClick(v); }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                EntrepriseAdapter.this.onItemLongClick(v);
                return true;
            }
        });

        return new EntrepriseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EntrepriseHolder holder, int position) {
        Etablissement e = etablissementRecherche.get(position);
        String ville = e.getVille() + ", " + e.getCodePostal();

        holder.nom.setText(limiteText(e.getNom(),15));
        holder.adresse.setText(limiteText(e.getAdresse(),12));
        holder.ville.setText(limiteText(ville,12));
        holder.nature.setText(limiteText(e.getNature(),15));
    }


    @Override
    public int getItemCount() { return etablissementRecherche.size(); }

    public abstract  void onItemClick(View v);

    public abstract boolean onItemLongClick(View v);

    @Override
    public Filter getFilter() { return listeFiltre; }

    private final Filter listeFiltre = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Etablissement> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0) { filteredList.addAll(etablissementsComplete); }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(Etablissement e: etablissementsComplete) { if(e.getNom().toLowerCase().contains(filterPattern)) { filteredList.add(e); } }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @SuppressWarnings({"unchecked", "rawtypes"})
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
                etablissementRecherche.clear();
                etablissementRecherche.addAll((List) results.values);
                notifyDataSetChanged();
        }
    };

    private String limiteText(String text,int taille){
        String res = text;
        if (text.length()>taille){
            res = text.substring(0,taille)+"...";
        }
        return res;
    }
}
