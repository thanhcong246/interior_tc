package com.vn.tcshop.foodapp.Products;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.vn.tcshop.foodapp.Adapters.Products.ProductAdapter;
import com.vn.tcshop.foodapp.Apis.RetrofitApi;
import com.vn.tcshop.foodapp.Carts.CartActivity;
import com.vn.tcshop.foodapp.Configs.Constant;
import com.vn.tcshop.foodapp.HomeActivity;
import com.vn.tcshop.foodapp.Models.Product;
import com.vn.tcshop.foodapp.R;
import com.vn.tcshop.foodapp.Settings.SettingActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private Constant constant = new Constant();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        recyclerView = findViewById(R.id.recyclerView);


        // Khởi tạo adapter và thiết lập RecyclerView
        adapter = new ProductAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        // --------------------------

        bottomNavigationView();
        getProducts();
        click_item_product();


    }

    private void click_item_product() {
        ProductAdapter.ItemClickListener itemClickListener = new ProductAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int productId) {
                //Chuyển sang trang chi tiết sản phẩm với ID của sản phẩm được nhấp
                Intent intent = new Intent(ProductActivity.this, ProductDetailActivity.class);
                intent.putExtra("productId", productId);
                startActivity(intent);
            }
        };
        // Thiết lập ItemClickListener cho adapter
        adapter.setItemClickListener(itemClickListener);
    }

    private void getProducts() {
        RetrofitApi retrofitApi = constant.retrofit.create(RetrofitApi.class);
        Call<List<Product>> call = retrofitApi.get_all_product();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> productList = response.body();
                    // Cập nhật danh sách sản phẩm trong adapter
                    adapter.setProductList(productList);
                    adapter.notifyDataSetChanged();
                } else {
                    // Xử lý khi không thành công
                    Log.e("fale product", "Failed to get products");
                }

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });

    }


    private void bottomNavigationView() {
        bottomNavigationView.setSelectedItemId(R.id.nav_product);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getTitle().toString()) {
                    case "Trang chủ":
                        startActivity(new Intent(ProductActivity.this, HomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case "Sản phẩm":
                        return true;
                    case "Giỏ hàng":
                        startActivity(new Intent(ProductActivity.this, CartActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case "Cài đặt":
                        startActivity(new Intent(ProductActivity.this, SettingActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

}