package com.sa.souqbinadriver.upcomingorder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.sa.souqbinadriver.Activity.MainActivity;
import com.sa.souqbinadriver.R;
import com.sa.souqbinadriver.global.GlobalFunctions;
import com.sa.souqbinadriver.global.GlobalVariables;
import com.sa.souqbinadriver.services.ServerResponseInterface;
import com.sa.souqbinadriver.services.ServicesMethodsManager;
import com.sa.souqbinadriver.services.model.OrderDetailMainModel;
import com.sa.souqbinadriver.services.model.OrderListModel;
import com.sa.souqbinadriver.services.model.OrderModel;
import com.squareup.picasso.Picasso;
import com.vlonjatg.progressactivity.ProgressLinearLayout;

import java.util.ArrayList;
import java.util.List;


public class UpcomingOrderDetailsActivity extends AppCompatActivity {
    public static final String
            TAG = "UpcomingOrderDetailsActivity",
            BUNDLE_UPCOMING_ORDER_DETAILS_MODEL = "UpcomingOrderDetailsActivity",
             TAG2 = "UpcomingOrdescriptionActivity";

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
    ImageView vendor_image, customer_image;

    TextView tvSchedule_date;
    TextView tv_VendorName, tv_vendorMMobile, tv_vendorCall;
    TextView tv_CustomerName, tv_CustomerMMobile, tv_CustomerCall;
    TextView tv_pickAddress, tv_dropAddress, tv_pickDirection, tv_dropDirection;

    ProgressLinearLayout progressActivity;
    RecyclerView recyclerView;
    RecyclerView update_status_recyclerview;
    RelativeLayout RlStatusUpdate;
    GlobalFunctions globalFunctions;
    GlobalVariables globalVariables;
    RelativeLayout bottomSheet;
    SwipeRefreshLayout swipe_container;
    List<OrderModel> list = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    private UpcomingOrderProductDescriptionListAdapter adapter;
    private UpdateStatusAdapter statusAdapter;

    RelativeLayout rl_status_update;
    TextView tvUpdateStatus;
    BottomSheetBehavior behavior;

    OrderListModel detail = null;
    int index = 0;
    String order_vendor_product_id = null;

    LinearLayout upcomingOrder;
    private List<OrderModel> allUpcomingOrders;
    // private UpcomingOrderFragment.GetAllUpcomingOrderAdapter adapter;
    private boolean shouldRefreshOnResume = false;
    OrderModel orderModel = null;

    View view;

    public static Intent newInstance(Activity activity, OrderModel model) {
        Intent intent = new Intent(activity, UpcomingOrderDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_UPCOMING_ORDER_DETAILS_MODEL, model);
        intent.putExtras(bundle);
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

        tvSchedule_date = findViewById(R.id.tv_Schedule_date_time);
        tv_VendorName = findViewById(R.id.tv_vendor_name);
        tv_vendorMMobile = findViewById(R.id.tv_vendor_mobile);
        tv_vendorCall = findViewById(R.id.tv_vendor_call);
        tv_CustomerName = findViewById(R.id.tv_customer_name);
        tv_CustomerMMobile = findViewById(R.id.tv_customer_mobile_number);
        tv_CustomerCall = findViewById(R.id.tv_call_customer);
        tv_pickAddress = findViewById(R.id.tv_pick_address);
        tv_dropAddress = findViewById(R.id.tv_drop_address);
        tv_pickDirection = findViewById(R.id.tv_pick_direction);
        tv_dropDirection = findViewById(R.id.tv_drop_direction);
        vendor_image = findViewById(R.id.vendor_image);
        customer_image = findViewById(R.id.tv_customer_image);
        progressActivity =findViewById( R.id.details_progressActivity );
        swipe_container = findViewById( R.id.swipe_container );
        linearLayoutManager = new LinearLayoutManager( activity );


        rl_status_update=findViewById(R.id.rl_status_update);
        update_status_recyclerview=findViewById(R.id.statusupdate_recyclerview_list);
        tvUpdateStatus=findViewById(R.id.tv_update_status);
        //rl_status_update=findViewById(R.id.RlStatus);
        View bottomSheet = findViewById(R.id.bottom_sheet);
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // React to state change
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // React to dragging events
            }
        });

        rl_status_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                if(behavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }

            }
        });

        recyclerView=findViewById(R.id.product_desc_recyclerview);
        recyclerView.setAdapter(adapter);
      // Log.e("adapter count",""+recyclerView.getAdapter().getItemCount()) ;


        mainView = tvSchedule_date;

        swipe_container.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productDescription();
            }
        } );


        if (getIntent().hasExtra(BUNDLE_UPCOMING_ORDER_DETAILS_MODEL)) {
            orderModel = (OrderModel) getIntent().getSerializableExtra(BUNDLE_UPCOMING_ORDER_DETAILS_MODEL);
        } else {
            orderModel = null;
        }

        if (orderModel != null) {
            if (GlobalFunctions.isNotNullValue(orderModel.getOrder_vendor_product_id())) {
                order_vendor_product_id = orderModel.getOrder_vendor_product_id();
            }
        }




        //getting order details
        getOrderDetails();


        //set product description in recyclerview
        productDescription();


        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        // toolbar.setPadding(0, GlobalFunctions.getStatusBarHeight(context), 0, 0);
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
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black_trans));
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

    private void productDescription() {
        //GlobalFunctions.showProgress( context, getString( R.string.loading ));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getorderDetails(context, order_vendor_product_id, new ServerResponseInterface() {
            @SuppressLint("LongLogTag")
            @Override
            public void OnSuccessFromServer(Object arg0) {
                GlobalFunctions.hideProgress();
                if (swipe_container.isRefreshing()) {
                    swipe_container.setRefreshing( false );
                }
                Log.d(TAG2, "Response : " + arg0.toString());
                OrderDetailMainModel orderMainModel = (OrderDetailMainModel) arg0;
                OrderModel orderModel = orderMainModel.getOrderModel();

                setupProductList(orderModel);
            }

            @SuppressLint("LongLogTag")
            @Override
            public void OnFailureFromServer(String msg) {
                GlobalFunctions.hideProgress();
                if (swipe_container.isRefreshing()) {
                    swipe_container.setRefreshing( false );
                }
                Log.d(TAG2, "Failure : " + msg);
                GlobalFunctions.displayMessaage(context, mainView, msg);
            }

            @SuppressLint("LongLogTag")
            @Override
            public void OnError(String msg) {
                GlobalFunctions.hideProgress();
                if (swipe_container.isRefreshing()) {
                    swipe_container.setRefreshing( false );
                }
                Log.d(TAG2, "Error : " + msg);
                GlobalFunctions.displayMessaage(context, mainView, msg);
            }
        }, "Order List");

    }



    private void setupProductList(OrderModel orderModel) {
        if (orderModel != null && list != null) {
            list.clear();
            list.addAll( orderModel.getOrderModelList());
            if (adapter != null) {
                synchronized (adapter) {
                    adapter.notifyDataSetChanged();
                }
            }
            if (list.size() <= 0) {
                showEmptyPage();
            } else {
                showContent();
                initRecyclerView();
            }
        }
    }

    private void initRecyclerView() {
        adapter = new UpcomingOrderProductDescriptionListAdapter(list, activity);
        recyclerView.setLayoutManager( linearLayoutManager );
        recyclerView.setAdapter( adapter );
        recyclerView.setHasFixedSize( true );
    }

    private void showContent() {
        if (progressActivity != null) {
            progressActivity.showContent();
        }
    }

    private void showEmptyPage() {
        if (progressActivity != null) {
            progressActivity.showEmpty(getResources().getDrawable(R.drawable.ic_group_no_order), getString(R.string.emptyList),
                    getString(R.string.no_product));
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        shouldRefreshOnResume = true;
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
        //GlobalFunctions.showProgress( context, getString( R.string.loading ));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getorderDetails(context, order_vendor_product_id, new ServerResponseInterface() {
            @SuppressLint("LongLogTag")
            @Override
            public void OnSuccessFromServer(Object arg0) {
                GlobalFunctions.hideProgress();
                Log.d(TAG, "Response : " + arg0.toString());
                OrderDetailMainModel orderMainModel = (OrderDetailMainModel) arg0;
                OrderModel orderModel = orderMainModel.getOrderModel();
                //  OrderModel orderModel=(OrderModel)arg0;
                // orderModel.setOrder_id(orderModel.getOrder_vendor_product_id());
                //   OrderListModel orderListModel=orderModel.getOrder_id();

                setOrderDetails(orderModel);
            }

            @SuppressLint("LongLogTag")
            @Override
            public void OnFailureFromServer(String msg) {
                GlobalFunctions.hideProgress();

                Log.d(TAG, "Failure : " + msg);
                GlobalFunctions.displayMessaage(context, mainView, msg);
            }

            @SuppressLint("LongLogTag")
            @Override
            public void OnError(String msg) {
                GlobalFunctions.hideProgress();
                Log.d(TAG, "Error : " + msg);
                GlobalFunctions.displayMessaage(context, mainView, msg);
            }
        }, "Order List");
    }
    @Override
    public void onPause() {
        super.onPause();
        if (getFragmentManager().findFragmentByTag( TAG ) != null)
            getFragmentManager().findFragmentByTag( TAG ).setRetainInstance( true );
    }

    @Override
    public void onStart() {

       /* if(hint != null) {
            hint.launchAutomaticHintForCall(activity.findViewById(R.id.action_call));
        }*/
//       globalFunctions.launchAutomaticHintForSearch(mainView, getString(R.string.search_title),  getString(R.string.search_description));
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



    @SuppressLint("LongLogTag")
    private void setOrderDetails(OrderModel orderModel) {
        if (orderModel != null && context != null) {
            if (GlobalFunctions.isNotNullValue(orderModel.getScheduled_for())) {
                tvSchedule_date.setText(GlobalFunctions.getDateFormat(orderModel.getScheduled_for()));
            }
            if (GlobalFunctions.isNotNullValue(orderModel.getVendor_image())) {
                Picasso.with(context).load(orderModel.getVendor_image()).placeholder(R.drawable.mask_group_details).into(vendor_image);


            }
                String
                        fullName = orderModel.getVendor_fname() + " " + orderModel.getVendor_lname();
                        tv_VendorName.setText(fullName);


            }
            if (GlobalFunctions.isNotNullValue(orderModel.getVendor_number())) {
                tv_vendorMMobile.setText(orderModel.getVendor_number());
                tv_vendorCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+orderModel.getVendor_number()));
                        startActivity(intent);

                    }
                });
            }


            if (GlobalFunctions.isNotNullValue(orderModel.getPickup_address())) {
                tv_pickAddress.setText(orderModel.getPickup_address());
                tv_pickDirection.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tackPickLocation();

                    }
                });
            }


        if (GlobalFunctions.isNotNullValue(orderModel.getDrop_address())) {
                tv_dropAddress.setText(orderModel.getDrop_address());
                tv_dropDirection.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tackDropLocation();

                    }
                });
            }
            if (GlobalFunctions.isNotNullValue(orderModel.getUser_image())) {
                Picasso.with(context).load(orderModel.getUser_image()).placeholder(R.drawable.ic_baseline_person_24).into(customer_image);
            }
                String
                        fullName = orderModel.getUser_fname() + " " + orderModel.getUser_lname();
                        tv_CustomerName.setText(fullName);

            if (GlobalFunctions.isNotNullValue(orderModel.getUser_number())) {
                tv_CustomerMMobile.setText(orderModel.getUser_number());
                tv_CustomerCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+orderModel.getUser_number()));
                        startActivity(intent);
                    }
                });
            }


    }

    private void tackDropLocation() {
        try {
            Uri uri=Uri.parse("https://www.google.co.in/maps/dir/"+""+"/"+orderModel.getDrop_address());
            Intent intent=new Intent(Intent.ACTION_VIEW,uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch (ActivityNotFoundException e){
            Uri uri=Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps,maps");
            Intent intent=new Intent(Intent.ACTION_VIEW,uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }
    }

    private void tackPickLocation() {
        try {
            Uri uri=Uri.parse("https://www.google.co.in/maps/dir/"+""+"/"+orderModel.getPickup_address());
            Intent intent=new Intent(Intent.ACTION_VIEW,uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch (ActivityNotFoundException e){
            Uri uri=Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps,maps");
            Intent intent=new Intent(Intent.ACTION_VIEW,uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }
    }

}
