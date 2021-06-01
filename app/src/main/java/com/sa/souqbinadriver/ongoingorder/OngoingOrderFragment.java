package com.sa.souqbinadriver.ongoingorder;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.sa.souqbinadriver.Activity.AppController;
import com.sa.souqbinadriver.Activity.MainActivity;
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
import com.sa.souqbinadriver.services.model.OrderMainModel;
import com.sa.souqbinadriver.services.model.OrderModel;
import com.sa.souqbinadriver.services.model.OrderStatus;
import com.sa.souqbinadriver.services.model.OrderStatusListModel;
import com.sa.souqbinadriver.services.model.OrderStatusUpdateModel;
import com.sa.souqbinadriver.services.model.StatusMainModel;
import com.sa.souqbinadriver.services.model.StatusModel;
import com.sa.souqbinadriver.upcomingorder.ProductDescriptionListAdapter;
import com.sa.souqbinadriver.upcomingorder.UpdateStatusAdapter;
import com.sa.souqbinadriver.upcomingorder.UpdateStatusInterface;
import com.squareup.picasso.Picasso;
import com.vlonjatg.progressactivity.ProgressLinearLayout;

import java.util.ArrayList;
import java.util.List;


public class OngoingOrderFragment extends Fragment implements UpdateStatusInterface {
    TextView tvStatus;
    public static final String TAG = "OngoingOrderFragment";

    Context context = null;

    static Activity activity = null;
    static Window window = null;
    View mainView;
    static FragmentManager fragmentManager = null;
    LayoutInflater layoutInflater;
    LatlongMainModel latlongModel;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

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

    LinearLayout empty_order;

    ProgressLinearLayout progressActivity;
    RecyclerView recyclerView;
    GlobalFunctions globalFunctions;
    GlobalVariables globalVariables;
    SwipeRefreshLayout swipe_container;
    List<OrderModel> list = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    private ProductDescriptionListAdapter adapter;
    UpdateStatusAdapter updateStatusAdapter;

    OrderListModel detail = null;
    int index = 0;
    String order_vendor_product_id = null;

    LinearLayout upcomingOrder;
    private List<OrderModel> allUpcomingOrders;
    // private UpcomingOrderFragment.GetAllUpcomingOrderAdapter adapter;
    private boolean shouldRefreshOnResume = false;
    OrderModel orderModel = null;
    RelativeLayout rl_status_update;
    TextView tvUpdateStatus;
    BottomSheetBehavior behavior;
    RecyclerView update_status_recyclerview;
    List<OrderStatus> orderStatusList = new ArrayList<>();
    Double latitute,longitute;

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ongoing_order_details, container, false);


        context = getActivity();
        activity = getActivity();
        fragmentManager = getActivity().getSupportFragmentManager();
        window = getActivity().getWindow();

        layoutInflater = activity.getLayoutInflater();


        tvSchedule_date = view.findViewById(R.id.tv_Schedule_date_time);
        tv_VendorName = view.findViewById(R.id.tv_vendor_name);
        tv_vendorMMobile = view.findViewById(R.id.tv_vendor_mobile);
        tv_vendorCall = view.findViewById(R.id.tv_vendor_call);
        tv_CustomerName = view.findViewById(R.id.tv_customer_name);
        tv_CustomerMMobile = view.findViewById(R.id.tv_customer_mobile_number);
        tv_CustomerCall = view.findViewById(R.id.tv_call_customer);
        tv_pickAddress = view.findViewById(R.id.tv_pick_address);
        tv_dropAddress = view.findViewById(R.id.tv_drop_address);
        tv_pickDirection = view.findViewById(R.id.tv_pick_direction);
        tv_dropDirection = view.findViewById(R.id.tv_drop_direction);
        vendor_image = view.findViewById(R.id.vendor_image);
        customer_image = view.findViewById(R.id.tv_customer_image);
        empty_order = view.findViewById(R.id.ongoing_order_empty);
        tv_statusTitle = view.findViewById(R.id.tv_statusTitle);
        activity = getActivity();
        context = getActivity();
        // recyclerView = view.findViewById(R.id.ongoing_recyclerview_list);
        globalFunctions = AppController.getInstance().getGlobalFunctions();
        globalVariables = AppController.getInstance().getGlobalVariables();
        linearLayoutManager = new LinearLayoutManager(activity);
        product_image = view.findViewById(R.id.product_image);
        product_title = view.findViewById(R.id.tv_product_title);
        product_quantity = view.findViewById(R.id.tv_quantity);
        //  progressActivity = view.findViewById( R.id.details_progressActivity );
        //swipe_container = view.findViewById( R.id.swipe_container );

        rl_status_update = view.findViewById(R.id.rl_status_update);

        tvUpdateStatus = view.findViewById(R.id.tv_update_status);


        GPSTracker mGPS = new GPSTracker(getActivity());

        if (mGPS.canGetLocation()) {
            mGPS.getLocation();
            latitute = mGPS.getLatitude();
            longitute = mGPS.getLongitude();
            Log.e("Latlong",""+latitute+","+longitute);
            updateLatlong(latlongModel);

        } else {
            System.out.println("Unable");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

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



        //recyclerView.setAdapter(adapter);
       /* swipe_container.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               // productDescription();
            }
        } );

*/
        mainView = tvSchedule_date;
        loadOngoingOrderDetails();

        return view;
    }

    private void loadOngoingOrderDetails() {
        //GlobalFunctions.showProgress( context, getString( R.string.loading ));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getOrderList(context, index, GlobalVariables.LIST_REQUEST_SIZE, GlobalVariables.ORDER_TYPE_ON_GOING, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
                //GlobalFunctions.hideProgress();
                Log.d(TAG, "Response : " + arg0.toString());
                OrderMainModel orderMainModel = (OrderMainModel) arg0;
                if (orderMainModel != null && orderMainModel.getOrderListModel() != null && orderMainModel.getOrderListModel().getOrderModelList().size() > 0) {
                    orderModel = orderMainModel.getOrderListModel().getOrderModelList().get(0);
                }
                if (orderModel != null) {
                    setThisPage(orderModel);
                    if (orderModel.getOrderStatusListModel() != null) {
                        StatusUpdate(orderModel.getOrderStatusListModel());
                    }
                } else {
                    showEmptyPage();
                }

            }

            @Override
            public void OnFailureFromServer(String msg) {
                //GlobalFunctions.hideProgress();

                Log.d(TAG, "Failure : " + msg);
                GlobalFunctions.displayMessaage(context, mainView, msg);
            }

            @Override
            public void OnError(String msg) {
                GlobalFunctions.hideProgress();

                Log.d(TAG, "Error : " + msg);
                GlobalFunctions.displayMessaage(context, mainView, msg);
            }
        }, "Order List");
    }

    private void StatusUpdate(OrderStatusListModel orderStatusListModel) {
        if (orderStatusListModel != null && orderStatusList != null) {
            orderStatusList.clear();
            orderStatusList.addAll(orderStatusListModel.getOrderStatusList());
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

    private void showEmptyPage() {
        empty_order.setVisibility(View.VISIBLE);

    }
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
    private void updateLatlong(LatlongMainModel latlongModel) {
        /*ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getLatlongUpdate(context, latlongModel, new ServerResponseInterface() {
            @SuppressLint("LongLogTag")
            @Override
            public void OnSuccessFromServer(Object arg0) {
                Log.d(TAG, "Response Update : " + arg0.toString());
                //LatlongMainModel latlongMainModel = (LatlongMainModel) arg0;
               // LatlongModel latlongModel = latlongMainModel.getLatlongModel();
               *//* if (statusMainModel.isStatus()){
                    GlobalFunctions.displayDialog(activity,statusModel.getMessage());
                    getOrderDetails();
                }else {
                    GlobalFunctions.displayMessaage(activity,mainView,statusModel.getMessage());
                }*//*

            }

            @SuppressLint("LongLogTag")
            @Override
            public void OnFailureFromServer(String msg) {
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
        }, "Order List");*/
    }

    private void setThisPage(OrderModel orderModel) {

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
            if (GlobalFunctions.isNotNullValue(orderModel.getStatus_title())) {
                tv_statusTitle.setText(orderModel.getStatus_title());
            }


            if (GlobalFunctions.isNotNullValue(orderModel.getPickup_address())) {
                tv_pickAddress.setText(orderModel.getPickup_address());
            }


            if (GlobalFunctions.isNotNullValue(orderModel.getUser_image())) {
                Picasso.with(context).load(orderModel.getUser_image()).placeholder(R.drawable.ic_baseline_person_24).into(customer_image);
            }

            if (GlobalFunctions.isNotNullValue(orderModel.getUser_fname())) {
                String UserfullName = orderModel.getUser_fname();
                if (GlobalFunctions.isNotNullValue(orderModel.getUser_lname())) {
                    UserfullName = UserfullName + " " + orderModel.getUser_lname();
                    tv_VendorName.setText(UserfullName);

                } else {
                    tv_VendorName.setText(UserfullName);
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

    private void openOrderStatusDialog(List<OrderStatus> orderStatusList) {
        View view = layoutInflater.inflate(R.layout.status_recyclerview, null, false);
        BottomSheetDialog alertView = new BottomSheetDialog(activity);
        alertView.setContentView(view);
        alertView.setCancelable(true);
        RecyclerView order_status_rv = alertView.findViewById(R.id.statusupdate_recyclerview_list);
        setStatusRecyclerview(order_status_rv, orderStatusList);
        alertView.show();
    }

    private void setStatusRecyclerview(RecyclerView order_status_rv, List<OrderStatus> statusList) {
        linearLayoutManager = new LinearLayoutManager(activity);
        updateStatusAdapter = new UpdateStatusAdapter(statusList, GlobalVariables.ORDER_TYPE_ON_GOING, activity, this);
        order_status_rv.setLayoutManager(linearLayoutManager);
        order_status_rv.setAdapter(updateStatusAdapter);
        order_status_rv.setHasFixedSize(true);
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // Refresh tab data:

        if (getFragmentManager() != null) {

            getFragmentManager()
                    .beginTransaction()
                    .detach(this)
                    .attach(this)
                    .commit();
        }
    }

    @Override
    public void onResume() {
        ((MainActivity) activity).setTitle(getString(R.string.app_name), R.mipmap.app_icon, 0);
        // ((MainActivity) activity).setTitle("", 0, 0);
        super.onResume();
        if (shouldRefreshOnResume) {
            //  loadOrderList();
        }
     /*   if (getFragmentManager().findFragmentByTag( TAG ) != null)
            getFragmentManager().findFragmentByTag( TAG ).getRetainInstance();*/
    }


    @Override
    public void OnItemClickListener(OrderStatus orderStatus) {
        OrderStatusUpdateModel orderStatusUpdateModel = new OrderStatusUpdateModel();
        orderStatusUpdateModel.setId(order_vendor_product_id);
        if (orderStatus != null && GlobalFunctions.isNotNullValue(orderStatus.getId())) {
            orderStatusUpdateModel.setStatus(orderStatus.getId());
        }
        statusUpdate(orderStatusUpdateModel);
    }

    private void statusUpdate(OrderStatusUpdateModel orderStatusUpdateModel) {
        GlobalFunctions.showProgress(context, getString(R.string.loading));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getStatusUpdate(context, orderStatusUpdateModel, new ServerResponseInterface() {
            @SuppressLint("LongLogTag")
            @Override
            public void OnSuccessFromServer(Object arg0) {
                GlobalFunctions.hideProgress();
                Log.d(TAG, "Response Update : " + arg0.toString());
                StatusMainModel statusMainModel = (StatusMainModel) arg0;
                StatusModel statusModel = statusMainModel.getStatusModel();
                if (statusMainModel.isStatus()) {
                    GlobalFunctions.displayDialog(activity, statusModel.getMessage());
                    loadOngoingOrderDetails();
                } else {
                    GlobalFunctions.displayMessaage(activity, mainView, statusModel.getMessage());
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
