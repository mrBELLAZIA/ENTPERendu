package com.example.entpe.storage.utility;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class JSONFileStorage<T> extends FileStorage<T> {
	///////////////////////////////////////////////////////////////////////////////////////////////
	// Constants //////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	private static final String EXTENSION = ".json";
	private static final String LIST = "list";
	private static final String NEXT_ID = "next_id";
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	// Attributes /////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	protected JSONObject json;
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	// Constructors ///////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Main constructor
	 * @param name - The file's name
	 */
	public JSONFileStorage(Context context, String name) { super(context, name, EXTENSION); }
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	// Methods ////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	//// Overriden methods ///////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	////// Storage methods //////////////////////
	/////////////////////////////////////////////
	@Override
	@Nullable
	public T find(int id) {
		T object = null;
		
		try { object = jsonObjectToObject(json.getJSONObject(LIST).getJSONObject(String.valueOf(id))); }
		catch(JSONException e) { e.printStackTrace(); }
		
		return object;
	}

	@Override
	@NonNull
	public List<T> findAll() {
		ArrayList<T> list = new ArrayList<>();

        try {
            Iterator<String> iterator = json.getJSONObject(LIST).keys();
            while (iterator.hasNext()) { list.add(jsonObjectToObject(json.getJSONObject(LIST).getJSONObject(iterator.next()))); }
        }
        catch (JSONException e) {
            e.printStackTrace();
            list = new ArrayList<>();
        }

        return list;
	}

	@Override
	public int size() {
		int size = 0;

        try { size = json.getJSONObject(LIST).length(); }
        catch (JSONException e) { e.printStackTrace(); }

        return size;
	}

	@Override
	public void insert(@NonNull T newObject) {
		int nextId = json.optInt(NEXT_ID);
        
        try {
            json.getJSONObject(LIST).put(String.valueOf(nextId), objectToJsonObject(nextId, newObject));
            json.put(NEXT_ID, nextId + 1);
        } catch (JSONException e) { e.printStackTrace(); }

        write();
	}

	@Override
	public void update(int id, @NonNull T updatedObject) {
		try { json.getJSONObject(LIST).put(String.valueOf(id), objectToJsonObject(id, updatedObject)); }
        catch (JSONException e) { e.printStackTrace(); }

        write();
	}

	@Override
	public void delete(int id) {
		try { json.getJSONObject(LIST).remove(String.valueOf(id)); }
        catch (JSONException e) { e.printStackTrace(); }

		write();
	}

	/////////////////////////////////////////////
	////// FileStorage methods //////////////////
	/////////////////////////////////////////////
	@Override
	/**
	 * Creates a new JSON Object
	 */
	protected void create() {
		json = new JSONObject();
		
		try {
			json.put(LIST, new JSONObject());
			json.put(NEXT_ID, 1);
		} catch(JSONException e) { e.printStackTrace(); }
	}
	
	@Override
	/**
	 * Initializes a JSON Object
	 * @param value - The object's value
	 */
	protected void initialize(String value) {
		try { json = new JSONObject(value); }
		catch(JSONException e) { e.printStackTrace(); }
	}
	
	@Override
	/**
	 * Get a JSON Object's value
	 * @return the value
	 */
	protected String getValue() { return json.toString(); }
	
	//////////////////////////////////////////////////////////////////////
	//// Abstract methods ////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	@NonNull
	protected abstract JSONObject objectToJsonObject(int id, T object);

	@Nullable
	protected abstract T jsonObjectToObject(JSONObject jsonObject);
}
