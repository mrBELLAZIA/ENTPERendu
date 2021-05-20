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
import com.example.entpe.model.Vehicule;
import com.example.entpe.storage.DataBaseManager;

public class DeleteVehiculeDialogFragment extends DialogFragment {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private Updatable updatable;
    private int id;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // TODO : JAVADOC
    public DeleteVehiculeDialogFragment(Updatable updatable, int id) {
        setUpdatable(updatable);
        setId(id);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Setters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private void setUpdatable(Updatable updatable) { this.updatable = updatable; }

    private void setId(int id) { this.id = id; }

    public int getIdVehicule() {
        return id;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressLint({"StringFormatMatches", "StringFormatInvalid"})
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        //Tache correspondant à la tache selectionné dans la vue
        DataBaseManager dbm = new DataBaseManager(getContext());

        Vehicule vehicule = dbm.findVehicule(id);
        assert vehicule != null;

        return new AlertDialog.Builder(getActivity())
                .setTitle("Supression")
                .setMessage("Etes-vous sûre de vouloir supprpimer le véhicule avce la plaque " + vehicule.getPlaque())
                .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Suppression
                        DataBaseManager dbm = new DataBaseManager(getContext());
                        dbm.deleteVehicule(getIdVehicule());
                        updatable.update();
                        Toast.makeText(getActivity(),"Véhicule supprimé !", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Annuler", null)
                .create();
    }
}
