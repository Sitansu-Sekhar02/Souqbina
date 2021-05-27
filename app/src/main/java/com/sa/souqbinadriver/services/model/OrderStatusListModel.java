package com.sa.souqbinadriver.services.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderStatusListModel implements Serializable {

    private final String TAG = "OrderStatusListModel";

    private final String RESPONSE = "order_status_history";

    List<OrderStatus> orderStatusList = new ArrayList<OrderStatus>();

    public OrderStatusListModel(){}

    public List<OrderStatus> getOrderStatusList() {
        return orderStatusList;
    }

    public void setOrderStatusList(List<OrderStatus> orderStatusList) {
        this.orderStatusList = orderStatusList;
    }

    public boolean toObject(String jsonObjectString){
        try{
            JSONObject json = new JSONObject(jsonObjectString);
            JSONArray array = json.getJSONArray(RESPONSE);
            List<OrderStatus> list = new ArrayList<OrderStatus>();
            for (int i=0;i<array.length();i++){
                JSONObject jsonObject = array.getJSONObject(i);
                OrderStatus keyValueModel = new OrderStatus();
                keyValueModel.toObject(jsonObject.toString());
                list.add(keyValueModel);
            }
            this.orderStatusList = list;
            return true;
        }catch(Exception ex){
            Log.d(TAG, "Json Exception : " + ex);}
        return false;
    }

    public boolean toObject(JSONArray jsonArray){
        try{
            List<OrderStatus> list = new ArrayList<OrderStatus>();
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                OrderStatus keyValueModel = new OrderStatus();
                keyValueModel.toObject(jsonObject.toString());
                list.add(keyValueModel);
            }
            this.orderStatusList = list;
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
            List<OrderStatus> list = this.orderStatusList;
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
            List<OrderStatus> list = this.orderStatusList;
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
