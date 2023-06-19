package com.vn.tcshop.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.vn.tcshop.foodapp.Accounts.LoginActivity;

public class MainActivity extends AppCompatActivity {
    private TextView main_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_login = findViewById(R.id.main_login);

        login_txt();
    }

    private void login_txt() {
        main_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}