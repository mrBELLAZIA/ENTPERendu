package com.example.entpe.model;


import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.entpe.activity.ListEntrepriseActivity;
import com.example.entpe.activity.ListVehiculeActivity;
import com.example.entpe.application.MyApplication;
import com.example.entpe.dialog.Api;
import com.example.entpe.dialog.EtablissementAPIDialog;
import com.example.entpe.dialog.EtablissementDialogFragment;
import com.example.entpe.dialog.Updatable;
import com.example.entpe.storage.DataBaseManager;
import com.google.android.gms.common.internal.StringResourceValueReader;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ApiManager {
    ////////////////////////
    ///////Constantes///////
    ////////////////////////
    ////////API REST////////
    public static final String adresse = "http://xxx.xxx.xxx.xxx"; //Adresse et port du serveur pour l'envoi de requêtes
    public static final String getEtablissements = "/getEtablissement";
    public static final String insertEtablissements = "/insertEtablissement";
    public static final String updateEtablissements = "/updateEtablissement";

    public static final String getVehicule = "/getVehicule";
    public static final String insertVehicule = "/insertVehicule";
    public static final String updateVehicule = "/updateVehicule";

    public static final String insertEnquete = "/insertEnquete";
    public static final String insertEnqueteComplete = "/insertEnqueteComplete";
    public static final String getDerniereEnquete = "/getDerniereEnquete";

    //Vehicule
    private static final String ID = "id";
    private static final String SIRET = "siret";
    private static final String NOM = "nom";
    private static final String ADRESSE = "adresse";
    private static final String CODE_POSTAL = "code_postal";
    private static final String VILLE = "ville";
    private static final String NATURE = "nature";

    private static final String PLAQUE = "plaque";
    private static final String MARQUE = "marque";
    private static final String MODELE = "modele";
    private static final String TYPE = "type";
    private static final String CATEGORIE = "categorie";
    private static final String MOTORISATION = "motorisation";

    private static final String PTAC = "ptac";
    private static final String PTRA = "ptra";
    private static final String POIDS_VIDE = "poidsvide";
    private static final String CHARGE_UTILE = "chargeutile";

    private static final String AGE = "age";
    private static final String SURFACE_SOL = "surfacesol";
    private static final String LARGEUR = "largeur";
    private static final String LONGUEUR = "longueur";
    private static final String HAUTEUR = "hauteur";

    private static final String CRITAIR = "critair";
    private static final String PHOTO = "photo";

    private static final String ETABLISSEMENT_ID = "etablissement_id";

    //////SIRENE///////
    private static String ADRESSE_SIRENE_V3 = "https://entreprise.data.gouv.fr/api/sirene/v3/";
    private static String ETABLISSEMENT = "etablissements/";
    private String ADRESSE_SIRENE_V1 ="https://entreprise.data.gouv.fr/api/sirene/v1/";
    private String RECHERCHE_TEXTE ="full_text/";

    ////////////////////////
    ///////Attributes///////
    ////////////////////////
    private DataBaseManager dbm;
    private RequestQueue request;



    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////METHODS API REST//////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    public ApiManager(){
        dbm = new DataBaseManager(MyApplication.getAppContext());
        request = Volley.newRequestQueue(MyApplication.getAppContext());
    }

    public void recupEtablissements(ListEntrepriseActivity activity){
        JsonArrayRequest objectRequest= new JsonArrayRequest(
                Request.Method.GET,
                adresse+getEtablissements,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i<response.length();i++){
                            JSONObject JSONetablissment = null;
                            try {
                                JSONetablissment = (JSONObject) response.get(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                Etablissement etablissement = new Etablissement(JSONetablissment.getInt(ID),
                                        JSONetablissment.getString(SIRET),
                                        JSONetablissment.getString(NOM),
                                        JSONetablissment.getString(ADRESSE),
                                        JSONetablissment.getString(CODE_POSTAL),
                                        JSONetablissment.getString(VILLE),
                                        JSONetablissment.getString(NATURE));
                                dbm.insert(etablissement);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        activity.onResume();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest API",error.toString());
                    }
                }
        );
        request.add(objectRequest);
    }

    public void recupEtablissements(){
        JsonArrayRequest objectRequest= new JsonArrayRequest(
                Request.Method.GET,
                adresse+getEtablissements,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i<response.length();i++){
                            JSONObject JSONetablissment = null;
                            try {
                                JSONetablissment = (JSONObject) response.get(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                Etablissement etablissement = new Etablissement(JSONetablissment.getInt(ID),
                                        JSONetablissment.getString(SIRET),
                                        JSONetablissment.getString(NOM),
                                        JSONetablissment.getString(ADRESSE),
                                        JSONetablissment.getString(CODE_POSTAL),
                                        JSONetablissment.getString(VILLE),
                                        JSONetablissment.getString(NATURE));
                                dbm.insert(etablissement);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest API",error.toString());
                    }
                }
        );
        request.add(objectRequest);
    }

    public void recupVehicule(ListVehiculeActivity activity){
        JsonArrayRequest objectRequest= new JsonArrayRequest(
                Request.Method.GET,
                adresse+getVehicule,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i<response.length();i++){
                            JSONObject JSONvehicule = null;
                            try {
                                JSONvehicule = (JSONObject) response.get(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                Vehicule vehicule = new Vehicule(
                                        JSONvehicule.getInt(ID),
                                        JSONvehicule.getString(PLAQUE),
                                        JSONvehicule.getString(MARQUE),
                                        JSONvehicule.getString(MODELE),
                                        JSONvehicule.getInt(TYPE),
                                        JSONvehicule.getInt(CATEGORIE),
                                        JSONvehicule.getInt(MOTORISATION),
                                        JSONvehicule.getLong(PTAC),
                                        JSONvehicule.getLong(PTRA),
                                        JSONvehicule.getLong(POIDS_VIDE),
                                        JSONvehicule.getLong(CHARGE_UTILE),
                                        JSONvehicule.getInt(AGE),
                                        JSONvehicule.getLong(SURFACE_SOL),
                                        JSONvehicule.getInt(LARGEUR),
                                        JSONvehicule.getInt(LONGUEUR),
                                        JSONvehicule.getInt(HAUTEUR),
                                        JSONvehicule.getInt(ETABLISSEMENT_ID),
                                        JSONvehicule.getInt(CRITAIR),
                                        JSONvehicule.getString(PHOTO));
                                dbm.insert(vehicule);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        activity.onResume();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest API",error.toString());
                    }
                }
        );
        request.add(objectRequest);
    }

    public void recupVehicule(){
        JsonArrayRequest objectRequest= new JsonArrayRequest(
                Request.Method.GET,
                adresse+getVehicule,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i<response.length();i++){
                            JSONObject JSONvehicule = null;
                            try {
                                JSONvehicule = (JSONObject) response.get(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                Vehicule vehicule = new Vehicule(
                                        JSONvehicule.getInt(ID),
                                        JSONvehicule.getString(PLAQUE),
                                        JSONvehicule.getString(MARQUE),
                                        JSONvehicule.getString(MODELE),
                                        JSONvehicule.getInt(TYPE),
                                        JSONvehicule.getInt(CATEGORIE),
                                        JSONvehicule.getInt(MOTORISATION),
                                        JSONvehicule.getLong(PTAC),
                                        JSONvehicule.getLong(PTRA),
                                        JSONvehicule.getLong(POIDS_VIDE),
                                        JSONvehicule.getLong(CHARGE_UTILE),
                                        JSONvehicule.getInt(AGE),
                                        JSONvehicule.getLong(SURFACE_SOL),
                                        JSONvehicule.getInt(LARGEUR),
                                        JSONvehicule.getInt(LONGUEUR),
                                        JSONvehicule.getInt(HAUTEUR),
                                        JSONvehicule.getInt(ETABLISSEMENT_ID),
                                        JSONvehicule.getInt(CRITAIR),
                                        JSONvehicule.getString(PHOTO));
                                dbm.insert(vehicule);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest API",error.toString());
                    }
                }
        );
        request.add(objectRequest);
    }

    public void insertEtablissement(Etablissement etablissement){
        //CREATION DE L'OBJET JSON CORRESPONDANT A L'ETABLISSEMENT
        JSONObject jsonObject = etablissement.toJSONObject();
        //ENVOI DE LA REQUETE
        JsonObjectRequest objectRequest= new JsonObjectRequest(
                Request.Method.POST,
                adresse+insertEtablissements,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest API",error.toString());
                    }
                }
        );
        request.add(objectRequest);
    }

    public void updateEtablissement(Etablissement etablissement){
        //CREATION DE L'OBJET JSON CORRESPONDANT A L'ETABLISSEMENT
        JSONObject jsonObject = etablissement.toJSONObjectID();
        //ENVOI DE LA REQUETE
        JsonObjectRequest objectRequest= new JsonObjectRequest(
                Request.Method.POST,
                adresse+updateEtablissements,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest API",error.toString());
                    }
                }
        );
        request.add(objectRequest);
    }

    public void insertVehicule(Vehicule vehicule){
        //CREATION DE L'OBJET JSON CORRESPONDANT A L'ETABLISSEMENT
        JSONObject jsonObject = vehicule.toJSONObject();
        //ENVOI DE LA REQUETE
        JsonObjectRequest objectRequest= new JsonObjectRequest(
                Request.Method.POST,
                adresse+insertVehicule,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest API",error.toString());
                    }
                }
        );
        request.add(objectRequest);
    }

    public void updateVehicule(Vehicule vehicule){
        //CREATION DE L'OBJET JSON CORRESPONDANT A L'ETABLISSEMENT
        JSONObject jsonObject = vehicule.toJSONObjectID();
        //ENVOI DE LA REQUETE
        JsonObjectRequest objectRequest= new JsonObjectRequest(
                Request.Method.POST,
                adresse+updateVehicule,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest API",error.toString());
                    }
                }
        );
        request.add(objectRequest);
    }

    public void insertEnquete(Enquete enquete) throws InterruptedException {
        final int[] id = new int[1];
        //CREATION DE L'OBJET JSON CORRESPONDANT A L'ETABLISSEMENT
        JSONObject jsonObject = enquete.toJSONObject();
        //ENVOI DE LA REQUETE
        JsonObjectRequest objectRequest= new JsonObjectRequest(
                Request.Method.POST,
                adresse+insertEnquete,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest API",error.toString());
                    }
                }
        );
        request.add(objectRequest);
        Thread.sleep(100);
        JsonArrayRequest objectRequestID= new JsonArrayRequest(
                Request.Method.GET,
                adresse+getDerniereEnquete,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject JSONenquete = null;
                            try {
                                JSONenquete = (JSONObject) response.get(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                id[0] = JSONenquete.getInt("id");

                                insertEnqueteComplete(id[0]);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest API",error.toString());
                    }
                }
        );
        request.add(objectRequestID);
    }

    public void insertEnqueteComplete(int id) throws JSONException {
        //CREATION DE L'OBJET JSON CORRESPONDANT A L'ETABLISSEMENT
        JSONObject jsonObject = new JSONObject();
        JSONArray arrets = new JSONArray();
        JSONArray positions = new JSONArray();
        //Création de l'objet arrets
        List<Arret> arretList = dbm.findAllArretByID(MyApplication.getEnqueteId());
        for(int i=0;i<arretList.size();i++){
            JSONObject inter = arretList.get(i).toJSONObject();
            inter.put("id_enquete",id);
            arrets.put(inter);
        }

        //Création de l'objet positions
        List<Position> positionList = dbm.findAllPostionByID(MyApplication.getEnqueteId());
        for(int i=0;i<positionList.size();i++){
            JSONObject inter = positionList.get(i).toJSONObject();
            inter.put("id_enquete",id);
            positions.put(inter);

        }
        jsonObject.put("arrets",arrets);
        jsonObject.put("positions",positions);


        //ENVOI DE LA REQUETE
        JsonObjectRequest objectRequest= new JsonObjectRequest(
                Request.Method.POST,
                adresse+insertEnqueteComplete,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest API",error.toString());
                    }
                }
        );
        request.add(objectRequest);
        dbm.deleteAllPosition();
        dbm.deleteAllEnquete();
        dbm.deleteAllArret();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////METHODS API SIRENE//////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void getEtablissementBySiret(String siret, Api updatable){
        final EtablissementAPI[] etablissementAPI = {null};
        //etablissementAPI[0] = null;
        JsonObjectRequest objectRequest= new JsonObjectRequest(
                Request.Method.GET,
                "https://entreprise.data.gouv.fr/api/sirene/v1/siret/"+siret,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject eta = new JSONObject();
                        try {
                            eta = (JSONObject) response.get("etablissement");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            etablissementAPI[0] = new EtablissementAPI(eta.getString("siret"),
                                    eta.getString("l1_normalisee")!="null"?eta.getString("l1_normalisee"):"incomplet",
                                    eta.getString("code_postal")!="null"?eta.getString("code_postal"):"incomplet",
                                    eta.getString("geo_adresse")!="null"?eta.getString("geo_adresse"):"incomplet",
                                    eta.getString("longitude")!="null"?Float.parseFloat(eta.getString("longitude")):0,
                                    eta.getString("latitude")!="null"?Float.parseFloat(eta.getString("latitude")):0,
                                    eta.getString("libelle_commune")!="null"?eta.getString("libelle_commune"):"incomplet",
                                    eta.getString("libelle_activite_principale")!="null"?eta.getString("libelle_activite_principale"):"incomplet"
                            );
                            MyApplication.setEtablissementAPI(etablissementAPI[0]);
                            updatable.choix();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        MyApplication.setEtablissementAPI(null);
                        updatable.choix();

                    }
                }
        );
        request.add(objectRequest);
    }

        public void getEtablissementByTexte(String texte, EtablissementAPIDialog updatable){
            ArrayList<EtablissementAPI> etablissementAPIS = new ArrayList<>();
        //etablissementAPI[0] = null;
        JsonObjectRequest objectRequest= new JsonObjectRequest(
                Request.Method.GET,
                ADRESSE_SIRENE_V1+RECHERCHE_TEXTE+texte+"?page=2&per_page=20",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray liste = new JSONArray();
                        try {
                            liste = (JSONArray) response.get("etablissement");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for(int i=0;i<liste.length();i++) {
                            JSONObject inter = null;
                            try {
                                inter = (JSONObject) liste.get(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                EtablissementAPI eta = new EtablissementAPI(inter.getString("siret"),
                                        (inter.getString("l1_normalisee")!="null"?inter.getString("l1_normalisee"):"incomplet").toLowerCase(),
                                        inter.getString("code_postal")!="null"?inter.getString("code_postal"):"incomplet",
                                        (inter.getString("geo_adresse")!="null"?inter.getString("geo_adresse"):"incomplet").toLowerCase(),
                                        inter.getString("longitude")!="null"?Float.parseFloat(inter.getString("longitude")):0,
                                        inter.getString("latitude")!="null"?Float.parseFloat(inter.getString("latitude")):0,
                                        (inter.getString("libelle_commune")!="null"?inter.getString("libelle_commune"):"incomplet").toLowerCase(),
                                        (inter.getString("libelle_activite_principale")!="null"?inter.getString("libelle_activite_principale"):"incomplet").toLowerCase()

                                );
                                etablissementAPIS.add(eta);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        updatable.setListe(etablissementAPIS);
                        updatable.onResume();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        MyApplication.setEtablissementAPI(null);

                    }
                }
        );
        request.add(objectRequest);
    }

}
