package com.vn.tcshop.foodapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.vn.tcshop.foodapp.Accounts.LoginActivity;
import com.vn.tcshop.foodapp.Carts.CartActivity;
import com.vn.tcshop.foodapp.Products.ProductActivity;
import com.vn.tcshop.foodapp.Settings.SettingActivity;

public class HomeActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFS_NAME = "login_prefs";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_REMEMBER_ME = "remember_me";

    private TextView name_user_txt;
    private Button logout_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        name_user_txt = findViewById(R.id.home_name_user);
        logout_btn = findViewById(R.id.home_logout);

        sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(KEY_REMEMBER_ME, false)) {
            String savedName = sharedPreferences.getString(KEY_NAME, "");
            String savedEmail = sharedPreferences.getString(KEY_EMAIL, "");
            String savedPassword = sharedPreferences.getString(KEY_PASSWORD, "");

            name_user_txt.setText(savedName);
        }

        logout_btn();
        bottomNavigationView();
    }

    private void logout_btn() {
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Khi đăng xuất
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(KEY_EMAIL);
                editor.remove(KEY_PASSWORD);
                editor.remove(KEY_REMEMBER_ME);
                editor.apply();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void bottomNavigationView() {
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getTitle().toString()) {
                    case "Trang chủ":
                        return true;
                    case "Sản phẩm":
                        startActivity(new Intent(HomeActivity.this, ProductActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case "Giỏ hàng":
                        startActivity(new Intent(HomeActivity.this, CartActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case "Cài đặt":
                        startActivity(new Intent(HomeActivity.this, SettingActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }
}