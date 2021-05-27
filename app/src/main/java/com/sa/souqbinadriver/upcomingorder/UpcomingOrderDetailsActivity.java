package com.sa.souqbinadriver.upcomingorder;

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
import android.widget.LinearLayout;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sa.souqbinadriver.Activity.MainActivity;
import com.sa.souqbinadriver.R;
import com.sa.souqbinadriver.completedorder.CompletedOrderDetailsActivity;
import com.sa.souqbinadriver.global.GlobalFunctions;
import com.sa.souqbinadriver.global.GlobalVariables;
import com.sa.souqbinadriver.services.ServerResponseInterface;
import com.sa.souqbinadriver.services.ServicesMethodsManager;
import com.sa.souqbinadriver.services.model.OrderListModel;
import com.sa.souqbinadriver.services.model.OrderMainModel;
import com.sa.souqbinadriver.services.model.OrderModel;
import com.sa.souqbinadriver.services.model.ProfileMainModel;
import com.sa.souqbinadriver.services.model.ProfileModel;
import com.vlonjatg.progressactivity.ProgressLinearLayout;

import java.util.ArrayList;
import java.util.List;


public class UpcomingOrderDetailsActivity extends AppCompatActivity {
    public static final String
            TAG = "UpcomingOrderDetailsActivity",
            BUNDLE_UPCOMING_ORDER_DETAILS_MODEL = "UpcomingOrderDetailsActivity";

    Context context = null;
    static Activity activity = null;
    static Window window = null;
    View mainView;
    static FragmentManager fragmentManager = null;

    static Toolbar toolbar;
    static ActionBar actionBar;
    static String mTitle;
    static int mResourceID, titleResourseID;
    static TextView toolbar_title;

    static ImageView toolbar_logo, tool_bar_back_icon;

    TextView tvSchedule_date;
    TextView tv_VendorName,tv_vendorMMobile,tv_vendorCall;
    TextView tv_CustomerName,tv_CustomerMMobile,tv_CustomerCall;
    TextView tv_pickAddress,tv_dropAddress,tv_pickDirection,tv_dropDirection;

    ProgressLinearLayout progressActivity;
    GlobalFunctions globalFunctions;
    GlobalVariables globalVariables;
    SwipeRefreshLayout swipe_container;
    List<OrderModel> list = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;

    OrderListModel detail = null;
    int index=0;

    LinearLayout upcomingOrder;
    RecyclerView recyclerView;
    private List<OrderModel> allUpcomingOrders;
   // private UpcomingOrderFragment.GetAllUpcomingOrderAdapter adapter;
    private boolean shouldRefreshOnResume = false;

    View view;

    public static Intent newInstance(Activity activity, OrderModel model) {
        Intent intent = new Intent( activity, UpcomingOrderDetailsActivity.class );
        Bundle bundle = new Bundle();
        bundle.putSerializable( BUNDLE_UPCOMING_ORDER_DETAILS_MODEL, model );
        intent.putExtras( bundle );
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upcoming_order_details);

        context = this;
        activity = this;
        fragmentManager = getSupportFragmentManager();
        window = getWindow();

        tvSchedule_date=findViewById(R.id.tv_Schedule_date_time);
        tv_VendorName=findViewById(R.id.tv_vendor_name);
        tv_vendorMMobile=findViewById(R.id.tv_vendor_mobile);
        tv_vendorCall=findViewById(R.id.tv_vendor_call);
        tv_CustomerName=findViewById(R.id.tv_customer_name);
        tv_CustomerMMobile=findViewById(R.id.tv_customer_mobile_number);
        tv_CustomerCall=findViewById(R.id.tv_call_customer);
        tv_pickAddress=findViewById(R.id.tv_pick_address);
        tv_dropAddress=findViewById(R.id.tv_drop_address);
        tv_pickDirection=findViewById(R.id.tv_pick_direction);
        tv_dropDirection=findViewById(R.id.tv_drop_direction);
        //tv_dropAddress=findViewById(R.id.tv_vendor_name);

        mainView=toolbar;


        getOrderDetails();


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


       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //enable translucent statusbar via flags
            GlobalFunctions.setTranslucentStatusFlag(window, true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //we don't need the translucent flag this is handled by the theme
            GlobalFunctions.setTranslucentStatusFlag(window, true);
            //set the statusbarcolor transparent to remove the black shadow
            this.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }*/

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

    private void getOrderDetails() {
      /*//  GlobalFunctions.showProgress( context, getString( R.string.loading ));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getorderDetails( context, new ServerResponseInterface() {
            @SuppressLint("LongLogTag")
            @Override
            public void OnSuccessFromServer(Object arg0) {
                GlobalFunctions.hideProgress();
                Log.d( TAG, "Response : " + arg0.toString());
                OrderMainModel orderMainModel=(OrderMainModel) arg0;
                 OrderListModel orderListModel=orderMainModel.getOrderListModel();
                 //OrderModel orderModel=(OrderModel)arg0;
                // orderModel.setOrder_id(orderModel.getOrder_id());
              //   OrderListModel orderListModel=orderModel.getOrder_id();

                setOrderDetails( orderListModel );
            }

            @SuppressLint("LongLogTag")
            @Override
            public void OnFailureFromServer(String msg) {
                GlobalFunctions.hideProgress();

                Log.d( TAG, "Failure : " + msg );
                GlobalFunctions.displayMessaage( context, mainView, msg );
            }

            @SuppressLint("LongLogTag")
            @Override
            public void OnError(String msg) {
                GlobalFunctions.hideProgress();
                Log.d( TAG, "Error : " + msg );
                GlobalFunctions.displayMessaage( context, mainView, msg );
            }
        }, "Order List" );*/
    }


    private void setOrderDetails(OrderListModel orderListModel) {
      /*  if (context != null && isAdded()) {
            detail = GlobalFunctions.get( context );
            //setThisPage();
        }*/
    }



}
