package com.vn.tcshop.foodapp.Activitis.Carts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vn.tcshop.foodapp.Activitis.Accounts.EmailSender;
import com.vn.tcshop.foodapp.Activitis.Accounts.RecoverCodeActivity;
import com.vn.tcshop.foodapp.Activitis.Accounts.RecoverEmailActivity;
import com.vn.tcshop.foodapp.Models.CartByPayment;
import com.vn.tcshop.foodapp.Models.City;
import com.vn.tcshop.foodapp.Models.District;
import com.vn.tcshop.foodapp.Models.Payments;
import com.vn.tcshop.foodapp.Models.Province;
import com.vn.tcshop.foodapp.Models.Responses.CartDeleteAllResponse;
import com.vn.tcshop.foodapp.Models.Responses.RecoverEmailResponse;
import com.vn.tcshop.foodapp.Models.Ward;
import com.vn.tcshop.foodapp.R;
import com.vn.tcshop.foodapp.Retrofits.Apis.RetrofitApi;
import com.vn.tcshop.foodapp.Retrofits.Configs.Constant;
import com.vn.tcshop.foodapp.Retrofits.Configs.ConstrantContactForm;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailCartActivity extends AppCompatActivity {

    private ConstrantContactForm constrantContactForm = new ConstrantContactForm();
    private Spinner spinnerProvices;
    private Spinner spinnerDistrict;
    private Spinner spinnerWard;
    private ArrayAdapter<Province> adapter1;
    private ArrayAdapter<District> adapter2;
    private ArrayAdapter<Ward> adapter3;
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFS_NAME = "login_prefs";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_REMEMBER_ME = "remember_me";
    private String selectedProvinceName = "";
    private String selectedDistrictName = "";
    private String selectedWardName = "";
    private Constant constant = new Constant();
    private View closePaymentProduct;
    private Button payment_submit;
    private TextView payment_quantity;
    private TextView payment_total;
    private EditText payment_apartment_number, payment_name, payment_phone, payment_email, payment_note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cart);
        spinnerProvices = findViewById(R.id.spinnerProvices);
        spinnerDistrict = findViewById(R.id.spinnerDistrict);
        spinnerWard = findViewById(R.id.spinnerWard);
        closePaymentProduct = findViewById(R.id.closePaymentProduct);
        payment_submit = findViewById(R.id.payment_submit);
        payment_quantity = findViewById(R.id.payment_quantity);
        payment_total = findViewById(R.id.payment_total);
        payment_apartment_number = findViewById(R.id.payment_apartment_number);
        payment_name = findViewById(R.id.payment_name);
        payment_phone = findViewById(R.id.payment_phone);
        payment_email = findViewById(R.id.payment_email);
        payment_note = findViewById(R.id.payment_note);

        // Tạo ArrayAdapter và thiết lập cho Spinner
        adapter1 = new ArrayAdapter<>(DetailCartActivity.this, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvices.setAdapter(adapter1);

        adapter2 = new ArrayAdapter<>(DetailCartActivity.this, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDistrict.setAdapter(adapter2);

        adapter3 = new ArrayAdapter<>(DetailCartActivity.this, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWard.setAdapter(adapter3);

        sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(KEY_REMEMBER_ME, false)) {
            String savedName = sharedPreferences.getString(KEY_NAME, "");
            String savedEmail = sharedPreferences.getString(KEY_EMAIL, "");
            payment_name.setText(savedName);
            payment_email.setText(savedEmail);
            int caseP = getIntent().getIntExtra("case", 0);
            if (caseP == 1) {
                getTotalCartPayment(savedName, savedEmail);
                closePm();
            } else {
                getTotalCartPaymentm(savedName, savedEmail);
                closePmm();
            }
        }

        getProvices();
    }

    private void paymentProduct(String savedName, String savedEmail, int quantityProduct, int total) {
        payment_name.setEnabled(false);
        payment_email.setEnabled(false);
        payment_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String apartment_number = payment_apartment_number.getText().toString().trim();
                String phone = payment_phone.getText().toString().trim();
                String note = payment_note.getText().toString().trim();
                if (TextUtils.isEmpty(apartment_number)) {
                    payment_apartment_number.setError("Địa chỉ nhà trống");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    payment_phone.setError("Phone trống");
                    return;
                }

                if (!phone.matches("\\d+")) {
                    payment_phone.setError("Phone không hợp lệ");
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(DetailCartActivity.this);
                builder.setTitle("Xác nhận thanh toán")
                        .setMessage("Bạn có chắc chắn muốn tiến hành thanh toán?")
                        .setPositiveButton("Thanh toán", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                RetrofitApi retrofitApi = constant.retrofit.create(RetrofitApi.class);
                                Call<Payments> call = retrofitApi.add_payments(savedName, savedEmail, phone, apartment_number,
                                        selectedWardName, selectedDistrictName, selectedProvinceName, note,
                                        quantityProduct, total);
                                call.enqueue(new Callback<Payments>() {
                                    @Override
                                    public void onResponse(Call<Payments> call, Response<Payments> response) {
                                        if (response.isSuccessful()) {
                                            Payments payments = response.body();
                                            String payment = payments.getPayments();
                                            if (payment.equals("000")) {
                                                Toast.makeText(getApplicationContext(), "Bạn đã thanh toán sản phẩm đội ngũ chúng tôi sẽ liên hệ bạn sớm vui lòng kiểm tra email", Toast.LENGTH_SHORT).show();
                                                int caseP = getIntent().getIntExtra("case", 0);
                                                if (caseP == 1) {
                                                    deleteAllProductCart(savedEmail);
                                                    sendMailPayment(savedName, savedEmail, quantityProduct, total);
                                                    startActivity(new Intent(DetailCartActivity.this, CartActivity.class));
                                                    finish();
                                                } else {
                                                    deleteAllProductCartPayment(savedEmail);
                                                    sendMailPayment(savedName, savedEmail, quantityProduct, total);
                                                    startActivity(new Intent(DetailCartActivity.this, PaymentActivity.class));
                                                    finish();
                                                }

                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Payments> call, Throwable t) {

                                    }
                                });
                            }
                        })
                        .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss(); // Đóng hộp thoại
                            }
                        })
                        .show();
            }
        });
    }

    private void sendMailPayment(String savedName, String savedEmail, int quantityProduct, int total) {

        RetrofitApi retrofitApi = constant.retrofit.create(RetrofitApi.class);
        Call<RecoverEmailResponse> call = retrofitApi.recoverEmail(savedEmail);
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
                        Log.e("email", "Email không tồn tại");
                        return;
                    }
                    if (Objects.equals(error_recover_email, "000")) {
                        Log.d("error_recover_email", email + " " + login_info);
                        String subject = "Food Tech";
                        String body = "Xin chào " + savedName + ",\n" +
                                "\n" +
                                "Chúng tôi xin gửi đến bạn thông tin chi tiết về đơn hàng của bạn:\n" +
                                "\n" +
                                "- Tên khách hàng: " + savedName + "\n" +
                                "- Email: " + savedEmail + "\n" +
                                "- Số lượng sản phẩm: " + quantityProduct + "\n" +
                                "- Tổng tiền: " + total + "\n" +
                                "\n" +
                                "Chúng tôi xác nhận đã nhận thanh toán của bạn và đang tiến hành xử lý đơn hàng. Sản phẩm sẽ được gửi đến địa chỉ đã cung cấp trong thời gian sớm nhất có thể.\n" +
                                "Nếu bạn có bất kỳ câu hỏi hoặc yêu cầu hỗ trợ nào, vui lòng liên hệ với chúng tôi qua địa chỉ email này. Chúng tôi sẵn lòng giúp đỡ bạn.\n" +
                                "Một lần nữa, chúng tôi xin chân thành cảm ơn sự ủng hộ của bạn và hy vọng bạn sẽ hài lòng với sản phẩm đã mua.\n" +
                                "\n" +
                                "Trân trọng,\n" +
                                "TCShop FD";
                        EmailSender.sendEmail(savedEmail, subject, body);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<RecoverEmailResponse> call, Throwable t) {

            }
        });
    }

    private void deleteAllProductCartPayment(String savedEmail) {
        RetrofitApi retrofitApi = constant.retrofit.create(RetrofitApi.class);
        Call<CartDeleteAllResponse> call = retrofitApi.delete_all_cart_payment(savedEmail);
        call.enqueue(new Callback<CartDeleteAllResponse>() {
            @Override
            public void onResponse(Call<CartDeleteAllResponse> call, Response<CartDeleteAllResponse> response) {
                if (response.isSuccessful()) {
                    CartDeleteAllResponse cartDeleteAllResponse = response.body();
                    String error_all_cart_by_product_id = cartDeleteAllResponse.getError_all_cart_by_product_id();
                    if (Objects.equals(error_all_cart_by_product_id, "000")) {
                        String name = "";
                        getTotalCartPayment(name, savedEmail);
                    }
                }
            }

            @Override
            public void onFailure(Call<CartDeleteAllResponse> call, Throwable t) {

            }
        });
    }

    private void deleteAllProductCart(String savedEmail) {
        RetrofitApi retrofitApi = constant.retrofit.create(RetrofitApi.class);
        Call<CartDeleteAllResponse> call = retrofitApi.delete_all_cart(savedEmail);
        call.enqueue(new Callback<CartDeleteAllResponse>() {
            @Override
            public void onResponse(Call<CartDeleteAllResponse> call, Response<CartDeleteAllResponse> response) {
                if (response.isSuccessful()) {
                    CartDeleteAllResponse cartDeleteAllResponse = response.body();
                    String error_all_cart_by_product_id = cartDeleteAllResponse.getError_all_cart_by_product_id();
                    if (Objects.equals(error_all_cart_by_product_id, "000")) {
                        String name = "";
                        getTotalCartPayment(name, savedEmail);
                    }
                }
            }

            @Override
            public void onFailure(Call<CartDeleteAllResponse> call, Throwable t) {

            }
        });
    }

    private void getTotalCartPaymentm(String savedName, String savedEmail) {
        RetrofitApi retrofitApi = constant.retrofit.create(RetrofitApi.class);
        Call<CartByPayment> call = retrofitApi.total_price_and_quantity_cart_payment_by_id(savedEmail);
        call.enqueue(new Callback<CartByPayment>() {
            @Override
            public void onResponse(Call<CartByPayment> call, Response<CartByPayment> response) {
                if (response.isSuccessful()) {
                    CartByPayment cartByPayment = response.body();
                    int total_quantity = cartByPayment.getTotal_quantity();
                    int total_payment = cartByPayment.getTotal_payment();

                    // Hiển thị giá sản phẩm với định dạng ngăn cách hàng nghìn
                    DecimalFormat decimalFormat = new DecimalFormat("#.###");
                    String total_payment_format = decimalFormat.format(total_payment);
                    payment_quantity.setText(String.valueOf(total_quantity));
                    payment_total.setText(total_payment_format + "₫");

                    paymentProduct(savedName, savedEmail, total_quantity, total_payment);
                }
            }

            @Override
            public void onFailure(Call<CartByPayment> call, Throwable t) {

            }
        });
    }

    private void getTotalCartPayment(String savedName, String savedEmail) {
        SharedPreferences saveQuantityProduct = getSharedPreferences("notification_quantity_product", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = saveQuantityProduct.edit();
        RetrofitApi retrofitApi = constant.retrofit.create(RetrofitApi.class);
        Call<CartByPayment> call = retrofitApi.total_price_and_quantity_cart_by_id(savedEmail);
        call.enqueue(new Callback<CartByPayment>() {
            @Override
            public void onResponse(Call<CartByPayment> call, Response<CartByPayment> response) {
                if (response.isSuccessful()) {
                    CartByPayment cartByPayment = response.body();
                    int total_quantity = cartByPayment.getTotal_quantity();
                    int total_payment = cartByPayment.getTotal_payment();

                    // lưu thông tin thông báo product
                    editor.putInt("total_quantity_product", total_quantity);
                    editor.apply();

                    // Hiển thị giá sản phẩm với định dạng ngăn cách hàng nghìn
                    DecimalFormat decimalFormat = new DecimalFormat("#.###");
                    String total_payment_format = decimalFormat.format(total_payment);
                    payment_quantity.setText(String.valueOf(total_quantity));
                    payment_total.setText(total_payment_format + "₫");

                    paymentProduct(savedName, savedEmail, total_quantity, total_payment);
                }
            }

            @Override
            public void onFailure(Call<CartByPayment> call, Throwable t) {

            }
        });
    }

    private void closePm() {
        closePaymentProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailCartActivity.this, CartActivity.class));
                finish();
            }
        });
    }

    private void closePmm() {
        closePaymentProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailCartActivity.this, PaymentActivity.class));
                finish();
            }
        });
    }

    private void getProvices() {
        RetrofitApi retrofitApi = constrantContactForm.retrofitContactForm.create(RetrofitApi.class);
        Call<List<Province>> call = retrofitApi.getProvinces();
        call.enqueue(new Callback<List<Province>>() {
            @Override
            public void onResponse(Call<List<Province>> call, Response<List<Province>> response) {
                if (response.isSuccessful()) {
                    List<Province> provinces = response.body();
                    adapter1.clear();
                    adapter1.addAll(provinces);
                    adapter1.notifyDataSetChanged();

                    // Xử lý sự kiện khi người dùng chọn một mục từ Spinner
                    spinnerProvices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Province selectedProvince = (Province) parent.getItemAtPosition(position);
                            adapter2.clear();
                            adapter3.clear();
                            int provinceCode = selectedProvince.getCode();
                            selectedProvinceName = selectedProvince.getName();
                            getDistricts(provinceCode);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // Xử lý khi không có mục nào được chọn trong Spinner
                        }
                    });
                } else {
                    // Xử lý khi có lỗi trong cuộc gọi API
                }
            }

            @Override
            public void onFailure(Call<List<Province>> call, Throwable t) {
                // Xử lý khi có lỗi trong cuộc gọi API
            }
        });
    }

    private void getDistricts(int provinceCode) {
        RetrofitApi retrofitApi = constrantContactForm.retrofitContactForm.create(RetrofitApi.class);
        Call<City> districtCall = retrofitApi.getDistricts(provinceCode);
        districtCall.enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                City city = response.body();
                adapter2.clear();
                // Hiển thị thông tin districts
                List<District> districts = city.getDistricts();
                for (District district : districts) {
                    adapter2.add(district);
                }
                adapter2.notifyDataSetChanged();

                // Xử lý sự kiện khi người dùng chọn một mục từ Spinner
                spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        District selectedDistrict = (District) parent.getItemAtPosition(position);
                        int districtCode = selectedDistrict.getCode();
                        selectedDistrictName = selectedDistrict.getName();
                        getWard(districtCode);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // Xử lý khi không có mục nào được chọn trong Spinner
                    }
                });
            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {

            }
        });
    }

    private void getWard(int districtCode) {
        RetrofitApi retrofitApi = constrantContactForm.retrofitContactForm.create(RetrofitApi.class);
        Call<District> call = retrofitApi.getWards(districtCode);
        call.enqueue(new Callback<District>() {
            @Override
            public void onResponse(Call<District> call, Response<District> response) {
                District district = response.body();
                adapter3.clear();
                // Hiển thị thông tin districts
                List<Ward> wards = district.getWards();
                for (Ward ward : wards) {
                    adapter3.add(ward);
                }
                adapter3.notifyDataSetChanged();
                // Xử lý sự kiện khi người dùng chọn một mục từ Spinner
                spinnerWard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Ward selectedWard = (Ward) parent.getItemAtPosition(position);
                        selectedWardName = selectedWard.getName();
                        getWard(districtCode);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // Xử lý khi không có mục nào được chọn trong Spinner
                    }
                });
            }

            @Override
            public void onFailure(Call<District> call, Throwable t) {

            }
        });
    }

}
