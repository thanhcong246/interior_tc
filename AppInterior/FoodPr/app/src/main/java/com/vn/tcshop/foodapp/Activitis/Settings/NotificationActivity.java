package com.vn.tcshop.foodapp.Activitis.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.vn.tcshop.foodapp.Activitis.HomeActivity;
import com.vn.tcshop.foodapp.Adapters.NotificationAdapter;
import com.vn.tcshop.foodapp.Models.Notification;
import com.vn.tcshop.foodapp.R;
import com.vn.tcshop.foodapp.Retrofits.Apis.RetrofitApi;
import com.vn.tcshop.foodapp.Retrofits.Configs.Constant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {
    private View closeNotification;
    private Constant constant = new Constant();
    private RecyclerView recycle_notification;
    private NotificationAdapter adapterNotifi;
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFS_NAME = "login_prefs";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_REMEMBER_ME = "remember_me";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        closeNotification = findViewById(R.id.closeNotification);
        recycle_notification = findViewById(R.id.recycle_notification);

        adapterNotifi = new NotificationAdapter(new ArrayList<>());
        recycle_notification.setAdapter(adapterNotifi);
        recycle_notification.setLayoutManager(new LinearLayoutManager(this));


        sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(KEY_REMEMBER_ME, false)) {
            String savedEmail = sharedPreferences.getString(KEY_EMAIL, "");
            getNotifications(savedEmail);
        }

        closeNotification_btn();
    }

    private void getNotifications(String saveEmail) {
        RetrofitApi retrofitApi = constant.retrofit.create(RetrofitApi.class);
        Call<List<Notification>> call = retrofitApi.get_notifications(saveEmail);
        call.enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                if (response.isSuccessful()) {
                    List<Notification> notificationList = response.body();
                    Collections.reverse(notificationList); // Đảo ngược danh sách
                    adapterNotifi.setNotificationList(notificationList);
                    adapterNotifi.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {

            }
        });
    }

    private void closeNotification_btn() {
        closeNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(NotificationActivity.this, HomeActivity.class));
            }
        });
    }
}