package com.sa.souqbinadriver.upcomingorder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sa.souqbinadriver.R;
import com.sa.souqbinadriver.services.model.OrderModel;

import java.util.List;

public class UpcomingOrderDetailsListAdapter extends RecyclerView.Adapter<UpcomingOrderDetailsListAdapter.ViewHolder> {


        public static final String TAG = "GetAllUpcomingOrderAdapter";

            private List<OrderModel> mModel;
            private final Activity activity;

    public UpcomingOrderDetailsListAdapter(List<OrderModel> mModel, Activity activity) {
        this.mModel = mModel;
        this.activity = activity;
    }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_recycler_viewdata, parent, false));

        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final OrderModel model = mModel.get(position);
            String order_vendor_product_id=mModel.get(position).getOrder_vendor_product_id();
            Log.e("order_vendor_product_id","order_vendor_product_id"+order_vendor_product_id);

            if (model.getScheduled_for() != null) {
                holder.scheduled_datetime.setText(model.getScheduled_for());
            }
            if (model.getPickup_address() != null) {
                holder.tv_Address.setText((model.getPickup_address()));
            }


            holder.item_Click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                  /*  Fragment upcomingOrderDetails=new UpcomingOrderDetailsFragment();
                    replaceFragment( upcomingOrderDetails, UpcomingOrderDetailsFragment.TAG, getString( R.string.app_name ), 0, 0 );*/
                    Intent intent = UpcomingOrderDetailsActivity.newInstance( activity, model );
                    activity.startActivity( intent );
                }
            });

        }

        @Override
        public int getItemCount() {
            return mModel.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView scheduled_datetime;
            TextView tv_Address;
            LinearLayout item_Click;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                scheduled_datetime = itemView.findViewById(R.id.tv_Schedule_date_time);
                tv_Address = itemView.findViewById(R.id.tv_address);
                item_Click = itemView.findViewById(R.id.UpcomingButton);
            }
        }
}

