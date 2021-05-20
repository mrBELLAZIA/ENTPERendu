package com.example.entpe.storage.utility;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public abstract class FileStorage<T> implements Storage<T> {
	///////////////////////////////////////////////////////////////////////////////////////////////
	// Attributes /////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	private Context context;
	private String fileName;
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	// Constructors ///////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Main constructor
	 * @param context - The context
	 * @param name - The file's name
	 * @param extension - The file's extension
	 */
	public FileStorage(Context context, String name, String extension) {
		this.context = context;
		this.fileName = name + extension;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	// Methods ////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	//// Defined methods /////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	/**
	 * Reads the file
	 */
	protected void read() {
		try {
			FileInputStream in = context.openFileInput(fileName);

			if(in != null) {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
				String temp;
				StringBuilder builder = new StringBuilder();
				
				while((temp = bufferedReader.readLine()) != null) { builder.append(temp); }
				
				in.close();
				initialize(builder.toString());
			}
		} catch(FileNotFoundException e) {
			create();
			write();
		} catch(Exception e) { e.printStackTrace(); }
	}
	
	/**
	 * Writes into the file
	 */
	protected void write() {
		try {
			FileOutputStream out = context.openFileOutput(fileName, Context.MODE_PRIVATE);
			OutputStreamWriter writer = new OutputStreamWriter(out);
			writer.write(getValue());
			writer.close();
		} catch(Exception e) { e.printStackTrace(); }
	}
	
	//////////////////////////////////////////////////////////////////////
	//// Abstract methods ////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	protected abstract void create();
	
	protected abstract void initialize(String s);
	
	protected abstract String getValue();
}
