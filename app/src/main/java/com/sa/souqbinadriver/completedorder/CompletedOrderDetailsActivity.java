package com.sa.souqbinadriver.completedorder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.sa.souqbinadriver.Activity.MainActivity;
import com.sa.souqbinadriver.R;
import com.sa.souqbinadriver.global.GlobalFunctions;
import com.sa.souqbinadriver.services.model.OrderModel;


public class CompletedOrderDetailsActivity extends AppCompatActivity {

    public static final String
            TAG = "CompletedOrderDetailsActivity",
            BUNDLE_COMPLETED_ORDER_DETAILS_MODEL = "BundleCompletedOrderDetailsActivity";


    ImageView tool_bar_back_icon, tool_bar_icon;
    private GlobalFunctions
            globalFunctions;
    static FragmentManager fragmentManager = null;


    Context context = null;
    static Activity activity = null;
    static Window window = null;
    View mainView;

    static Toolbar toolbar;
    static ActionBar actionBar;
    static String mTitle;
    static int mResourceID, titleResourseID;
    static TextView toolbar_title;

    // private static Activity activity;
    //Context context;

    View view;

    public static Intent newInstance(Activity activity, OrderModel model) {
        Intent intent = new Intent( activity, CompletedOrderDetailsActivity.class );
        Bundle bundle = new Bundle();
        bundle.putSerializable( BUNDLE_COMPLETED_ORDER_DETAILS_MODEL, model );
        intent.putExtras( bundle );
        return intent;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.completed_order_details);

        context = this;
        activity = this;
        window = getWindow();
        fragmentManager = getSupportFragmentManager();


        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        // toolbar.setPadding(0, GlobalFunctions.getStatusBarHeight(context), 0, 0);
        // toolbar.setNavigationIcon(R.drawable.ic_back);
        tool_bar_back_icon = (ImageView) toolbar.findViewById(R.id.tool_bar_back_icon);
        //toolbar.setContentInsetsAbsolute(0, 0);
        toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        /*actionBar.setShowHideAnimationEnabled(true);*/

        tool_bar_back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags( WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS );
            window.clearFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS );
            window.setStatusBarColor( ContextCompat.getColor( this, R.color.black_trans ) );
        }


        setTitle(getString(R.string.completed_order_details), 0, 0);

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
        restoreToolbar();
    }

    @SuppressLint("LongLogTag")
    private static void restoreToolbar() {
        //toolbar = (Toolbar) findViewById(R.id.tool_bar);
        Log.d(TAG, "Restore Tool Bar");
        if (actionBar != null) {
            Log.d(TAG, "Restore Action Bar not Null");
            Log.d(TAG, "Title : " + mTitle);
            toolbar_title.setText(mTitle);
            if (mResourceID != 0) toolbar.setBackgroundResource(mResourceID);
            //actionBar.setTitle("");
           // actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

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
