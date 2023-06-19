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
import android.widget.Toast;

import com.vn.tcshop.foodapp.Apis.RetrofitApi;
import com.vn.tcshop.foodapp.Configs.Constant;
import com.vn.tcshop.foodapp.R;
import com.vn.tcshop.foodapp.Responses.RecoverCodePesponse;
import com.vn.tcshop.foodapp.Responses.RecoverCodeToPesponse;
import com.vn.tcshop.foodapp.Responses.RecoverEmailResponse;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecoverCodeActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private EditText recover_edt_code_email;
    private Button recover_code_send, recover_code_send_to;
    private Bundle extras;
    private Constant constant = new Constant();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_code);

        progressBar = findViewById(R.id.progressBar);
        recover_edt_code_email = findViewById(R.id.recover_edt_code_email);
        recover_code_send = findViewById(R.id.recover_code_send);
        recover_code_send_to = findViewById(R.id.recover_code_send_to);

        extras = getIntent().getExtras();

        codeSend();
        codeSendTo();
    }

    private void codeSendTo() {
        recover_code_send_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (extras != null) {
                    String email = extras.getString("email");

                    progressBar.setVisibility(View.VISIBLE);
                    recover_code_send_to.setEnabled(false);

                    RetrofitApi retrofitApi = constant.retrofit.create(RetrofitApi.class);
                    Call<RecoverCodeToPesponse> call = retrofitApi.recoverCodeTo(email);
                    call.enqueue(new Callback<RecoverCodeToPesponse>() {
                        @Override
                        public void onResponse(Call<RecoverCodeToPesponse> call, Response<RecoverCodeToPesponse> response) {
                            if (response.isSuccessful()) {
                                RecoverCodeToPesponse recoverCodeToPesponse = response.body();
                                String error_recover_email_code_to = recoverCodeToPesponse.getError_recover_email_code_to();
                                String email = recoverCodeToPesponse.getEmail();
                                String login_info = recoverCodeToPesponse.getLogin_info();
                                Log.d("error_recover_email_code_to", error_recover_email_code_to);
                                if (Objects.equals(error_recover_email_code_to, "111")) {
                                    progressBar.setVisibility(View.GONE);
                                    recover_code_send_to.setEnabled(true);
                                    Log.e("error_recover_email_code_to", error_recover_email_code_to);
                                    return;
                                }
                                if (Objects.equals(error_recover_email_code_to, "000")) {
                                    Log.d("error_recover_email", email + " " + login_info);
                                    login_info = recoverCodeToPesponse.getLogin_info();
                                    String subject = "Food Tech";
                                    String body = "Xin chào,\n" +
                                            "\n" +
                                            "Chúng tôi đã nhận được yêu cầu khôi phục mật khẩu cho tài khoản của bạn. Dưới đây là thông tin cần thiết để bạn thực hiện quy trình khôi phục mật khẩu:\n" +
                                            "\n" +
                                            "Email: " + email + "\n" +
                                            "\n" +
                                            "Mã khôi phục: " + login_info + "\n" +
                                            "\n" +
                                            "Vui lòng sử dụng mã khôi phục trên để hoàn tất quy trình khôi phục mật khẩu. Nếu bạn không yêu cầu khôi phục mật khẩu, vui lòng bỏ qua email này.\n" +
                                            "\n" +
                                            "Trân trọng!\n" +
                                            "\n" +
                                            "Lưu ý: Đây là email tự động, vui lòng không phản hồi email này."; // Tùy chỉnh nội dung email theo yêu cầu của bạn
                                    EmailSender.sendEmail(email, subject, body);
                                    Toast.makeText(getApplication(), "Yêu cầu của bạn đã được gửi đi", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    recover_code_send_to.setEnabled(true);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<RecoverCodeToPesponse> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }

    private void codeSend() {
        recover_code_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login_info = recover_edt_code_email.getText().toString();
                if (TextUtils.isEmpty(login_info)) {
                    recover_edt_code_email.setError("Bạn chưa nhập mã");
                    return;
                }
                if (extras != null) {
                    String email = extras.getString("email");

                    progressBar.setVisibility(View.VISIBLE);
                    recover_code_send.setEnabled(false);

                    RetrofitApi retrofitApi = constant.retrofit.create(RetrofitApi.class);
                    Call<RecoverCodePesponse> call = retrofitApi.recoverCode(email, login_info);
                    call.enqueue(new Callback<RecoverCodePesponse>() {
                        @Override
                        public void onResponse(Call<RecoverCodePesponse> call, Response<RecoverCodePesponse> response) {
                            if (response.isSuccessful()) {
                                RecoverCodePesponse recoverCodePesponse = response.body();
                                String email = recoverCodePesponse.getEmail();
                                String login_info = recoverCodePesponse.getLogin_info();
                                String recover_send_mail_code = recoverCodePesponse.getError_recover_email_code();
                                Log.d("recover_send_mail_code", recover_send_mail_code);
                                if (Objects.equals(recover_send_mail_code, "112")) {
                                    progressBar.setVisibility(View.GONE);
                                    recover_code_send.setEnabled(true);
                                    Toast.makeText(RecoverCodeActivity.this, "Mã xác thực không đúng", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (Objects.equals(recover_send_mail_code, "113")) {
                                    progressBar.setVisibility(View.GONE);
                                    recover_code_send.setEnabled(true);
                                    Log.e("recover_send_mail_code", "Failed to update login_info");
                                    return;
                                }
                                if (Objects.equals(recover_send_mail_code, "000")) {
                                    Log.d("000", email + " " + login_info);
                                    Intent intent = new Intent(RecoverCodeActivity.this, RecoverActivity.class);
                                    intent.putExtra("email", email);
                                    startActivity(intent);
                                    progressBar.setVisibility(View.GONE);
                                    recover_code_send.setEnabled(true);
                                }

                            } else {
                                Log.e("error", "error occured, please try again !");
                            }
                        }

                        @Override
                        public void onFailure(Call<RecoverCodePesponse> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }
}