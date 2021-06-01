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
import android.view.LayoutInflater;
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
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.sa.souqbinadriver.Activity.AppController;
import com.sa.souqbinadriver.BackgroundServive.GPSTracker;
import com.sa.souqbinadriver.R;
import com.sa.souqbinadriver.global.GlobalFunctions;
import com.sa.souqbinadriver.global.GlobalVariables;
import com.sa.souqbinadriver.services.ServerResponseInterface;
import com.sa.souqbinadriver.services.ServicesMethodsManager;
import com.sa.souqbinadriver.services.model.LatlongMainModel;
import com.sa.souqbinadriver.services.model.LatlongModel;
import com.sa.souqbinadriver.services.model.OrderDetailMainModel;
import com.sa.souqbinadriver.services.model.OrderListModel;
import com.sa.souqbinadriver.services.model.OrderModel;
import com.sa.souqbinadriver.services.model.OrderStatus;
import com.sa.souqbinadriver.services.model.OrderStatusListModel;
import com.sa.souqbinadriver.services.model.OrderStatusMainModel;
import com.sa.souqbinadriver.services.model.OrderStatusUpdateModel;
import com.sa.souqbinadriver.services.model.StatusMainModel;
import com.sa.souqbinadriver.services.model.StatusModel;
import com.squareup.picasso.Picasso;
import com.vlonjatg.progressactivity.ProgressLinearLayout;

import java.util.ArrayList;
import java.util.List;


public class UpcomingOrderDetailsActivity extends AppCompatActivity  implements  UpdateStatusInterface{
    public static final String
            TAG = "UpcomingOrderDetailsActivity",
            BUNDLE_UPCOMING_ORDER_DETAILS_MODEL = "UpcomingOrderDetailsActivity",
            TAG2 = "UpcomingOrderDescriptionActivity",
            TAG3 = "UpdateStatusActivity";


    Context context;
    private UpdateStatusInterface listner;

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
    ImageView vendor_image, customer_image, product_image;
    TextView product_title, product_quantity;


    TextView tvSchedule_date;
    TextView tv_statusTitle;
    TextView tv_VendorName, tv_vendorMMobile, tv_vendorCall;
    TextView tv_CustomerName, tv_CustomerMMobile, tv_CustomerCall;
    TextView tv_pickAddress, tv_dropAddress, tv_pickDirection, tv_dropDirection;

    ProgressLinearLayout progressActivity;
    RecyclerView recyclerView;
    RecyclerView status_update_recyclerview;
    RelativeLayout RlStatusUpdate;
    GlobalFunctions globalFunctions;
    GlobalVariables globalVariables;
    RelativeLayout bottomSheet;
    SwipeRefreshLayout swipe_container;
    List<OrderModel> productDescriptionlist = new ArrayList<>();
    List<OrderStatus> orderStatusList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    ProductDescriptionListAdapter descriptionListAdapter;
    UpdateStatusAdapter updateStatusAdapter;

    OrderStatus orderStatus = null;
    String status=null;


    RelativeLayout rl_status_update;
    TextView tvUpdateStatus;
    BottomSheetBehavior behavior;

    OrderListModel detail = null;
    int index = 0;
    //int status = 0;
    String order_vendor_product_id = null;

    LinearLayout upcomingOrder;
    private List<OrderModel> allUpcomingOrders;
    private boolean shouldRefreshOnResume = false;
    OrderModel orderModel = null;
    LayoutInflater layoutInflater;

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
        listner=this;
        fragmentManager = getSupportFragmentManager();
        globalFunctions = AppController.getInstance().getGlobalFunctions();
        globalVariables = AppController.getInstance().getGlobalVariables();
        window = getWindow();

        layoutInflater = activity.getLayoutInflater();

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
        tv_statusTitle = findViewById(R.id.tv_statusTitle);
        //progressActivity = findViewById(R.id.details_progressActivity);
        //swipe_container = findViewById(R.id.swipe_container);
        product_image = findViewById(R.id.product_image);
        product_title = findViewById(R.id.tv_product_title);
        product_quantity = findViewById(R.id.tv_quantity);
        linearLayoutManager = new LinearLayoutManager(activity);


        rl_status_update = findViewById(R.id.rl_status_update);
        tvUpdateStatus = findViewById(R.id.tv_update_status);
        //rl_status_update=findViewById(R.id.RlStatus);


        ///status_update_recyclerview = findViewById(R.id.statusupdate_recyclerview_list);
       // status_update_recyclerview.setAdapter(updateStatusAdapter);
        //update status recyclerview method
        //updateStatusList();



        rl_status_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderStatusList.size() > 0) {
                    openOrderStatusDialog(orderStatusList);
                }
            }
        });


        tv_vendorCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GlobalFunctions.isNotNullValue(orderModel.getVendor_number())) {
                    GlobalFunctions.callPhone(activity, orderModel.getVendor_number());
                }

            }
        });
        tv_CustomerCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GlobalFunctions.isNotNullValue(orderModel.getUser_number())) {
                    GlobalFunctions.callPhone(activity, orderModel.getUser_number());
                }

            }
        });

        tv_pickDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tackPickLocation();

            }
        });

        tv_dropDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tackDropLocation();

            }
        });

        // recyclerView=findViewById(R.id.product_desc_recyclerview);
        //recyclerView.setAdapter(descriptionListAdapter);

        //set product description in recyclerview
        // productDescription();


        mainView = tvSchedule_date;

      /*  swipe_container.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //productDescription();
            }
        });
*/

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
        if (orderStatus != null) {
            if (GlobalFunctions.isNotNullValue(orderStatus.getId())) {
                status = orderStatus.getId();
            }
        }


        //getting order details
        getOrderDetails();


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


        setTitle(getString(R.string.upcoming_order_details), 0, 0);

    }



    private void openOrderStatusDialog(List<OrderStatus> orderStatusList) {
        View view = layoutInflater.inflate(R.layout.status_recyclerview, null, false);
        BottomSheetDialog alertView = new BottomSheetDialog(activity);
        alertView.setContentView(view);
        alertView.setCancelable(true);
        RecyclerView order_status_rv = alertView.findViewById(R.id.statusupdate_recyclerview_list);
        setStatusRecyclerview(order_status_rv, orderStatusList);
        alertView.show();
    }


    private void StatusUpdate(OrderStatusListModel orderStatus) {
        if (orderStatus != null && orderStatusList != null) {
            orderStatusList.clear();
            orderStatusList.addAll(orderStatus.getOrderStatusList());
            /*if (updateStatusAdapter != null) {
                synchronized (updateStatusAdapter) {
                    updateStatusAdapter.notifyDataSetChanged();
                }
            }*/
           /* if (orderStatusList.size() <= 0) {
                showEmptyPage();
            } else {
                showStatusContent();
//                setStatusRecyclerview();
            }*/
        }
    }

    private void setStatusRecyclerview(RecyclerView order_status_rv, List<OrderStatus> statusList) {
        linearLayoutManager = new LinearLayoutManager(activity);
        updateStatusAdapter = new UpdateStatusAdapter(statusList,GlobalVariables.ORDER_TYPE_UPCOMING,activity,this);
        order_status_rv.setLayoutManager(linearLayoutManager);
        order_status_rv.setAdapter(updateStatusAdapter);
        order_status_rv.setHasFixedSize(true);
    }

    private void showStatusContent() {
        if (progressActivity != null) {
            progressActivity.showContent();
        }
    }

   /* private void productDescription() {
        GlobalFunctions.showProgress( context, getString( R.string.loading ));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getorderDetails(context, order_vendor_product_id, new ServerResponseInterface() {
            @SuppressLint("LongLogTag")
            @Override
            public void OnSuccessFromServer(Object arg0) {
                GlobalFunctions.hideProgress();
                if (swipe_container.isRefreshing()) {
                    swipe_container.setRefreshing( false );
                }
                Log.d(TAG2, "Response description : " + arg0.toString());
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

    }*/


    private void setupProductList(OrderModel orderModel) {
        if (orderModel != null && productDescriptionlist != null) {
            productDescriptionlist.clear();
            productDescriptionlist.addAll(orderModel.getOrderModelList());
            if (descriptionListAdapter != null) {
                synchronized (descriptionListAdapter) {
                    descriptionListAdapter.notifyDataSetChanged();
                }
            }
            if (productDescriptionlist.size() <= 0) {
                showEmptyPage();
            } else {
                showContent();
                initRecyclerView();
            }
        }
    }

    private void initRecyclerView() {
        descriptionListAdapter = new ProductDescriptionListAdapter(productDescriptionlist, activity);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(descriptionListAdapter);
        recyclerView.setHasFixedSize(true);
        Log.e("ffff", "" + recyclerView.getAdapter().getItemCount());
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
        GlobalFunctions.showProgress(context, getString(R.string.loading));
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


                if (orderModel != null) {
                    setOrderDetails(orderModel);
                    //   setupProductList(orderModel);
                    if (orderModel.getOrderStatusListModel() != null) {
                        StatusUpdate(orderModel.getOrderStatusListModel());
                    }
                }
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
        if (getFragmentManager().findFragmentByTag(TAG) != null)
            getFragmentManager().findFragmentByTag(TAG).setRetainInstance(true);
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

            if (orderModel.getOrderStatusListModel() != null) {
                orderStatusList.addAll(orderModel.getOrderStatusListModel().getOrderStatusList());
            }

            if (GlobalFunctions.isNotNullValue(orderModel.getScheduled_for())) {
                tvSchedule_date.setText(GlobalFunctions.getDateFormat(orderModel.getScheduled_for()));
            }

            if (GlobalFunctions.isNotNullValue(orderModel.getVendor_image())) {
                Picasso.with(context).load(orderModel.getVendor_image()).placeholder(R.drawable.ic_baseline_person_24).into(vendor_image);

            }

            if (GlobalFunctions.isNotNullValue(orderModel.getUser_number())) {
                tv_CustomerMMobile.setText(orderModel.getUser_number());

            }

            if (GlobalFunctions.isNotNullValue(orderModel.getVendor_fname())) {
                String VendorfullName = orderModel.getVendor_fname();
                if (GlobalFunctions.isNotNullValue(orderModel.getVendor_lname())) {
                    VendorfullName = VendorfullName + " " + orderModel.getVendor_lname();
                    tv_VendorName.setText(VendorfullName);

                } else {
                    tv_VendorName.setText(VendorfullName);
                }
            }

            if (GlobalFunctions.isNotNullValue(orderModel.getVendor_number())) {
                tv_vendorMMobile.setText(orderModel.getVendor_number());
            }
            if (GlobalFunctions.isNotNullValue(orderModel.getDrop_address())) {
                tv_dropAddress.setText(orderModel.getDrop_address());
            }


            if (GlobalFunctions.isNotNullValue(orderModel.getPickup_address())) {
                tv_pickAddress.setText(orderModel.getPickup_address());
            }
            if (GlobalFunctions.isNotNullValue(orderModel.getStatus_title())) {
                tv_statusTitle.setText(orderModel.getStatus_title());
            }

            if (GlobalFunctions.isNotNullValue(orderModel.getUser_image())) {
                Picasso.with(context).load(orderModel.getUser_image()).placeholder(R.drawable.ic_baseline_person_24).into(customer_image);
            }

            if (GlobalFunctions.isNotNullValue(orderModel.getUser_fname())) {
                String UserfullName = orderModel.getUser_fname();
                if (GlobalFunctions.isNotNullValue(orderModel.getUser_lname())) {
                    UserfullName = UserfullName + " " + orderModel.getUser_lname();
                    tv_CustomerName.setText(UserfullName);

                } else {
                    tv_CustomerName.setText(UserfullName);
                }
            }


            if (GlobalFunctions.isNotNullValue(orderModel.getProduct_image())) {
                Picasso.with(context).load(orderModel.getProduct_image()).placeholder(R.drawable.ic_baseline_image_24).into(product_image);
            }
            if (GlobalFunctions.isNotNullValue(orderModel.getProduct_title())) {
                product_title.setText(orderModel.getProduct_title());
            }
            if (GlobalFunctions.isNotNullValue(orderModel.getQuantity())) {
                product_quantity.setText(orderModel.getQuantity());
            }

        }


    }

    private void tackDropLocation() {
        try {
            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + "" + "/" + orderModel.getDrop_address());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps,maps");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }
    }

    private void tackPickLocation() {
        try {
            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + "" + "/" + orderModel.getPickup_address());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps,maps");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }
    }

    @Override
    public void OnItemClickListener(OrderStatus orderStatus) {
        OrderStatusUpdateModel orderStatusUpdateModel=new OrderStatusUpdateModel();
        orderStatusUpdateModel.setId(order_vendor_product_id);
        if (orderStatus!=null && GlobalFunctions.isNotNullValue(orderStatus.getId())) {
            orderStatusUpdateModel.setStatus(orderStatus.getId());
        }
        statusUpdate(orderStatusUpdateModel);
    }

    private void statusUpdate(OrderStatusUpdateModel orderStatusUpdateModel) {
            GlobalFunctions.showProgress(context, getString(R.string.loading));
            ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
            servicesMethodsManager.getStatusUpdate(context,orderStatusUpdateModel, new ServerResponseInterface() {
                @SuppressLint("LongLogTag")
                @Override
                public void OnSuccessFromServer(Object arg0) {
                    GlobalFunctions.hideProgress();
                    Log.d(TAG, "Response Update : " + arg0.toString());
                    StatusMainModel statusMainModel = (StatusMainModel) arg0;
                    StatusModel statusModel = statusMainModel.getStatusModel();
                    if (statusMainModel.isStatus()){
                        GlobalFunctions.displayDialog(activity,statusModel.getMessage());
                        getOrderDetails();
                    }else {
                        GlobalFunctions.displayMessaage(activity,mainView,statusModel.getMessage());
                    }

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
}
