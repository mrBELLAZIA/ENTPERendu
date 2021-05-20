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
import com.example.entpe.model.Vehicule;

import java.util.ArrayList;
import java.util.List;

public abstract class VehiculeAdapter  extends RecyclerView.Adapter<VehiculeAdapter.VehiculeHolder>  implements Filterable {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Private class //////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    static class VehiculeHolder extends RecyclerView.ViewHolder {
        //////////////////////////////////////////////////////////////////
        //// Attributes //////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        public TextView marque;
        public TextView modele;
        public TextView plaque;
        public TextView categorie;

        //////////////////////////////////////////////////////////////////
        //// Constructors ////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        public VehiculeHolder(@NonNull View itemView) {
            super(itemView);
            marque = itemView.findViewById(R.id.afficheMarque);
            modele = itemView.findViewById(R.id.afficheModèle);
            plaque = itemView.findViewById(R.id.affichePlaque);
            categorie = itemView.findViewById(R.id.afficheCategorie);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    List<Vehicule> flottesCompletes;
    List<Vehicule> flottesRecherche;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public VehiculeAdapter(List<Vehicule> flottes) {
        this.flottesRecherche = flottes;
        flottesCompletes =  new ArrayList<>(flottesRecherche);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    @NonNull
    @Override
    public VehiculeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicule_element,parent,false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { VehiculeAdapter.this.onItemClick(v); }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                VehiculeAdapter.this.onItemLongClick(v);
                return true;
            }
        });

        return new VehiculeAdapter.VehiculeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehiculeAdapter.VehiculeHolder holder, int position) {
        holder.marque.setText(limiteText(flottesRecherche.get(position).getMarque(),15));
        holder.modele.setText(limiteText(flottesRecherche.get(position).getModele(),15));
        holder.plaque.setText(limiteText(flottesRecherche.get(position).getPlaque(),15));
        holder.categorie.setText(limiteText(String.valueOf(flottesRecherche.get(position).getCritAir()),15));
    }

    @Override
    public int getItemCount() { return flottesRecherche.size(); }

    public abstract  void onItemClick(View v);

    public abstract boolean onItemLongClick(View v);

    @Override
    public Filter getFilter() { return listeFiltre; }

    private final Filter listeFiltre= new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Vehicule> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0) { filteredList.addAll(flottesCompletes); }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(Vehicule v: flottesCompletes) { if(v.getPlaque().toLowerCase().contains(filterPattern)) { filteredList.add(v); } }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @SuppressWarnings({"unchecked", "rawtypes"})
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            flottesRecherche.clear();
            flottesRecherche.addAll((List) results.values);
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
