package com.vn.tcshop.foodapp.Accounts;

import android.content.Intent;
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

import androidx.appcompat.app.AppCompatActivity;

import com.vn.tcshop.foodapp.Apis.RetrofitApi;
import com.vn.tcshop.foodapp.Configs.Constant;
import com.vn.tcshop.foodapp.R;
import com.vn.tcshop.foodapp.Responses.RegisterResponse;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView register_login;
    private EditText edt_name, edt_email, edt_password, edt_confirmPass;
    private Button btn_register;
    private Constant constant = new Constant();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressBar = findViewById(R.id.progressBar);
        register_login = findViewById(R.id.register_login);
        edt_name = findViewById(R.id.register_user);
        edt_email = findViewById(R.id.register_email);
        edt_password = findViewById(R.id.register_password);
        edt_confirmPass = findViewById(R.id.register_confirm_password);
        btn_register = findViewById(R.id.register_btn);

        register();

        login_txt();
    }

    private void register() {
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edt_name.getText().toString();
                String email = edt_email.getText().toString();
                String password = edt_password.getText().toString();
                String confirmPass = edt_confirmPass.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    edt_name.setError("Bạn chưa nhập tên");
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    edt_email.setError("Bạn chưa nhập email");
                    return;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    edt_email.setError("Email không hợp lệ");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    edt_password.setError("Bạn chưa nhập mật khẩu");
                    return;
                } else if (password.length() < 6) {
                    edt_password.setError("Mật khẩu phải có ít nhất 6 kí tự");
                    return;
                } else if (!password.equals(confirmPass) || TextUtils.isEmpty(confirmPass)) {
                    edt_confirmPass.setError("Xác nhận mật khẩu không trùng khớp");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                btn_register.setEnabled(false);

                RetrofitApi retrofitApi = constant.retrofit.create(RetrofitApi.class);
                Call<RegisterResponse> call = retrofitApi.register(name, email, password);
                call.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        RegisterResponse reg_response = response.body();
                        String errorcode = reg_response.getErrorcode();
                        String message = reg_response.getMessage();
                        Log.d("errorcode", errorcode);
                        Log.d("message", message);
                        if (Objects.equals(errorcode, "201")) {
                            progressBar.setVisibility(View.GONE);
                            btn_register.setEnabled(true);
                            edt_name.setError("Tên đã tồn tại");
                            return;
                        }
                        if (Objects.equals(errorcode, "202")) {
                            progressBar.setVisibility(View.GONE);
                            btn_register.setEnabled(true);
                            edt_email.setError("Email đã tồn tại");
                            return;
                        }
                        if (Objects.equals(errorcode, "000")) {
                            Toast.makeText(getApplication(), "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            progressBar.setVisibility(View.GONE);
                            btn_register.setEnabled(true);
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        Log.d("error", t.getMessage());
                    }
                });
            }
        });
    }

    private void login_txt() {
        register_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }
}