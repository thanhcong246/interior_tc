package com.vn.tcshop.foodapp.Activitis;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.vn.tcshop.foodapp.Activitis.Carts.CartActivity;
import com.vn.tcshop.foodapp.Activitis.Products.CategorisActivity;
import com.vn.tcshop.foodapp.Fragments.MenuFragment;
import com.vn.tcshop.foodapp.Activitis.Products.ProductActivity;
import com.vn.tcshop.foodapp.R;
import com.vn.tcshop.foodapp.Activitis.Settings.SettingActivity;

public class HomeActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    private MenuFragment fragmentMenu;
    private ImageView menuButton;
    private SharedPreferences sharedPreferences;
    private TextView notification_product_cart;
    private RelativeLayout relativelayout_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        fragmentMenu = new MenuFragment();
        menuButton = findViewById(R.id.home_menu_btn);
        relativelayout_1 = findViewById(R.id.relativelayout_1);
        notification_product_cart = findViewById(R.id.notification_product_cart);

        // hiển thị thông báo product
        sharedPreferences = getSharedPreferences("notification_quantity_product", Context.MODE_PRIVATE);
        int savedTotalQuantity = sharedPreferences.getInt("total_quantity_product", 0);
        if (savedTotalQuantity == 0) {
            relativelayout_1.setVisibility(View.GONE);
        } else {
            relativelayout_1.setVisibility(View.VISIBLE);
            notification_product_cart.setText(String.valueOf(savedTotalQuantity));
        }
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
                        startActivity(new Intent(HomeActivity.this, CategorisActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case "Giỏ hàng":
                        startActivity(new Intent(HomeActivity.this, CartActivity.class));
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