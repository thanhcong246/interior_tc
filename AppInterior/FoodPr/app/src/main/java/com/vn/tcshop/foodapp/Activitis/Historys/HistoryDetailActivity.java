package com.vn.tcshop.foodapp.Activitis.Historys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.vn.tcshop.foodapp.Models.Payment;
import com.vn.tcshop.foodapp.R;
import com.vn.tcshop.foodapp.Retrofits.Apis.RetrofitApi;
import com.vn.tcshop.foodapp.Retrofits.Configs.Constant;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryDetailActivity extends AppCompatActivity {
    private View close;
    private TextView pm_name, pm_email, pm_phone, pm_apartment_number, pm_ward, pm_district, pm_province, pm_note, pm_quantityProduct, pm_total, pm_payment_date;
    private Constant constant = new Constant();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);

        close = findViewById(R.id.closeHistotyDt);
        pm_name = findViewById(R.id.pm_name);
        pm_email = findViewById(R.id.pm_email);
        pm_phone = findViewById(R.id.pm_phone);
        pm_apartment_number = findViewById(R.id.pm_apartment_number);
        pm_ward = findViewById(R.id.pm_ward);
        pm_district = findViewById(R.id.pm_district);
        pm_province = findViewById(R.id.pm_province);
        pm_note = findViewById(R.id.pm_note);
        pm_quantityProduct = findViewById(R.id.pm_quantityProduct);
        pm_total = findViewById(R.id.pm_total);
        pm_payment_date = findViewById(R.id.pm_payment_date);

        int payment_id = getIntent().getIntExtra("payment_id", 0);
        getPmDetail(payment_id);

        closehdt();
    }

    private void getPmDetail(int payment_id) {
        RetrofitApi retrofitApi = constant.retrofit.create(RetrofitApi.class);
        Call<Payment> call = retrofitApi.get_history_detail(payment_id);
        call.enqueue(new Callback<Payment>() {
            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {
                if (response.isSuccessful()) {
                    Payment payment = response.body();
                    assert payment != null;
                    // Hiển thị giá sản phẩm với định dạng ngăn cách hàng nghìn
                    DecimalFormat decimalFormat = new DecimalFormat("#,###");
                    String priceFormatted = decimalFormat.format(payment.getTotal());

                    pm_name.setText(payment.getName());
                    pm_email.setText(payment.getEmail());
                    pm_phone.setText(payment.getPhone());
                    pm_apartment_number.setText(payment.getApartment_number());
                    pm_ward.setText(payment.getWard());
                    pm_district.setText(payment.getDistrict());
                    pm_province.setText(payment.getProvince());
                    pm_note.setText(payment.getNote());
                    pm_quantityProduct.setText(String.valueOf(payment.getQuantityProduct()));
                    pm_total.setText(priceFormatted + "đ");
                    pm_payment_date.setText(payment.getPayment_date());
                }
            }

            @Override
            public void onFailure(Call<Payment> call, Throwable t) {

            }
        });

    }

    private void closehdt() {
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HistoryDetailActivity.this, HistoryActivity.class));
                finish();
            }
        });
    }
}