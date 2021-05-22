package com.sa.souqbinadriver.services.model;

import android.util.Log;


import com.sa.souqbinadriver.global.GlobalVariables;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class OrderSubmitModel implements Serializable {

    private final String TAG = "OrderSubmitModel";

    private final String
            CITY                         = "city",
            APARTMENT                    = "apartment",
            RESIDENCE_TYPE               = "residence_type",
            SERVICE                      = "service",
            PLAN_LIST                    = "cars",
            ADDRESS                      = "address";

    GlobalVariables.CAR_SERVICE_TYPE carServiceType = GlobalVariables.CAR_SERVICE_TYPE.SINGLE_CAR;

    CityModel
            cityModel           =null;
    /*ApartmentModel
            apartmentModel      =null;
*/
    CreatePlanModel
            createPlanModel     =null;

    KeyValueModel
            residenceModel      =null,
            serviceModel        =null;

    AddressModel
            addressModel        =null;

    CreatePlanListModel createPlanList = null;


    public CityModel getCityModel() {
        return cityModel;
    }

    public void setCityModel(CityModel cityModel) {
        this.cityModel = cityModel;
    }

    public KeyValueModel getServiceModel() {
        return serviceModel;
    }

    public void setServiceModel(KeyValueModel serviceModel) {
        this.serviceModel = serviceModel;
    }

    public CreatePlanModel getCreatePlanModel() {
        return createPlanModel;
    }

    public void setCreatePlanModel(CreatePlanModel createPlanModel) {
        this.createPlanModel = createPlanModel;
    }

    public AddressModel getAddressModel() {
        return addressModel;
    }

    public void setAddressModel(AddressModel addressModel) {
        this.addressModel = addressModel;
    }

    public CreatePlanListModel getCreatePlanList() {
        return createPlanList;
    }

    public void setCreatePlanList(CreatePlanListModel createPlanList) {
        this.createPlanList = createPlanList;
    }

    /*public ApartmentModel getApartmentModel() {
        return apartmentModel;
    }

    public void setApartmentModel(ApartmentModel apartmentModel) {
        this.apartmentModel = apartmentModel;
    }*/

    public KeyValueModel getResidenceModel() {
        return residenceModel;
    }

    public void setResidenceModel(KeyValueModel residenceModel) {
        this.residenceModel = residenceModel;
    }

    public GlobalVariables.CAR_SERVICE_TYPE getCarServiceType() {
        return carServiceType;
    }

    public void setCarServiceType(GlobalVariables.CAR_SERVICE_TYPE carServiceType) {
        this.carServiceType = carServiceType;
    }

    public boolean toObject(String jsonObject){
        try{
            JSONObject json = new JSONObject(jsonObject);
            if(json.has(CITY)){
                CityModel modelLocal = new CityModel();
                if(modelLocal.toObject(json.getJSONObject(CITY).toString())){
                    this.cityModel = modelLocal;
                }
            }
            if(json.has(RESIDENCE_TYPE)){
                KeyValueModel modelLocal = new KeyValueModel();
                if(modelLocal.toObject(json.getJSONObject(RESIDENCE_TYPE).toString())){
                    this.residenceModel = modelLocal;
                }
            }
          /*  if(json.has(APARTMENT)){
                ApartmentModel modelLocal = new ApartmentModel();
                if(modelLocal.toObject(json.getJSONObject(APARTMENT).toString())){
                    this.apartmentModel = modelLocal;
                }
            }*/
            if(json.has(SERVICE)){
                KeyValueModel modelLocal = new KeyValueModel();
                if(modelLocal.toObject(json.getJSONObject(SERVICE).toString())){
                    this.serviceModel = modelLocal;
                }
                try{
                    this.carServiceType = GlobalVariables.CAR_SERVICE_TYPE.SINGLE_CAR.getCAR_SERVICE_TYPE(modelLocal.getId());
                }catch (Exception exx){
                    this.carServiceType = GlobalVariables.CAR_SERVICE_TYPE.SINGLE_CAR;
                }
            }
            if(json.has(ADDRESS)){
                AddressModel modelLocal = new AddressModel();
                if(modelLocal.toObject(json.getJSONObject(ADDRESS).toString())){
                    this.addressModel = modelLocal;
                }
            }

            if(json.has(PLAN_LIST)){
                JSONArray array = json.getJSONArray(PLAN_LIST);
                CreatePlanListModel modelLocal = new CreatePlanListModel();
                if(modelLocal.toObject(array)){
                    this.createPlanList = modelLocal;
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
            jsonMain.put(CITY, this.cityModel != null? new JSONObject(this.cityModel.toString()) : new JSONObject());
            jsonMain.put(RESIDENCE_TYPE, this.residenceModel != null? new JSONObject(this.residenceModel.toString()) : new JSONObject());
           // jsonMain.put(APARTMENT, this.apartmentModel != null? new JSONObject(this.apartmentModel.toString()) : new JSONObject());
            jsonMain.put(SERVICE, this.serviceModel != null? new JSONObject(this.serviceModel.toString()) : new JSONObject());
            jsonMain.put(ADDRESS, this.addressModel != null? new JSONObject(this.addressModel.toString()) : new JSONObject());
            jsonMain.put(PLAN_LIST, this.createPlanList != null? new JSONArray(this.createPlanList.toString(true)) : new JSONArray());

            returnString = jsonMain.toString();}
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }
}
