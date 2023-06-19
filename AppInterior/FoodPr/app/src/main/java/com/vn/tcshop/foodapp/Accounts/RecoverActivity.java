package com.vn.tcshop.foodapp.Accounts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vn.tcshop.foodapp.Apis.RetrofitApi;
import com.vn.tcshop.foodapp.Configs.Constant;
import com.vn.tcshop.foodapp.R;
import com.vn.tcshop.foodapp.Responses.RecoverPesponse;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecoverActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private EditText recover_edt_password, recover_edt_confirm_password;
    private Button recover_submit;
    private Bundle extras;
    private Constant constant = new Constant();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover);

        progressBar = findViewById(R.id.progressBar);
        recover_edt_password = findViewById(R.id.recover_edt_password);
        recover_edt_confirm_password = findViewById(R.id.recover_edt_confirm_password);
        recover_submit = findViewById(R.id.recover_submit);

        extras = getIntent().getExtras();

        recover();

    }

    private void recover() {
        recover_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = recover_edt_password.getText().toString();
                String confirmPass = recover_edt_confirm_password.getText().toString();
                if (TextUtils.isEmpty(password)) {
                    recover_edt_password.setError("Bạn chưa nhập mật khẩu");
                    return;
                } else if (password.length() < 6) {
                    recover_edt_password.setError("Mật khẩu phải có ít nhất 6 kí tự");
                    return;
                } else if (!password.equals(confirmPass) || TextUtils.isEmpty(confirmPass)) {
                    recover_edt_confirm_password.setError("Xác nhận mật khẩu không trùng khớp");
                    return;
                }
                if (extras != null) {
                    String email = extras.getString("email");

                    progressBar.setVisibility(View.VISIBLE);
                    recover_submit.setEnabled(false);

                    RetrofitApi retrofitApi = constant.retrofit.create(RetrofitApi.class);
                    Call<RecoverPesponse> call = retrofitApi.recover(email, password);
                    call.enqueue(new Callback<RecoverPesponse>() {
                        @Override
                        public void onResponse(Call<RecoverPesponse> call, Response<RecoverPesponse> response) {
                            if (response.isSuccessful()) {
                                RecoverPesponse recoverPesponse = response.body();
                                String error_recover_password = recoverPesponse.getError_recover_password();
                                if (Objects.equals(error_recover_password, "000")) {
                                    Toast.makeText(getApplication(), "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RecoverActivity.this, LoginActivity.class));
                                    progressBar.setVisibility(View.GONE);
                                    recover_submit.setEnabled(true);
                                    finish();
                                } else {
                                    Log.e("error_recover_password", error_recover_password);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<RecoverPesponse> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }

}