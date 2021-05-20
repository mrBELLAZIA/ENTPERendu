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

import com.example.entpe.activity.Initialisation;
import com.example.entpe.R;
import com.example.entpe.activity.Questionnaire;

import java.util.Objects;

public class NatureMarchandiseDialog extends DialogFragment {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private Initialisation questionnaire;
    private View view;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TODO : JAVADOC
    public NatureMarchandiseDialog(Initialisation questionnaire) { setQuestionnaire(questionnaire); }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Setters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private void setQuestionnaire(Initialisation questionnaire) { this.questionnaire = questionnaire; }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstance) {
        view = requireActivity().getLayoutInflater().inflate(R.layout.choix_nature_merchandise, null);
        setToView();

        return new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                .setView(view)
                .setPositiveButton(R.string.dialog_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String natureMerchandise = getFromView();
                        questionnaire.setNatureMerch(natureMerchandise);
                        questionnaire.update();
                    }
                })
                .setNegativeButton(R.string.dialog_negative_button,null)
                .create();
    }

    public String getFromView(){
        RadioGroup groupe = view.findViewById(R.id.choixNature);
        RadioButton choix = groupe.findViewById(groupe.getCheckedRadioButtonId());
        String res = null;

        if(choix != null) {
            res = choix.getText().toString();
            if(res.equals(getString(R.string.Autres))) { res = ((EditText) view.findViewById(R.id.autreNature)).getText().toString(); }
        }

        return res;
    }

    public void setToView() {
        String choix = questionnaire.getNatureMerch();

        if(choix != null) {
            if(choix.equals(getString(R.string.natureMerchChoix1))) {
                ((RadioButton) view.findViewById(R.id.natureMerchChoix1)).setChecked(true);
            } else if(choix.equals(getString(R.string.natureMerchChoix2))) {
                ((RadioButton) view.findViewById(R.id.natureMerchChoix2)).setChecked(true);
            } else if(choix.equals(getString(R.string.natureMerchChoix3))) {
                ((RadioButton) view.findViewById(R.id.natureMerchChoix3)).setChecked(true);
            } else if(choix.equals(getString(R.string.natureMerchChoix4))) {
                ((RadioButton) view.findViewById(R.id.natureMerchChoix4)).setChecked(true);
            } else if(choix.equals(getString(R.string.natureMerchChoix5))) {
                ((RadioButton) view.findViewById(R.id.natureMerchChoix5)).setChecked(true);
            } else if(choix.equals(getString(R.string.natureMerchChoix6))) {
                ((RadioButton) view.findViewById(R.id.natureMerchChoix6)).setChecked(true);
            } else if(choix.equals(getString(R.string.natureMerchChoix7))) {
                ((RadioButton) view.findViewById(R.id.natureMerchChoix7)).setChecked(true);
            } else if(choix.equals(getString(R.string.natureMerchChoix8))) {
                ((RadioButton) view.findViewById(R.id.natureMerchChoix8)).setChecked(true);
            } else if(choix.equals(getString(R.string.natureMerchChoix9))) {
                ((RadioButton) view.findViewById(R.id.natureMerchChoix9)).setChecked(true);
            } else if(choix.equals(getString(R.string.natureMerchChoix10))) {
                ((RadioButton) view.findViewById(R.id.natureMerchChoix10)).setChecked(true);
            } else if(choix.equals(getString(R.string.natureMerchChoix11))) {
                ((RadioButton) view.findViewById(R.id.natureMerchChoix11)).setChecked(true);
            } else if(choix.equals(getString(R.string.natureMerchChoix12))) {
                ((RadioButton) view.findViewById(R.id.natureMerchChoix12)).setChecked(true);
            } else if(choix.equals(getString(R.string.natureMerchChoix13))) {
                ((RadioButton) view.findViewById(R.id.natureMerchChoix13)).setChecked(true);
            } else if(choix.equals(getString(R.string.natureMerchChoix14))) {
                ((RadioButton) view.findViewById(R.id.natureMerchChoix14)).setChecked(true);
            } else {
                ((RadioButton) view.findViewById(R.id.natureChoixAutres)).setChecked(true);
                ((EditText)view.findViewById(R.id.editTextTextPersonName4)).setText(choix);
            }
        }
    }
}
