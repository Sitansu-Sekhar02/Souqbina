package com.sa.souqbinadriver.services.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

public class SearchModel implements Serializable {

    private final String TAG = "SearchModel";

    private final String PHRASE = "phrase";
    private final String KEY_PHRASE = "keyphrase";

    String
            phrase = null,
            keyphrase = null;

    public SearchModel(){}

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getKeyphrase() {
        return keyphrase;
    }

    public void setKeyphrase(String keyphrase) {
        this.keyphrase = keyphrase;
    }

    public boolean toObject(String jsonObjectString){
        try{
            JSONObject json = new JSONObject(jsonObjectString);
            phrase = json.getString(PHRASE);
            keyphrase = json.getString(KEY_PHRASE);
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
            jsonMain.put(PHRASE, phrase);
            jsonMain.put(KEY_PHRASE, keyphrase);
            returnString = jsonMain.toString();
        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }

}
