package com.sa.souqbinadriver.upcomingorder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sa.souqbinadriver.R;
import com.sa.souqbinadriver.global.GlobalFunctions;
import com.sa.souqbinadriver.services.model.OrderModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductDescriptionListAdapter extends RecyclerView.Adapter<ProductDescriptionListAdapter.ViewHolder> {


        public static final String TAG = "ProductDescriptionListAdapter";

            private List<OrderModel> modelList;
            private final Activity activity;

        public ProductDescriptionListAdapter(List<OrderModel> mModel, Activity activity) {
            this.modelList = mModel;
            this.activity = activity;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_orderdesc_recyclerview_data, parent, false));

        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {



            if (GlobalFunctions.isNotNullValue(modelList.get(position).getProduct_title())) {
                holder.product_name.setText(modelList.get(position).getProduct_title());
                Log.e("product_name",""+modelList.get(position).getProduct_title());

            }
            if (GlobalFunctions.isNotNullValue(modelList.get(position).getQuantity())) {
                holder.product_quantity.setText((modelList.get(position).getQuantity()));

            }
            try {
                if (GlobalFunctions.isNotNullValue(modelList.get(position).getProduct_image())  || ! modelList.get(position).getProduct_image().equals( "null" ) || !modelList.get(position).getProduct_image().equalsIgnoreCase( "" )) {
                    Picasso.with( activity ).load( modelList.get(position).getProduct_image() ).placeholder( R.drawable.ic_baseline_image_24).into( holder.product_image );
                }
            } catch (Exception e) {
            }


        }

        @SuppressLint("LongLogTag")
        @Override
        public int getItemCount() {
            Log.d(TAG, "getItemCount: list DATA-----" + modelList.size());
            return (modelList == null) ? 0 : modelList.size();

            //return modelList.size();

        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView product_name;
            ImageView product_image;
            TextView product_quantity;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                product_name = itemView.findViewById(R.id.tv_product_description);
                product_image = itemView.findViewById(R.id.product_image);
                product_quantity = itemView.findViewById(R.id.tv_quantity);
            }
        }
}

