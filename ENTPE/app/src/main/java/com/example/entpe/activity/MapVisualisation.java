package com.example.entpe.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Pair;

import com.example.entpe.R;
import com.example.entpe.application.MyApplication;
import com.example.entpe.model.CSVLoader;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MapVisualisation extends AppCompatActivity implements OnMapReadyCallback {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constants //////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private static final int WIDTH_A_PIED = 3;
    private static final int WIDTH_EN_TOURNEE = 3;

    /**
     * À ajuster selon les goûts
     */
    private static final float ZOOM = 20;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    //EnqueteCSV enquete;
    //int enqueteId;
    GoogleMap map;

    List<Polyline> polylineList = new ArrayList<>();
    List<Marker> markerList = new ArrayList<>();

    Dialog progressDialog;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Getters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private int getColor(boolean inCar) { return getColor(inCar ? R.color.trace_a_pied : R.color.trace_en_tournee); }

    private int getWidth(boolean inCar) { return inCar ? WIDTH_A_PIED : WIDTH_EN_TOURNEE; }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_visualisation);

        //enquete = Objects.requireNonNull(getIntent().getExtras()).getParcelable("Enquete");
        //enqueteId = getIntent().getExtras().getInt("EnqueteId");

        SupportMapFragment smf = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        Objects.requireNonNull(smf).getMapAsync(this);

        progressDialog = new Dialog(MapVisualisation.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    //////////////////////////////////////////////////////////////////////
    //// OnMapReadyCallback methods //////////////////////////////////////
    //////////////////////////////////////////////////////////////////////
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        LatLng first = loadPath();
        loadClients();

        progressDialog.dismiss();

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(first, ZOOM));
    }

    //////////////////////////////////////////////////////////////////////
    //// Private methods /////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////
    ////// Path-loading methods /////////////////
    /////////////////////////////////////////////
    /**
     * Charge et affiche le trajet GPS sur la carte
     * @return la première position (pour zoom joli sur la carte)
     */
    private LatLng loadPath() {
        List<Pair<LatLng, Boolean>> positions = CSVLoader.loadPath(MyApplication.getEnquete().getPosition());
        List<LatLng> latLngList = new ArrayList<>();

        latLngList.add(positions.get(0).first);
        boolean prev = positions.get(0).second;

        for(Pair<LatLng, Boolean> coords : positions.subList(1, positions.size())) {
            LatLng latLng = coords.first;
            boolean inCar = coords.second;

            if(inCar != prev) {
                PolylineOptions po = new PolylineOptions()
                        .addAll(latLngList)
                        .color(getColor(inCar))
                        .width(getWidth(inCar));

                polylineList.add(map.addPolyline(po));
                latLngList.clear();
            }

            latLngList.add(latLng);
            prev = inCar;
        }

        PolylineOptions po = new PolylineOptions()
                .addAll(latLngList)
                .color(getColor(prev))
                .width(getWidth(prev));

        polylineList.add(map.addPolyline(po));
        latLngList.clear();

        return positions.get(0).first;
    }

    /////////////////////////////////////////////
    ////// Client-loading methods ///////////////
    /////////////////////////////////////////////
    /**
     * Charge et affiche les clients sur la carte
     */
    private void loadClients() {
        List<LatLng> locations = CSVLoader.loadClients(MyApplication.getEnquete().getArret());

        for(LatLng location : locations) {
            markerList.add(map.addMarker(new MarkerOptions().position(location).title("Client " + (locations.indexOf(location) + 1))));

            /*
            List<Address> addressList = new ArrayList<>();

            if(!location.isEmpty()) {
                Geocoder geocoder = new Geocoder(MapVisualisation.this);

                try { addressList = geocoder.getFromLocationName(location, 1); }
                catch(IOException e) { e.printStackTrace(); }
                if(addressList.size() == 0) return;
                Address address = addressList.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                markerList.add(map.addMarker(new MarkerOptions().position(latLng).title("Client " + (locations.indexOf(location) + 1))));
            }
            */
        }
    }
}