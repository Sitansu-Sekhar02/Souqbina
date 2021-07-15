package com.sa.souqbinadriver.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.sa.souqbinadriver.Fragments.FragmentAllProcess;
import com.sa.souqbinadriver.completedorder.CompletedOrderFragment;
import com.sa.souqbinadriver.fcm.analytics.AnalyticsReport;
import com.sa.souqbinadriver.global.GlobalFunctions;
import com.sa.souqbinadriver.global.GlobalVariables;
import com.sa.souqbinadriver.profile.ProfileFragment;
import com.sa.souqbinadriver.R;
import com.sa.souqbinadriver.profile.ProfileMainActivity;
import com.sa.souqbinadriver.services.ServerResponseInterface;
import com.sa.souqbinadriver.services.ServicesMethodsManager;
import com.sa.souqbinadriver.services.model.CountryModel;
import com.sa.souqbinadriver.services.model.KeyValueModel;
import com.sa.souqbinadriver.services.model.LogoutModel;
import com.sa.souqbinadriver.services.model.NotificationModel;
import com.sa.souqbinadriver.services.model.OrderListModel;
import com.sa.souqbinadriver.services.model.OrderMainModel;
import com.sa.souqbinadriver.services.model.OrderModel;
import com.sa.souqbinadriver.services.model.ProfileModel;
import com.sa.souqbinadriver.services.model.PushNotificationModel;
import com.sa.souqbinadriver.services.model.StatusMainModel;
import com.sa.souqbinadriver.services.model.StatusModel;
import com.sa.souqbinadriver.services.model.UtilityMainModel;
import com.sa.souqbinadriver.services.model.UtilityModel;
import com.sa.souqbinadriver.view.AlertDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    public static final String BUNDLE_DEEPLINK_URL = "BundleDeepLinkURL";
    public static final String BUNDLE_MAIN_NOTIFICATION_MODEL = "BundleMainModelNotificationModel";
    public static int backPressed = 0;
    public static ImageView iv_menu,iv_share,iv_home;
    public static Context mainContext = null;
    static Activity activity = null;
    public static RelativeLayout cart_notification_layout, notification_layout;

    DrawerLayout drawer;
    public static NavigationView navigationView;
    public static TextView tvHeaderText;
    public static TextView tvCount,header_completed_order;
    LinearLayout nav_header;
    public static TextView iv_Notification;
    public static ImageView iv_support;
    TextView Logout;
    private LayoutInflater layoutInflater;
    public static String TAG = "MainActivity";
    public static LocationServices locServices = null;
    static Toolbar toolbar;
    static ActionBar actionBar;

    static String mTitle;
    static int mResourceID;
    static int titleResourseID;
    static GlobalFunctions globalFunctions;
    static TextView toolbar_title;
    static ImageView toolbar_logo;
    static Intent locationintent;
    public Menu menu;
    FragmentManager mainActivityFM = null;
    Window mainWindow = null;
    KeyValueModel keyValueModel;
    View mainView;
    GlobalVariables globalVariables;
    View navigationHeaderView;


    //Langauge Textview
    static  TextView arabic_language_tv, english_language_tv;

    static View arabic_language_iv, english_language_iv;

    int gravity = 0;
    private NotificationModel notificationModel = null;
    int mToolbarHeight;
    AnalyticsReport analyticsReport;


    public static Intent newInstance(Context context, String url) {
        Intent intent = new Intent( context, MainActivity.class );
        intent.putExtra( BUNDLE_DEEPLINK_URL, url );
        return intent;
    }

    public static Intent newInstance(Context context, NotificationModel notificationModel) {
        Intent intent = new Intent( context, MainActivity.class );
        intent.putExtra( BUNDLE_MAIN_NOTIFICATION_MODEL, notificationModel );
        return intent;
    }

    public static void setTitle(String title, int titleImageID, int backgroundResourceID) {
        mTitle = title;
        if (backgroundResourceID != 0) {
            mResourceID = backgroundResourceID;
        } else {
            mResourceID = 0;
        }
        if (titleImageID != 0) {
            titleResourseID = titleImageID;
        } else {
            titleResourseID = 0;
        }
       //restoreToolbar();
    }

/*    public static void setItemCount(int count) {
        setUpTabs();

    }*/

    public static void setMyTitle(String title, int titleImageID, int backgroundResourceID) {
        mTitle = title;
        if (backgroundResourceID != 0) {
            mResourceID = backgroundResourceID;
        } else {
            mResourceID = 0;
        }
        if (titleImageID != 0) {
            titleResourseID = titleImageID;
        } else {
            titleResourseID = 0;
        }
        //restoreToolbar();
    }
   /* private static void restoreToolbar() {
        //toolbar = (Toolbar) findViewById(R.id.tool_bar);
        Log.d( TAG, "Restore Tool Bar" );
        if (actionBar != null) {
            Log.d( TAG, "Restore Action Bar not Null" );
            Log.d( TAG, "Title : " + mTitle );
            if (titleResourseID != 0) {
                toolbar_logo.setVisibility( View.VISIBLE );
                toolbar_title.setVisibility( View.GONE );
                toolbar_logo.setImageResource( titleResourseID );
            } else {
                toolbar_logo.setVisibility( View.GONE );
                toolbar_title.setVisibility( View.VISIBLE );
                toolbar_title.setText( mTitle );
            }

            if (mResourceID != 0) toolbar.setBackgroundResource( mResourceID );
            //actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled( true );
        }
    }*/

    public static void closeThisActivity() {
        if (activity != null) {
            activity.finish();
        }
    }
    public static void startLocationService() {
        activity.startService( locationintent );
    }

    public static void stopLocationService() {

        activity.stopService( locationintent );
    }


    public static void setNotification(boolean isNotify) {
        if (notification_layout != null) {
            /*final ImageView tv = (ImageView) notification_layout.findViewById(R.id.actionbar_badge_notification_textview);
            if(!isNotify){
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv.setVisibility(View.GONE);
                    }
                });

            }else{
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv.setVisibility(View.VISIBLE);
                    }
                });
            }*/
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        activity = this;
        mainContext = this;
        mainWindow = getWindow();

        mainActivityFM = getSupportFragmentManager();
        layoutInflater = activity.getLayoutInflater();
        globalFunctions = AppController.getInstance().getGlobalFunctions();
        globalVariables = AppController.getInstance().getGlobalVariables();
        analyticsReport = new AnalyticsReport();
       // nav_header=navigationHeaderView.findViewById(R.id.Profilebutton);


        arabic_language_tv=findViewById(R.id.arabic_language_tv);
        english_language_tv=findViewById(R.id.english_language_tv);
        arabic_language_iv=findViewById(R.id.arabic_language_iv);
        english_language_iv=findViewById(R.id.english_language_iv);
        iv_support=findViewById(R.id.iv_support);
        initialize();

        //drawer.closeDrawer(GravityCompat.START);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


      //  Drawable navIconDrawable = ResourcesCompat.getDrawable( getResources(), R.drawable.ic_group_menu, getTheme() );


        toolbar = ( Toolbar ) findViewById( R.id.tool_bar ); // Attaching the layout to the toolbar object
       //toolbar.setPadding(0, GlobalFunctions.getStatusBarHeight(mainContext), 0, 0);
      //  toolbar.setContentInsetsAbsolute( 0, 0 );
      //  toolbar.setNavigationIcon( navIconDrawable );
        ///toolbar_title = ( TextView ) toolbar.findViewById( R.id.toolbar_title );
       //toolbar_logo = ( ImageView ) toolbar.findViewById( R.id.tool_bar_logo );
      //  toolbar_title.setVisibility( View.GONE );
      //  toolbar_logo.setVisibility( View.VISIBLE );


        mainView=toolbar;
        setSupportActionBar( toolbar );
        actionBar = getSupportActionBar();
       // actionBar.setHomeAsUpIndicator( navIconDrawable );
        setOptionsMenuVisiblity( false );


        Drawable navIconDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_group_menu, getTheme());
        gravity = globalFunctions.getLanguage( mainContext ) == GlobalVariables.LANGUAGE.ARABIC ? GravityCompat.START : GravityCompat.START;
        drawer = ( DrawerLayout ) findViewById( R.id.drawer_layout );
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        toggle.setDrawerIndicatorEnabled( false );
        toggle.setHomeAsUpIndicator( navIconDrawable );
        toggle.setToolbarNavigationClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerVisible( gravity )) {
                    drawer.closeDrawer( gravity );
                } else {
                    drawer.openDrawer( gravity );
                }
            }
        } );
        drawer.addDrawerListener( toggle );
        toggle.syncState();

        navigationView = ( NavigationView ) findViewById( R.id.nav_view );
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            navigationView.setPointerIcon(PointerIcon.load(getResources(), R.drawable.ic_menu));
        }*/
        navigationView.setNavigationItemSelectedListener( this );
        navigationHeaderView = navigationView.getHeaderView( 0 );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags( WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS );
            window.clearFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS );
            window.setStatusBarColor( ContextCompat.getColor( this, R.color.black_trans ) );
        }

        String token = FirebaseInstanceId.getInstance().getToken();
        if (token != null) {
            PushNotificationModel pushNotificationModel = new PushNotificationModel();
            pushNotificationModel.setRegistration_id( token );
            sendPushNotificationID( mainContext, pushNotificationModel );
        }
        if (getIntent().hasExtra( BUNDLE_MAIN_NOTIFICATION_MODEL )) {
            notificationModel = ( NotificationModel ) getIntent().getSerializableExtra( BUNDLE_MAIN_NOTIFICATION_MODEL );
        } else {
            notificationModel = null;
        }
        accessPermissions( this );

        gravity = globalFunctions.getLanguage( mainContext ) == GlobalVariables.LANGUAGE.ARABIC ? GravityCompat.START : GravityCompat.START;



        mainActivityFM.addOnBackStackChangedListener( new FragmentManager.OnBackStackChangedListener() {

            @Override
            public void onBackStackChanged() {
                if (mainActivityFM != null) {
                    Fragment currentFragment = mainActivityFM.findFragmentById( R.id.container );
                    if (currentFragment != null) {
                        currentFragment.onResume();
                    }
                }
            }
        } );


        getUtility();


       // replaceFragmentWithAnimation(new FragmentAllProcess());
        Fragment AllFragment=new FragmentAllProcess();
        replaceFragment( AllFragment, FragmentAllProcess.TAG, getString( R.string.app_name ), 0, 0 );



    }

    private void getUtility() {
        //GlobalFunctions.showProgress( context, getString( R.string.loading ));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getSupport( mainContext, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
                //GlobalFunctions.hideProgress();
                Log.d( TAG, "Response : " + arg0.toString());
                UtilityMainModel utilityMainModel = (UtilityMainModel) arg0;
                UtilityModel utilityModel = utilityMainModel.getUtilityModel();
                setUpPage( utilityModel );

            }
            @Override
            public void OnFailureFromServer(String msg) {
               // GlobalFunctions.hideProgress();
                Log.d( TAG, "Failure : " + msg );
                GlobalFunctions.displayMessaage( mainContext, mainView, msg );
            }

            @Override
            public void OnError(String msg) {
               // GlobalFunctions.hideProgress();
                Log.d( TAG, "Error : " + msg );
                GlobalFunctions.displayMessaage( mainContext, mainView, msg );
            }
        }, "Utility" );
    }

    private void setUpPage(UtilityModel utilityModel) {
        if (utilityModel != null) {
            if (GlobalFunctions.isNotNullValue(utilityModel.getContact_number())) {
                iv_support.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GlobalFunctions.callPhone(activity, utilityModel.getContact_number());
                    }
                });
            }
        }
    }

    private void replaceFragment(@Nullable Fragment allFragment,@Nullable String tag,@Nullable String title, int titleImageID,@Nullable int bgResID) {
        if (allFragment != null && mainActivityFM != null) {
            Fragment tempFrag = mainActivityFM.findFragmentByTag( tag );
            if (tempFrag != null) {
//                mainActivityFM.beginTransaction().remove(tempFrag).commitAllowingStateLoss();
                mainActivityFM.beginTransaction().remove( tempFrag ).commit();
            }
            setTitle( title, titleImageID, bgResID );
            FragmentTransaction ft = mainActivityFM.beginTransaction();
            ft.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
            //ft.setCustomAnimations(R.anim.slide_out_left, R.anim.slide_out_right);
            ft.add( R.id.container, allFragment, tag ).addToBackStack( tag ).commitAllowingStateLoss();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       /* if(resultCode == globalVariables.REQUEST_SEARCH_ACTIVITY){
                if (data != null) {
                    KeyValueModel model = (KeyValueModel) data.getSerializableExtra(SearchActivity.BUNDLE_KEYVALUE_MODEL);
                    Fragment fragment = ProductListFragment.newInstance(model);
                    replaceFragment(fragment, ProductListFragment.TAG, getString(R.string.search_results), 0, 0);
            }
        }else {*/
        super.onActivityResult( requestCode, resultCode, data );
        /*}*/
    }

    private void accessPermissions(MainActivity mainActivity) {
        int permissionCheck_getAccounts = ContextCompat.checkSelfPermission( activity, android.Manifest.permission.GET_ACCOUNTS );
        int permissionCheck_callPhone = ContextCompat.checkSelfPermission( activity, android.Manifest.permission.CALL_PHONE );
        int permissionCheck_lockwake = ContextCompat.checkSelfPermission( activity, android.Manifest.permission.WAKE_LOCK );
        int permissionCheck_internet = ContextCompat.checkSelfPermission( activity, android.Manifest.permission.INTERNET );
        int permissionCheck_Access_internet = ContextCompat.checkSelfPermission( activity, android.Manifest.permission.ACCESS_NETWORK_STATE );
        int permissionCheck_Access_wifi = ContextCompat.checkSelfPermission( activity, android.Manifest.permission.ACCESS_WIFI_STATE );
        // int permissionCheck_External_storage = ContextCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        // int permissionCheck_cam = ContextCompat.checkSelfPermission(activity, android.Manifest.permission.CAMERA);

        if (permissionCheck_internet != PackageManager.PERMISSION_GRANTED || permissionCheck_Access_internet != PackageManager.PERMISSION_GRANTED || permissionCheck_callPhone != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale( activity, android.Manifest.permission.INTERNET ) && ActivityCompat.shouldShowRequestPermissionRationale( activity, android.Manifest.permission.ACCESS_NETWORK_STATE ) && ActivityCompat.shouldShowRequestPermissionRationale( activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE ) && ActivityCompat.shouldShowRequestPermissionRationale( activity, android.Manifest.permission.CAMERA ) || ActivityCompat.shouldShowRequestPermissionRationale( activity, android.Manifest.permission.CALL_PHONE )) {
                Fragment homeFragment = null;
                homeFragment = new FragmentAllProcess();
                mainActivityFM.beginTransaction().replace( R.id.container, homeFragment, "" ).commitAllowingStateLoss();
            } else {
                ActivityCompat.requestPermissions( activity, new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.INTERNET, android.Manifest.permission.ACCESS_NETWORK_STATE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CALL_PHONE}, globalVariables.PERMISSIONS_REQUEST_PRIMARY );
            }
        }
    }

    private void sendPushNotificationID(Context mainContext, PushNotificationModel pushNotificationModel) {
        if (globalFunctions.getSharedPreferenceString( globalVariables.SHARED_PREFERENCE_COOKIE ) != null) {
            ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
            servicesMethodsManager.sendPushNotificationID( mainContext, pushNotificationModel, new ServerResponseInterface() {
                @Override
                public void OnSuccessFromServer(Object arg0) {
                    Log.d( TAG, "Response : " + arg0.toString() );
                }

                @Override
                public void OnFailureFromServer(String msg) {
                    Log.d( TAG, "Failure : " + msg );
                }

                @Override
                public void OnError(String msg) {
                    Log.d( TAG, "Error : " + msg );
                }
            }, "Send_Push_Notification_ID" );
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onStart() {
      /*  if (upComingFragment != null) {
            Fragment fragment = upComingFragment.instantiate( activity, 0 );
            (( MyAppointmentsNewListFragment ) fragment).refreshList();
        }
        if (completedFragment != null) {
            Fragment fragment = completedFragment.instantiate( activity, 0 );
            (( MyAppointmentsConfirmedListFragment ) fragment).refreshList();
        }*/
        super.onStart();
    }

    @Override
    protected void onResume() {
        setNavigationHeaders();
        super.onResume();

    }

    public void setOptionsMenuVisiblity(boolean showMenu) {
        if (menu == null)
            return;
        //menu.setGroupVisible(R.id.menu, showMenu);
    }

    private void setNavigationHeaders() {
        if (navigationHeaderView != null && mainContext != null) {
            TextView
                    header_name_tv = ( TextView ) navigationHeaderView.findViewById( R.id.nav_tvUsefullname ),
                    header_tv_counrty_code = ( TextView ) navigationHeaderView.findViewById( R.id.nav_tv_country_code ),
                    header_email_tv = ( TextView ) navigationHeaderView.findViewById( R.id.nav_tvEmail );
              TextView
                      header_phone_tv = ( TextView ) navigationHeaderView.findViewById( R.id.nav_tv_userNumber );

              LinearLayout
                     nav_header=navigationHeaderView.findViewById(R.id.Profilebutton),
                     nav_header_orders=navigationHeaderView.findViewById(R.id.LinearOrders);


                    header_completed_order = ( TextView ) navigationHeaderView.findViewById( R.id.tv_total_completed_order );

                    nav_header_orders.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                             // Fragment completedOrderFragment=new CompletedOrderFragment();
                             // replaceFragment( completedOrderFragment, CompletedOrderFragment.TAG, getString( R.string.app_name ), 0, 0 );
                             //  replaceFragmentWithAnimation(new CompletedOrderFragment());
                        }
                    });


            if (globalFunctions.getLanguage(mainContext) == GlobalVariables.LANGUAGE.ENGLISH) {
                english_language_iv.setVisibility(View.VISIBLE);
                arabic_language_iv.setVisibility(View.GONE);
            } else {
                english_language_iv.setVisibility(View.GONE);
                arabic_language_iv.setVisibility(View.VISIBLE);
            }

            arabic_language_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (globalFunctions.getLanguage(mainContext) == GlobalVariables.LANGUAGE.ENGLISH) {
                        LanguageChange(mainContext);
//                    RestartEntireApp(mainContext, true);
                    } else {
                        ///nothing...
                    }
                    drawer.closeDrawer(gravity);
                }
            });

            english_language_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (globalFunctions.getLanguage(mainContext) == GlobalVariables.LANGUAGE.ENGLISH) {
                        //nothing
                    } else {
                        LanguageChange(mainContext);
//                    RestartEntireApp(mainContext, true);
                    }
                    drawer.closeDrawer(gravity);
                }
            });


              TextView
                      logout=navigationHeaderView.findViewById(R.id.Logout);

                                logout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                                            drawer.closeDrawer(GravityCompat.START);
                                            final AlertDialog alertDialog = new AlertDialog( activity );
                                            alertDialog.setCancelable( false );
                                            alertDialog.setIcon( R.mipmap.app_icon );
                                            alertDialog.setTitle( activity.getString( R.string.app_name ) );
                                            alertDialog.setMessage( activity.getResources().getString( R.string.appLogoutText ));
                                            alertDialog.setPositiveButton( activity.getString( R.string.yes ), new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    alertDialog.dismiss();
                                                    //Logout the Application
                                                    LogoutModel logoutModel=new LogoutModel();
                                                    logoutModel.setUuid(GlobalFunctions.getUniqueID(activity));
                                                    logoutUser( mainContext ,logoutModel);
                                                }
                                            } );
                                            alertDialog.setNegativeButton( activity.getString( R.string.cancel ), new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    alertDialog.dismiss();
                                                }
                                            } );

                                            alertDialog.show();
                                        }
                                    });





             ImageView
                    // header_close_iv = (ImageView) navigationHeaderView.findViewById(R.id.navigation_header_close_iv),
                    header_app_iv = ( ImageView ) navigationHeaderView.findViewById( R.id.nav_profile_image );

            nav_header.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);

                    Intent intent = new Intent( mainContext, ProfileMainActivity.class );
                    startActivity( intent );

                  //  Fragment profileFragment = null;
                   // profileFragment = ProfileFragment.newInstance();
                   // replaceFragment(profileFragment, ProfileFragment.TAG, "", 0, 0);

                    //  Fragment profilefragment=new ProfileFragment();
                    //  replaceFragment( profilefragment, ProfileFragment.TAG, getString( R.string.app_name ), 0, 0 );
                    //replaceFragmentWithAnimation(new ProfileFragment());
                }
            });


            ProfileModel profileModel = globalFunctions.getProfile( mainContext );
            if (profileModel != null && mainContext != null) {
                try {

                    String
                            fullName = profileModel.getFirstName() + " " + profileModel.getLastName();
                            header_name_tv.setText( fullName != null ? fullName : getString( R.string.guest ) );
                           header_email_tv.setText( profileModel.getEmail() != null ? profileModel.getEmail() : getString( R.string.email ) );
                           header_tv_counrty_code.setText( profileModel.getCountry_code() != null ? profileModel.getCountry_code() : getString( R.string.number ) );
                           header_phone_tv.setText( profileModel.getPhone() != null ? profileModel.getPhone() : getString( R.string.mobile_no) );
                           header_completed_order.setText(profileModel.getOrders()!=null ?profileModel.getOrders() :"0");

                    try {
                        if (profileModel.getProfileImg() != null || !profileModel.getProfileImg().equals( "null" ) || !profileModel.getProfileImg().equalsIgnoreCase( "" )) {
                            Picasso.with( mainContext ).load(profileModel.getProfileImg() ).placeholder( R.drawable.ic_baseline_person_24 ).into( header_app_iv );
                        }
                    } catch (Exception e) {
                    }

             /*       if (profileModel.getImage() != null ) {
                        Picasso.with( layoutInflater.getContext() ).load( profileModel.getImage() ).fit().centerInside()
                                .placeholder( R.drawable.ic_boys_icon )
                                .error( R.drawable.ic_boys_icon )
                                .into( header_app_iv );
                    }*/

                } catch (Exception exxx) {
                    Log.e( TAG, exxx.getMessage() );
                }

            } else {
                if (mainContext != null) {
                    try {
                        header_email_tv.setVisibility( View.GONE );
                        header_name_tv.setText( mainContext.getString( R.string.guest ) );
                    } catch (Exception exxx) {
                        Log.e( TAG, exxx.getMessage() );
                    }
                }
            }

            final DrawerLayout drawer = ( DrawerLayout ) findViewById( R.id.drawer_layout );

            /*header_app_iv.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replaceFragmentWithAnimation(new ProfileFragment());

                   *//* Intent intent = new Intent( mainContext, ProfileFragment.class );
                    startActivity( intent );*//*
                    drawer.closeDrawer( gravity );
                  *//*  Fragment homeFragment = new HomeFragment();
                    replaceFragment(homeFragment, HomeFragment.TAG, getString(R.string.app_name), 0, 0);
                    drawer.closeDrawer(gravity);*//*
                }
            } );*/

            /*header_name_tv.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent( mainContext, ProfileMainActivity.class );
                    startActivity( intent );
                    drawer.closeDrawer( gravity );
                }
            } );

            header_address_tv.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent( mainContext, ProfileMainActivity.class );
                    startActivity( intent );
                    drawer.closeDrawer( gravity );
                }
            } );
*/
        }
    }

    private void LanguageChange(Context mainContext) {
        ShowPopUpLanguage(mainContext);

    }

    private void ShowPopUpLanguage(Context mainContext) {
        final AlertDialog alertDialog = new AlertDialog(activity);
        alertDialog.setCancelable(false);
        alertDialog.setIcon(R.mipmap.app_icon);
        alertDialog.setTitle(activity.getString(R.string.app_name));
        alertDialog.setMessage(activity.getString(R.string.lang_change));
        alertDialog.setPositiveButton(activity.getString(R.string.ok), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                RestartEntireAppForChange(mainContext, true);
            }
        });

        alertDialog.setNegativeButton(activity.getString(R.string.cancel), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void RestartEntireAppForChange(Context mainContext, boolean isLanguageChange) {
        if (isLanguageChange) {
            SharedPreferences shared_preference = PreferenceManager.getDefaultSharedPreferences(this
                    .getApplicationContext());

            String mCustomerLanguage = shared_preference.getString(
                    globalVariables.SHARED_PREFERENCE_SELECTED_LANGUAGE, "null");
            String mCurrentlanguage;
            if ((mCustomerLanguage.equalsIgnoreCase("en"))) {
                globalFunctions.setLanguage(mainContext, GlobalVariables.LANGUAGE.ARABIC);

                mCurrentlanguage = "ar";
            } else {
                mCurrentlanguage = "en";
                globalFunctions.setLanguage(mainContext, GlobalVariables.LANGUAGE.ENGLISH);

            }
            SharedPreferences.Editor editor = shared_preference.edit();
            editor.putString(globalVariables.SHARED_PREFERENCE_SELECTED_LANGUAGE, mCurrentlanguage);
            editor.commit();
        }
        globalFunctions.closeAllActivities();
        Intent i = new Intent(this, SplashActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        System.exit(0);
    }

    public void RestartEntireApp(Context context, boolean isLanguageChange) {
        if (isLanguageChange) {
            SharedPreferences shared_preference = PreferenceManager.getDefaultSharedPreferences( this
                    .getApplicationContext() );

            String mCustomerLanguage = shared_preference.getString(
                    globalVariables.SHARED_PREFERENCE_SELECTED_LANGUAGE, "null" );
            String mCurrentlanguage;
            if ((mCustomerLanguage.equalsIgnoreCase( "en" ))) {
                globalFunctions.setLanguage( context, GlobalVariables.LANGUAGE.ARABIC );

                mCurrentlanguage = "ar";
            } else {
                mCurrentlanguage = "en";
                globalFunctions.setLanguage( context, GlobalVariables.LANGUAGE.ENGLISH );

            }
            SharedPreferences.Editor editor = shared_preference.edit();
            editor.putString( globalVariables.SHARED_PREFERENCE_SELECTED_LANGUAGE, mCurrentlanguage );
            editor.commit();
        }
        globalFunctions.closeAllActivities();
        Intent i = new Intent( this, SplashActivity.class );
        i.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK );
        startActivity( i );
        System.exit( 0 );
    }
    public void stimulateOnResumeFunction() {
        mainActivityFM.findFragmentById( R.id.container ).onResume();
    }

    public void releseFragments() {
        for (int i = 0; i < mainActivityFM.getBackStackEntryCount(); ++i) {
            mainActivityFM.popBackStack();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState( outState );
    }


    private void initialize() {

        iv_menu = findViewById(R.id.iv_menu);
        iv_share=findViewById(R.id.iv_share);
        tvCount=findViewById(R.id.tvCount);
        iv_home=findViewById(R.id.ivHome);
        iv_Notification=findViewById(R.id.ivNotification);
        tvHeaderText = findViewById(R.id.tvHeaderText);

        iv_menu.setOnClickListener(this);
        //drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = ( NavigationView ) findViewById( R.id.nav_view );
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            navigationView.setPointerIcon(PointerIcon.load(getResources(), R.drawable.ic_menu));
        }*/
      //  navigationView.setNavigationItemSelectedListener( this );
      //  navigationHeaderView = navigationView.getHeaderView( 0 );
        //navigationview
        //navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener( this );
       // navigationHeaderView=navigationView.getHeaderView(0);


       // Logout=navigationHeaderView.findViewById(R.id.Logout);


       /* nav_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

                Fragment profileFragment = null;
                profileFragment = ProfileFragment.newInstance();
                replaceFragment(profileFragment, ProfileFragment.TAG, "", 0, 0);

              //  Fragment profilefragment=new ProfileFragment();
              //  replaceFragment( profilefragment, ProfileFragment.TAG, getString( R.string.app_name ), 0, 0 );
                //replaceFragmentWithAnimation(new ProfileFragment());
            }
        });*/
        /*Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                final AlertDialog alertDialog = new AlertDialog( activity );
                alertDialog.setCancelable( false );
                alertDialog.setIcon( R.mipmap.app_icon );
                alertDialog.setTitle( activity.getString( R.string.app_name ) );
                alertDialog.setMessage( activity.getResources().getString( R.string.appLogoutText ));
                alertDialog.setPositiveButton( activity.getString( R.string.yes ), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        //Logout the Application
                        LogoutModel logoutModel=new LogoutModel();
                        logoutModel.setUuid(GlobalFunctions.getUniqueID(activity));
                        logoutUser( mainContext ,logoutModel);
                    }
                } );
                alertDialog.setNegativeButton( activity.getString( R.string.cancel ), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alertDialog.dismiss();
                    }
                } );

                alertDialog.show();
            }
        });
*/

    }

    private void logoutUser(final  Context mainContext,final LogoutModel logoutModel) {
        GlobalFunctions.showProgress( mainContext, getString( R.string.logingout ) );
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.logout( mainContext,logoutModel, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
                GlobalFunctions.hideProgress();
                Log.d( TAG, "Response : " + arg0.toString() );
                validateOutput( arg0 );
            }

            @Override
            public void OnFailureFromServer(String msg) {
                GlobalFunctions.hideProgress();
                Toast.makeText( mainContext, msg, Toast.LENGTH_SHORT ).show();
                //GlobalFunctions.displayMessaage(context, mainView, msg);
                Log.d( TAG, "Failure : " + msg );
            }

            @Override
            public void OnError(String msg) {
                GlobalFunctions.hideProgress();
                Toast.makeText( mainContext, msg, Toast.LENGTH_SHORT ).show();
                //GlobalFunctions.displayMessaage(context, mainView, msg);
                Log.d( TAG, "Error : " + msg );
            }
        }, "Logout_User" );
    }

    private void validateOutput(Object arg0) {
        if (arg0 instanceof StatusMainModel) {
            StatusMainModel statusMainModel = ( StatusMainModel ) arg0;
            StatusModel statusModel = statusMainModel.getStatusModel();
            //globalFunctions.displayMessaage(activity, mainView, statusModel.getMessage());
            if (statusMainModel.isStatus()) {
                /*Logout success, Clear all cache and reload the home page*/

                globalFunctions.logoutApplication( mainContext );
                GlobalFunctions.closeAllActivities();
                RestartEntireApp( mainContext, false );

            }
        }

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
          /*  case R.id.iv_menu:
                drawer.openDrawer(Gravity.LEFT);
                break;*/
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    public void replaceFragmentWithAnimation(Fragment fragment) {
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        //deRegisterBroadcast();
        super.onDestroy();
    }



    @Override
    public void onBackPressed() {
        if (mainActivityFM != null) {
            String currentFragment = mainActivityFM.findFragmentById( R.id.container ).getClass().getName();
            String homeFragmentName = FragmentAllProcess.class.getName();
            DrawerLayout drawer = ( DrawerLayout ) findViewById( R.id.drawer_layout );
            if (drawer.isDrawerOpen( GravityCompat.START )) {
                drawer.closeDrawers();
            } else if (!currentFragment.equalsIgnoreCase( homeFragmentName )) {
                Fragment homeFragment = homeFragment = new FragmentAllProcess();
                replaceFragment( homeFragment, FragmentAllProcess.TAG, getString( R.string.app_name ), 0, 0 );
            } else if (currentFragment.equalsIgnoreCase( homeFragmentName )) {
                /*Exit Alert Box*/
                final AlertDialog alertDialog = new AlertDialog( mainContext );
                alertDialog.setCancelable( false );
                alertDialog.setIcon( R.mipmap.app_icon );
                alertDialog.setTitle( getString( R.string.app_name ) );
                alertDialog.setMessage( getResources().getString( R.string.appExitText ) );
                alertDialog.setPositiveButton( getString( R.string.yes ), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        GlobalFunctions.closeAllActivities();
                        finishAffinity();
                        System.exit( 0 );
                    }
                } );
                alertDialog.setNegativeButton( getString( R.string.cancel ), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                } );

                alertDialog.show();

            } else {
                super.onBackPressed();
               stimulateOnResumeFunction();
            }
        } else {
            super.onBackPressed();
            stimulateOnResumeFunction();

        }
        //stimulateOnResumeFunction();
    }

}