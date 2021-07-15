package com.sa.souqbinadriver.services.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class UtilityMainModel implements Serializable {
    private final String TAG = "UtilityMainModel";
    private final String
            RESPONSE            = "response",
            STATUS              = "status_bool",
            MESSAGE             = "status";

    UtilityModel
            utilityModel      = null;


    String message = null;
    boolean isStatus=false;

    public UtilityMainModel(){}

    public UtilityModel getUtilityModel() {
        return utilityModel;
    }

    public void setUtilityModel(UtilityModel utilityModel) {
        this.utilityModel = utilityModel;
    }

    public boolean isStatus() {
        return isStatus;
    }

    public void setStatus(boolean status) {
        isStatus = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean toObject(String jsonObjectString){
        try{
            JSONObject json = new JSONObject(jsonObjectString);
            if(json.has(MESSAGE)){this.message = json.getString(MESSAGE);}
            if(json.has(STATUS)){this.isStatus = json.getBoolean(STATUS);}


            if(json.has(RESPONSE)){
                UtilityModel utilityModel1 = new UtilityModel();
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1 = json.getJSONObject(RESPONSE);
                if(jsonObject1 != null){utilityModel1.toObject(jsonObject1.toString());}
                utilityModel = utilityModel1;
            }



            return true;
        }catch(Exception ex){
            Log.d(TAG, "Json Exception : " + ex);}
        return false;
    }

    @Override
    public String toString(){
        String returnString = null;
        try{
            JSONObject jsonMain = new JSONObject();
            jsonMain.put(STATUS, isStatus);
            jsonMain.put(MESSAGE, message);
            jsonMain.put(RESPONSE, utilityModel != null ? new JSONObject(this.utilityModel.toString()) : new JSONObject());


            returnString = jsonMain.toString();
        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }
}
