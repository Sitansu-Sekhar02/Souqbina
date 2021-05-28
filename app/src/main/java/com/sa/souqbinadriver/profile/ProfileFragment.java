package com.sa.souqbinadriver.profile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.sa.souqbinadriver.Activity.MainActivity;
import com.sa.souqbinadriver.R;
import com.sa.souqbinadriver.global.GlobalFunctions;
import com.sa.souqbinadriver.services.ServerResponseInterface;
import com.sa.souqbinadriver.services.ServicesMethodsManager;
import com.sa.souqbinadriver.services.model.ProfileMainModel;
import com.sa.souqbinadriver.services.model.ProfileModel;
import com.sa.souqbinadriver.services.model.StatusModel;
import com.sa.souqbinadriver.view.AlertDialog;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {
    public static final String TAG ="ProfileFragment" ;
    public static final String BUNDLE_KEY_CITY_HOUSE_TYPE_FRAGMENT_ORDER_SUBMIT_MODEL = "BundleKeyCreateRideFragmentOrderSubmit";

    Activity activity;
    Context context;
    View mainView;
    TextView name_tv, therapist_tv, update_button, email_ev;
    TextView first_name_etv, last_name_etv, mobile_etv, email_etv, password_etv, confirm_password_etv;
    CircleImageView profile_image;

    View view;
    ProfileModel detail = null;

    public ProfileFragment() {
        setHasOptionsMenu( true );
    }

    public static Fragment newInstance() {
        Fragment fragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putString( BUNDLE_KEY_CITY_HOUSE_TYPE_FRAGMENT_ORDER_SUBMIT_MODEL, null );
        fragment.setArguments( bundle );
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu( true );
        super.onCreate( savedInstanceState );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView( inflater, container, savedInstanceState );
        view = inflater.inflate(R.layout.activity_my_profile, container, false);

        activity = getActivity();
        context = getActivity();

       // name_tv = ( TextView ) view.findViewById( R.id.name_tv );

        first_name_etv = view.findViewById( R.id.tv_firstname );
        last_name_etv =  view.findViewById( R.id.tv_lastName );
        mobile_etv =  view.findViewById( R.id.tv_mobileNumber );
        email_etv = view.findViewById( R.id.tv_emailId );
        //email_ev = view.findViewById( R.id.email_ev );
     /*   password_etv = ( EditText ) view.findViewById( R.id.password_etv );
        confirm_password_etv = ( EditText ) view.findViewById( R.id.confirm_password_etv );*/

        profile_image = ( CircleImageView ) view.findViewById( R.id.ivProfimeImage );


        getProfile();
        mainView=first_name_etv;



        return view;
    }

    private void getProfile() {

        GlobalFunctions.showProgress( context, getString( R.string.getting_profile ) );
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getProfile( context, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
                GlobalFunctions.hideProgress();
                Log.d( TAG, "Response : " + arg0.toString() );
                setDetails( arg0);
            }

            @Override
            public void OnFailureFromServer(String msg) {
                GlobalFunctions.hideProgress();
                GlobalFunctions.displayMessaage( context, mainView, msg );
                Log.d( TAG, "Failure : " + msg );
                setDetails();
            }

            @Override
            public void OnError(String msg) {
                GlobalFunctions.hideProgress();
                GlobalFunctions.displayMessaage( context, mainView, msg );
                Log.d( TAG, "Error : " + msg );
                setDetails();
            }
        }, "Get Profile" );

    }

    private void setDetails() {
        if (context != null && isAdded()) {
            detail = GlobalFunctions.getProfile( context );
            setThisPage();
        }
    }

    private void setDetails(Object arg0) {
        ProfileMainModel profileMainModel = ( ProfileMainModel ) arg0;
        ProfileModel profileModel = profileMainModel.getProfileModel();
        GlobalFunctions.setProfile( context, profileModel );
        //GlobalFunctions.setProfile( context, profile );
        if (context != null && isAdded()) {
            detail = GlobalFunctions.getProfile( context );
            setThisPage();
        }
       // ProfileModel model = ( ProfileModel ) profile;
    }

    private void setThisPage() {
        if (detail != null) {
            if (isAdded()) {
                //  setEditPage(false);

                Log.d( "heckDetail",""+ detail );

               // String fullName = detail.getFirstName() + " " + detail.getLastName();
               // name_tv.setText( fullName );
                first_name_etv.setText( detail.getFirstName() );
                last_name_etv.setText( detail.getLastName() );
                mobile_etv.setText( detail.getPhone() );
                email_etv.setText( detail.getEmail() );
//                email_ev.setText( detail.getEmail() );

                try {
                    if (detail.getProfileImg() != null || !detail.getProfileImg().equals( "null" ) || !detail.getProfileImg().equalsIgnoreCase( "" )) {
                        Picasso.with( context ).load( detail.getProfileImg() ).placeholder( R.drawable.profile_img ).into( profile_image );
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    @Override
    public void onResume() {
        /* getProfile();*/
        // MainActivity.setTitleResourseID(0);
        (( ProfileMainActivity ) activity).setTitle( getString( R.string.my_profile ), R.mipmap.app_icon, 0 );
        super.onResume();
    }

    private void checkChangePasswordAfter(Object model) {
        if (model instanceof StatusModel) {
            StatusModel statusModel = ( StatusModel ) model;
//            GlobalFunctions.displayMessaage(context, mainView, statusModel.getMessage());
            if (statusModel.isStatus()) {
                showAlertDialog( context, statusModel.getMessage() );
            }
        }
    }

    private void showAlertDialog(final Context context, final String message) {
        final AlertDialog alertDialog = new AlertDialog( context );
        alertDialog.setCancelable( false );
        alertDialog.setIcon( R.mipmap.app_icon );
        alertDialog.setTitle( getString( R.string.app_name ) );
        alertDialog.setMessage( message );
        alertDialog.setPositiveButton( getString( R.string.ok ), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                //logoutUser( context );
            }
        } );

        alertDialog.show();
    }

/*
    private void logoutUser(Context context) {
        GlobalFunctions.showProgress( context, getString( R.string.logingout ) );
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.logout( context, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
                GlobalFunctions.hideProgress();
                Log.d( TAG, "Response : " + arg0.toString() );
                validateOutput( arg0 );
            }

            @Override
            public void OnFailureFromServer(String msg) {
                GlobalFunctions.hideProgress();
                Toast.makeText( context, msg, Toast.LENGTH_SHORT ).show();
                //GlobalFunctions.displayMessaage(context, mainView, msg);
                Log.d( TAG, "Failure : " + msg );
            }

            @Override
            public void OnError(String msg) {
                GlobalFunctions.hideProgress();
                Toast.makeText( context, msg, Toast.LENGTH_SHORT ).show();
                //GlobalFunctions.displayMessaage(context, mainView, msg);
                Log.d( TAG, "Error : " + msg );
            }
        }, "Logout_User" );
    }
*/

    private void validateOutput(Object arg0) {
        if (arg0 instanceof StatusModel) {
            StatusModel statusModel = ( StatusModel ) arg0;
            GlobalFunctions.displayMessaage( context, mainView, statusModel.getMessage() );
            if (statusModel.isStatus()) {
                /*Logout success, Clear all cache and reload the home page*/

            }
            GlobalFunctions.logoutApplication( context );
            // MainActivity.RestartEntireApp(context);
        }
    }


}
