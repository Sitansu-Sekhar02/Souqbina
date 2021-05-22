package com.sa.souqbinadriver.services.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

public class DriverTaskModel implements Serializable {
    private final String TAG = "AddressModel";
    private final String
            ID                             = "id",
            BOOKING_ID                     = "booking_id",
            DRIVER_ID                      = "driver_id",
            BOOKING_TIME                    = "booking_time",
            T_THERAPISTS_NAME               = "t_therapists_name",
            T_MOBILE_NO                     = "t_mobile_number",
            T_IMAGE                          = "t_image",
            ADDRESS                          = "address",
            CUSTOMER_NAME                    = "customer_name",
            BOOKING_DATE                    = "booking_date",
            STATUS                          = "status",
            THERAPISTS_ID                   = "therapist_id",
            DRIVER_FIRST_NAME                = "driver_first_name",
            DRIVER_LAST_NAME                 = "driver_last_name",
            THERAPISTS_PICKUP_TIME           = "therapist_pickup_time",
            START_LATITUDE                    = "start_latitude",
            START_LONGITUDE                   = "start_longitude",
            CURRENT_LATITUDE                  = "current_latitude",
            CURRENT_LONGITUDE                 = "current_longitude",
            THERAPISTS_LATITUDE               = "therapists_latitude",
            THERAPISTS_LONGITUDE              = "therapists_longitude",
            END_LATITUDE                       = "end_latitude",
            END_LONGITUDE                      = "end_longitude";

    String
            id                  = null,
            booking_id           = null,
            driver_id           =null,
            booking_time        =null,
            t_therapists_name   = null,
            t_mobile_number      =null,
            t_image              = null,
            address              = null,
            customer_name        = null,
            booking_date         = null,
            status               = null,
            therapist_id         = null,
            driver_first_name    =null,
            driver_last_name     =null,
            therapist_pickup_time=null,
            start_latitude       =null,
            start_longitude      =null,
            current_latitude     =null,
            current_longitude    =null,
            therapists_latitude   =null,
            therapists_longitude  =null,
            end_latitude          =null,
            end_longitude          =null;


    public DriverTaskModel(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getBooking_time() {
        return booking_time;
    }

    public void setBooking_time(String booking_time) {
        this.booking_time = booking_time;
    }

    public String getT_therapists_name() {
        return t_therapists_name;
    }

    public void setT_therapists_name(String t_therapists_name) {
        this.t_therapists_name = t_therapists_name;
    }

    public String getT_mobile_number() {
        return t_mobile_number;
    }

    public void setT_mobile_number(String t_mobile_number) {
        this.t_mobile_number = t_mobile_number;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getTherapist_id() {
        return therapist_id;
    }

    public void setTherapist_id(String therapist_id) {
        this.therapist_id = therapist_id;
    }

    public String getT_image() {
        return t_image;
    }

    public void setT_image(String t_image) {
        this.t_image = t_image;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(String booking_date) {
        this.booking_date = booking_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDriver_first_name() {
        return driver_first_name;
    }

    public void setDriver_first_name(String driver_first_name) {
        this.driver_first_name = driver_first_name;
    }

    public String getDriver_last_name() {
        return driver_last_name;
    }

    public void setDriver_last_name(String driver_last_name) {
        this.driver_last_name = driver_last_name;
    }

    public String getTherapist_pickup_time() {
        return therapist_pickup_time;
    }

    public void setTherapist_pickup_time(String therapist_pickup_time) {
        this.therapist_pickup_time = therapist_pickup_time;
    }

    public String getStart_latitude() {
        return start_latitude;
    }

    public void setStart_latitude(String start_latitude) {
        this.start_latitude = start_latitude;
    }

    public String getStart_longitude() {
        return start_longitude;
    }

    public void setStart_longitude(String start_longitude) {
        this.start_longitude = start_longitude;
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

    public String getTherapists_latitude() {
        return therapists_latitude;
    }

    public void setTherapists_latitude(String therapists_latitude) {
        this.therapists_latitude = therapists_latitude;
    }

    public String getTherapists_longitude() {
        return therapists_longitude;
    }

    public void setTherapists_longitude(String therapists_longitude) {
        this.therapists_longitude = therapists_longitude;
    }

    public String getEnd_latitude() {
        return end_latitude;
    }

    public void setEnd_latitude(String end_latitude) {
        this.end_latitude = end_latitude;
    }

    public String getEnd_longitude() {
        return end_longitude;
    }

    public void setEnd_longitude(String end_longitude) {
        this.end_longitude = end_longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean toObject(String jsonObject){
        try{
            JSONObject json = new JSONObject(jsonObject);
            if(json.has(ID))id = json.getString(ID);
            if(json.has(BOOKING_ID)) booking_id = json.getString(BOOKING_ID);
            if(json.has(DRIVER_ID))driver_id = json.getString(DRIVER_ID);
            if(json.has(BOOKING_TIME))booking_time = json.getString(BOOKING_TIME);
            if(json.has(T_THERAPISTS_NAME))t_therapists_name = json.getString(T_THERAPISTS_NAME);
            if(json.has(T_MOBILE_NO))t_mobile_number = json.getString(T_MOBILE_NO);
            if(json.has(T_IMAGE))t_image = json.getString(T_IMAGE);
            if(json.has(BOOKING_DATE))booking_date = json.getString(BOOKING_DATE);
            if(json.has(STATUS))status = json.getString(STATUS);
            if(json.has(DRIVER_FIRST_NAME))driver_first_name = json.getString(DRIVER_FIRST_NAME);
            if(json.has(DRIVER_LAST_NAME))driver_last_name = json.getString(DRIVER_LAST_NAME);
            if(json.has(THERAPISTS_PICKUP_TIME))therapist_pickup_time = json.getString(THERAPISTS_PICKUP_TIME);
            if(json.has(START_LATITUDE))start_latitude = json.getString(START_LATITUDE);
            if(json.has(START_LONGITUDE))start_longitude = json.getString(START_LONGITUDE);
            if(json.has(CURRENT_LATITUDE))current_latitude = json.getString(CURRENT_LATITUDE);
            if(json.has(CURRENT_LONGITUDE))current_longitude = json.getString(CURRENT_LONGITUDE);
            if(json.has(THERAPISTS_LATITUDE))therapists_latitude = json.getString(THERAPISTS_LATITUDE);
            if(json.has(THERAPISTS_LONGITUDE))therapists_longitude = json.getString(THERAPISTS_LONGITUDE);
            if(json.has(END_LATITUDE))end_latitude = json.getString(END_LATITUDE);
            if(json.has(END_LONGITUDE))end_longitude = json.getString(END_LONGITUDE);
            if(json.has(THERAPISTS_ID))therapist_id = json.getString(THERAPISTS_ID);
            if(json.has(CUSTOMER_NAME))customer_name = json.getString(CUSTOMER_NAME);
            if(json.has(ADDRESS))address = json.getString(ADDRESS);
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
            jsonMain.put(BOOKING_ID, booking_id);
            jsonMain.put(DRIVER_ID, driver_id);
            jsonMain.put(BOOKING_TIME, booking_time);
            jsonMain.put(T_THERAPISTS_NAME, t_therapists_name);
            jsonMain.put(T_MOBILE_NO, t_mobile_number);
            jsonMain.put(T_IMAGE, t_image);
            jsonMain.put(BOOKING_DATE, booking_date);
            jsonMain.put(STATUS, status);
            jsonMain.put(DRIVER_FIRST_NAME, driver_first_name);
            jsonMain.put(DRIVER_LAST_NAME, driver_last_name);
            jsonMain.put(THERAPISTS_PICKUP_TIME, therapist_pickup_time);
            jsonMain.put(START_LATITUDE, start_latitude+"");
            jsonMain.put(START_LONGITUDE, start_longitude+"");
            jsonMain.put(CURRENT_LATITUDE, current_latitude+"");
            jsonMain.put(CURRENT_LONGITUDE, current_longitude+"");
            jsonMain.put(THERAPISTS_LATITUDE, therapists_latitude+"");
            jsonMain.put(THERAPISTS_LONGITUDE, therapists_longitude+"");
            jsonMain.put(END_LATITUDE, end_latitude);
            jsonMain.put(END_LONGITUDE, end_longitude);
            jsonMain.put(THERAPISTS_ID, therapist_id);
            jsonMain.put(CUSTOMER_NAME, customer_name);
            jsonMain.put(ADDRESS, address);
            returnString = jsonMain.toString();
        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }
}
