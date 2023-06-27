package com.vn.tcshop.foodapp.Retrofits.Configs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vn.tcshop.foodapp.Models.District;
import com.vn.tcshop.foodapp.Models.Province;
import com.vn.tcshop.foodapp.Models.Ward;
import com.vn.tcshop.foodapp.Retrofits.Apis.RetrofitApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConstrantContactForm {
    Gson gson = new GsonBuilder().setLenient().create();

    public Retrofit retrofitContactForm = new Retrofit.Builder()
            .baseUrl("https://provinces.open-api.vn/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
}
