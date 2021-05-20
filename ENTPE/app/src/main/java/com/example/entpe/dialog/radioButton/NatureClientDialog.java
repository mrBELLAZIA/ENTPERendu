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

public class NatureClientDialog extends DialogFragment {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private Questionnaire questionnaire;
    private View view;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TODO : JAVADOC
    public NatureClientDialog(Questionnaire questionnaire) { setQuestionnaire(questionnaire); }

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
        view = requireActivity().getLayoutInflater().inflate(R.layout.choix_nature_client, null);
        setToView();

        return new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                .setView(view)
                .setPositiveButton(R.string.dialog_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String natureClient = getFromView();
                        questionnaire.setNatureLieu(natureClient);
                        questionnaire.update();
                    }
                })
                .setNegativeButton(R.string.dialog_negative_button,null)
                .create();
    }

    public String getFromView() {
        RadioGroup groupe = view.findViewById(R.id.groupeNatureClient);
        RadioButton choix = groupe.findViewById(groupe.getCheckedRadioButtonId());
        String res = null;

        if(choix != null) {
            res = choix.getText().toString();
            if(res.equals(getString(R.string.Autres))) { res = ((EditText) view.findViewById(R.id.editTextTextPersonName4)).getText().toString(); }
        }

        return res;
    }

    public void setToView() {
        String choix = questionnaire.getNatureLieu();

        if(choix != null) {
            if(choix.equals(getString(R.string.natureChoix1))) {
                ((RadioButton) view.findViewById(R.id.natureChoix1)).setChecked(true);
            } else if(choix.equals(getString(R.string.natureChoix2))) {
                ((RadioButton) view.findViewById(R.id.natureChoix2)).setChecked(true);
            } else if(choix.equals(getString(R.string.natureChoix3))) {
                ((RadioButton) view.findViewById(R.id.natureChoix3)).setChecked(true);
            } else if(choix.equals(getString(R.string.natureChoix4))) {
                ((RadioButton) view.findViewById(R.id.natureChoix4)).setChecked(true);
            } else if(choix.equals(getString(R.string.natureChoix5))) {
                ((RadioButton) view.findViewById(R.id.natureChoix5)).setChecked(true);
            } else if(choix.equals(getString(R.string.natureChoix6))) {
                ((RadioButton) view.findViewById(R.id.natureChoix6)).setChecked(true);
            } else if(choix.equals(getString(R.string.natureChoix7))) {
                ((RadioButton) view.findViewById(R.id.natureChoix7)).setChecked(true);
            } else if(choix.equals(getString(R.string.natureChoix8))) {
                ((RadioButton) view.findViewById(R.id.natureChoix8)).setChecked(true);
            } else if(choix.equals(getString(R.string.natureChoix9))) {
                ((RadioButton) view.findViewById(R.id.natureChoix9)).setChecked(true);
            } else if(choix.equals(getString(R.string.natureChoix10))) {
                ((RadioButton) view.findViewById(R.id.natureChoix10)).setChecked(true);
            } else if(choix.equals(getString(R.string.natureChoix11))) {
                ((RadioButton) view.findViewById(R.id.natureChoix11)).setChecked(true);
            } else {
                ((RadioButton) view.findViewById(R.id.natureChoixAutres)).setChecked(true);
                ((EditText)view.findViewById(R.id.editTextTextPersonName4)).setText(choix);
            }
        }
    }
}
