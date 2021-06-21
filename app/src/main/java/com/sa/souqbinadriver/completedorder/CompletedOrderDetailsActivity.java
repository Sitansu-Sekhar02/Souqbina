package com.sa.souqbinadriver.completedorder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sa.souqbinadriver.R;
import com.sa.souqbinadriver.global.GlobalFunctions;
import com.sa.souqbinadriver.global.GlobalVariables;
import com.sa.souqbinadriver.services.ServerResponseInterface;
import com.sa.souqbinadriver.services.ServicesMethodsManager;
import com.sa.souqbinadriver.services.model.OrderDetailMainModel;
import com.sa.souqbinadriver.services.model.OrderListModel;
import com.sa.souqbinadriver.services.model.OrderModel;
import com.sa.souqbinadriver.services.model.OrderStatus;
import com.sa.souqbinadriver.upcomingorder.ProductDescriptionListAdapter;
import com.sa.souqbinadriver.upcomingorder.UpdateStatusInterface;
import com.squareup.picasso.Picasso;
import com.vlonjatg.progressactivity.ProgressLinearLayout;

import java.util.ArrayList;
import java.util.List;


public class CompletedOrderDetailsActivity extends AppCompatActivity implements  UpdateStatusInterface {

    public static final String
            TAG = "CompletedOrderDetailsActivity",
            BUNDLE_COMPLETED_ORDER_DETAILS_MODEL = "BundleCompletedOrderDetailsActivity";


    private UpdateStatusInterface mInterface;
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
    ImageView vendor_image, customer_image, product_image;
    TextView product_title, product_quantity;

    TextView tvSchedule_date,order_id;
    TextView tv_VendorName, tv_vendorMMobile;
    TextView tv_CustomerName, tv_CustomerMMobile;
    TextView tv_pickAddress, tv_dropAddress, tv_pickDirection, tv_dropDirection;

    private UpdateStatusInterface listner;

    ProgressLinearLayout progressActivity;
    RecyclerView recyclerView;
    GlobalFunctions globalFunctions;
    GlobalVariables globalVariables;
    SwipeRefreshLayout swipe_container;
    List<OrderModel> list = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    ProductDescriptionListAdapter adapter;

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
        listner=this;


        tvSchedule_date = findViewById(R.id.tv_Schedule_date_time);
        order_id = findViewById(R.id.tv_order_id);
        tv_VendorName = findViewById(R.id.tv_vendor_name);
        tv_vendorMMobile = findViewById(R.id.tv_vendor_mobile);
        tv_CustomerName = findViewById(R.id.tv_customer_name);
        tv_CustomerMMobile = findViewById(R.id.tv_customer_mobile_number);
        vendor_image = findViewById(R.id.vendor_image);
        customer_image = findViewById(R.id.iv_customer_image);
        tv_pickAddress = findViewById(R.id.tv_pick_address);
        tv_dropAddress = findViewById(R.id.tv_drop_address);
       // progressActivity =findViewById( R.id.details_progressActivity );
       // swipe_container = findViewById( R.id.swipe_container );
        linearLayoutManager = new LinearLayoutManager( activity );

        product_image = findViewById(R.id.product_image);
        product_title = findViewById(R.id.tv_product_title);
        product_quantity = findViewById(R.id.tv_quantity);
       // recyclerView=findViewById(R.id.product_desc_recyclerview);
        //recyclerView.setAdapter(adapter);
        // Log.e("adapter count",""+recyclerView.getAdapter().getItemCount()) ;


        mainView = tvSchedule_date;

        /*swipe_container.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productDescription();
            }
        });
*/

        if (getIntent().hasExtra(BUNDLE_COMPLETED_ORDER_DETAILS_MODEL)) {
            orderModel = (OrderModel) getIntent().getSerializableExtra(BUNDLE_COMPLETED_ORDER_DETAILS_MODEL);
        } else {
            orderModel = null;
        }

        if (orderModel != null) {
            if (GlobalFunctions.isNotNullValue(orderModel.getOrder_vendor_product_id())) {
                order_vendor_product_id = orderModel.getOrder_vendor_product_id();
            }
        }
        setTitle(getString(R.string.completed_order_details), 0, 0);

        //set product description in recyclerview
        //productDescription();

        //getting order details
        getOrderDetails();

        //set product description in recyclerview
       // productDescription();


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

        setTitle(getString(R.string.completed_order_details), 0, 0);


    }


    private void getOrderDetails() {
        //GlobalFunctions.showProgress( context, getString( R.string.loading ));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getorderDetails(context,order_vendor_product_id, new ServerResponseInterface() {
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

    private void setOrderDetails(OrderModel orderModel) {
        if (orderModel != null && context != null) {
            if (GlobalFunctions.isNotNullValue(orderModel.getScheduled_for())) {
                tvSchedule_date.setText(GlobalFunctions.getDateTimeFormat(orderModel.getScheduled_for()));
            }
            if (GlobalFunctions.isNotNullValue(orderModel.getOrder_id())) {
                order_id.setText("#"+orderModel.getOrder_id());
            }
            if (GlobalFunctions.isNotNullValue(orderModel.getVendor_image())) {
                Picasso.with(context).load(orderModel.getVendor_image()).placeholder(R.drawable.ic_baseline_person_24).into(vendor_image);


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
            if (GlobalFunctions.isNotNullValue(orderModel.getUser_fname())) {
                String UserfullName = orderModel.getUser_fname();
                if (GlobalFunctions.isNotNullValue(orderModel.getUser_lname())) {
                    UserfullName = UserfullName + " " + orderModel.getUser_lname();
                    tv_CustomerName.setText(UserfullName);

                } else {
                    tv_CustomerName.setText(UserfullName);
                }
            }
            if (GlobalFunctions.isNotNullValue(orderModel.getOrder_vendor_product_id())) {
                order_vendor_product_id=orderModel.getOrder_vendor_product_id();
            }
            if (GlobalFunctions.isNotNullValue(orderModel.getUser_number())) {
                tv_CustomerMMobile.setText(orderModel.getUser_number());

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

            if (GlobalFunctions.isNotNullValue(orderModel.getUser_image())) {
                Picasso.with(context).load(orderModel.getUser_image()).placeholder(R.drawable.ic_baseline_person_24).into(customer_image);
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
    private void initRecyclerView() {
        adapter = new ProductDescriptionListAdapter(list, activity);
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


    @Override
    public void OnItemClickListener(OrderStatus position) {
       // UpdateStatus();

    }
}
