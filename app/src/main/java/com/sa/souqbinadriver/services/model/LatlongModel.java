package com.sa.souqbinadriver.services.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

public class LatlongModel implements Serializable {
    private final String TAG = "LatlongModel";

    private final String
            ORDER_VENDOR_PRODUCT_ID = "order_vendor_product_id",
            LATITUDE = "latitude",
            LONGITUDE = "longitude";


    private String
            order_vendor_product_id   =null,
            latitude                  = null,
            longitude                 = null;



    public LatlongModel(){
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

    public String getOrder_vendor_product_id() {
        return order_vendor_product_id;
    }

    public void setOrder_vendor_product_id(String order_vendor_product_id) {
        this.order_vendor_product_id = order_vendor_product_id;
    }

    public boolean toObject(String jsonObject){
        try{
            JSONObject json = new JSONObject(jsonObject);
            if(json.has(LATITUDE))latitude = json.getString(LATITUDE);
            if(json.has(LONGITUDE))longitude = json.getString(LONGITUDE);
            if(json.has(ORDER_VENDOR_PRODUCT_ID))order_vendor_product_id = json.getString(ORDER_VENDOR_PRODUCT_ID);


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
            jsonMain.put(LATITUDE, latitude);
            jsonMain.put(LONGITUDE, longitude);
            jsonMain.put(ORDER_VENDOR_PRODUCT_ID, order_vendor_product_id);

            returnString = jsonMain.toString();
        }
        catch (Exception ex){Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }
}
