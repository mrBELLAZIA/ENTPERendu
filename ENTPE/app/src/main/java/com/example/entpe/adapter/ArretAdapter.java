package com.example.entpe.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entpe.R;
import com.example.entpe.model.Arret;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class ArretAdapter extends RecyclerView.Adapter<ArretAdapter.ArretHolder> implements Filterable {

    static class ArretHolder extends RecyclerView.ViewHolder {
        //////////////////////////////////////////////////////////////////
        //// Attributes //////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        public TextView heureDep;
        public TextView heureArr;
        public TextView natureClient;
        public TextView adresseClient;


        public ArretHolder(@NonNull View itemView){
            super(itemView);
            heureArr = itemView.findViewById(R.id.afficheHeurearr);
            heureDep = itemView.findViewById(R.id.afficheHeuredep);
            natureClient = itemView.findViewById(R.id.affichenature);
            adresseClient = itemView.findViewById(R.id.afficheaddresse);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    List<Arret> listeArrets;
    List<Arret> listearretsRecherche;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    public ArretAdapter(List<Arret> liste) {
        this.listearretsRecherche = liste;
        listeArrets = new ArrayList<>(listearretsRecherche);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    @NonNull
    @Override
    public ArretHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.arret_element,parent,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArretAdapter.this.onItemClick(v);
            }
        });
        return new ArretHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ArretHolder holder, int position) {
        String heureDep = listearretsRecherche.get(position).getHeureDepart().toString();
        String heureArr = listearretsRecherche.get(position).getHeureDepart().toString();
        heureArr = heureArr.substring(11,16);
        heureDep = heureDep.substring(11,16);
        holder.heureDep.setText(heureDep);
        holder.heureArr.setText(heureArr);
        holder.natureClient.setText(limiteText(listearretsRecherche.get(position).getNatureLieu(),12));
        holder.adresseClient.setText(limiteText(listearretsRecherche.get(position).getAdresseClient(),12));
    }

    @Override
    public int getItemCount() {
        return listearretsRecherche.size();
    }

    public abstract void onItemClick(View v);

    public abstract boolean onItemLongClick(View v);

    @Override
    public Filter getFilter() {
        return listeFiltre;
    }


    private final android.widget.Filter listeFiltre = new android.widget.Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Arret> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(listeArrets);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Arret a : listeArrets) {
                    if (a.getAdresseClient().toLowerCase().contains(filterPattern)) {
                        filteredList.add(a);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listearretsRecherche.clear();
            listearretsRecherche.addAll((List) results.values);
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

