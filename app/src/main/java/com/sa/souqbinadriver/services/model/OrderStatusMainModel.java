package com.sa.souqbinadriver.services.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class OrderStatusMainModel implements Serializable {
    private final String TAG = "OrderMainModel";
    private final String
            RESPONSE            = "response",
            STATUS              = "status_bool",
            MESSAGE             = "status";

    OrderStatusListModel
            orderStatusListModel      = null;

    String message = null;
    boolean isStatus=false;

    public OrderStatusMainModel(){}

    public OrderStatusListModel getOrderStatusListModel() {
        return orderStatusListModel;
    }

    public void setOrderStatusListModel(OrderStatusListModel orderStatusListModel) {
        this.orderStatusListModel = orderStatusListModel;
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
                OrderStatusListModel orderDetailListModel = new OrderStatusListModel();
                JSONArray jsonArray = new JSONArray();
                jsonArray = json.getJSONArray(RESPONSE);
                if(jsonArray!=null){orderDetailListModel.toObject(jsonArray);}
                this.orderStatusListModel = orderDetailListModel;
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
            jsonMain.put(RESPONSE, this.orderStatusListModel != null? new JSONArray(this.orderStatusListModel.toString(true)) : new JSONArray());

            returnString = jsonMain.toString();
        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }
}
