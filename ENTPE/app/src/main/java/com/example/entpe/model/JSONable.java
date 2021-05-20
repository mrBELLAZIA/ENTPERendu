package com.example.entpe.model;

import org.json.JSONObject;

public interface JSONable {
    void fromJSONObject(JSONObject object);
    JSONObject toJSONObject();
}
