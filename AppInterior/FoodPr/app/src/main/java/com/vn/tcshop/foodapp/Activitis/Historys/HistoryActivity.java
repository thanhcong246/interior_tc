package com.vn.tcshop.foodapp.Activitis.Historys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.vn.tcshop.foodapp.Activitis.Settings.SettingActivity;
import com.vn.tcshop.foodapp.Adapters.History.HistoryAdapter;
import com.vn.tcshop.foodapp.Models.Payment;
import com.vn.tcshop.foodapp.R;
import com.vn.tcshop.foodapp.Retrofits.Apis.RetrofitApi;
import com.vn.tcshop.foodapp.Retrofits.Configs.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {
    private HistoryAdapter historyAdapterl;
    private RecyclerView recyclerView;
    private View closeHistoty;
    private Constant constant = new Constant();
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFS_NAME = "login_prefs";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_REMEMBER_ME = "remember_me";
    private RelativeLayout view_h2, view_h1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        closeHistoty = findViewById(R.id.closeHistoty);
        recyclerView = findViewById(R.id.recyce_history);
        view_h1 = findViewById(R.id.view_h1);
        view_h2 = findViewById(R.id.view_h2);

        historyAdapterl = new HistoryAdapter(new ArrayList<>());
        recyclerView.setAdapter(historyAdapterl);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(KEY_REMEMBER_ME, false)) {
            String savedEmail = sharedPreferences.getString(KEY_EMAIL, "");
            getHistorys(savedEmail);
        }

        closeHistoty_btn();
        click_history_detail();
    }

    private void click_history_detail() {
        HistoryAdapter.ItemClickListener itemClickListener = new HistoryAdapter.ItemClickListener() {
            @Override
            public void onClickItem(int paymentId) {
                Intent intent = new Intent(HistoryActivity.this, HistoryDetailActivity.class);
                intent.putExtra("payment_id", paymentId);
                startActivity(intent);
            }
        };
        historyAdapterl.setItemClickListener(itemClickListener);
    }

    private void closeHistoty_btn() {
        closeHistoty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HistoryActivity.this, SettingActivity.class));
                finish();
            }
        });
    }

    private void getHistorys(String savedEmail) {
        view_h1.setVisibility(View.VISIBLE);
        view_h2.setVisibility(View.GONE);
        RetrofitApi retrofitApi = constant.retrofit.create(RetrofitApi.class);
        Call<List<Payment>> call = retrofitApi.get_history(savedEmail);
        call.enqueue(new Callback<List<Payment>>() {
            @Override
            public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                List<Payment> payment = response.body();
                if (payment != null && !payment.isEmpty()) {
                    historyAdapterl.setPaymentsList(payment);
                    historyAdapterl.notifyDataSetChanged();
                    view_h1.setVisibility(View.GONE);
                    view_h2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Payment>> call, Throwable t) {

            }
        });
    }
}