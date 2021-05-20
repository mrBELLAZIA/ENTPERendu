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


public class StationementDialog extends DialogFragment {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private Questionnaire questionnaire;
    private View view;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TODO : JAVADOC
    public StationementDialog(Questionnaire questionnaire) { setQuestionnaire(questionnaire); }

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
        view = requireActivity().getLayoutInflater().inflate(R.layout.choix_stationement, null);
        setStationnementToView();

        return new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                .setView(view)
                .setPositiveButton(R.string.dialog_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String stationnement = getStationnementFromView();
                        questionnaire.setLieuStationnement(stationnement);
                        questionnaire.update();
                    }
                })
                .setNegativeButton(R.string.dialog_negative_button,null)
                .create();
            }

    public String getStationnementFromView(){
        RadioGroup groupe = view.findViewById(R.id.groupeStationnement);
        RadioButton choix = groupe.findViewById(groupe.getCheckedRadioButtonId());
        String res = null;

        if(choix != null) {
            res = choix.getText().toString();
            if(res.equals(getString(R.string.Autres))) { res = ((EditText) view.findViewById(R.id.stationnementEdit)).getText().toString(); }
        }

        return res;
    }

    public void setStationnementToView() {
        String choix = questionnaire.getLieuStationnement();

        if(choix != null) {
            if(choix.equals(getString(R.string.stationnementchoix1))) {
                ((RadioButton) view.findViewById(R.id.stationnementchoixEtablissement)).setChecked(true);
            } else if(choix.equals(getString(R.string.stationnementchoix2))) {
                ((RadioButton) view.findViewById(R.id.stationnementchoixAutorise)).setChecked(true);
            } else if(choix.equals(getString(R.string.stationnementchoix3))) {
                ((RadioButton) view.findViewById(R.id.stationnementchoixPayant)).setChecked(true);
            } else if(choix.equals(getString(R.string.stationnementchoix4))) {
                ((RadioButton) view.findViewById(R.id.stationnementchoixInterdit)).setChecked(true);
            } else if(choix.equals(getString(R.string.stationnementchoix5))) {
                ((RadioButton) view.findViewById(R.id.stationnementchoix5)).setChecked(true);
            } else if(choix.equals(getString(R.string.stationnementchoix6))) {
                ((RadioButton) view.findViewById(R.id.stationnementchoixAire)).setChecked(true);
            } else if(choix.equals(getString(R.string.stationnementchoix7))) {
                ((RadioButton) view.findViewById(R.id.stationnementchoixCouloir)).setChecked(true);
            } else if(choix.equals(getString(R.string.stationnementchoix8))) {
                ((RadioButton) view.findViewById(R.id.stationnementchoixDoubleFile)).setChecked(true);
            } else {
                ((RadioButton) view.findViewById(R.id.stationnementchoixautres)).setChecked(true);
                ((EditText)view.findViewById(R.id.stationnementEdit)).setText(choix);
            }
        }
    }
}
