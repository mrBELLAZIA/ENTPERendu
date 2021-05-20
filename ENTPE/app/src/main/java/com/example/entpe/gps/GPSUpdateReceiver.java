package com.example.entpe.gps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;

import com.example.entpe.model.EnqueteCSV;
import com.example.entpe.model.Position;

import java.io.IOException;
import java.util.Objects;

public class GPSUpdateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        EnqueteCSV enquete = Objects.requireNonNull(intent.getExtras()).getParcelable("Enquete");
        Location der = Objects.requireNonNull(intent.getExtras()).getParcelable("dernierLoc");
        Location location = (Location) intent.getParcelableExtra(LocationManager.KEY_LOCATION_CHANGED);

        if(der != null) {
            // Ajout dans l'arraylist
            Position position = new Position(-1,enquete.getCompteurPosition(),
                                                (float) location.getLatitude(),
                                                (float) location.getLongitude(),
                                                (float) location.getAltitude(),
                                                location.getSpeed(),
                                                der.distanceTo(location),
                                                location.getBearing(),-1
                                            );

            enquete.ajouterPosition(position);

            // Ajout dans CSV
            try { enquete.ajoutPosition(new String[] {
                    Integer.toString(enquete.getCompteurPosition()),
                    Double.toString(location.getLatitude()),
                    Double.toString(location.getLongitude()),
                    Double.toString(location.getAltitude()),
                    Double.toString(location.getSpeed()),
                    Double.toString(location.getBearing()),
                    Float.toString(der.distanceTo(location))
            }); }
            catch (IOException e) { e.printStackTrace(); }

            der = location;
        } else {
            Position position = new Position(-1,enquete.getCompteurPosition(),
                                                (float) location.getLatitude(),
                                                (float) location.getLongitude(),
                                                (float) location.getAltitude(),
                                                location.getSpeed(),
                                                0.0f,
                                                location.getBearing(),-1);

            enquete.ajouterPosition(position);

            try { enquete.ajoutPosition(new String[] {
                    Integer.toString(enquete.getCompteurPosition()),
                    Double.toString(location.getLatitude()),
                    Double.toString(location.getLongitude()),
                    Double.toString(location.getAltitude()),
                    Double.toString(location.getSpeed()),
                    Double.toString(location.getBearing()),
                    "0"
            }); }
            catch (IOException e) { e.printStackTrace(); }

            der = location;
        }
    }
}