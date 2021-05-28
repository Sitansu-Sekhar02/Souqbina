package com.sa.souqbinadriver.completedorder;

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

public class CompletedOrderProductDescriptionListAdapter extends RecyclerView.Adapter<CompletedOrderProductDescriptionListAdapter.ViewHolder> {


        public static final String TAG = "CompletedOrderProductDescriptionListAdapter";

            private List<OrderModel> mModel;
            private final Activity activity;

        public CompletedOrderProductDescriptionListAdapter(List<OrderModel> mModel, Activity activity) {
            this.mModel = mModel;
            this.activity = activity;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_orderdesc_recyclerview_data, parent, false));

        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final OrderModel model = mModel.get(position);

            if (model.getProduct_title() != null) {
                holder.product_name.setText(model.getProduct_title());
            }
            if (model.getQuantity() != null) {
                holder.product_quantity.setText((model.getQuantity()));

            }
            try {
                if (model.getProduct_image() != null || !model.getProduct_image().equals( "null" ) || !model.getProduct_image().equalsIgnoreCase( "" )) {
                    Picasso.with( activity ).load( model.getProduct_image() ).placeholder( R.drawable.ic_baseline_image_24).into( holder.product_image );
                }
            } catch (Exception e) {
            }


        }

        @Override
        public int getItemCount() {
            return mModel.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView product_name;
            ImageView product_image;
            TextView product_quantity;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                product_name = itemView.findViewById(R.id.tv_product_description);
                product_image = itemView.findViewById(R.id.prodcut_image);
                product_quantity = itemView.findViewById(R.id.tv_quantity);
            }
        }
}

