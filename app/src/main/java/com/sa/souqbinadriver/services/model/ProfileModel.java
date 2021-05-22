package com.sa.souqbinadriver.services.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class ProfileModel implements Serializable {
    private final String TAG = "ProfileModel";
    private final String
            ID = "id",
            FIRST_NAME = "first_name",
            FULL_NAME = "full_name",
            LAST_NAME = "last_name",
            TIN_NUMBER = "tin",
            PAN_NUMBER = "pan",
            IS_OTP_VERIFIED = "is_otp_verified",
            EMAIL = "email_id",
            AGENCY_NAME = "agency_name",
            PHONE = "mobile_number",
            ADDRESS = "address",
            IMAGE = "image",
            PROFILE_IMAGE = "profile_img",
            CITY = "city",
            CITY_ID = "city_id",
            CITY_NAME = "city_name",
            ORDER_COUNT = "order_count",
            CATEGORY = "category";

    String
            id = null,
            fullname = null,
            tinNumber = null,
            orderCount = null,
            panNumber = null,
            agencyName = null,
            firstName = null,
            lastName = null,
            email = null,
            phone = null,
            image = null,
            profileImg = null,
            cityId = null,
            cityName = null;

    AddressModel address = new AddressModel();

    CityListModel
            cities = null;

    boolean otp_verified = true;

    ProfileCategoryListModel
            profileCategoryListModel = null;

    public ProfileModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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

    public String getTinNumber() {
        return tinNumber;
    }

    public void setTinNumber(String tinNumber) {
        this.tinNumber = tinNumber;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public AddressModel getAddress() {
        return address;
    }

    public void setAddress(AddressModel address) {
        this.address = address;
    }

    public boolean isOtp_verified() {
        return otp_verified;
    }

    public void setOtp_verified(boolean otp_verified) {
        this.otp_verified = otp_verified;
    }

    public CityListModel getCities() {
        return cities;
    }

    public void setCities(CityListModel cities) {
        this.cities = cities;
    }

    public String getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public ProfileCategoryListModel getProfileCategoryListModel() {
        return profileCategoryListModel;
    }

    public void setProfileCategoryListModel(ProfileCategoryListModel profileCategoryListModel) {
        this.profileCategoryListModel = profileCategoryListModel;
    }

    public boolean toObject(String jsonObject) {
        try {
            JSONObject json = new JSONObject(jsonObject);
            if (json.has(ID)) {
                id = json.getString(ID);
            }

            if (json.has(FIRST_NAME)) {
                firstName = json.getString(FIRST_NAME);
            }
            if (json.has(LAST_NAME)) {
                lastName = json.getString(LAST_NAME);
            }
            if (json.has(FULL_NAME)) {
                fullname = json.getString(FULL_NAME);
            }
            if (json.has(EMAIL)) {
                email = json.getString(EMAIL);
            }
            if (json.has(TIN_NUMBER)) {
                tinNumber = json.getString(TIN_NUMBER);
            }
            if (json.has(PAN_NUMBER)) {
                panNumber = json.getString(PAN_NUMBER);
            }
            if (json.has(AGENCY_NAME)) {
                agencyName = json.getString(AGENCY_NAME);
            }
            if (json.has(PHONE)) {
                phone = json.getString(PHONE);
            }
            if (json.has(IMAGE)) {
                image = json.getString(IMAGE);
            }
            if (json.has(IS_OTP_VERIFIED)) {
                otp_verified = json.getBoolean(IS_OTP_VERIFIED);
            }
            if (json.has(ORDER_COUNT)) {
                orderCount = json.getString(ORDER_COUNT);
            }
            if (json.has(CITY_ID)) {
                cityId = json.getString(CITY_ID);
            }
            if (json.has(CITY_NAME)) {
                cityName = json.getString(CITY_NAME);
            }
            if (json.has(PROFILE_IMAGE)) {
                profileImg = json.getString(PROFILE_IMAGE);
            }

            /*get Address*/
            if (json.has(ADDRESS)) {
                AddressModel addressModelTemp = new AddressModel();
                JSONObject addressJson = new JSONObject();
                addressJson = json.getJSONObject(ADDRESS);
                if (addressJson != null) {
                    addressModelTemp.toObject(addressJson.toString());
                }
                address = addressModelTemp;
            }

            if (json.has(CITY)) {
                CityListModel vehicleListModel = new CityListModel();
                JSONArray jsonObject1 = new JSONArray();
                jsonObject1 = json.getJSONArray(CITY);
                if (jsonObject1 != null) {
                    vehicleListModel.toObject(jsonObject1);
                }
                cities = vehicleListModel;
            }

            if (json.has(CATEGORY)) {
                try {
                    ProfileCategoryListModel profileCategoryListModel = new ProfileCategoryListModel();
                    if (profileCategoryListModel.toObject(json.getJSONArray(CATEGORY))) {
                        this.profileCategoryListModel = profileCategoryListModel;
                    }
                } catch (Exception exx) {
                    this.profileCategoryListModel = null;
                }
            }

            /*  *//*get Image*//*
            ImageModel imageModelTemp = new ImageModel();
            JSONObject imageJSON = new JSONObject();
            imageJSON = json.getJSONObject(IMAGE);
            if(imageJSON!=null){imageModelTemp.toObject(imageJSON.toString());}
            image = imageModelTemp;*/

            return true;
        } catch (Exception ex) {
            Log.d(TAG, "Json Exception : " + ex);
        }
        return false;
    }

    @Override
    public String toString() {
        String returnString = null;
        try {
            JSONObject jsonMain = new JSONObject();
            jsonMain.put(ID, id);
            jsonMain.put(FIRST_NAME, firstName);
            jsonMain.put(LAST_NAME, lastName);
            jsonMain.put(FULL_NAME, fullname);
            jsonMain.put(TIN_NUMBER, tinNumber);
            jsonMain.put(PAN_NUMBER, panNumber);
            jsonMain.put(AGENCY_NAME, agencyName);
            jsonMain.put(ADDRESS, new JSONObject(this.address.toString()));
            jsonMain.put(EMAIL, email);
            jsonMain.put(PHONE, phone);
            jsonMain.put(CITY_ID, cityId);
            jsonMain.put(CITY_NAME, cityName);
            jsonMain.put(IS_OTP_VERIFIED, isOtp_verified());
            jsonMain.put(CITY, cities != null ? new JSONArray(this.cities.toString(true)) : new JSONArray());
            jsonMain.put(IMAGE, image);//new JSONObject(image.toString()));
            jsonMain.put(ORDER_COUNT, orderCount);
            jsonMain.put(PROFILE_IMAGE, profileImg);
            jsonMain.put(CATEGORY, profileCategoryListModel != null ? new JSONArray(profileCategoryListModel.toString(true)) : new JSONArray());
            returnString = jsonMain.toString();
        } catch (Exception ex) {
            Log.d(TAG, " To String Exception : " + ex);
        }
        return returnString;
    }
}
