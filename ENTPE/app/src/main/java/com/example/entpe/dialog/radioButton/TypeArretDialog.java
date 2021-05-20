package com.example.entpe.dialog.radioButton;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.entpe.activity.Questionnaire;
import com.example.entpe.R;

import java.util.Objects;

public class TypeArretDialog extends DialogFragment {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private Questionnaire questionnaire;
    private View view;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TODO : JAVADOC
    public TypeArretDialog(Questionnaire questionnaire) { setQuestionnaire(questionnaire); }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Setters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private void setQuestionnaire(Questionnaire questionnaire) { this.questionnaire = questionnaire; }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstance) {
        view = requireActivity().getLayoutInflater().inflate(R.layout.choix_type_lieu, null);
        setToView();

        return new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                .setView(view)
                .setPositiveButton(R.string.dialog_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String typeLieu = getFromView();
                        questionnaire.setLieuAction(typeLieu);
                        questionnaire.update();
                    }
                })
                .setNegativeButton(R.string.dialog_negative_button,null)
                .create();
    }


    public String getFromView(){
        RadioGroup groupe = view.findViewById(R.id.groupeTypeLieu);
        RadioButton choix = groupe.findViewById(groupe.getCheckedRadioButtonId());
        String res = null;

        if(choix != null){
            res = choix.getText().toString();
            if(res.equals(getString(R.string.Autres))) { res = ((EditText) view.findViewById(R.id.editTextTextPersonName4)).getText().toString(); }
        }

        return res;
    }

    public void setToView() {
        String choix = questionnaire.getLieuAction();

        if(choix != null) {
            if(choix.equals(getString(R.string.lieuChoix1))) {
                ((RadioButton) view.findViewById(R.id.lieuChoix1)).setChecked(true);
            } else if(choix.equals(getString(R.string.lieuChoix2))) {
                ((RadioButton) view.findViewById(R.id.lieuChoix2)).setChecked(true);
            } else if(choix.equals(getString(R.string.lieuChoix3))) {
                ((RadioButton) view.findViewById(R.id.lieuChoix3)).setChecked(true);
            } else if(choix.equals(getString(R.string.lieuChoix4))) {
                ((RadioButton) view.findViewById(R.id.lieuChoix4)).setChecked(true);
            } else if(choix.equals(getString(R.string.lieuChoix1))) {
                ((RadioButton) view.findViewById(R.id.lieuChoix5)).setChecked(true);
            } else if(choix.equals(getString(R.string.lieuChoix5))) {
                ((RadioButton) view.findViewById(R.id.lieuChoix5)).setChecked(true);
            } else if(choix.equals(getString(R.string.lieuChoix7))) {
                ((RadioButton) view.findViewById(R.id.lieuChoix7)).setChecked(true);
            } else if(choix.equals(getString(R.string.lieuChoix8))) {
                ((RadioButton) view.findViewById(R.id.lieuChoix8)).setChecked(true);
            } else if(choix.equals(getString(R.string.lieuChoix9))) {
                ((RadioButton) view.findViewById(R.id.lieuChoix9)).setChecked(true);
            } else if(choix.equals(getString(R.string.lieuChoix10))) {
                ((RadioButton) view.findViewById(R.id.lieuChoix10)).setChecked(true);
            } else if(choix.equals(getString(R.string.lieuChoix11))) {
                ((RadioButton) view.findViewById(R.id.lieuChoix11)).setChecked(true);
            } else {
                ((RadioButton) view.findViewById(R.id.lieuChoixAutres)).setChecked(true);
                ((EditText)view.findViewById(R.id.editTextTextPersonName4)).setText(choix);
            }
        }
    }
}
