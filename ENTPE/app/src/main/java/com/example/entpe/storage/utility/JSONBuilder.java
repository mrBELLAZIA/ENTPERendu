package com.example.entpe.storage.utility;

import com.example.entpe.model.Arret;
import com.example.entpe.model.Enquete;
import com.example.entpe.model.JSONable;
import com.example.entpe.model.Position;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

public class JSONBuilder {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constants //////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private static final String ARRETS = "arrets";
    private static final String POSITIONS = "positions";

    public static final String FILE_NAME = "Enquete.json";

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Writes into the file
     */
    public static void writeToFile(String payload) {
        try {
            File file = new File(FILE_NAME);
            if(file.delete()) System.out.println(FILE_NAME + " already existed, was deleted");
            FileChannel channel = new FileOutputStream(file).getChannel();
            channel.write(ByteBuffer.wrap(payload.getBytes()));
        } catch(Exception e) { e.printStackTrace(); }
    }

    @SuppressWarnings("unchecked")
    public static JSONObject parseRow(Enquete enquete, List<Arret> arrets, List<Position> positions) {
        JSONObject jsonObject = enquete.toJSONObject();

        try {
            assert jsonObject != null;
            jsonObject.put(ARRETS, toJSONArray((List<JSONable>)(List<?>) arrets));
            jsonObject.put(POSITIONS, toJSONArray((List<JSONable>)(List<?>) positions));
        } catch(JSONException e) { e.printStackTrace(); }

        return jsonObject;
    }

    private static JSONArray toJSONArray(List<JSONable> list) {
        JSONArray jsonArray = new JSONArray();

        for(JSONable object : list) { jsonArray.put(object.toJSONObject()); }

        return jsonArray;
    }
}
