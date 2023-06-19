package com.vn.tcshop.foodapp.Settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.vn.tcshop.foodapp.Carts.CartActivity;
import com.vn.tcshop.foodapp.HomeActivity;
import com.vn.tcshop.foodapp.Products.ProductActivity;
import com.vn.tcshop.foodapp.R;

public class SettingActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        bottomNavigationView = findViewById(R.id.bottom_navigation_bar);

        bottomNavigationView();
    }
    private void bottomNavigationView() {
        bottomNavigationView.setSelectedItemId(R.id.nav_settings);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getTitle().toString()) {
                    case "Trang chủ":
                        startActivity(new Intent(SettingActivity.this, HomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case "Sản phẩm":
                        startActivity(new Intent(SettingActivity.this, ProductActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case "Giỏ hàng":
                        startActivity(new Intent(SettingActivity.this, CartActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case "Cài đặt":
                        return true;
                }
                return false;
            }
        });
    }
}