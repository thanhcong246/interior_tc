package com.vn.tcshop.foodapp.Products;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.vn.tcshop.foodapp.R;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Lấy productId từ intent
        int productId = getIntent().getIntExtra("productId", 0);

        Log.d("productId", String.valueOf(productId));
    }
}