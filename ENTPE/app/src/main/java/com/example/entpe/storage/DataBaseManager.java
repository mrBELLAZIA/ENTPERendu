package com.example.entpe.storage;

import android.content.Context;

import com.example.entpe.model.Arret;
import com.example.entpe.model.Enquete;
import com.example.entpe.model.Etablissement;
import com.example.entpe.model.Position;
import com.example.entpe.model.Settings;
import com.example.entpe.model.Utilisateur;
import com.example.entpe.model.UtilisateurHasEtablissement;
import com.example.entpe.model.Vehicule;
import com.example.entpe.storage.database_storage.ArretDataBaseStorage;
import com.example.entpe.storage.database_storage.EnqueteDataBaseStorage;
import com.example.entpe.storage.database_storage.EtablissementDataBaseStorage;
import com.example.entpe.storage.database_storage.PositionDataBaseStorage;
import com.example.entpe.storage.database_storage.SettingDataBaseStorage;
import com.example.entpe.storage.database_storage.UtilisateurDataBaseStorage;
import com.example.entpe.storage.database_storage.UtilisateurHasEtablissementDBStorage;
import com.example.entpe.storage.database_storage.VehiculeDataBaseStorage;
import com.example.entpe.storage.utility.JSONBuilder;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class DataBaseManager {
	///////////////////////////////////////////////////////////////////////////////////////////////
	// Attributes /////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	private static ArretDataBaseStorage ARRET_STORAGE;
	private static EnqueteDataBaseStorage ENQUETE_STORAGE;
	private static EtablissementDataBaseStorage ETABLISSEMENT_STORAGE;
	private static PositionDataBaseStorage POSITION_STORAGE;
	private static SettingDataBaseStorage SETTING_STORAGE;
	private static UtilisateurDataBaseStorage UTILISATEUR_STORAGE;
	private static UtilisateurHasEtablissementDBStorage UHE_STORAGE;
	private static VehiculeDataBaseStorage VEHICULE_STORAGE;

	///////////////////////////////////////////////////////////////////////////////////////////////
	// Constructor ////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Main constructor
	 */
	public DataBaseManager(Context context) {
		try { set(context); }
		catch(IOException e) { e.printStackTrace(); }
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	// Setters ////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Instantiates all the tables
	 * @throws IOException - En cas de problème lors de la création de la config
	 */
	private void set(Context context) throws IOException {
		ARRET_STORAGE = ArretDataBaseStorage.get(context);
		ENQUETE_STORAGE = EnqueteDataBaseStorage.get(context);
		ETABLISSEMENT_STORAGE = EtablissementDataBaseStorage.get(context);
		POSITION_STORAGE = PositionDataBaseStorage.get(context);
		SETTING_STORAGE = SettingDataBaseStorage.get(context);
		UTILISATEUR_STORAGE = UtilisateurDataBaseStorage.get(context);
		UHE_STORAGE = UtilisateurHasEtablissementDBStorage.get(context);
		VEHICULE_STORAGE = VehiculeDataBaseStorage.get(context);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	// Methods ////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Exporte toutes les tables dans des fichiers au format JSON
	 */
	public void exportAll() {
		try {
			// Fusionne ENQUETE_STORAGE, ARRET_STORAGE et POSITION_STORAGE
			exportEnquete();

			ETABLISSEMENT_STORAGE.export();
			// SETTING_STORAGE.export();
			// UTILISATEUR_STORAGE.export();
			// UHE_STORAGE.export();
			VEHICULE_STORAGE.export();
		} catch(Exception e) { e.printStackTrace(); }
	}

	/**
	 * Fusionne les arrêts, enquêtes et positions
	 */
	private void exportEnquete() {
		JSONArray enquetes = new JSONArray();

		for(Enquete enquete : findAllEnquete()) {
			int id = enquete.getId();

			List<Arret> arrets = findAllArretByID(id);
			List<Position> positions = findAllPostionByID(id);

			enquetes.put(JSONBuilder.parseRow(enquete, arrets, positions));
		}

		try { JSONBuilder.writeToFile(enquetes.toString(1)); }
		catch(JSONException e) { e.printStackTrace(); }
	}

	//////////////////////////////////////////////////////////////////////
	//// Insertion methods ///////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	public void insert(Arret arret) { ARRET_STORAGE.insert(arret); }

	public void insert(Enquete enquete) { ENQUETE_STORAGE.insert(enquete); }

	public void insert(Etablissement etablissement) { ETABLISSEMENT_STORAGE.insert(etablissement); }

	public void insert(Position position) { POSITION_STORAGE.insert(position); }

	public void insert(Utilisateur utilisateur) { UTILISATEUR_STORAGE.insert(utilisateur); }

	public void insert(UtilisateurHasEtablissement utilisateurHasEtablissement) { UHE_STORAGE.insert(utilisateurHasEtablissement); }
	
	public void insert(Vehicule vehicule) { VEHICULE_STORAGE.insert(vehicule); }

	public void insert(Settings settings) { SETTING_STORAGE.insert(settings);}
	
	//////////////////////////////////////////////////////////////////////
	//// Research methods ////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	////// Find methods /////////////////////////
	/////////////////////////////////////////////
	public Arret findArret(int id) { return ARRET_STORAGE.find(id); }

	public Enquete findEnquete(int id) { return ENQUETE_STORAGE.find(id); }

	public Etablissement findEtablissement(int id) { return ETABLISSEMENT_STORAGE.find(id); }

	public Position findPosition(int id) { return POSITION_STORAGE.find(id); }

	public Utilisateur findUtilisateur(int id) { return UTILISATEUR_STORAGE.find(id); }

	public UtilisateurHasEtablissement findUHE(int id) { return UHE_STORAGE.find(id); }

	public Settings findSettings(int id){ return SETTING_STORAGE.find(id); }

	public Vehicule findVehicule(int id) { return VEHICULE_STORAGE.find(id); }

	public int findIdEnqueteCourante(){
		List<Enquete> enquetes = ENQUETE_STORAGE.findAll();
		return enquetes.get(enquetes.size()-1).getId();
	}

	/////////////////////////////////////////////
	////// Find All methods /////////////////////
	/////////////////////////////////////////////
	public List<Arret> findAllArret() { return ARRET_STORAGE.findAll(); }

	public List<Enquete> findAllEnquete() { return ENQUETE_STORAGE.findAll(); }

	public List<Etablissement> findAllEtablissement() { return ETABLISSEMENT_STORAGE.findAll(); }

	public List<Position> findAllPosition() { return POSITION_STORAGE.findAll(); }

	public List<Utilisateur> findAllUtilisateur() { return UTILISATEUR_STORAGE.findAll(); }

	public List<UtilisateurHasEtablissement> findAllUHE() { return UHE_STORAGE.findAll(); }

	public List<Vehicule> findAllVehicule() { return VEHICULE_STORAGE.findAll(); }

	public List<Settings> findAllSettings() { return SETTING_STORAGE.findAll(); }

	/////////////////////////////////////////////
	////// Find with parameters methods /////////
	/////////////////////////////////////////////
	/**
	 * Récupère tous les véhicules appartenant à la flotte d'un établissement
	 * @param id est l'identifiant de l'établissement
	 * @return une liste des véhicules correspondants
	 */
	public List<Vehicule> findAllBySiret(int id) {
		List<Vehicule> list = findAllVehicule();
		int compteur = 0;

		while(compteur < list.size()) {
			if(list.get(compteur).getEtablissementId() != id) { list.remove(compteur); }
			else { compteur++; }
		}

		return list;
	}

	public List<Arret> findAllArretByID(int id) {
		List<Arret> list = findAllArret();
		int compteur = 0;

		while (compteur < list.size()) {
			System.out.println("Id enquete: "+list.get(compteur).getEnqueteId());
			if (list.get(compteur).getEnqueteId() != id) {
				list.remove(compteur);
			} else {
				compteur++;
			}
		}
		return list;
	}

	public List<Position> findAllPostionByID(int id) {
		List<Position> list = findAllPosition();
		int compteur = 0;

		while (compteur < list.size()) {
			if (list.get(compteur).getEnqueteId() != id) {
				list.remove(compteur);
			} else {
				compteur++;
			}
		}
		return list;
	}

		
	//////////////////////////////////////////////////////////////////////
	//// Size methods ////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	public int sizeArret() { return ARRET_STORAGE.size(); }
	
	public int sizeEnquete() { return ENQUETE_STORAGE.size(); }
	
	public int sizeEtablissement() { return ETABLISSEMENT_STORAGE.size(); }
	
	public int sizePosition() { return POSITION_STORAGE.size(); }
	
	public int sizeUtilisateur() { return UTILISATEUR_STORAGE.size(); }
	
	public int sizeUHE() { return UHE_STORAGE.size(); }
	
	public int sizeVehicule() { return VEHICULE_STORAGE.size(); }

	public int sizeSettings() { return SETTING_STORAGE.size(); }
	
	//////////////////////////////////////////////////////////////////////
	//// Update methods //////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	public void update(int id, Arret arret) { ARRET_STORAGE.update(id, arret); }

	public void update(int id, Enquete enquete) { ENQUETE_STORAGE.update(id, enquete); }

	public void update(int id, Etablissement etablissement) { ETABLISSEMENT_STORAGE.update(id, etablissement); }

	public void update(int id, Position position) { POSITION_STORAGE.update(id, position); }

	public void update(int id, Utilisateur utilisateur) { UTILISATEUR_STORAGE.update(id, utilisateur); }

	public void update(int id, UtilisateurHasEtablissement utilisateurHasEtablissement) { UHE_STORAGE.update(id, utilisateurHasEtablissement); }

	public void update(int id, Vehicule vehicule) { VEHICULE_STORAGE.update(id, vehicule); }

	public void update(int id, Settings settings) { SETTING_STORAGE.update(id, settings);}
	
	//////////////////////////////////////////////////////////////////////
	//// Delete methods //////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	public void deleteArret(int id) { ARRET_STORAGE.delete(id); }

	public void deleteEnquete(int id) { ENQUETE_STORAGE.delete(id); }

	public void deleteEtablissement(int id) { ETABLISSEMENT_STORAGE.delete(id); }

	public void deletePosition(int id) { POSITION_STORAGE.delete(id); }

	public void deleteUtilisateur(int id) { UTILISATEUR_STORAGE.delete(id); }

	public void deleteUHE(int id) { UHE_STORAGE.delete(id); }

	public void deleteVehicule(int id) { VEHICULE_STORAGE.delete(id); }

	public void deleteSettings(int id) { SETTING_STORAGE.delete(id);}

	public void deleteAllArret(){
		List<Arret> arrets = findAllArret();
		for(int i = 0;i<arrets.size();i++){
			deleteArret(arrets.get(i).getId());
		}
	}

	public void deleteAllEnquete(){
		List<Enquete> enquetes = findAllEnquete();
		for(int i = 0;i<enquetes.size();i++){
			deleteEnquete(enquetes.get(i).getId());
		}
	}

	public void deleteAllPosition(){
		List<Position> positions = findAllPosition();
		for(int i = 0;i<positions.size();i++){
			deletePosition(positions.get(i).getId());
		}
	}

	public void deleteAllVehicule(){
		List<Vehicule> vehicules = findAllVehicule();
		for(int i = 0;i<vehicules.size();i++){
			deleteVehicule(vehicules.get(i).getId());
		}
	}

	public void deleteAllEtablissement(){
		List<Etablissement> etablissements = findAllEtablissement();
		for(int i = 0;i<etablissements.size();i++){
			deleteEtablissement(etablissements.get(i).getId());
		}
	}


}
