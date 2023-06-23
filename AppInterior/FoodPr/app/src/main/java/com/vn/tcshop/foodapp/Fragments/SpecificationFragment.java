package com.vn.tcshop.foodapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vn.tcshop.foodapp.Apis.RetrofitApi;
import com.vn.tcshop.foodapp.Configs.Constant;
import com.vn.tcshop.foodapp.Models.Product_detail_desc;
import com.vn.tcshop.foodapp.Models.Product_detail_specifications;
import com.vn.tcshop.foodapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpecificationFragment extends Fragment {
    private Constant constant = new Constant();
    private TextView product_detail_specifications_width, product_detail_specifications_length,
            product_detail_specifications_height, product_detail_specifications_number_of_drawers,
            product_detail_specifications_type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_specification, container, false);

        product_detail_specifications_width = rootView.findViewById(R.id.product_detail_specifications_width);
        product_detail_specifications_length = rootView.findViewById(R.id.product_detail_specifications_length);
        product_detail_specifications_height = rootView.findViewById(R.id.product_detail_specifications_height);
        product_detail_specifications_number_of_drawers = rootView.findViewById(R.id.product_detail_specifications_number_of_drawers);
        product_detail_specifications_type = rootView.findViewById(R.id.product_detail_specifications_type);

        // Kiểm tra xem arguments có null hay không
        Bundle arguments = getArguments();
        if (arguments != null) {
            int productId = arguments.getInt("productId");
            product_detail_specifications(productId);
        }

        return rootView;
    }

    private void product_detail_specifications(int productId) {
        RetrofitApi retrofitApi = constant.retrofit.create(RetrofitApi.class);
        Call<List<Product_detail_specifications>> call = retrofitApi.get_product_by_id_specification(productId);
        call.enqueue(new Callback<List<Product_detail_specifications>>() {
            @Override
            public void onResponse(Call<List<Product_detail_specifications>> call, Response<List<Product_detail_specifications>> response) {
                if (response.isSuccessful()) {
                    Product_detail_specifications product_detail_specifications = response.body().get(0);
                    String specification_width = product_detail_specifications.getWidth();
                    String specification_length = product_detail_specifications.getLength();
                    String specification_height = product_detail_specifications.getHeight();
                    String specification_number_of_drawers = product_detail_specifications.getNumber_of_drawers();
                    String specification_type = product_detail_specifications.getType();

                    product_detail_specifications_width.setText(specification_width);
                    product_detail_specifications_length.setText(specification_length);
                    product_detail_specifications_height.setText(specification_height);
                    product_detail_specifications_number_of_drawers.setText(specification_number_of_drawers);
                    product_detail_specifications_type.setText(specification_type);

                } else {
                    Log.e("product_detail_speccification_fg", "fail");
                }
            }

            @Override
            public void onFailure(Call<List<Product_detail_specifications>> call, Throwable t) {

            }
        });

    }
}