package com.sa.souqbinadriver.services.model;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.hbb20.CountryCodePicker;
import com.sa.souqbinadriver.Activity.AppController;
import com.sa.souqbinadriver.Activity.MainActivity;
import com.sa.souqbinadriver.R;
import com.sa.souqbinadriver.global.GlobalFunctions;
import com.sa.souqbinadriver.global.GlobalVariables;
import com.sa.souqbinadriver.services.ServerResponseInterface;
import com.sa.souqbinadriver.services.ServicesMethodsManager;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CreatePlanModel implements Serializable {
    private final String TAG = "CreatePlanModel";

    private final String
            PLAN_TYPE       = "plan_type",
            CAR_TYPE        = "car_type",
            CAR_COLOR       = "car_color",
            FLAT_NUMBER     = "flatNumber",
            PARKING_NUMBER  = "parkingNumber",
//            SESSION_Type    = "session_type",
//            TIME_SLOT       = "time_slot",
            CAR_REG_NUMBER  = "carReg_number",
            SLOT            = "slot";

    KeyValueModel
            carType         = null,
            carColor        = null,
            planType        = null,
//            sessionType     = null,
            timeSlotType    = null;

    private String
            carRegNumber    = null,
            flatNumber      = null,
            parkingNumber   = null,
            slot            = null;

    public KeyValueModel getCarType() {
        return carType;
    }

    public void setCarType(KeyValueModel carType) {
        this.carType = carType;
    }

    public KeyValueModel getCarColor() {
        return carColor;
    }

    public void setCarColor(KeyValueModel carColor) {
        this.carColor = carColor;
    }

    public KeyValueModel getPlanType() {
        return planType;
    }

    public void setPlanType(KeyValueModel planType) {
        this.planType = planType;
    }

//    public KeyValueModel getSessionType() {
//        return sessionType;
//    }

//    public void setSessionType(KeyValueModel sessionType) {
//        this.sessionType = sessionType;
//    }

    public KeyValueModel getTimeSlotType() {
        return timeSlotType;
    }

    public void setTimeSlotType(KeyValueModel timeSlotType) {
        this.timeSlotType = timeSlotType;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public String getParkingNumber() {
        return parkingNumber;
    }

    public void setParkingNumber(String parkingNumber) {
        this.parkingNumber = parkingNumber;
    }

    public String getCarRegNumber() {
        return carRegNumber;
    }

    public void setCarRegNumber(String carRegNumber) {
        this.carRegNumber = carRegNumber;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public boolean toObject(String jsonObject){
        try{
            JSONObject json = new JSONObject(jsonObject);
            if(json.has(PLAN_TYPE)){
                JSONObject localObject = json.getJSONObject(PLAN_TYPE);
                KeyValueModel modalLocal = new KeyValueModel();
                if(modalLocal.toObject(localObject.toString())){
                   this.planType = modalLocal;
                }
            }
            if(json.has(CAR_TYPE)){
                JSONObject localObject = json.getJSONObject(CAR_TYPE);
                KeyValueModel modalLocal = new KeyValueModel();
                if(modalLocal.toObject(localObject.toString())){
                    this.carType = modalLocal;
                }
            }
            if(json.has(CAR_COLOR)){
                JSONObject localObject = json.getJSONObject(CAR_COLOR);
                KeyValueModel modalLocal = new KeyValueModel();
                if(modalLocal.toObject(localObject.toString())){
                    this.carColor = modalLocal;
                }
            }
           /* if(json.has(SESSION_Type)){
                JSONObject localObject = json.getJSONObject(SESSION_Type);
                KeyValueModel modalLocal = new KeyValueModel();
                if(modalLocal.toObject(localObject.toString())){
                    this.sessionType = modalLocal;
                }
            }*/
            /*if(json.has(TIME_SLOT)){
                JSONObject localObject = json.getJSONObject(TIME_SLOT);
                KeyValueModel modalLocal = new KeyValueModel();
                if(modalLocal.toObject(localObject.toString())){
                    this.timeSlotType = modalLocal;
                }
            }*/

            if(json.has(CAR_REG_NUMBER))carRegNumber = json.getString(CAR_REG_NUMBER);
            if(json.has(FLAT_NUMBER))flatNumber = json.getString(FLAT_NUMBER);
            if(json.has(PARKING_NUMBER))parkingNumber = json.getString(PARKING_NUMBER);
            if(json.has(SLOT))slot = json.getString(SLOT);

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

            jsonMain.put(CAR_REG_NUMBER, carRegNumber);
            jsonMain.put(FLAT_NUMBER, flatNumber);
            jsonMain.put(PARKING_NUMBER, parkingNumber);
            jsonMain.put(SLOT, slot);

            jsonMain.put(CAR_TYPE, carType!=null?new JSONObject(carType.toString()):new JSONObject());
            jsonMain.put(PLAN_TYPE, planType!=null?new JSONObject(planType.toString()):new JSONObject());
            jsonMain.put(CAR_COLOR, carColor!=null?new JSONObject(carColor.toString()):new JSONObject());
//            jsonMain.put(TIME_SLOT, timeSlotType!=null?new JSONObject(timeSlotType.toString()):new JSONObject());            jsonMain.put(TIME_SLOT, timeSlotType!=null?new JSONObject(timeSlotType.toString()):new JSONObject());
//            jsonMain.put(SESSION_Type, sessionType!=null?new JSONObject(sessionType.toString()):new JSONObject());

            returnString = jsonMain.toString();
        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }

}
