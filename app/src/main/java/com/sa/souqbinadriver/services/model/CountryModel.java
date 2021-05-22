package com.sa.souqbinadriver.services.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Sivasabharish on 12/4/2017.
 */

public class CountryModel implements Serializable {
    private final String TAG = "CityModel";

    private final String
            ID                  = "id",
            NAME                = "name",
            CURRENCY            = "cureancy",
            COUNTRY_CODE        = "country_code",
            CONTACT_LENGTH      = "contact_length",
            CITY_LIST           = "city";

    String
            id                  = null,
            name                = null,
            currency            = null,
            countryCode         = null,
            contactLength       = null;

    CityListModel
            cityList         = null;

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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getContactLength() {
        return contactLength;
    }

    public void setContactLength(String contactLength) {
        this.contactLength = contactLength;
    }

    public CityListModel getCityList() {
        return cityList;
    }

    public void setCityList(CityListModel cityList) {
        this.cityList = cityList;
    }

    public boolean toObject(String jsonObject){
        try{
            JSONObject json = new JSONObject(jsonObject);
            this.id = json.getString(ID);
            if(json.has(NAME)){this.name = json.getString(NAME);}
            if(json.has(CURRENCY)){this.currency = json.getString(CURRENCY);}
            if(json.has(COUNTRY_CODE)){this.countryCode = json.getString(COUNTRY_CODE);}
            if(json.has(CONTACT_LENGTH)){this.contactLength = json.getString(CONTACT_LENGTH);}

            if(json.has(CITY_LIST)) {
                JSONArray timeSlotArray = json.getJSONArray(CITY_LIST);
                CityListModel cityListModel = new CityListModel();
                if (cityListModel.toObject(timeSlotArray)) {
                    this.cityList = cityListModel;
                } else {
                    this.cityList = null;
                }
            }

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
            jsonMain.put(CURRENCY, this.currency);
            jsonMain.put(COUNTRY_CODE, this.countryCode);
            jsonMain.put(CONTACT_LENGTH, this.contactLength);
            jsonMain.put(CITY_LIST, cityList!=null ? new JSONArray(cityList.toString(true)) : new JSONArray());

            returnString = jsonMain.toString();
        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }

}
