package com.sa.souqbinadriver.services.model;

import android.util.Log;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;


public class OrderModel implements Serializable {

    private final String TAG = "OrderModel";

    private final String
            ID              = "id",
            ORDER_NUMBER    = "order_number",
            TAX             = "tax",
            CURRENCY        = "currency",
            CREATED_ON      = "created_on",
            DELIVERED_ON    = "delivered_on",
            SUB_TOTAL       = "sub_total",
            OFFER_PRICE     = "offer_price",
            SHIPPING_PRICE  = "shipping_price",
            DISCOUNT_TOTAL  = "discount_total",
            GRAND_TOTAL     = "grand_total",
            STATUS          = "status",
            DELIVERY_ADDRESS = "delivery_address",
            BILLING_ADDRESS = "billing_address",
            ORDER_DETAILS   = "order_details";

    private String id = null, order_number = null, currency = null, created_on = null, delivered_on = null;

    private com.sa.souqbinadriver.global.STATUS.ORDER status = com.sa.souqbinadriver.global.STATUS.ORDER.NEW;

    private  double tax  =  0.0,
            sub_total    =  0.0,
            shipping_price    =  0.0,
            offer_price  =  0.0,
            discount_total  =  0.0,
            grand_total  =  0.0;

    private OrderDetailListModel order_details = null;


    OrderSendAddressModel deliveryAddress, billingAddress ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public com.sa.souqbinadriver.global.STATUS.ORDER getStatus() {
        return status;
    }

    public void setStatus(com.sa.souqbinadriver.global.STATUS.ORDER status) {
        this.status = status;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getSub_total() {
        return sub_total;
    }

    public double getShipping_price() {
        return shipping_price;
    }

    public void setShipping_price(double shipping_price) {
        this.shipping_price = shipping_price;
    }

    public void setSub_total(double sub_total) {
        this.sub_total = sub_total;
    }

    public double getOffer_price() {
        return offer_price;
    }

    public void setOffer_price(double offer_price) {
        this.offer_price = offer_price;
    }

    public double getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(double grand_total) {
        this.grand_total = grand_total;
    }

    public OrderDetailListModel getOrder_details() {
        return order_details;
    }

    public void setOrder_details(OrderDetailListModel order_details) {
        this.order_details = order_details;
    }

    public OrderSendAddressModel getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(OrderSendAddressModel deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public OrderSendAddressModel getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(OrderSendAddressModel billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getDelivered_on() {
        return delivered_on;
    }

    public void setDelivered_on(String delivered_on) {
        this.delivered_on = delivered_on;
    }

    public double getDiscount_total() {
        return discount_total;
    }

    public void setDiscount_total(double discount_total) {
        this.discount_total = discount_total;
    }

    public boolean toObject(String jsonObject){
        try{
            JSONObject json = new JSONObject(jsonObject);
            this.id = json.getString(ID);
            if(json.has(ORDER_NUMBER))this.order_number = json.getString(ORDER_NUMBER);
            if(json.has(CURRENCY))this.currency = json.getString(CURRENCY);
            if(json.has(CREATED_ON))this.created_on = json.getString(CREATED_ON);

            if(json.has(SHIPPING_PRICE)) {
                try {
                    this.shipping_price = json.getDouble(SHIPPING_PRICE);
                }catch (Exception e){this.shipping_price = 0.0;}
            }

             if(json.has(DISCOUNT_TOTAL)) {
                try {
                    this.discount_total = json.getDouble(DISCOUNT_TOTAL);
                }catch (Exception e){this.discount_total = 0.0;}
            }

            if(json.has(DELIVERED_ON)){
                this.delivered_on = json.getString(DELIVERED_ON);
                if(this.delivered_on.trim().equalsIgnoreCase("null") || this.delivered_on.trim().isEmpty()){
                    this.delivered_on=null;
                }
            }
            if(json.has(STATUS)){
                String tempStatusString = json.getString(STATUS);
                try{
                    this.status = com.sa.souqbinadriver.global.STATUS.ORDER.NEW.getORDER_TYPE(tempStatusString);
                }catch (Exception exxxx){
                    this.status = com.sa.souqbinadriver.global.STATUS.ORDER.NEW;
                }
            }

            if(json.has(TAX)){
                try {
                    this.tax = json.getDouble(TAX);
                }catch (Exception e){
                    tax= 0.0;
                }
            }

           if(json.has(OFFER_PRICE)){
                try {
                    this.offer_price = json.getDouble(OFFER_PRICE);
                }catch (Exception e){
                    offer_price= 0.0;
                }
            }

          if(json.has(SUB_TOTAL)){
                    try {
                        this.sub_total = json.getDouble(SUB_TOTAL);
                    }catch (Exception e){
                        sub_total= 0.0;
                    }
                }

         if(json.has(GRAND_TOTAL)){
                    try {
                        this.grand_total = json.getDouble(GRAND_TOTAL);
                    }catch (Exception e){
                        grand_total= 0.0;
                    }
                }

            if(json.has(ORDER_DETAILS)){
                OrderDetailListModel orderDetailListModel = new OrderDetailListModel();
                JSONArray jsonArray = new JSONArray();
                jsonArray = json.getJSONArray(ORDER_DETAILS);
                if(jsonArray!=null){orderDetailListModel.toObject(jsonArray);}
                this.order_details = orderDetailListModel;
            }



            //get Delivery Address
            if(json.has(DELIVERY_ADDRESS)){
                OrderSendAddressModel addressModelTemp = new OrderSendAddressModel();
                JSONObject addressJson = new JSONObject();
                addressJson = json.getJSONObject(DELIVERY_ADDRESS);
                if(addressJson!=null){addressModelTemp.toObject(addressJson.toString());}
                deliveryAddress = addressModelTemp;
            }


            //get Billing Address
            if(json.has(BILLING_ADDRESS)){
                OrderSendAddressModel addressModelTemp = new OrderSendAddressModel();
                JSONObject addressJson = new JSONObject();
                addressJson = json.getJSONObject(BILLING_ADDRESS);
                if(addressJson!=null){addressModelTemp.toObject(addressJson.toString());}
                billingAddress = addressModelTemp;
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
            jsonMain.put(ORDER_NUMBER, this.order_number);
            jsonMain.put(STATUS, this.status!=null?this.status.getCode():null);
            jsonMain.put(CURRENCY, this.currency);
            jsonMain.put(CREATED_ON, this.created_on);
            jsonMain.put(DELIVERED_ON, this.delivered_on);
            jsonMain.put(TAX, this.tax);
            jsonMain.put(SUB_TOTAL, this.sub_total);
            jsonMain.put(DISCOUNT_TOTAL, this.discount_total);
            jsonMain.put(GRAND_TOTAL, this.grand_total);
            jsonMain.put(ORDER_DETAILS, this.order_details!=null?new JSONArray(this.order_details.toString()):new JSONArray());
            jsonMain.put(DELIVERY_ADDRESS, this.deliveryAddress!=null?new JSONObject(this.deliveryAddress.toString()):new JSONObject());
            jsonMain.put(BILLING_ADDRESS, this.billingAddress!=null?new JSONObject(this.billingAddress.toString()):new JSONObject());
            returnString = jsonMain.toString();
        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }

}
