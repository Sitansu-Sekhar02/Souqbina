package com.sa.souqbinadriver.Login;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.hbb20.CountryCodePicker;
import com.sa.souqbinadriver.Activity.AppController;
import com.sa.souqbinadriver.Activity.MainActivity;
import com.sa.souqbinadriver.R;
import com.sa.souqbinadriver.global.GlobalFunctions;
import com.sa.souqbinadriver.global.GlobalVariables;
import com.sa.souqbinadriver.services.ServerResponseInterface;
import com.sa.souqbinadriver.services.ServicesMethodsManager;
import com.sa.souqbinadriver.services.model.LoginModel;
import com.sa.souqbinadriver.services.model.ProfileMainModel;
import com.sa.souqbinadriver.services.model.ProfileModel;
import com.sa.souqbinadriver.services.model.StatusMainModel;
import com.sa.souqbinadriver.services.model.StatusModel;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public  class LoginActivity extends AppCompatActivity {
    public static final String TAG = "LoginActivity";

    TextInputEditText etMobile,etPassword;
    Button login;
    Dialog dialog;
    Context context;
    StringRequest request;
    static Activity activity;
    View mainView;

    EditText mobileNumber_etv, password_etv;
    TextView sendActivation_tv;

    CountryCodePicker countryCode_ccsp;

    SearchableSpinner
            select_country_code_spinner;


    String selectedCountryCodeLength = null;

    int selectCountryCodeLength = 0;

    private ArrayAdapter<String> countryAdapter;

    private List<String>
            countryStringList = new ArrayList();

    GlobalVariables globalVariables;
    GlobalFunctions globalFunctions;
    LoginModel loginModel;
    String selected_country_code = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.login_activity);


        login=findViewById(R.id.BtnLogin);
        etMobile=findViewById(R.id.etMobile);
        etPassword=findViewById(R.id.etPassword);

        context = this;
        activity = this;

        globalFunctions = AppController.getInstance().getGlobalFunctions();
        globalVariables = AppController.getInstance().getGlobalVariables();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags( WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS );
            window.clearFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS );
            //            window.setStatusBarColor(ContextCompat.getColor(this, R.color.ColorStatusBar));
        }
        mainView = etMobile;


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateInput();

            }
        });


    }

    private void validateInput() {
        if (etMobile != null) {
            String
                    mobileNo = etMobile.getText().toString().trim(),
                    password = etPassword.getText().toString().trim();

            if (mobileNo.isEmpty()) {
                etMobile.setError( getString( R.string.phn_erro) );
                etMobile.setFocusableInTouchMode( true );
                etMobile.requestFocus();
            } else if (password.isEmpty()) {
                etPassword.setError( getString( R.string.password_error ) );
                etPassword.setFocusableInTouchMode( true );
                etPassword.requestFocus();
            } else if (!globalFunctions.isPhoneNumberValid( mobileNo )) {
                //                mobNo_ev.setText( "" );
                etMobile.setError( getString( R.string.mobileNoNotValid ) );
                etMobile.setFocusableInTouchMode( true);
                etMobile.requestFocus();
                // GlobalFunctions.displayMessaage(context, mainView, getString(R.string.mobileNumberNotValid));
            } /*else if (selected_country_code.isEmpty()) {
                            globalFunctions.displayMessaage( activity, mainView, getString( R.string.countryCodeNONotValid ) );
                        }*/
            else {
                LoginModel loginModel = new LoginModel();
                loginModel.setEmail_id( mobileNo );
                //loginModel.setCountryCode( selected_country_code );
                loginModel.setPassword( password );

                loginUser( context, loginModel );

            }
        }
    }


    private void loginUser(final Context context, final LoginModel model) {
        GlobalFunctions.showProgress( context, getString(R.string.loggin) );
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.loginUser( context, model, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
                //hideLoading();
                GlobalFunctions.hideProgress();
                Log.d( TAG, "Response : " + arg0.toString() );
                validateOutput( arg0 );
            }

            @Override
            public void OnFailureFromServer(String msg) {
                //                hideLoading();
                GlobalFunctions.hideProgress();
                GlobalFunctions.displayMessaage( context, mainView, msg );
                Log.d( TAG, "Failure : " + msg );
            }

            @Override
            public void OnError(String msg) {
                //                hideLoading();
                GlobalFunctions.hideProgress();
                GlobalFunctions.displayMessaage( context, mainView, msg );
                Log.d( TAG, "Error : " + msg );
            }
        }, "Login_User" );
    }

    private void validateOutput(Object model) {
        if (model instanceof StatusMainModel) {
            StatusMainModel statusMainModel = ( StatusMainModel ) model;
            StatusModel statusModel = statusMainModel.getStatusModel();
            if (statusMainModel.isStatus()){

                GlobalFunctions.setSharedPreferenceString(context, GlobalVariables.SHARED_PREFERENCE_TOKEN, statusModel.getToken());
                Intent intent = new Intent( context, MainActivity.class);
                startActivity( intent );
                //get profile api
                getProfile();
            }else {
                GlobalFunctions.displayErrorDialog( context, statusModel.getMessage());
            }
             /*   if (statusModel.getExtra().equalsIgnoreCase( "1" )) {
                    final AlertDialog alertDialog = new AlertDialog( activity );
                    alertDialog.setCancelable( false );
                    alertDialog.setIcon( R.drawable.ic_app );
                    alertDialog.setTitle( getString( R.string.otp ) );
                    alertDialog.setMessage( statusModel.getMessage() );

                    alertDialog.setPositiveButton( getString( R.string.ok ), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
    //                        GlobalFunctions.closeAllActivities();
                            OtpModel otpModel = new OtpModel();
                            otpModel.setPhone( loginModel.getUserName() );
                            otpModel.setCountryCode( loginModel.getCountryCode() );
                            Intent intent = RegisterActivity.newInstance( context, otpModel );
                            startActivity( intent );
                        }

                    } );
                    alertDialog.show();

                } else {*/
            //            GlobalFunctions.displayErrorDialog( context, statusModel.getMessage() );

        } /*else if (model instanceof ProfileModel) {
                GlobalFunctions.closeAllActivities();
                ProfileModel profileModel = ( ProfileModel ) model;
                GlobalFunctions.setProfile( context, profileModel );
                Intent intent = new Intent( context, MainActivity.class );
                startActivity( intent );
            }*/
    }

    private void getProfile() {
        GlobalFunctions.showProgress(context, getString(R.string.loading_profile) );// it should not be hardcoded string ok??
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();

        servicesMethodsManager.getProfile(context,new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
                //                hideLoading();
                GlobalFunctions.hideProgress();
                Log.d( TAG, "Response : " + arg0.toString() );
                //ProfileDetails(arg0);
                validateProfileOutput(arg0); //naming convention of method should be proper ?

            }

            @Override
            public void OnFailureFromServer(String msg) {
                //                hideLoading();
                GlobalFunctions.hideProgress();
                GlobalFunctions.displayMessaage(LoginActivity.this.context, mainView, msg );
                Log.d( TAG, "Failure : " + msg );
            }

            @Override
            public void OnError(String msg) {
                //                hideLoading();
                GlobalFunctions.hideProgress();
                GlobalFunctions.displayMessaage(LoginActivity.this.context, mainView, msg );
                Log.d( TAG, "Error : " + msg );
            }
        }, "ProfileFragment" );
    }

    private void validateProfileOutput(Object arg0) {
         if (arg0 instanceof ProfileMainModel){

            GlobalFunctions.closeAllActivities();
             ProfileMainModel profileMainModel = ( ProfileMainModel ) arg0;
            ProfileModel profileModel = profileMainModel.getProfileModel();
            GlobalFunctions.setProfile( context, profileModel );
            Intent intent = new Intent( context, MainActivity.class );
            startActivity( intent );

        }

    }
    @Override
    public void onBackPressed() {
        closeThisActivity();
        super.onBackPressed();
    }

    public static void closeThisActivity() {
        if (activity != null) {
            activity.finish();
            //activity.overridePendingTransition(R.anim.zoom_in,R.anim.zoom_out);
        }
    }
}

