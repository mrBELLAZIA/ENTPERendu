package com.example.entpe.dialog.radioButton;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.entpe.activity.Questionnaire;
import com.example.entpe.R;

import java.util.Objects;


public class ManutentionDialog extends DialogFragment {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private Questionnaire questionnaire;
    private View view;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TODO : JAVADOC
    public ManutentionDialog(Questionnaire questionnaire) { setQuestionnaire(questionnaire); }

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
        view = requireActivity().getLayoutInflater().inflate(R.layout.choix_manutention, null);
        setManutentionToView();

        return new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                .setView(view)
                .setPositiveButton(R.string.dialog_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String manutention = getManutentionFromView();
                        questionnaire.setMoyenManutention(manutention);
                        questionnaire.update();
                    }
                })
                .setNegativeButton("Annuler", null)
                .create();
    }

    private String getManutentionFromView() {
        RadioGroup groupe = view.findViewById(R.id.groupeManutention);
        RadioButton choix = groupe.findViewById(groupe.getCheckedRadioButtonId());
        String res = null;

        if(choix != null) {
            res = choix.getText().toString();
            //if("Autres".equals(res)) {
            if(res.equals(getString(R.string.Autres))) { res = ((EditText) view.findViewById(R.id.manutentionEdit)).getText().toString(); }
        }

        return res;
    }

    private void setManutentionToView() {
        String choix = questionnaire.getMoyenManutention();

        if(choix != null) {
            if(choix.equals(getString(R.string.manutentionchoix1))) {
                ((RadioButton) view.findViewById(R.id.manutentionchoix1)).setChecked(true);
            } else if(choix.equals(getString(R.string.manutentionchoix2))) {
                ((RadioButton) view.findViewById(R.id.manutentionchoix2)).setChecked(true);
            } else if(choix.equals(getString(R.string.manutentionchoix3))) {
                ((RadioButton) view.findViewById(R.id.manutentionchoix3)).setChecked(true);
            } else if(choix.equals(getString(R.string.manutentionchoix4))) {
                ((RadioButton) view.findViewById(R.id.manutentionchoix4)).setChecked(true);
            } else if(choix.equals(getString(R.string.manutentionchoix5))) {
                ((RadioButton) view.findViewById(R.id.manutentionchoix5)).setChecked(true);
            } else if(choix.equals(getString(R.string.manutentionchoix6))) {
                ((RadioButton) view.findViewById(R.id.manutentionchoix6)).setChecked(true);
            } else if(choix.equals(getString(R.string.manutentionchoix7))) {
                ((RadioButton) view.findViewById(R.id.manutentionchoix7)).setChecked(true);
            } else {
                ((RadioButton) view.findViewById(R.id.manutentionchoixAutres)).setChecked(true);
                ((EditText)view.findViewById(R.id.manutentionEdit)).setText(choix);
            }
        }
    }
}