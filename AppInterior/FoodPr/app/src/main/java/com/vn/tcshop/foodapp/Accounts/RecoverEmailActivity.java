package com.vn.tcshop.foodapp.Accounts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.vn.tcshop.foodapp.Responses.RecoverEmailResponse;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RecoverEmailActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private Button recover_send;
    private EditText sendmail_edt;
    private TextView recover_login;

    private Constant constant = new Constant();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_email);

        progressBar = findViewById(R.id.progressBar);
        recover_send = findViewById(R.id.recover_btn_email);
        sendmail_edt = findViewById(R.id.recover_edt_email);
        recover_login = findViewById(R.id.recover_login);

        login_txt();
//        sendMail
        recoverSendMail();
    }

    private void login_txt() {
        recover_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RecoverEmailActivity.this, LoginActivity.class));
            }
        });
    }

    private void recoverSendMail() {
        recover_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String recipientEmail = sendmail_edt.getText().toString().trim();
                if (recipientEmail.isEmpty()) {
                    sendmail_edt.setError("Vui lòng nhập email");
                    return;
                }
                if (!isValidEmail(recipientEmail)) {
                    sendmail_edt.setError("Email không đúng định dạng");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                recover_send.setEnabled(false);

                RetrofitApi retrofitApi = constant.retrofit.create(RetrofitApi.class);
                Call<RecoverEmailResponse> call = retrofitApi.recoverEmail(recipientEmail);
                call.enqueue(new Callback<RecoverEmailResponse>() {
                    @Override
                    public void onResponse(Call<RecoverEmailResponse> call, Response<RecoverEmailResponse> response) {
                        if (response.isSuccessful()) {
                            RecoverEmailResponse recoverEmailResponse = response.body();
                            String error_recover_email = recoverEmailResponse.getError_recover_email();
                            String email = recoverEmailResponse.getEmail();
                            String login_info = recoverEmailResponse.getLogin_info();
                            Log.d("error_recover_email", error_recover_email);
                            if (Objects.equals(error_recover_email, "111")) {
                                progressBar.setVisibility(View.GONE);
                                recover_send.setEnabled(true);
                                Toast.makeText(RecoverEmailActivity.this, "Email không tồn tại", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (Objects.equals(error_recover_email, "000")) {
                                Log.d("error_recover_email", email + " " + login_info);
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
                                EmailSender.sendEmail(recipientEmail, subject, body);
                                Toast.makeText(getApplication(), "Yêu cầu của bạn đã được gửi đi", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RecoverEmailActivity.this, RecoverCodeActivity.class);
                                intent.putExtra("email", email);
                                startActivity(intent);
                                progressBar.setVisibility(View.GONE);
                                recover_send.setEnabled(true);
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RecoverEmailResponse> call, Throwable t) {

                    }
                });
            }
        });
    }

    public boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}