package com.sa.souqbinadriver.services.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class OrderStatusUpdateModel implements Serializable {

    private final String TAG = "OrderStatusUpdateModel";

    private final String
            ID              = "order_vendor_product_id",
            STATUS_TITLE    = "status";

    private String
            id             = null,
            status         = null;


    public OrderStatusUpdateModel(){}


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

    public boolean toObject(String jsonObject){
        try{
            JSONObject json = new JSONObject(jsonObject);
            if(json.has(STATUS_TITLE))this.status = json.getString(STATUS_TITLE);
            if(json.has(ID))this.id = json.getString(ID);


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
            jsonMain.put(STATUS_TITLE, status);

            returnString = jsonMain.toString();
        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }

}
