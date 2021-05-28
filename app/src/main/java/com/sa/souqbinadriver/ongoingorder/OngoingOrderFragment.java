package com.sa.souqbinadriver.ongoingorder;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.sa.souqbinadriver.Activity.AppController;
import com.sa.souqbinadriver.Activity.MainActivity;
import com.sa.souqbinadriver.R;
import com.sa.souqbinadriver.global.GlobalFunctions;
import com.sa.souqbinadriver.global.GlobalVariables;
import com.sa.souqbinadriver.services.ServerResponseInterface;
import com.sa.souqbinadriver.services.ServicesMethodsManager;
import com.sa.souqbinadriver.services.model.OrderListModel;
import com.sa.souqbinadriver.services.model.OrderMainModel;
import com.sa.souqbinadriver.services.model.OrderModel;
import com.sa.souqbinadriver.upcomingorder.UpcomingOrderDetailsListAdapter;
import com.sa.souqbinadriver.upcomingorder.UpcomingOrderProductDescriptionListAdapter;
import com.squareup.picasso.Picasso;
import com.vlonjatg.progressactivity.ProgressLinearLayout;

import java.util.ArrayList;
import java.util.List;


public class OngoingOrderFragment extends Fragment {
    TextView tvStatus;
    public static final String TAG = "OngoingOrderFragment";

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

    LinearLayout empty_order;

    ProgressLinearLayout progressActivity;
    RecyclerView recyclerView;
    GlobalFunctions globalFunctions;
    GlobalVariables globalVariables;
    SwipeRefreshLayout swipe_container;
    List<OrderModel> list = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    private UpcomingOrderProductDescriptionListAdapter adapter;

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

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ongoing_order_details, container, false);


        context = getActivity();
        activity = getActivity();
        fragmentManager = getActivity().getSupportFragmentManager();
        window = getActivity().getWindow();

        tvSchedule_date =view.findViewById(R.id.tv_Schedule_date_time);
        tv_VendorName =view. findViewById(R.id.tv_vendor_name);
        tv_vendorMMobile = view.findViewById(R.id.tv_vendor_mobile);
        tv_vendorCall = view.findViewById(R.id.tv_vendor_call);
        tv_CustomerName = view.findViewById(R.id.tv_customer_name);
        tv_CustomerMMobile =view. findViewById(R.id.tv_customer_mobile_number);
        tv_CustomerCall = view.findViewById(R.id.tv_call_customer);
        tv_pickAddress =view. findViewById(R.id.tv_pick_address);
        tv_dropAddress = view.findViewById(R.id.tv_drop_address);
        tv_pickDirection = view.findViewById(R.id.tv_pick_direction);
        tv_dropDirection =view. findViewById(R.id.tv_drop_direction);
        vendor_image = view.findViewById(R.id.vendor_image);
        customer_image = view.findViewById(R.id.tv_customer_image);
        empty_order=view.findViewById(R.id.ongoing_order_empty);
        activity = getActivity();
        context = getActivity();
        recyclerView = view.findViewById(R.id.ongoing_recyclerview_list);
        globalFunctions = AppController.getInstance().getGlobalFunctions();
        globalVariables = AppController.getInstance().getGlobalVariables();
        linearLayoutManager = new LinearLayoutManager( activity );
        progressActivity = view.findViewById( R.id.details_progressActivity );
        swipe_container = view.findViewById( R.id.swipe_container );

        rl_status_update=view.findViewById(R.id.rl_status_update);
        update_status_recyclerview=view.findViewById(R.id.statusupdate_recyclerview_list);
      //  rl_status_update=view.findViewById(R.id.RlStatus);

        tvUpdateStatus=view.findViewById(R.id.tv_update_status);
        View bottomSheet =view.findViewById(R.id.bottom_sheet);
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

        //recyclerView.setAdapter(adapter);
        swipe_container.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               // productDescription();
            }
        } );


        mainView = tvSchedule_date;
        loadOngoingOrderDetails();

        return view;
    }

    private void loadOngoingOrderDetails() {
        // GlobalFunctions.showProgress( context, getString( R.string.loading ));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getOrderList( context,index,GlobalVariables.LIST_REQUEST_SIZE,GlobalVariables.ORDER_TYPE_ON_GOING, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
                GlobalFunctions.hideProgress();
                if (swipe_container.isRefreshing()) {
                    swipe_container.setRefreshing( false );
                }
                Log.d( TAG, "Response : " + arg0.toString());
                OrderMainModel orderMainModel=(OrderMainModel) arg0;
                OrderListModel orderListModel=orderMainModel.getOrderListModel();
                if (orderListModel!=null){
                     //setUpList( orderListModel);
                }else
                {
                    showEmptyPage();

                }

            }

            @Override
            public void OnFailureFromServer(String msg) {
                GlobalFunctions.hideProgress();
                if (swipe_container.isRefreshing()) {
                    swipe_container.setRefreshing( false );
                }
                Log.d( TAG, "Failure : " + msg );
                GlobalFunctions.displayMessaage( context, mainView, msg );
            }

            @Override
            public void OnError(String msg) {
                GlobalFunctions.hideProgress();
                if (swipe_container.isRefreshing()) {
                    swipe_container.setRefreshing( false );
                }
                Log.d( TAG, "Error : " + msg );
                GlobalFunctions.displayMessaage( context, mainView, msg );
            }
        }, "Order List" );
    }

    private void showEmptyPage() {
        empty_order.setVisibility(View.VISIBLE);


    }

    private void setUpList(OrderListModel orderListModel) {
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint( isVisibleToUser );
        // Refresh tab data:

        if (getFragmentManager() != null) {

            getFragmentManager()
                    .beginTransaction()
                    .detach( this )
                    .attach( this )
                    .commit();
        }
    }

    @Override
    public void onResume() {
        ((MainActivity) activity).setTitle( getString( R.string.app_name ), R.mipmap.app_icon, 0 );
        // ((MainActivity) activity).setTitle("", 0, 0);
        super.onResume();
        if (shouldRefreshOnResume) {
          //  loadOrderList();
        }
     /*   if (getFragmentManager().findFragmentByTag( TAG ) != null)
            getFragmentManager().findFragmentByTag( TAG ).getRetainInstance();*/
    }

    private void statusPopup() {
        final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.status_update_dialog);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }


}
