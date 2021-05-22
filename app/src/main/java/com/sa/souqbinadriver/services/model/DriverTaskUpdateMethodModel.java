package com.sa.souqbinadriver.services.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

public class DriverTaskUpdateMethodModel implements Serializable {
    private final String TAG = "DriverTaUpdeMetdModel";
    private final String
            ID                      = "id",
            STATUS                  = "status",
            CURRENT_LATITUDE        = "current_latitude",
            CURRENT_LONGITUDE       = "current_longitude";


    String
            id                    = null,
            status               = null,
            current_latitude     =null,
            current_longitude    =null;

    public DriverTaskUpdateMethodModel(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrent_latitude() {
        return current_latitude;
    }

    public void setCurrent_latitude(String current_latitude) {
        this.current_latitude = current_latitude;
    }

    public String getCurrent_longitude() {
        return current_longitude;
    }

    public void setCurrent_longitude(String current_longitude) {
        this.current_longitude = current_longitude;
    }

    public boolean toObject(String jsonObject){
        try{
            JSONObject json = new JSONObject(jsonObject);
            if(json.has(ID))id = json.getString(ID);
            if(json.has(STATUS)) status = json.getString(STATUS);
            if(json.has(CURRENT_LATITUDE))current_latitude = json.getString(CURRENT_LATITUDE);
            if(json.has(CURRENT_LONGITUDE))current_longitude = json.getString(CURRENT_LONGITUDE);
            return true;
        }catch(Exception ex){
            Log.d(TAG, "Json Exception : " + ex);
            return false;}

    }

    @Override
    public String toString(){
        String returnString = null;
        try{
            JSONObject jsonMain = new JSONObject();
            jsonMain.put(ID, id);
            jsonMain.put(STATUS, status);
            jsonMain.put(CURRENT_LATITUDE, current_latitude+"");
            jsonMain.put(CURRENT_LONGITUDE, current_longitude+"");

            returnString = jsonMain.toString();
        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }
}
