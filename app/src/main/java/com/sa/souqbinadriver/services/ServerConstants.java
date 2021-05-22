package com.sa.souqbinadriver.services;

public class ServerConstants {

    //public static final String SERVER_URL               = "http://hardwarewagon.com/hardware_wagon_php/";
    public static final String SERVER_URL               = "http://35.154.4.154/souqbina/driver_app_new/v0_1/api/";

    public static final String SERVER_API_VERSION       = "driver_v0_1";
    public static final String BASE_URL                 = SERVER_URL+"app/"+SERVER_API_VERSION+"/api/";

    public static final String URL_GuestUserCreation    = BASE_URL + "guest_user_creation.php";
    public static final String URL_GetIndex             = BASE_URL + "get_index.php";
    public static final String URL_SendOTP              = BASE_URL + "send_otp.php";
    public static final String URL_VerifyOTP            = BASE_URL + "verify_otp.php";
    public static final String URL_LoginUser            = SERVER_URL + "login.php";
    public static final String URL_RegisterUser         = BASE_URL + "registration.php";
    public static final String URL_GetCategoryList      = BASE_URL + "category_list.php";
    public static final String URL_GetConfirmedAppointmentsList      = BASE_URL + "booking_display.php";
    public static final String URL_BookingStatusUpdate  = BASE_URL + "booking_status_update.php";
    public static final String URL_LogoutUser           = SERVER_URL + "logout.php";
    public static final String URL_UpdateProfile        = BASE_URL + "get_profile.php";
    public static final String URL_GetProfile           = SERVER_URL + "get_profile.php";
    public static final String URL_GetCityList          = BASE_URL + "city_list.php";
    public static final String URL_GetNewsList          = BASE_URL + "news_list.php";
    public static final String URL_GetMyCarsList        = BASE_URL + "my_cars.php";
    public static final String URL_ChangePassword       = BASE_URL + "change_password.php";
    public static final String URL_Address              = BASE_URL + "user_address.php";
    public static final String URL_GetAddress           = BASE_URL + "get_address.php";
    public static final String URL_DeleteAddress        = BASE_URL + "delete_user_address.php";
    public static final String URL_CreatePlan           = BASE_URL + "add_car_options.php";
    public static final String URL_GetPackage           = BASE_URL + "package.php";
    public static final String URL_Session_Type_Timings = BASE_URL + "session_type_timings.php";
    public static final String URL_CityDetails          = BASE_URL + "city_apartment_list.php";
    public static final String URL_SubmitOrder          = BASE_URL + "submit_order.php";
    public static final String URL_Request              = BASE_URL + "request_fo_booking.php";
    public static final String URL_DriverTaskList       = BASE_URL + "driver_task_list.php";
    public static final String URL_DriverTaskUpdate       = BASE_URL + "driver_task_update.php";

    public static final String URL_GetNotifications     = BASE_URL + "get_notifications.php";
    public static final String URL_ForgotPassword       = BASE_URL + "forgot_password.php";
    public static final String URL_PushNotification     = BASE_URL + "push.php";

    public static final String URL_GetMyPlansList       = BASE_URL + "";

    public static final String URL_AboutUs              = "http://hardwarewagon.com/hardware_wagon_ui/about-nomenu.html";
    public static final String URL_Terms                = "http://hardwarewagon.com/hardware_wagon_ui/terms-menu.html";
    public static final String URL_Privacy              = "http://hardwarewagon.com/hardware_wagon_ui/privacy-nomenu.html";


}
