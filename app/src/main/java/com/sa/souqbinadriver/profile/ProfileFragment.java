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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.sa.souqbinadriver.Activity.MainActivity;
import com.sa.souqbinadriver.R;
import com.sa.souqbinadriver.global.GlobalFunctions;
import com.sa.souqbinadriver.services.ServerResponseInterface;
import com.sa.souqbinadriver.services.ServicesMethodsManager;
import com.sa.souqbinadriver.services.model.ProfileModel;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {
    public static final String TAG ="ProfileFragment" ;
    public static final String BUNDLE_KEY_CITY_HOUSE_TYPE_FRAGMENT_ORDER_SUBMIT_MODEL = "BundleKeyCreateRideFragmentOrderSubmit";

    private static Activity activity;
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
        view = inflater.inflate(R.layout.activity_my_profile, container, false);

        activity = getActivity();
        context = getActivity();

        //name_tv = ( TextView ) view.findViewById( R.id.name_tv );

        first_name_etv = view.findViewById( R.id.tv_firstname );
        last_name_etv =  view.findViewById( R.id.tv_lastName );
        mobile_etv =  view.findViewById( R.id.tv_mobileNumber );
        email_etv = view.findViewById( R.id.tv_emailId );
        //email_ev = view.findViewById( R.id.email_ev );
     /*   password_etv = ( EditText ) view.findViewById( R.id.password_etv );
        confirm_password_etv = ( EditText ) view.findViewById( R.id.confirm_password_etv );*/

        profile_image = ( CircleImageView ) view.findViewById( R.id.ivProfimeImage );


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags( WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS );
            window.clearFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS );
//            window.setStatusBarColor(ContextCompat.getColor(this, R.color.ColorStatusBar));
        }
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        Intent i = new Intent(getActivity(), MainActivity.class);
                        startActivity(i);
                        getActivity().overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                        return true;
                    }
                }
                return false;
            }
        });

        MainActivity.iv_home.setVisibility(View.GONE);
        MainActivity.iv_menu.setImageResource(R.drawable.ic_group_back);
        MainActivity.tvHeaderText.setText(getString(R.string.my_profile));
        MainActivity.iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.slide_left, R.anim.slide_right);*/
                onBackPressed();

            }
        });
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
                setDetails( ( ProfileModel ) arg0 );
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

    private void setDetails(ProfileModel profile) {
        GlobalFunctions.setProfile( context, profile );
        if (context != null && isAdded()) {
            detail = GlobalFunctions.getProfile( context );
            setThisPage();
        }
        ProfileModel model = ( ProfileModel ) profile;
    }

    private void setThisPage() {
        if (detail != null) {
            if (isAdded()) {
                //  setEditPage(false);

                Log.d( "heckDetail",""+ detail );

                String fullName = detail.getFirstName() + " " + detail.getLastName();
                name_tv.setText( fullName );
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

    private void onBackPressed() {
        closeThisActivity();
        super.getActivity().onBackPressed();
    }
    public void closeThisActivity() {

        if (activity != null) {
            activity.finish();
            //activity.overridePendingTransition(R.anim.zoom_in,R.anim.zoom_out);
        }
    }

    public void replaceFragmentWithAnimation(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }


}
