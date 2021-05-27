package com.sa.souqbinadriver.profile;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.sa.souqbinadriver.Activity.AppController;
import com.sa.souqbinadriver.R;
import com.sa.souqbinadriver.global.GlobalFunctions;
import com.sa.souqbinadriver.global.GlobalVariables;
import com.sa.souqbinadriver.services.ServerResponseInterface;
import com.sa.souqbinadriver.services.ServicesMethodsManager;
import com.sa.souqbinadriver.services.model.OrderSubmitModel;
import com.sa.souqbinadriver.services.model.ProfileModel;
import com.sa.souqbinadriver.services.model.StatusModel;
import com.sa.souqbinadriver.view.AlertDialog;


public class ProfileMainActivity extends AppCompatActivity {

    public static String TAG = "ProfileMainActivity";
    public static final String BUNDLE_CITY_HOUSE_TYPE_MAIN_ACTIVITY_ORDER_SUBMIT_MODEL = "BundleCityHouseTypeMainActivityOrderSubmitModel";
    public static Context context = null;
    static Activity activity = null;
    static FragmentManager fragmentManager = null;
    static Window mainWindow = null;
    static Toolbar toolbar;
    static ActionBar actionBar;
    static String mTitle;
    static int mResourceID;
    static int titleResourseID;
    public Menu menu;
    static TextView toolbar_title;
    static ImageView toolbar_logo, tool_bar_back_icon;
    public static RelativeLayout cart_notification_layout, notification_layout;

    static int mToolbarHeight;

    static ValueAnimator mVaActionBar;

    GlobalFunctions globalFunctions;
    GlobalVariables globalVariables;
    View mainView;
    FrameLayout container = null;


   /* static Activity activity =null;

    Context context = null;
    FragmentManager fragmentManager=null;
    Window mainWindow = null;

    Toolbar toolbar;
    ActionBar actionBar;
    String mTitle;
    int mResourceID,titleResourseID;
    TextView toolbar_title;
    static ImageView toolbar_logo;
    AppBarLayout appBarLayout;
    Menu menu;

    View mainView = null;

    FrameLayout container = null;*/

    public static Intent newInstance(Context context, ProfileModel profileModel) {
        Intent intent = new Intent(context,ProfileMainActivity.class);
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_CITY_HOUSE_TYPE_MAIN_ACTIVITY_ORDER_SUBMIT_MODEL, profileModel);
        intent.putExtras(args);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_main);

        context = this;
        activity = this;
        fragmentManager = getSupportFragmentManager();
        mainWindow = getWindow();


        globalFunctions = AppController.getInstance().getGlobalFunctions();
        globalVariables = AppController.getInstance().getGlobalVariables();

       /* if(FirebaseInstanceId.getInstance().getToken()!=null){
            PushNotificationModel pushNotificationModel = new PushNotificationModel();
            pushNotificationModel.setRegistration_id(FirebaseInstanceId.getInstance().getToken());
            sendPushNotificationID(mainContext, pushNotificationModel);
        }*/
/*
        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
//        toolbar.setPadding(0, GlobalFunctions.getStatusBarHeight(context), 0, 0);
        //  toolbar.setNavigationIcon(R.drawable.ic_logo);
        toolbar.setContentInsetsAbsolute(0, 0);
        toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbar_logo = (ImageView) toolbar.findViewById(R.id.tool_bar_logo);

        mainView = toolbar;


        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
//        actionBar.setShowHideAnimationEnabled(true);
//        setTitle("Home", R.drawablelogin_logo.home_bg_nav);
        setOptionsMenuVisiblity(false);*/

        //Drawable navIconDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_back, getTheme());

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        //toolbar.setPadding(0, GlobalFunctions.getStatusBarHeight(mainContext), 0, 0);
        toolbar.setContentInsetsAbsolute(0, 0);
       // toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
       // toolbar_logo = (ImageView) toolbar.findViewById(R.id.tool_bar_logo);
        tool_bar_back_icon = (ImageView) toolbar.findViewById(R.id.tool_bar_back_icon);

        tool_bar_back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mainView = toolbar;

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
       // actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
      //  setOptionsMenuVisiblity(false);

        container = findViewById(R.id.profile_main_container);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags( WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS );
            window.clearFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS );
            window.setStatusBarColor( ContextCompat.getColor( this, R.color.black_trans ) );
        }

/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            GlobalFunctions.setTranslucentStatusFlag(mainWindow, true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            GlobalFunctions.setTranslucentStatusFlag(mainWindow, true);
            this.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
*/

        //setTitle(getString(R.string.my_profile), 0, 0);


        Fragment profileFragment = null;
        profileFragment = ProfileFragment.newInstance();
        replaceFragment(profileFragment, ProfileFragment.TAG, getString(R.string.my_profile), 0, 0);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        setTitleResourseID(0);
        super.onResume();
    }

    public void setOptionsMenuVisiblity(boolean showMenu){
        if(menu == null)
            return;
        //menu.setGroupVisible(R.id.menu, showMenu);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void setTitle(String title, int titleImageID, int backgroundResourceID){
        mTitle = title;
        if(backgroundResourceID!=0){mResourceID = backgroundResourceID;}else{mResourceID = 0;}
        if(titleImageID!=0){titleResourseID = titleImageID;}else{titleResourseID = 0;}
        restoreToolbar();
    }

    public int getTitleResourseID() {
        return titleResourseID;
    }

    public void setTitleResourseID(int titleResourseID) {
        this.titleResourseID = titleResourseID;
        restoreToolbar();
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_nothing, menu);
        this.menu = menu;
        setOptionsMenuVisiblity(false);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_nothing, menu);
        this.menu = menu;
        setOptionsMenuVisiblity(false);

        return true;
    }*/

  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                this.onBackPressed();
//                Intent mainIntent = new Intent(activity, MainActivity.class);
//                activity.startActivity(mainIntent);
                break;
          *//*  case R.id.action_edit_profile:
                Intent intent = new Intent(activity, EditProfileActivity.class);
                activity.startActivity(intent);
                break;*//*
        }
        return false;
    }*/

    private void logout() {
        final AlertDialog alertDialog = new AlertDialog(activity);
        alertDialog.setCancelable(false);
        alertDialog.setIcon(R.mipmap.app_icon);
        alertDialog.setTitle(activity.getString(R.string.app_name));
        alertDialog.setMessage(activity.getResources().getString(R.string.appLogoutText));
        alertDialog.setPositiveButton(activity.getString(R.string.yes), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                /*Logout the Application*/
               // logoutUser(context);
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

    public void replaceFragment (@NonNull Fragment fragment, @NonNull String tag, @NonNull String title, int titleImageID, @NonNull int bgResID){
        if(fragment!=null && fragmentManager!=null){
            Fragment tempFrag = fragmentManager.findFragmentByTag(tag);
            if(tempFrag!=null){fragmentManager.beginTransaction().remove(tempFrag).commit();}
            setTitle(title, titleImageID, bgResID);
            FragmentTransaction ft = fragmentManager.beginTransaction();
            //ft.setCustomAnimations(R.anim.slide_out_left, R.anim.slide_out_right);
            ft.add(container.getId(),fragment,tag).addToBackStack(tag).commitAllowingStateLoss();
        }
    }

    public void addFragment (@NonNull Fragment fragment, @NonNull String tag, @NonNull String title, int titleImageID, @NonNull int bgResID){
        if(fragment!=null && fragmentManager!=null){
            Fragment tempFrag = fragmentManager.findFragmentByTag(tag);
            if(tempFrag!=null){fragmentManager.beginTransaction().remove(tempFrag).commit();}
            setTitle(title, titleImageID, bgResID);
            FragmentTransaction ft = fragmentManager.beginTransaction();
            //ft.setCustomAnimations(R.anim.slide_out_left, R.anim.slide_out_right);
            ft.add(container.getId(),fragment,tag).addToBackStack(tag).commitAllowingStateLoss();
        }
    }

    public void overlayFragment (@NonNull Fragment fragment, @NonNull String tag, @NonNull String title, int titleImageID, @NonNull int bgResID){
        if(fragment!=null && fragmentManager!=null){
            Fragment tempFrag = fragmentManager.findFragmentByTag(tag);
            if(tempFrag!=null){fragment=tempFrag;}
            setTitle(title, titleImageID, bgResID);
            FragmentTransaction ft = fragmentManager.beginTransaction();
            //ft.setCustomAnimations(R.anim.slide_in_left, 0);
            ft.add(container.getId(),fragment,tag).addToBackStack(tag).commitAllowingStateLoss();
        }
    }


    /*public void stimulateOnResumeFunction(){
        Fragment fragment = fragmentManager.findFragmentById(R.id.vehicle_container);
        if(fragment!=null)fragment.onResume();
    }*/

    public void releseFragments(){
        for(int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
            fragmentManager.popBackStack();
        }
    }

    private void  logoutUser(final Context context){
       /* GlobalFunctions.showProgress(context, getString(R.string.logingout));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.logout(context, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
                GlobalFunctions.hideProgress();
                Log.d(TAG, "Response : "+ arg0.toString());
                validateOutput(arg0);
            }

            @Override
            public void OnFailureFromServer(String msg) {
                GlobalFunctions.hideProgress();
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                //GlobalFunctions.displayMessaage(context, mainView, msg);
                Log.d(TAG, "Failure : "+ msg);
            }

            @Override
            public void OnError(String msg) {
                GlobalFunctions.hideProgress();
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                //GlobalFunctions.displayMessaage(context, mainView, msg);
                Log.d(TAG, "Error : "+ msg);
            }
        }, "Logout_User");*/
    }

    private void validateOutput(Object model){
        if(model instanceof StatusModel){
            StatusModel statusModel = (StatusModel) model;
            GlobalFunctions.displayMessaage(context, mainView, statusModel.getMessage());
            if(statusModel.isStatus()){
                /*Logout success, Clear all cache and reload the home page*/

            }
            GlobalFunctions.logoutApplication(context);
            //MainActivity.RestartEntireApp(context);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private static void restoreToolbar() {
        //toolbar = (Toolbar) findViewById(R.id.tool_bar);
        Log.d(TAG, "Restore Tool Bar");
        if (actionBar != null) {
            Log.d(TAG, "Restore Action Bar not Null");
            Log.d(TAG, "Title : " + mTitle);
            if (titleResourseID != 0) {
               // toolbar_logo.setVisibility(View.VISIBLE);
                toolbar_title.setVisibility(View.GONE);
                //toolbar_logo.setImageResource(titleResourseID);
            } else {
              //  toolbar_logo.setVisibility(View.GONE);
                toolbar_title.setVisibility(View.VISIBLE);
                toolbar_title.setText(mTitle);
            }


            if (mResourceID != 0) toolbar.setBackgroundResource(mResourceID);
           // actionBar.setTitle("");
           //actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public void onBackPressed() {
       /* if (fragmentManager.getBackStackEntryCount() <= 1) {
            closeThisActivity();
        }*/
        closeThisActivity();
        super.onBackPressed();
//        stimulateOnResumeFunction();
    }

    public static void closeThisActivity(){
        if(activity!=null){
            activity.finish();
        }
    }

    @Override
    protected void onDestroy() {
        if(activity!=null)activity = null;
        super.onDestroy();
    }
}

