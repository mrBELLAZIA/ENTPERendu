package com.example.entpe.adapter;

import android.location.Criteria;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entpe.R;
import com.example.entpe.model.Settings;

import java.util.List;

public abstract class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.SettingsHolder> {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Private class //////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    static class SettingsHolder extends RecyclerView.ViewHolder {
        //////////////////////////////////////////////////////////////////
        //// Attributes //////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        public int id;
        public TextView nom;
        public TextView consommation;
        public TextView precision;
        public TextView periode;

        //////////////////////////////////////////////////////////////////
        //// Constructors ////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////
        public SettingsHolder(@NonNull View itemView) {
            super(itemView);
            nom = itemView.findViewById(R.id.afficheNomSet);
            consommation = itemView.findViewById(R.id.afficheConsommation);
            precision = itemView.findViewById(R.id.affichePrecision);
            periode = itemView.findViewById(R.id.affichePeriode);
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    List<Settings> settingsList;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public SettingsAdapter(List<Settings> settings) {
        this.settingsList = settings;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    @NonNull
    @Override
    public SettingsAdapter.SettingsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_element,parent,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { SettingsAdapter.this.onItemClick(v); }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SettingsAdapter.this.onItemLongClick(v);
                return true;
            }
        });

        return new SettingsAdapter.SettingsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingsAdapter.SettingsHolder holder, int position) {
        Settings s = settingsList.get(position);
        holder.nom.setText(s.getNomSettings());
        switch (s.getConsommation()){
            case Criteria.POWER_LOW:
                holder.consommation.setText("Faible");
                break;
                case Criteria.POWER_MEDIUM:
                    holder.consommation.setText("Moyenne");
                    break;
                case Criteria.POWER_HIGH:
                    holder.consommation.setText("Haute");
                    break;

        }

        switch (s.getPrecision()){
            case Criteria.ACCURACY_LOW:
                holder.precision.setText("Faible");
                break;
            case Criteria.ACCURACY_MEDIUM:
                holder.precision.setText("Moyenne");
                break;
            case Criteria.ACCURACY_HIGH:
                holder.precision.setText("Haute");
                break;
        }
        holder.periode.setText(s.getPeriode()+"s");
    }


    @Override
    public int getItemCount() { return settingsList.size(); }

    public abstract  void onItemClick(View v);

    public abstract boolean onItemLongClick(View v);
}
