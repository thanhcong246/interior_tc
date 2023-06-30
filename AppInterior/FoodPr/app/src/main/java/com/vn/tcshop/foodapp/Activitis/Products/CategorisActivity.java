package com.vn.tcshop.foodapp.Activitis.Products;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.vn.tcshop.foodapp.Activitis.Carts.CartActivity;
import com.vn.tcshop.foodapp.Activitis.HomeActivity;
import com.vn.tcshop.foodapp.Activitis.Settings.SettingActivity;
import com.vn.tcshop.foodapp.Adapters.Products.CategoriAdapter;
import com.vn.tcshop.foodapp.Models.Category;
import com.vn.tcshop.foodapp.R;
import com.vn.tcshop.foodapp.Retrofits.Apis.RetrofitApi;
import com.vn.tcshop.foodapp.Retrofits.Configs.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategorisActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private TextView notification_product_cart;
    private RelativeLayout relativelayout_1;
    private BottomNavigationView bottomNavigationView;
    private TextView category_getAllProduct;
    private RecyclerView view_all_category_recycler;
    private CategoriAdapter categoriAdapter;
    private Constant constant = new Constant();
    private TextView showSearchProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoris);

        bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        relativelayout_1 = findViewById(R.id.relativelayout_1);
        notification_product_cart = findViewById(R.id.notification_product_cart);
        category_getAllProduct = findViewById(R.id.category_getAllProduct);
        view_all_category_recycler = findViewById(R.id.view_all_category_recycler);
        showSearchProduct = findViewById(R.id.showSearchProduct);

        // hiển thị thông báo product
        sharedPreferences = getSharedPreferences("notification_quantity_product", Context.MODE_PRIVATE);
        int savedTotalQuantity = sharedPreferences.getInt("total_quantity_product", 0);
        if (savedTotalQuantity == 0) {
            relativelayout_1.setVisibility(View.GONE);
        } else {
            relativelayout_1.setVisibility(View.VISIBLE);
            notification_product_cart.setText(String.valueOf(savedTotalQuantity));
        }

        // Khởi tạo adapter và thiết lập RecyclerView
        categoriAdapter = new CategoriAdapter(new ArrayList<>());
        view_all_category_recycler.setAdapter(categoriAdapter);
        view_all_category_recycler.setLayoutManager(new LinearLayoutManager(this));

        bottomNavigationView();
        getAllProduct();
        getAllCategories();
        showSearchProductBtn();
        clickProductByIdCategory();
    }

    private void clickProductByIdCategory() {
        CategoriAdapter.ItemClickCategory itemClickCategory = new CategoriAdapter.ItemClickCategory() {
            @Override
            public void onClickCategory(int category_id) {
                Intent intent = new Intent(CategorisActivity.this, ProductByCategoryIdActivity.class);
                intent.putExtra("category_id", category_id);
                startActivity(intent);
            }
        };
        categoriAdapter.setItemClickCategory(itemClickCategory);
    }

    private void showSearchProductBtn() {
        showSearchProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CategorisActivity.this, SearchProductActivity.class));
            }
        });
    }

    private void getAllCategories() {
        RetrofitApi retrofitApi = constant.retrofit.create(RetrofitApi.class);
        Call<List<Category>> call = retrofitApi.get_all_category();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    List<Category> categoryList = response.body();
                    categoriAdapter.setCategoryList(categoryList);
                    categoriAdapter.notifyDataSetChanged();
                } else {
                    // Xử lý khi không thành công
                    Log.e("fale category", "Failed to get categories");
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });
    }

    private void getAllProduct() {
        category_getAllProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CategorisActivity.this, ProductActivity.class));
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
                        startActivity(new Intent(CategorisActivity.this, HomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case "Sản phẩm":
                        return true;
                    case "Giỏ hàng":
                        startActivity(new Intent(CategorisActivity.this, CartActivity.class));
                        return true;
                    case "Cài đặt":
                        startActivity(new Intent(CategorisActivity.this, SettingActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }
}