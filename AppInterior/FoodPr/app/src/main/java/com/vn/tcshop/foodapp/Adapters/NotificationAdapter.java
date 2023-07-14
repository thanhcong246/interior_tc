package com.vn.tcshop.foodapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vn.tcshop.foodapp.Models.Notification;
import com.vn.tcshop.foodapp.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHoldel> {
    private List<Notification> notificationList;

    public NotificationAdapter(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    public void setNotificationList(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public ViewHoldel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_notification, parent, false);
        return new ViewHoldel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoldel holder, int position) {
        Notification notification = notificationList.get(position);
        holder.title_notifi.setText(notification.getTitle());
        holder.date_notifi.setText(notification.getDate_time());
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class ViewHoldel extends RecyclerView.ViewHolder {
        private TextView title_notifi, date_notifi;

        public ViewHoldel(@NonNull View itemView) {
            super(itemView);
            title_notifi = itemView.findViewById(R.id.title_notifi);
            date_notifi = itemView.findViewById(R.id.date_notifi);
        }
    }
}
