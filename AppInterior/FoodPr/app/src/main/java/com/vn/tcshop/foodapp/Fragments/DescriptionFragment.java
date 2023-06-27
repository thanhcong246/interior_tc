package com.vn.tcshop.foodapp.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.vn.tcshop.foodapp.Retrofits.Apis.RetrofitApi;
import com.vn.tcshop.foodapp.Retrofits.Configs.Constant;
import com.vn.tcshop.foodapp.Models.Product_detail_desc;
import com.vn.tcshop.foodapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DescriptionFragment extends Fragment {
    private TextView product_detail_description_fragment;
    private Constant constant = new Constant();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_description, container, false);

        product_detail_description_fragment = rootView.findViewById(R.id.product_detail_description_fragment);

        // Kiểm tra xem arguments có null hay không
        Bundle arguments = getArguments();
        if (arguments != null) {
            int productId = arguments.getInt("productId");
            product_detail_description(productId);

        }

        return rootView;
    }

    private void product_detail_description(int productId) {
        RetrofitApi retrofitApi = constant.retrofit.create(RetrofitApi.class);
        Call<List<Product_detail_desc>> call = retrofitApi.get_product_by_id_description(productId);
        call.enqueue(new Callback<List<Product_detail_desc>>() {
            @Override
            public void onResponse(Call<List<Product_detail_desc>> call, Response<List<Product_detail_desc>> response) {
                if (response.isSuccessful()) {
                    Product_detail_desc product_detail_desc = response.body().get(0);
                    String pro_description = product_detail_desc.getDescription();
                    product_detail_description_fragment.setText(pro_description);
                } else {
                    Log.e("product_detail_description_fg", "fail");
                }
            }

            @Override
            public void onFailure(Call<List<Product_detail_desc>> call, Throwable t) {

            }
        });
    }

}