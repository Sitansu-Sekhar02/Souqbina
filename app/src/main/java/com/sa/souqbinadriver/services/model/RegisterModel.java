package com.sa.souqbinadriver.services.model;

import android.util.Log;


import com.sa.souqbinadriver.global.GlobalVariables;

import org.json.JSONObject;

import java.io.Serializable;

public class RegisterModel implements Serializable {

    private final String TAG = "RegisterModel";

    private final String
            FULL_NAME       = "full_name",
            FIRST_NAME      = "first_name",
            LAST_NAME       = "last_name",
            PASSWORD        = "password",
            EMAIL           = "email_id",
            IMAGE           = "image",
//            DOB             = "dob",
            GENDER          = "gender",
            CITY_ID         = "city_id",
            COUNTRY_CODE    = "country_code",
            PROVIDER_TOKEN  = "provider_token",
            PROVIDER        = "provider",
            SYSTEM_INFO     = "system_info",
            UUID            = "uuid",
            PHONE           = "mobile_number",
            CATEGORY        = "category";

    private String
            fullName        = null,
            firstName       = null,
            lastName        = null,
            password        = null,
            phone           = null,
            email           = null,
            mobileNumber    = null,
            systemInfo      = null,
            image           = null,
            providerToken   = null,
            uuid            = null,
            city_id         = null,
            countryCode     = null;



    private GlobalVariables.PROVIDERS
            provider        = null;

    private GlobalVariables.GENDER
            gender          = null;

   /* private long
            dob             = 0;*/

    public RegisterModel(){}

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSystemInfo() {
        return systemInfo;
    }

    public void setSystemInfo(String systemInfo) {
        this.systemInfo = systemInfo;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProviderToken() {
        return providerToken;
    }

    public void setProviderToken(String providerToken) {
        this.providerToken = providerToken;
    }

    public GlobalVariables.PROVIDERS getProvider() {
        return provider;
    }

    public void setProvider(GlobalVariables.PROVIDERS provider) {
        this.provider = provider;
    }

    public GlobalVariables.GENDER getGender() {
        return gender;
    }

    public void setGender(GlobalVariables.GENDER gender) {
        this.gender = gender;
    }



    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /*public long getDob() {
        return dob;
    }

    public void setDob(long dob) {
        this.dob = dob;
    }*/

    public boolean toObject(String jsonObject){
        try{
            JSONObject json = new JSONObject(jsonObject);

            this.systemInfo = json.getString(SYSTEM_INFO);
            this.uuid = json.getString(UUID);

            if(json.has(PASSWORD))password = json.getString(PASSWORD);
            if(json.has(FULL_NAME))fullName = json.getString(FULL_NAME);
            if(json.has(FIRST_NAME))firstName = json.getString(FIRST_NAME);
            if(json.has(LAST_NAME))lastName = json.getString(LAST_NAME);
            if(json.has(PHONE))phone = json.getString(PHONE);
            if(json.has(EMAIL))email = json.getString(EMAIL);
            if(json.has(IMAGE))image = json.getString(IMAGE);
            if(json.has(CITY_ID))city_id = json.getString(CITY_ID);
            if(json.has(COUNTRY_CODE))countryCode = json.getString(COUNTRY_CODE);

            /*if(json.has(DOB)){
                try{ this.dob = json.getLong(DOB); }
                catch (Exception exxxex){ this.dob = 0; }
            }*/

            if(json.has(GENDER)){
                try{
                    String genderLocal = json.getString(GENDER);
                    this.gender = GlobalVariables.GENDER.valueOf(genderLocal);
                }
                catch (Exception exxxex){ this.gender = GlobalVariables.GENDER.MALE; }
            }

            if(json.has(PROVIDER)){
                try{
                    String providerLocal = json.getString(PROVIDER);
                    this.provider = GlobalVariables.PROVIDERS.valueOf(providerLocal);
                }
                catch (Exception exxxex){ this.provider = GlobalVariables.PROVIDERS.ADMIN; }
            }

            if(json.has(PROVIDER_TOKEN))providerToken = json.getString(PROVIDER_TOKEN);



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
            jsonMain.put(FULL_NAME, fullName);
            jsonMain.put(FIRST_NAME, firstName);
            jsonMain.put(LAST_NAME, lastName);
            jsonMain.put(PASSWORD, password);
            jsonMain.put(PHONE, phone);
            jsonMain.put(EMAIL, email);
            jsonMain.put(IMAGE, image);
//            jsonMain.put(DOB, this.dob);
            jsonMain.put(GENDER, this.gender);
//            jsonMain.put(GENDER, this.gender.toInt());
            jsonMain.put(PROVIDER, this.provider);
//            jsonMain.put(PROVIDER, this.provider.toString());
            jsonMain.put(PROVIDER_TOKEN, this.providerToken);
            jsonMain.put(SYSTEM_INFO, this.systemInfo);
            jsonMain.put(UUID, this.uuid);
            jsonMain.put(CITY_ID, this.city_id);
            jsonMain.put(COUNTRY_CODE, this.countryCode);
            //jsonMain.put(CATEGORY, categoryListModel != null ? new JSONArray(categoryListModel.toString(true)) : new JSONArray());
            returnString = jsonMain.toString();
        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }
}
