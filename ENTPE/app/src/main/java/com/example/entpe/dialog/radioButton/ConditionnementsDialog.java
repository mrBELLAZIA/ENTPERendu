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

public class ConditionnementsDialog extends DialogFragment {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private Questionnaire questionnaire;
    private View view;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TODO : JAVADOC
    public ConditionnementsDialog(Questionnaire questionnaire) { setQuestionnaire(questionnaire); }

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
        view = requireActivity().getLayoutInflater().inflate(R.layout.choix_conditionnements, null);
        setConditionementToView();

        return new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                .setView(view)
                .setPositiveButton(R.string.dialog_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String operation = getConditionementFromView();
                        questionnaire.setNatureCondionnement(operation);
                        questionnaire.update();
                    }
                })
                .setNegativeButton(R.string.dialog_negative_button,null)
                .create();
    }

    private String getConditionementFromView() {
        RadioGroup groupe = view.findViewById(R.id.grouepConditionement);
        RadioButton choix = groupe.findViewById(groupe.getCheckedRadioButtonId());
        String res = null;

        if(choix != null) {
            res = choix.getText().toString();
            if(res.equals(getString(R.string.Autres))) { res = ((EditText) view.findViewById(R.id.conditionementEdit)).getText().toString(); }
        }

        return res;
    }

    private void setConditionementToView() {
        String choix = questionnaire.getNatureCondionnement();

        if(choix != null) {
            if(choix.equals(getString(R.string.conditionementChoix1))) {
                ((RadioButton) view.findViewById(R.id.conditionementChoix1)).setChecked(true);
            } else if (choix.equals(getString(R.string.conditionementChoix2))) {
                ((RadioButton) view.findViewById(R.id.conditionementChoix2)).setChecked(true);
            } else if (choix.equals(getString(R.string.conditionementChoix3))) {
                ((RadioButton) view.findViewById(R.id.conditionementChoix3)).setChecked(true);
            } else if (choix.equals(getString(R.string.conditionementChoix4))) {
                ((RadioButton) view.findViewById(R.id.conditionementChoix4)).setChecked(true);
            } else if (choix.equals(getString(R.string.conditionementChoix5))) {
                ((RadioButton) view.findViewById(R.id.conditionementChoix5)).setChecked(true);
            } else if (choix.equals(getString(R.string.conditionementChoix6))) {
                ((RadioButton) view.findViewById(R.id.conditionementChoix6)).setChecked(true);
            } else if (choix.equals(getString(R.string.conditionementChoix7))) {
                ((RadioButton) view.findViewById(R.id.conditionementChoix7)).setChecked(true);
            } else if (choix.equals(getString(R.string.conditionementChoix8))) {
                ((RadioButton) view.findViewById(R.id.conditionementChoix8)).setChecked(true);
            } else if (choix.equals(getString(R.string.conditionementChoix9))) {
                ((RadioButton) view.findViewById(R.id.conditionementChoix9)).setChecked(true);
            } else if (choix.equals(getString(R.string.conditionementChoix10))) {
                ((RadioButton) view.findViewById(R.id.conditionementChoix10)).setChecked(true);
            } else {
                ((RadioButton) view.findViewById(R.id.conditionementChoixAutres)).setChecked(true);
                ((EditText)view.findViewById(R.id.conditionementEdit)).setText(choix);
            }
        }
    }
}