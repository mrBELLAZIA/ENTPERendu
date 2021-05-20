package com.example.entpe.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entpe.R;
import com.example.entpe.activity.Parametres;
import com.example.entpe.adapter.SettingsAdapter;
import com.example.entpe.model.Settings;
import com.example.entpe.storage.DataBaseManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChoixSettings extends DialogFragment{
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private Parametres updatable;
    private SettingsAdapter adapter;
    private View view;

    private RecyclerView list;
    private List<Settings> settings;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public ChoixSettings(Parametres updatable) {
        this.updatable = updatable;
    }



    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public void start(){
        settings = new ArrayList<>();
        final DataBaseManager manager = new DataBaseManager(getContext());

        settings = manager.findAllSettings();
        list = view.findViewById(R.id.RecyclerSettingsChoix);
        list.setLayoutManager(new LinearLayoutManager(this.getContext()));

        adapter = new SettingsAdapter(settings) {

            @Override
            public void onItemClick(View v) {
                Settings setting = settings.get(list.getChildViewHolder(v).getAdapterPosition()) ;
                updatable.setSettings(setting);
                updatable.update();
                dismiss();
            }

            @Override
            public boolean onItemLongClick(View v) {
                Settings setting = settings.get(list.getChildViewHolder(v).getAdapterPosition()) ;
                manager.deleteSettings(setting.getId());
                dismiss();
                (new ChoixSettings(updatable)).show(updatable.getSupportFragmentManager(), "");
                return true;}
        };

        list.setAdapter(adapter);

    }
    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstance) {
        view = requireActivity().getLayoutInflater().inflate(R.layout.choix_settings,null);
        start();

        //Bouton Aucun
        Button aucun = view.findViewById(R.id.aucunSettings);
        aucun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatable.setSettings(null);
                updatable.update();
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
}
