package com.vn.tcshop.foodapp.Activitis.Carts;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.vn.tcshop.foodapp.Models.City;
import com.vn.tcshop.foodapp.Models.District;
import com.vn.tcshop.foodapp.Models.Province;
import com.vn.tcshop.foodapp.Models.Ward;
import com.vn.tcshop.foodapp.R;
import com.vn.tcshop.foodapp.Retrofits.Apis.RetrofitApi;
import com.vn.tcshop.foodapp.Retrofits.Configs.ConstrantContactForm;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailCartActivity extends AppCompatActivity {

    private ConstrantContactForm constrantContactForm = new ConstrantContactForm();
    private Spinner spinnerProvices;
    private Spinner spinnerDistrict;
    private Spinner spinnerWard;
    private ArrayAdapter<Province> adapter1;
    private ArrayAdapter<District> adapter2;
    private ArrayAdapter<Ward> adapter3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cart);
        spinnerProvices = findViewById(R.id.spinnerProvices);
        spinnerDistrict = findViewById(R.id.spinnerDistrict);
        spinnerWard = findViewById(R.id.spinnerWard);

        // Tạo ArrayAdapter và thiết lập cho Spinner
        adapter1 = new ArrayAdapter<>(DetailCartActivity.this, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvices.setAdapter(adapter1);

        adapter2 = new ArrayAdapter<>(DetailCartActivity.this, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDistrict.setAdapter(adapter2);

        adapter3 = new ArrayAdapter<>(DetailCartActivity.this, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWard.setAdapter(adapter3);

        getProvices();
    }

    private void getProvices() {
        RetrofitApi retrofitApi = constrantContactForm.retrofitContactForm.create(RetrofitApi.class);
        Call<List<Province>> call = retrofitApi.getProvinces();
        call.enqueue(new Callback<List<Province>>() {
            @Override
            public void onResponse(Call<List<Province>> call, Response<List<Province>> response) {
                if (response.isSuccessful()) {
                    List<Province> provinces = response.body();
                    adapter1.clear();
                    adapter1.addAll(provinces);
                    adapter1.notifyDataSetChanged();

                    // Xử lý sự kiện khi người dùng chọn một mục từ Spinner
                    spinnerProvices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Province selectedProvince = (Province) parent.getItemAtPosition(position);
                            adapter2.clear();
                            adapter3.clear();
                            int provinceCode = selectedProvince.getCode();
                            getDistricts(provinceCode);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // Xử lý khi không có mục nào được chọn trong Spinner
                        }
                    });
                } else {
                    // Xử lý khi có lỗi trong cuộc gọi API
                }
            }

            @Override
            public void onFailure(Call<List<Province>> call, Throwable t) {
                // Xử lý khi có lỗi trong cuộc gọi API
            }
        });
    }

    private void getDistricts(int provinceCode) {
        RetrofitApi retrofitApi = constrantContactForm.retrofitContactForm.create(RetrofitApi.class);
        Call<City> districtCall = retrofitApi.getDistricts(provinceCode);
        districtCall.enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                City city = response.body();
                adapter2.clear();
                // Hiển thị thông tin districts
                List<District> districts = city.getDistricts();
                for (District district : districts) {
                    adapter2.add(district);
                }
                adapter2.notifyDataSetChanged();

                // Xử lý sự kiện khi người dùng chọn một mục từ Spinner
                spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        District selectedDistrict = (District) parent.getItemAtPosition(position);
                        int districtCode = selectedDistrict.getCode();
                        getWard(districtCode);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // Xử lý khi không có mục nào được chọn trong Spinner
                    }
                });
            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {

            }
        });
    }

    private void getWard(int districtCode) {
        RetrofitApi retrofitApi = constrantContactForm.retrofitContactForm.create(RetrofitApi.class);
        Call<District> call = retrofitApi.getWards(districtCode);
        call.enqueue(new Callback<District>() {
            @Override
            public void onResponse(Call<District> call, Response<District> response) {
                District district = response.body();
                adapter3.clear();
                // Hiển thị thông tin districts
                List<Ward> wards = district.getWards();
                for (Ward ward : wards) {
                    adapter3.add(ward);
                }
                adapter3.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<District> call, Throwable t) {

            }
        });
    }

}
