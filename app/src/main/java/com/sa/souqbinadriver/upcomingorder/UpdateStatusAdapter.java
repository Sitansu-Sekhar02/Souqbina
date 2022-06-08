package com.sa.souqbinadriver.upcomingorder;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.sa.souqbinadriver.R;
import com.sa.souqbinadriver.global.GlobalFunctions;
import com.sa.souqbinadriver.global.GlobalVariables;
import com.sa.souqbinadriver.services.model.OrderModel;
import com.sa.souqbinadriver.services.model.OrderStatus;
import com.sa.souqbinadriver.view.AlertDialog;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UpdateStatusAdapter extends RecyclerView.Adapter<UpdateStatusAdapter.ViewHolder> {


    public static final String TAG = "UpdateStatusAdapter";

    private List<OrderStatus> list;
    private final Activity activity;
    private UpdateStatusInterface updateStatusInterface;
    int selectedPos = -1, clickedPos = -1;
    String pageFrom = null;


    public UpdateStatusAdapter(List<OrderStatus> list, String pageFrom, Activity activity, UpdateStatusInterface updateStatusInterface) {
        this.list = list;
        this.activity = activity;
        this.updateStatusInterface = updateStatusInterface;
        this.pageFrom = pageFrom;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.status_update_dialog, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final OrderStatus item = list.get(position);

        if (GlobalFunctions.isNotNullValue(pageFrom) && pageFrom.equalsIgnoreCase(GlobalVariables.ORDER_TYPE_UPCOMING)) {
            if (position == 0) {

                holder.status_title.setTextColor(activity.getResources().getColor(R.color.textcolor));
                holder.tv_update.setVisibility(View.GONE);

            } else if (position == 1) {
                clickedPos = position;
                holder.tv_update.setVisibility(View.VISIBLE);
                holder.status_title.setTextColor(activity.getResources().getColor(R.color.app_fontBlackColor));
                holder.tv_update.setTextColor(activity.getResources().getColor(R.color.colorPrimary));
                holder.circle_image.setImageResource(R.drawable.ic_component_circle_grey);

            } else {
                holder.tv_update.setVisibility(View.VISIBLE);
                holder.status_title.setTextColor(activity.getResources().getColor(R.color.textcolor));
                holder.tv_update.setTextColor(activity.getResources().getColor(R.color.textcolor));
                holder.circle_image.setImageResource(R.drawable.ic_component_circle);

            }
        } else if (GlobalFunctions.isNotNullValue(pageFrom) && pageFrom.equalsIgnoreCase(GlobalVariables.ORDER_TYPE_ON_GOING)) {

            if (GlobalFunctions.isNotNullValue(item.getCreated_on())) {
                holder.status_title.setTextColor(activity.getResources().getColor(R.color.textcolor));
                holder.tv_update.setTextColor(activity.getResources().getColor(R.color.textcolor));
                holder.circle_image.setImageResource(R.drawable.ic_component_circle_grey);

            } else {
                if (clickedPos == -1) {
                    clickedPos = position;
                    holder.status_title.setTextColor(activity.getResources().getColor(R.color.app_fontBlackColor));
                    holder.tv_update.setTextColor(activity.getResources().getColor(R.color.colorPrimary));
                }else{
                    holder.status_title.setTextColor(activity.getResources().getColor(R.color.textcolor));
                    holder.tv_update.setTextColor(activity.getResources().getColor(R.color.textcolor));
                    holder.circle_image.setImageResource(R.drawable.ic_component_circle);
                }
            }
        }

        if (GlobalFunctions.isNotNullValue(item.getStatus_title())) {
            holder.status_title.setText(item.getStatus_title());

        }

        holder.tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (clickedPos == position) {
                    selectedPos = position;
                    updateStatusInterface.OnItemClickListener(item);
                    notifyDataSetChanged();
                }
            }
        });


        if (position == list.size() - 1) {
            holder.view_view.setVisibility(View.GONE);
        } else {
            holder.view_view.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView status_title;
        ImageView circle_image;
        TextView tv_update;
        View view_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            status_title = itemView.findViewById(R.id.tv_status_title);
            circle_image = itemView.findViewById(R.id.image_circle);
            tv_update = itemView.findViewById(R.id.tv_update);
            view_view = itemView.findViewById(R.id.view_line);
        }
    }
}

