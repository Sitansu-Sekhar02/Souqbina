package com.sa.souqbinadriver.services.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DriverTaskListListModel implements Serializable {

    private final String TAG = "DriverTaskListListModel";

    private final String RESPONSE = "response";

    List<DriverTaskModel> driverTaskModels = new ArrayList<DriverTaskModel>();

    public DriverTaskListListModel(){}

    public List<DriverTaskModel> getDriverTaskModels() {
        return driverTaskModels;
    }

    public void setDriverTaskModels(List<DriverTaskModel> driverTaskModels) {
        this.driverTaskModels = driverTaskModels;
    }
/*
    public List<String> getNames(){
        List<String> list = new ArrayList<String>();
        for(int i =0 ;i<this.countryList.size(); i++){
            list.add(countryList.get(i).getName());
        }
        return list;
    }*/

    public boolean toObject(String jsonObjectString){
        try{
            JSONObject json = new JSONObject(jsonObjectString);
            JSONArray array = json.getJSONArray(RESPONSE);
            List<DriverTaskModel> list = new ArrayList<DriverTaskModel>();
            for (int i=0;i<array.length();i++){
                JSONObject jsonObject = array.getJSONObject(i);
                DriverTaskModel model = new DriverTaskModel();
                model.toObject(jsonObject.toString());
                list.add(model);
            }
            this.driverTaskModels = list;
            return true;
        }catch(Exception ex){
            Log.d(TAG, "Json Exception : " + ex);}
        return false;
    }

    public boolean toObject(JSONArray jsonArray){
        try{
            List<DriverTaskModel> list = new ArrayList<DriverTaskModel>();
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                DriverTaskModel model = new DriverTaskModel();
                model.toObject(jsonObject.toString());
                list.add(model);
            }
            this.driverTaskModels = list;
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
            JSONArray jsonArray = new JSONArray();
            List<DriverTaskModel> list = this.driverTaskModels;
            for(int i=0;i<list.size();i++){
                jsonArray.put(new JSONObject(list.get(i).toString()));
            }
            jsonMain.put(RESPONSE, jsonArray);
            returnString = jsonMain.toString();
        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }

    public String toString(boolean isArray){
        String returnString = null;
        try{
            JSONObject jsonMain = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            List<DriverTaskModel> list = this.driverTaskModels;
            for(int i=0;i<list.size();i++){
                jsonArray.put(new JSONObject(list.get(i).toString()));
            }
            if(!isArray){
                jsonMain.put(RESPONSE, jsonArray);
                returnString = jsonMain.toString();
            }else{
                returnString = jsonArray.toString();
            }

        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }
}
