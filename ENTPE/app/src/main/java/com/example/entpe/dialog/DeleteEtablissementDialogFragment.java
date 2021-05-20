package com.example.entpe.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.entpe.model.Etablissement;
import com.example.entpe.storage.DataBaseManager;


public class DeleteEtablissementDialogFragment extends DialogFragment {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private Updatable updatable;
    private int id;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TODO : JAVADOC
    public DeleteEtablissementDialogFragment(Updatable updatable, int id) {
        setUpdatable(updatable);
        setId(id);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Setters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private void setUpdatable(Updatable updatable) { this.updatable = updatable; }

    private void setId(int id) { this.id = id; }

    public int getIdEta() {
        return id;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressLint({"StringFormatMatches", "StringFormatInvalid"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //Tache correspondant à la tache selectionné dans la vue
        //Etablissement etablissement = EtablissementJSONFileStorage.get(getContext()).find(id);
        DataBaseManager dbm = new DataBaseManager(getContext());
        Etablissement etablissement = dbm.findEtablissement(id);
        assert etablissement != null;

        return new AlertDialog.Builder(getActivity())
                .setTitle("Supression")
                .setMessage("Etes-vous sûre de vouloir supprpimer " + etablissement.getNom())
                .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Suppression
                        DataBaseManager dbm = new DataBaseManager(getContext());
                        dbm.deleteEtablissement(getIdEta());
                        updatable.update();
                        Toast.makeText(getActivity(),"Etablissement supprimé !", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Annuler", null)
                .create();
    }
}
