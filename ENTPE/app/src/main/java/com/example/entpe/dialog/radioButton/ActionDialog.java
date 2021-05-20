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

public class ActionDialog extends DialogFragment {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private Questionnaire questionnaire;
    private View view;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TODO : JAVADOC
    public ActionDialog(Questionnaire questionnaire) { setQuestionnaire(questionnaire); }

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
        view = requireActivity().getLayoutInflater().inflate(R.layout.choix_action, null);
        setToView();

        return new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                .setView(view)
                .setPositiveButton(R.string.dialog_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String action = getFromView();
                        questionnaire.setAction(action);
                        questionnaire.update();
                    }
                })
                .setNegativeButton(R.string.dialog_negative_button,null)
                .create();
    }


    public String getFromView() {
        RadioGroup groupe = view.findViewById(R.id.groupeAction);
        RadioButton choix = groupe.findViewById(groupe.getCheckedRadioButtonId());
        String res = null;

        if(choix != null) {
            res = choix.getText().toString();
            if(res.equals(getString(R.string.Autres))) { res = ((EditText) view.findViewById(R.id.editTextTextPersonName4)).getText().toString(); }
        }

        return res;
    }

    public void setToView() {
        String choix = questionnaire.getAction();

        if(choix != null) {
            if(choix.equals(getString(R.string.actionChoix1))) {
                ((RadioButton) view.findViewById(R.id.actionChoix1)).setChecked(true);
            } else if(choix.equals(getString(R.string.actionChoix2))) {
                ((RadioButton) view.findViewById(R.id.actionChoix2)).setChecked(true);
            } else if(choix.equals(getString(R.string.actionChoix3))) {
                ((RadioButton) view.findViewById(R.id.actionChoix3)).setChecked(true);
            } else if(choix.equals(getString(R.string.actionChoix4))) {
                ((RadioButton) view.findViewById(R.id.actionChoix4)).setChecked(true);
            } else {
                ((RadioButton) view.findViewById(R.id.actionChoixAutres)).setChecked(true);
                ((EditText)view.findViewById(R.id.editTextTextPersonName4)).setText(choix);
            }
        }
    }
}
