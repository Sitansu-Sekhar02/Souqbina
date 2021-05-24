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
            ORDER_VENDOR_PRODUCT_ID    = "order_vendor_product_id",
            ORDER_ID    = "order_id",
            ORDER_VENDOR_ID    = "order_vendor_id",
            ORDER_DETAILS_ID    = "order_detail_id",
            SCHEDULED_FOR    = "scheduled_for",
            STATUS_TITLE    = "status_title",
            VENDOR_IMAGE    = "vendor_image",
            VENDOR_NUMBER    = "vendor_number",
            VENDOR_FIRSTNAME    = "vendor_fname",
            VENDOR_LASTNAME    = "vendor_lname",
            USER_IMAGE    = "user_image",
            USER_NUMBER    = "user_number",
            USER_FIRSTNAME    = "user_fname",
            USER_LASTNAME    = "user_lname",
            PICKUP_ADDRESS    = "pickup_address",
            PICKUP_LATITUDE    = "pickup_latitude",
            PICKUP_LONGITUDE    = "pickup_longitude",
            DROP_ADDRESS    = "drop_address",
            DROP_LATITUDE    = "drop_latitude",
            DROP_LONGITUDE    = "drop_longitude",
            PRODUCT_TITLE    = "product_title",
            QUANTITY    = "quantity",
            PRODUCT_IMAGE    = "product_image",
            DELIVERY_ON    = "deliverd_on",
            //ORDER_NUMBER    = "quantity",


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

    private String
            id = null,
            order_number = null,
            currency = null,
            created_on = null,
            order_vendor_product_id = null,
            order_id = null,
            order_vendor_id = null,
            scheduled_for = null,
            order_detail_id = null,
            status_title = null,
            vendor_image = null,
            vendor_number = null,
            vendor_fname = null,
            vendor_lname = null,
            user_image = null,
            user_number = null,
            user_fname = null,
            user_lname = null,
            pickup_address = null,
            pickup_latitude = null,
            pickup_longitude = null,
            drop_address = null,
            drop_latitude = null,
            drop_longitude = null,
            product_title = null,
            quantity = null,
            product_image = null,

              delivered_on = null;


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

    public String getOrder_vendor_product_id() {
        return order_vendor_product_id;
    }

    public void setOrder_vendor_product_id(String order_vendor_product_id) {
        this.order_vendor_product_id = order_vendor_product_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_vendor_id() {
        return order_vendor_id;
    }

    public void setOrder_vendor_id(String order_vendor_id) {
        this.order_vendor_id = order_vendor_id;
    }

    public String getScheduled_for() {
        return scheduled_for;
    }

    public void setScheduled_for(String scheduled_for) {
        this.scheduled_for = scheduled_for;
    }

    public String getOrder_detail_id() {
        return order_detail_id;
    }

    public void setOrder_detail_id(String order_detail_id) {
        this.order_detail_id = order_detail_id;
    }

    public String getStatus_title() {
        return status_title;
    }

    public void setStatus_title(String status_title) {
        this.status_title = status_title;
    }

    public String getVendor_image() {
        return vendor_image;
    }

    public void setVendor_image(String vendor_image) {
        this.vendor_image = vendor_image;
    }

    public String getVendor_number() {
        return vendor_number;
    }

    public void setVendor_number(String vendor_number) {
        this.vendor_number = vendor_number;
    }

    public String getVendor_fname() {
        return vendor_fname;
    }

    public void setVendor_fname(String vendor_fname) {
        this.vendor_fname = vendor_fname;
    }

    public String getVendor_lname() {
        return vendor_lname;
    }

    public void setVendor_lname(String vendor_lname) {
        this.vendor_lname = vendor_lname;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getUser_number() {
        return user_number;
    }

    public void setUser_number(String user_number) {
        this.user_number = user_number;
    }

    public String getUser_fname() {
        return user_fname;
    }

    public void setUser_fname(String user_fname) {
        this.user_fname = user_fname;
    }

    public String getUser_lname() {
        return user_lname;
    }

    public void setUser_lname(String user_lname) {
        this.user_lname = user_lname;
    }

    public String getPickup_address() {
        return pickup_address;
    }

    public void setPickup_address(String pickup_address) {
        this.pickup_address = pickup_address;
    }

    public String getPickup_latitude() {
        return pickup_latitude;
    }

    public void setPickup_latitude(String pickup_latitude) {
        this.pickup_latitude = pickup_latitude;
    }

    public String getPickup_longitude() {
        return pickup_longitude;
    }

    public void setPickup_longitude(String pickup_longitude) {
        this.pickup_longitude = pickup_longitude;
    }

    public String getDrop_address() {
        return drop_address;
    }

    public void setDrop_address(String drop_address) {
        this.drop_address = drop_address;
    }

    public String getDrop_latitude() {
        return drop_latitude;
    }

    public void setDrop_latitude(String drop_latitude) {
        this.drop_latitude = drop_latitude;
    }

    public String getDrop_longitude() {
        return drop_longitude;
    }

    public void setDrop_longitude(String drop_longitude) {
        this.drop_longitude = drop_longitude;
    }

    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
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

            if (json.has(ORDER_VENDOR_PRODUCT_ID)) {
                order_vendor_product_id = json.getString(ORDER_VENDOR_PRODUCT_ID);
            }if (json.has(ORDER_ID)) {
                order_id = json.getString(ORDER_ID);
            }if (json.has(ORDER_VENDOR_ID)) {
                order_vendor_id = json.getString(ORDER_VENDOR_ID);
            }if (json.has(ORDER_DETAILS_ID)) {
                order_detail_id = json.getString(ORDER_DETAILS_ID);
            }if (json.has(SCHEDULED_FOR)) {
                scheduled_for = json.getString(SCHEDULED_FOR);
            }if (json.has(STATUS_TITLE)) {
                status_title = json.getString(STATUS_TITLE);
            }if (json.has(VENDOR_IMAGE)) {
                vendor_image = json.getString(VENDOR_IMAGE);
            }if (json.has(VENDOR_NUMBER)) {
                vendor_number = json.getString(VENDOR_NUMBER);
            }if (json.has(VENDOR_FIRSTNAME)) {
                user_fname = json.getString(VENDOR_FIRSTNAME);
            }if (json.has(VENDOR_LASTNAME)) {
                vendor_lname = json.getString(VENDOR_LASTNAME);
            }if (json.has(USER_IMAGE)) {
                user_image = json.getString(USER_IMAGE);
            }if (json.has(USER_NUMBER)) {
                user_number = json.getString(USER_NUMBER);
            }if (json.has(USER_FIRSTNAME)) {
                user_fname = json.getString(USER_FIRSTNAME);
            }if (json.has(USER_LASTNAME)) {
                user_lname = json.getString(USER_LASTNAME);
            }if (json.has(PICKUP_ADDRESS)) {
                pickup_address = json.getString(PICKUP_ADDRESS);
            }if (json.has(PICKUP_LATITUDE)) {
                pickup_latitude = json.getString(PICKUP_LATITUDE);
            }if (json.has(PICKUP_LONGITUDE)) {
                pickup_longitude = json.getString(PICKUP_LONGITUDE);
            }if (json.has(DROP_ADDRESS)) {
                drop_address = json.getString(DROP_ADDRESS);
            }if (json.has(DROP_LATITUDE)) {
                drop_latitude = json.getString(DROP_LATITUDE);
            }if (json.has(DROP_LONGITUDE)) {
                drop_longitude = json.getString(DROP_LONGITUDE);
            }if (json.has(PRODUCT_TITLE)) {
                product_title = json.getString(PRODUCT_TITLE);
            }if (json.has(QUANTITY)) {
                quantity = json.getString(QUANTITY);
            }if (json.has(PRODUCT_IMAGE)) {
                product_image = json.getString(PRODUCT_IMAGE);
            }if (json.has(DELIVERY_ON)) {
                delivered_on = json.getString(DELIVERY_ON);
            }

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
            jsonMain.put(ORDER_VENDOR_PRODUCT_ID, order_vendor_product_id);
            jsonMain.put(ORDER_ID, order_id);
            jsonMain.put(ORDER_VENDOR_ID, order_vendor_id);
            jsonMain.put(ORDER_DETAILS_ID, order_detail_id);
            jsonMain.put(SCHEDULED_FOR, scheduled_for);
            jsonMain.put(STATUS_TITLE, status_title);
            jsonMain.put(VENDOR_IMAGE, vendor_image);
            jsonMain.put(VENDOR_FIRSTNAME, vendor_fname);
            jsonMain.put(VENDOR_LASTNAME, vendor_lname);
            jsonMain.put(VENDOR_NUMBER, vendor_number);
            jsonMain.put(USER_NUMBER, user_number);
            jsonMain.put(USER_FIRSTNAME, user_fname);
            jsonMain.put(USER_LASTNAME, user_lname);
            jsonMain.put(USER_IMAGE, user_image);
            jsonMain.put(PICKUP_ADDRESS, pickup_address);
            jsonMain.put(PICKUP_LATITUDE, pickup_latitude);
            jsonMain.put(PICKUP_LONGITUDE, pickup_longitude);
            jsonMain.put(DROP_ADDRESS, drop_address);
            jsonMain.put(DROP_LATITUDE, drop_latitude);
            jsonMain.put(DROP_LONGITUDE, drop_longitude);
            jsonMain.put(QUANTITY, quantity);
            jsonMain.put(PRODUCT_IMAGE, product_image);
            jsonMain.put(PRODUCT_TITLE, product_title);

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
