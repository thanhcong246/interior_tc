package com.vn.tcshop.foodapp.Retrofits.Configs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Constant {
    Gson gson = new GsonBuilder().setLenient().create();

    public Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl("http://192.168.1.9:86/interior_app/RetrofitPhpApi.php/")
            .baseUrl("https://tcshopfd.000webhostapp.com/interior_app/RetrofitPhpApi.php/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
}