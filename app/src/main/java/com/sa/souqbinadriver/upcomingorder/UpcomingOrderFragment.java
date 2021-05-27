package com.sa.souqbinadriver.upcomingorder;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.vlonjatg.progressactivity.ProgressLinearLayout;

import java.util.ArrayList;
import java.util.List;


public class UpcomingOrderFragment extends Fragment {
    public static final String TAG = "UpcomingOrderFragment";

    Activity activity;
    Context context;
    ProgressLinearLayout progressActivity;
    GlobalFunctions globalFunctions;
    GlobalVariables globalVariables;
    View mainView;
    SwipeRefreshLayout swipe_container;
    List <OrderModel> list = new ArrayList <>();
    LinearLayoutManager linearLayoutManager;

    LinearLayout upcomingOrder;
    RecyclerView recyclerView;
    private List<OrderModel> allUpcomingOrders;
    private UpcomingOrderDetailsListAdapter adapter;
    View view;
    private boolean shouldRefreshOnResume = false;
    int index=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.upcoming_activity, container, false);

        activity = getActivity();
        context = getActivity();
        recyclerView = view.findViewById(R.id.upcoming_recyclerview_list);
        globalFunctions = AppController.getInstance().getGlobalFunctions();
        globalVariables = AppController.getInstance().getGlobalVariables();
        linearLayoutManager = new LinearLayoutManager( activity );
        progressActivity = view.findViewById( R.id.details_progressActivity );
        swipe_container = view.findViewById( R.id.swipe_container );

        recyclerView.setAdapter(adapter);
        mainView = recyclerView;
        loadOrderList();

        swipe_container.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadOrderList();
            }
        } );



        return view;
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
            loadOrderList();
        }
     /*   if (getFragmentManager().findFragmentByTag( TAG ) != null)
            getFragmentManager().findFragmentByTag( TAG ).getRetainInstance();*/
    }

    private void loadOrderList() {
       // GlobalFunctions.showProgress( context, getString( R.string.loading ));
        ServicesMethodsManager servicesMethodsManager = new ServicesMethodsManager();
        servicesMethodsManager.getOrderList( context,index,GlobalVariables.LIST_REQUEST_SIZE,GlobalVariables.ORDER_TYPE_UPCOMING, new ServerResponseInterface() {
            @Override
            public void OnSuccessFromServer(Object arg0) {
                GlobalFunctions.hideProgress();
                if (swipe_container.isRefreshing()) {
                    swipe_container.setRefreshing( false );
                }
                Log.d( TAG, "Response : " + arg0.toString() );
                OrderMainModel orderMainModel=(OrderMainModel) arg0;
                OrderListModel orderListModel=orderMainModel.getOrderListModel();

                setUpList( orderListModel );
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



    private void showContent() {
        if (progressActivity != null) {
            progressActivity.showContent();
        }
    }


    private void showEmptyPage() {
        if (progressActivity != null) {
            progressActivity.showEmpty(getResources().getDrawable(R.drawable.ic_group_no_order), getString(R.string.emptyList),
                    getString(R.string.no_ongoing_order));
        }
    }


    private void setUpList(OrderListModel orderListModel) {
        if (orderListModel != null && list != null) {
            list.clear();
            list.addAll( orderListModel.getOrderModelList() );
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
        adapter = new UpcomingOrderDetailsListAdapter(list, activity);
        recyclerView.setLayoutManager( linearLayoutManager );
        recyclerView.setAdapter( adapter );
        recyclerView.setHasFixedSize( true );
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach( context );
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

    @Override
    public void onStop() {
        super.onStop();
        shouldRefreshOnResume = true;
    }


    public void replaceFragmentWithAnimation(Fragment fragment, String order_vendor_product_id) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("order_vendor_product_id", order_vendor_product_id);
        fragment.setArguments(bundle);
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }





}



