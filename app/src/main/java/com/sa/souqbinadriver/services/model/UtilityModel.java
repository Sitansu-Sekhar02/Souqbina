package com.sa.souqbinadriver.services.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class UtilityModel implements Serializable {

    private final String TAG = "UtilityModel";

    private final String
            ID                  = "id",
            CONTACT_NO           = "contact_number",
            EMAIL_ID            = "email_id",
            APP_STORE           = "app_store",
            PLAY_STORE           = "play_store",
            CORPORATE_OFFICE1    = "corporate_number_1",
            CORPORATE_OFFICE2    = "corporate_number_2",
            FACEBOOK            = "facebook",
            INSTAGRAM           = "instagram",
            TWITTER             = "twitter",
            YOUTUBE             = "youtube",
            LINKEDIN            = "linkedin",
            ADDRESS             = "address",
            WEBSITE              = "website";

    private String
            id                  = null,
            contact_number      = null,
            email_id            = null,
            app_store           = null,
            play_store          = null,
            corporate_number_1  = null,
            corporate_number_2  = null,
            facebook            = null,
            instagram           = null,
            twitter             = null,
            youtube             = null,
            linkedin            = null,
            address             = null,
            website             = null;


    public UtilityModel(){}

    List<UtilityModel> statusList = new ArrayList<UtilityModel>();

    public List<UtilityModel> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<UtilityModel> statusList) {
        this.statusList = statusList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getApp_store() {
        return app_store;
    }

    public void setApp_store(String app_store) {
        this.app_store = app_store;
    }

    public String getPlay_store() {
        return play_store;
    }

    public void setPlay_store(String play_store) {
        this.play_store = play_store;
    }

    public String getCorporate_number_1() {
        return corporate_number_1;
    }

    public void setCorporate_number_1(String corporate_number_1) {
        this.corporate_number_1 = corporate_number_1;
    }

    public String getCorporate_number_2() {
        return corporate_number_2;
    }

    public void setCorporate_number_2(String corporate_number_2) {
        this.corporate_number_2 = corporate_number_2;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public boolean toObject(String jsonObject){
        try{
            JSONObject json = new JSONObject(jsonObject);
            if(json.has(ID))this.id = json.getString(ID);
            if(json.has(CONTACT_NO))this.contact_number = json.getString(CONTACT_NO);
            if(json.has(EMAIL_ID))this.email_id = json.getString(EMAIL_ID);
            if(json.has(APP_STORE))this.app_store = json.getString(APP_STORE);
            if(json.has(PLAY_STORE))this.play_store = json.getString(PLAY_STORE);
            if(json.has(CORPORATE_OFFICE1))this.corporate_number_1 = json.getString(CORPORATE_OFFICE1);
            if(json.has(CORPORATE_OFFICE2))this.corporate_number_2 = json.getString(CORPORATE_OFFICE2);
            if(json.has(FACEBOOK))this.facebook = json.getString(FACEBOOK);
            if(json.has(INSTAGRAM))this.instagram = json.getString(INSTAGRAM);
            if(json.has(TWITTER))this.twitter = json.getString(TWITTER);
            if(json.has(YOUTUBE))this.youtube = json.getString(YOUTUBE);
            if(json.has(LINKEDIN))this.linkedin = json.getString(LINKEDIN);
            if(json.has(ADDRESS))this.address = json.getString(ADDRESS);
            if(json.has(WEBSITE))this.website = json.getString(WEBSITE);


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
            jsonMain.put(CONTACT_NO, contact_number);
            jsonMain.put(EMAIL_ID, email_id);
            jsonMain.put(APP_STORE, app_store);
            jsonMain.put(PLAY_STORE, play_store);
            jsonMain.put(CORPORATE_OFFICE1, corporate_number_1);
            jsonMain.put(CORPORATE_OFFICE2, corporate_number_2);
            jsonMain.put(FACEBOOK, facebook);
            jsonMain.put(INSTAGRAM, instagram);
            jsonMain.put(TWITTER, twitter);
            jsonMain.put(YOUTUBE, youtube);
            jsonMain.put(LINKEDIN, linkedin);
            jsonMain.put(ADDRESS, address);
            jsonMain.put(WEBSITE, website);

            returnString = jsonMain.toString();
        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }

}
