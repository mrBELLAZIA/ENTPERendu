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

public class OperationDialog extends DialogFragment {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private Questionnaire questionnaire;
    private View view;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TODO : JAVADOC
    public OperationDialog(Questionnaire questionnaire) { setQuestionnaire(questionnaire); }

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
        view = requireActivity().getLayoutInflater().inflate(R.layout.choix_operation, null);
        setOperationToView();
        return new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                .setView(view)
                .setPositiveButton(R.string.dialog_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String operation = getOperationFromView();
                        questionnaire.setOperationEffectue(operation);
                        questionnaire.update();
                    }
                })
                .setNegativeButton(R.string.dialog_negative_button,null)
                .create();
    }

    private String getOperationFromView() {
        RadioGroup groupe = view.findViewById(R.id.groupeOperation);
        RadioButton choix = groupe.findViewById(groupe.getCheckedRadioButtonId());
        String res = null;

        if(choix != null){
            res = choix.getText().toString();
            if(res.equals(getString(R.string.Autres))) { res = ((EditText) view.findViewById(R.id.operationEdit)).getText().toString(); }
        }

        return res;
    }

    private void setOperationToView() {
        String choix = questionnaire.getOperationEffectue();

        if(choix!=null) {
            if(choix.equals(getString(R.string.operationChoix1))) {
                ((RadioButton) view.findViewById(R.id.operationChoix1)).setChecked(true);
            } else if(choix.equals(getString(R.string.operationChoix2))) {
                ((RadioButton) view.findViewById(R.id.operationChoix2)).setChecked(true);
            } else if(choix.equals(getString(R.string.operationChoix3))) {
                ((RadioButton) view.findViewById(R.id.operationChoix3)).setChecked(true);
            } else if(choix.equals(getString(R.string.operationChoix4))) {
                ((RadioButton) view.findViewById(R.id.operationChoix4)).setChecked(true);
            } else if(choix.equals(getString(R.string.operationChoix5))) {
                ((RadioButton) view.findViewById(R.id.operationChoix5)).setChecked(true);
            } else if(choix.equals(getString(R.string.operationChoix6))) {
                ((RadioButton) view.findViewById(R.id.operationChoix6)).setChecked(true);
            } else if(choix.equals(getString(R.string.operationChoix7))) {
                ((RadioButton) view.findViewById(R.id.operationChoix7)).setChecked(true);
            } else {
                ((RadioButton) view.findViewById(R.id.operationChoixAutres)).setChecked(true);
                ((EditText)view.findViewById(R.id.operationEdit)).setText(choix);
            }
        }
    }
}
