package com.vn.tcshop.foodapp.Accounts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vn.tcshop.foodapp.Apis.RetrofitApi;
import com.vn.tcshop.foodapp.Configs.Constant;
import com.vn.tcshop.foodapp.HomeActivity;
import com.vn.tcshop.foodapp.R;
import com.vn.tcshop.foodapp.Responses.LoginResponse;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView login_register, login_getback;
    private EditText login_edt_email, login_edt_password;
    private Button login_btn;
    private Constant constant = new Constant();

    // nhớ mật khẩu
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFS_NAME = "login_prefs";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_REMEMBER_ME = "remember_me";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = findViewById(R.id.progressBar);
        login_register = findViewById(R.id.login_register);
        login_getback = findViewById(R.id.login_getback);
        login_edt_email = findViewById(R.id.login_edt_email);
        login_edt_password = findViewById(R.id.login_edt_password);
        login_btn = findViewById(R.id.login_btn);

        login();

        register_txt();
        login_getback_txt();
    }

    private void login() {
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = login_edt_email.getText().toString();
                String password = login_edt_password.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    login_edt_email.setError("Bạn chưa nhập email");
                    return;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    login_edt_email.setError("Email không hợp lệ");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    login_edt_password.setError("Bạn chưa nhập mật khẩu");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                login_btn.setEnabled(false);

                RetrofitApi retrofitApi = constant.retrofit.create(RetrofitApi.class);
                Call<LoginResponse> call = retrofitApi.login(email, password);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            LoginResponse loginResponse = response.body();
                            String error_login = loginResponse.getError_login();
                            String name = loginResponse.getName();
                            String email1 = loginResponse.getEmail();
                            String password1 = loginResponse.getPassword();
                            Log.d("error_login", error_login);
                            if (Objects.equals(error_login, "101")) {
                                progressBar.setVisibility(View.GONE);
                                login_btn.setEnabled(true);
                                Toast.makeText(LoginActivity.this, "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (Objects.equals(error_login, "111")) {
                                progressBar.setVisibility(View.GONE);
                                login_btn.setEnabled(true);
                                Toast.makeText(LoginActivity.this, "Email không tồn tại", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (Objects.equals(error_login, "000")) {
                                Log.d("user", name + " " + email1 + " " + password1);

                                // Lưu thông tin đăng nhập vào SharedPreferences
                                sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(KEY_NAME, name);
                                editor.putString(KEY_EMAIL, email);
                                editor.putString(KEY_PASSWORD, password);
                                editor.putBoolean(KEY_REMEMBER_ME, true);
                                editor.apply();

                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                                progressBar.setVisibility(View.GONE);
                                login_btn.setEnabled(true);
                            }
                        } else {
                            Log.e("error", "error occured, please try again !");
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void register_txt() {
        login_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void login_getback_txt() {
        login_getback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RecoverEmailActivity.class));
            }
        });
    }
}
