package com.sa.souqbinadriver.ongoingorder;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sa.souqbinadriver.R;
import com.sa.souqbinadriver.services.model.OrderModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OngoingUpdateStatusAdapter extends RecyclerView.Adapter<OngoingUpdateStatusAdapter.ViewHolder> {


        public static final String TAG = "OngoingUpdateStatusAdapter";

            private List<OrderModel> mModel;
            private final Activity activity;

        public OngoingUpdateStatusAdapter(List<OrderModel> mModel, Activity activity) {
            this.mModel = mModel;
            this.activity = activity;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.status_update_dialog, parent, false));

        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final OrderModel model = mModel.get(position);

            if (model.getStatus_title() != null) {
                holder.status_title.setText(model.getStatus_title());
            }
            try {
                if (model.getProduct_image() != null || !model.getProduct_image().equals( "null" ) || !model.getProduct_image().equalsIgnoreCase( "" )) {
                    Picasso.with( activity ).load( model.getProduct_image() ).placeholder( R.drawable.ic_baseline_image_24).into( holder.circle_image );
                }
            } catch (Exception e) {
            }


        }

        @Override
        public int getItemCount() {
            return mModel.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView status_title;
            ImageView circle_image;
            TextView tv_update;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                status_title = itemView.findViewById(R.id.tv_status_title);
                circle_image = itemView.findViewById(R.id.circle_image);
                tv_update = itemView.findViewById(R.id.tv_update);
            }
        }
}

