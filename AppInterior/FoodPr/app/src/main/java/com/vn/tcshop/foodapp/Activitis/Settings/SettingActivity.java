package com.vn.tcshop.foodapp.Activitis.Settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.vn.tcshop.foodapp.Activitis.Carts.CartActivity;
import com.vn.tcshop.foodapp.Activitis.HomeActivity;
import com.vn.tcshop.foodapp.Activitis.Products.CategorisActivity;
import com.vn.tcshop.foodapp.Activitis.Products.ProductActivity;
import com.vn.tcshop.foodapp.Fragments.SettingFragment;
import com.vn.tcshop.foodapp.R;

public class SettingActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFS_NAME = "login_prefs";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_REMEMBER_ME = "remember_me";
    private TextView setting_user_name, setting_user_email;
    private BottomNavigationView bottomNavigationView;
    private TextView notification_product_cart;
    private RelativeLayout relativelayout_1;
    private SettingFragment settingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        setting_user_name = findViewById(R.id.setting_user_name);
        setting_user_email = findViewById(R.id.setting_user_email);
        relativelayout_1 = findViewById(R.id.relativelayout_1);
        notification_product_cart = findViewById(R.id.notification_product_cart);

        // hiển thị thông báo product
        SharedPreferences sharedPreferencess = getSharedPreferences("notification_quantity_product", Context.MODE_PRIVATE);
        int savedTotalQuantity = sharedPreferencess.getInt("total_quantity_product", 0);
        if (savedTotalQuantity == 0) {
            relativelayout_1.setVisibility(View.GONE);
        } else {
            relativelayout_1.setVisibility(View.VISIBLE);
            notification_product_cart.setText(String.valueOf(savedTotalQuantity));
        }


        sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(KEY_REMEMBER_ME, false)) {
            String savedName = sharedPreferences.getString(KEY_NAME, "");
            String savedEmail = sharedPreferences.getString(KEY_EMAIL, "");

            setting_user_name.setText(savedName);
            setting_user_email.setText(savedEmail);

            logout_btn();
        }

        bottomNavigationView();
        displayFragmentMenu();
    }

    private void displayFragmentMenu() {
        settingFragment = new SettingFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.setting_fragment, settingFragment);
        fragmentTransaction.commit();

    }

    private void logout_btn() {
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
                        startActivity(new Intent(SettingActivity.this, CategorisActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case "Giỏ hàng":
                        startActivity(new Intent(SettingActivity.this, CartActivity.class));
                        return true;
                    case "Cài đặt":
                        return true;
                }
                return false;
            }
        });
    }
}