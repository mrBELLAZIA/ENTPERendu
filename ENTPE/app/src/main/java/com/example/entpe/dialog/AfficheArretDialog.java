package com.example.entpe.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.entpe.R;
import com.example.entpe.activity.Questionnaire;
import com.example.entpe.model.Arret;
import com.example.entpe.model.EnqueteCSV;
import com.example.entpe.storage.DataBaseManager;

import java.util.Objects;


public class AfficheArretDialog extends DialogFragment {

    private Updatable updatable;
    private int id;
    private int arretId;
    private View view;
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TODO : JAVADOC
    public AfficheArretDialog(Updatable updatable,int id,int arretId){
        setUpdatable(updatable);
        setArretId(arretId);
        setId(id);
    }



    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Setters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private void setUpdatable(Updatable updatable) { this.updatable = updatable; }
    private void setArretId(int arretId) {this.arretId = arretId;}
    private void setId(int id) { this.id = id; }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstance){
        view = requireActivity().getLayoutInflater().inflate(R.layout.affiche_arrete,null);
        setArretToView();

        //Bouton vers modification
        Button edit = view.findViewById(R.id.ModifierArret);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(view.getContext(), Questionnaire.class); // à modifier
                myIntent.putExtra("id",arretId);
                startActivity(myIntent);
            }
        });
        return new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                .setView(view)
                .create();
    }

    @SuppressLint("SetTextI18n")
    private void setArretToView() {
        DataBaseManager dbm = new DataBaseManager(getContext());
        Arret arret = dbm.findArret(arretId);
        final LinearLayout photo = view.findViewById(R.id.layout_image);
        final LinearLayout questionnaireMerch = view.findViewById(R.id.layout_merch);

        if(arret != null) {
            ((TextView)view.findViewById(R.id.numArret)).setText(""+arret.getNum_arret());
            ((TextView)view.findViewById(R.id.numClient)).setText(""+arret.getNumClient());
            ((TextView)view.findViewById(R.id.editStationnement)).setText(limiteText(arret.getLieuStationnement(),50));
            ((TextView)view.findViewById(R.id.editAdresseClient)).setText(limiteText(arret.getAdresseClient(),30));
            ((TextView)view.findViewById(R.id.editNatureclient)).setText(limiteText(arret.getNatureLieu(),20));
            ((TextView)view.findViewById(R.id.edittypearrets)).setText(limiteText(arret.getAction(),30));
            ((TextView)view.findViewById(R.id.editlieudepose)).setText(limiteText(arret.getLieuAction(),30));
            ((TextView)view.findViewById(R.id.editmanutention)).setText(arret.getMoyenManutention());
            ((TextView)view.findViewById(R.id.editConditionement)).setText(arret.getNatureConditionnement());
            ((TextView)view.findViewById(R.id.editNbunites)).setText(Integer.toString(arret.getNombreUnite()));
            ((TextView)view.findViewById(R.id.editnaturemerch)).setText(arret.getNatureMarchandise());
            ((TextView)view.findViewById(R.id.editpoidsmerch)).setText(Math.round(arret.getPoids())+"");
            ((TextView)view.findViewById(R.id.editvolumemerch)).setText(Math.round(arret.getVolume())+"");
            ((TextView)view.findViewById(R.id.editoperation)).setText(arret.getOperationEffectuee());
            ((TextView)view.findViewById(R.id.editCom)).setText(limiteText(arret.getCommentaire(),40));
            String imagerecup = arret.getPhoto();
            if(imagerecup.equals("")) {
                questionnaireMerch.setVisibility(View.VISIBLE);
                photo.setVisibility(View.GONE);
            }
            else{
                questionnaireMerch.setVisibility(View.GONE);
                photo.setVisibility(View.VISIBLE);
                ImageView imageAffiche = view.findViewById(R.id.imageMerch);
                Bitmap image = BitmapFactory.decodeFile(arret.getPhoto());
                imageAffiche.setImageBitmap(image);
            }
        }
    }
    private String limiteText(String text,int taille){
        String res = text;
        if (text.length()>taille){
            res = text.substring(0,taille)+"...";
        }
        return res;
    }

}
