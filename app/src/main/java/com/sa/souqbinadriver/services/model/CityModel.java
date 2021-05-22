package com.sa.souqbinadriver.services.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Sivasabharish on 12/4/2017.
 */

public class CityModel implements Serializable {
    private final String TAG = "CityModel";

    private final String
            ID                  = "id",
            NAME                = "name",
            LATITUDE            = "latitude",
            LONGITUDE           = "longitude";

    String
            id                  = null,
            name                = null,
            latitude            = null,
            longitude           = null;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public boolean toObject(String jsonObject){
        try{
            JSONObject json = new JSONObject(jsonObject);
            this.id = json.getString(ID);
            if(json.has(NAME)){this.name = json.getString(NAME);}
//            if(json.has(IMAGE)){this.image = json.getString(IMAGE);}
            if(json.has(LATITUDE)){this.latitude = json.getString(LATITUDE);}
            if(json.has(LONGITUDE)){this.longitude = json.getString(LONGITUDE);}

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
            jsonMain.put(ID, this.id);
            jsonMain.put(NAME, this.name);
            jsonMain.put(LATITUDE, this.latitude);
            jsonMain.put(LONGITUDE, this.longitude);

            returnString = jsonMain.toString();
        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }

}
