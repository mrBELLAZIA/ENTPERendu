package com.example.entpe.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.entpe.R;
import com.example.entpe.activity.FinTournee;
import com.example.entpe.activity.ListArretActivity;
import com.example.entpe.application.MyApplication;
import com.example.entpe.model.EnqueteCSV;
import com.example.entpe.model.TrackerGPS;

import java.util.Objects;

public class ValidationFinTourneDialog extends DialogFragment {

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////


    private int id;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public ValidationFinTourneDialog(int id){
        setId(id);
    }



    private void setId(int id) { this.id = id; }


    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Setters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressLint({"StringFormatMatches", "StringFormatInvalid"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        return new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.validation))
                .setMessage(getString(R.string.advise))
                .setPositiveButton(getString(R.string.dialog_positive_button),new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //pageSuivante
                    MyApplication.setEtatGps(MyApplication.ETAT_STOPPED);
                    Intent activite = new Intent(getContext(), ListArretActivity.class);
                    //activite.putExtra("EnqueteId",id);
                    startActivity(activite);
                }


        })
                .setNegativeButton("Annuler", null)
                .create();

    }
}
