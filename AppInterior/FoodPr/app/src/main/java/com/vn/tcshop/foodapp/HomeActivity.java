package com.vn.tcshop.foodapp;

import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.vn.tcshop.foodapp.Accounts.LoginActivity;
import com.vn.tcshop.foodapp.Carts.CartActivity;
import com.vn.tcshop.foodapp.Fragments.MenuFragment;
import com.vn.tcshop.foodapp.Products.ProductActivity;
import com.vn.tcshop.foodapp.Settings.SettingActivity;

public class HomeActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    private MenuFragment fragmentMenu;
    private ImageView menuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottom_navigation_bar);

        fragmentMenu = new MenuFragment();

        menuButton = findViewById(R.id.home_menu_btn);

        bottomNavigationView();
        // Hiển thị Fragment Menu
        displayFragmentMenu();
    }

    private void displayFragmentMenu() {
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Áp dụng animation cho Fragment mới
                fragmentTransaction.setCustomAnimations(R.anim.slide_right_to_left, 0, 0, R.anim.slide_left_to_right);

                fragmentTransaction.replace(R.id.fragment_container, fragmentMenu);
                fragmentTransaction.addToBackStack(null); // (Optional) Cho phép quay lại Fragment trước đó
                fragmentTransaction.commit();
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